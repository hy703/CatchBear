package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DBConn {
	// �̱��� ���� ����
	private static DBConn instance = new DBConn();

	public static DBConn getInstance() {
		return instance;
	}

	DBConn() {
	};

	// DB ����
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ���� ����");
		} catch (ClassNotFoundException e1) {
			System.out.println("����̹� ���� ����");
		} // ����̹� ����

		String url = "jdbc:oracle:thin:@net.yjc.ac.kr:1521:orcl";
		String id = "s1501299";
		String pw = "p1501299";

		try {
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("�����ͺ��̽� ���� ����.");
		} catch (SQLException e) {
			System.out.println("������ ���̽� ���ῡ �����Ͽ����ϴ�..");
		}
		return conn;
	}

	// �Էµ� ����� DB���� ������ �����ӿ� ����մϴ�.(���������� ������ �ؼ�)
	public ArrayList selectRank() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		RankBean rbean;
		ArrayList<RankBean> ra = new ArrayList<RankBean>();
		try {
			conn = getConnection();
			// users���̺� money�� �����ؼ� ������ ������ ���̺��� �� ��ȣ�� 5������ �ͱ��� �����ش�.
			String sql = "select * from (select * from bear order by score DESC) where rownum <= 5";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rbean = new RankBean();
				rbean.setScore(rs.getInt("score"));
				ra.add(rbean);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "����");
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

	// �¸� ���
	public void insertScore(int score) {
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         conn = getConnection();
         String sql = "insert into bear values(?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, score);
         pstmt.executeUpdate();
         JOptionPane.showMessageDialog(null, "����� �Ϸ��Ͽ����ϴ�.");
         System.out.println("��� �Ϸ�");
      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "��Ͽ� �����Ͽ����ϴ�.");
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
