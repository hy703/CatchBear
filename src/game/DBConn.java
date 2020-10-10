package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DBConn {
	// 싱글톤 생성 시작
	private static DBConn instance = new DBConn();

	public static DBConn getInstance() {
		return instance;
	}

	DBConn() {
	};

	// DB 연결
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 적재 성공");
		} catch (ClassNotFoundException e1) {
			System.out.println("드라이버 적재 실패");
		} // 드라이버 적재

		String url = "jdbc:oracle:thin:@net.yjc.ac.kr:1521:orcl";
		String id = "s1501299";
		String pw = "p1501299";

		try {
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("데이터베이스 연결 성공.");
		} catch (SQLException e) {
			System.out.println("데이터 베이스 연결에 실패하였습니다..");
		}
		return conn;
	}

	// 입력된 기록을 DB에서 가져와 프레임에 출력합니다.(쿼리문으로 정렬을 해서)
	public ArrayList selectRank() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		RankBean rbean;
		ArrayList<RankBean> ra = new ArrayList<RankBean>();
		try {
			conn = getConnection();
			// users테이블에 money를 정렬해서 정렬한 가상의 테이블의 행 번호가 5까지인 것까지 보여준다.
			String sql = "select * from (select * from bear order by score DESC) where rownum <= 5";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rbean = new RankBean();
				rbean.setScore(rs.getInt("score"));
				ra.add(rbean);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "실패");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return ra;
	}

	// 승리 기록
	public void insertScore(int score) {
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         conn = getConnection();
         String sql = "insert into bear values(?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, score);
         pstmt.executeUpdate();
         JOptionPane.showMessageDialog(null, "기록을 완료하였습니다.");
         System.out.println("기록 완료");
      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "기록에 실패하였습니다.");
      } finally {
         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (SQLException e) {
            }
         }
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException e) {
            }
         }
      }
   }
}
