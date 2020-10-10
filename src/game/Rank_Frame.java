package game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Rank_Frame extends JFrame {
   private JButton ExitBtn; // 나가기 버튼
   private ArrayList<RankBean> ra = new ArrayList<RankBean>();
   private JLabel rank; // 순위를 넣을 라벨


   public Rank_Frame() {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE); //순위표의 x표시를 눌러도 모든 프레임이 꺼지지 않습니다.

      setSize(700, 500);
      setLocationRelativeTo(null); // 화면 정중앙 코드

      // 전체를 담는 큰 패널
      JPanel panel4 = new JPanel();
      panel4.setLayout(new GridLayout(3, 0));

      // 맨위에 전체기록 라벨을 넣는 패널
      JPanel panel1 = new JPanel();
      panel1.setLayout(new FlowLayout());
      JLabel recLabel = new JLabel("순 위 표");
      panel1.add(recLabel);

      // 순위표를 보여주는 패널
      JPanel panel2 = new JPanel();
      panel2.setLayout(new GridLayout(5, 0));

      DBConn db = DBConn.getInstance();
      ra = db.selectRank(); // ArrayList에 DB에서 가져온 정보를 저장합니다.

      //저장한 정보들을 Label에 넣어서 출력합니다.
      for (int i = 0; i < ra.size(); i++) {
          rank = new JLabel(
                i + 1 + "등  -> " + ra.get(i).getScore());
          panel2.add(rank);
       }

      // 나가기 버튼을 넣는 패널
      JPanel panel3 = new JPanel();
      ExitBtn = new JButton("나가기");
      panel3.setLayout(null);
      ExitBtn.setBounds(310, 100, 80, 25);
      panel3.add(ExitBtn);

      panel4.add(panel1);
      panel4.add(panel2);
      panel4.add(panel3);
      this.add(panel4);

      setVisible(true);

      ExitBtn.addActionListener(new ActionListener() { // 나가기 버튼 리스너 설정
         @Override
         public void actionPerformed(ActionEvent e) {

            dispose();
         }
      });
   }
}