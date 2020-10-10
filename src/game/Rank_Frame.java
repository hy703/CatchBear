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
   private JButton ExitBtn; // ������ ��ư
   private ArrayList<RankBean> ra = new ArrayList<RankBean>();
   private JLabel rank; // ������ ���� ��


   public Rank_Frame() {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE); //����ǥ�� xǥ�ø� ������ ��� �������� ������ �ʽ��ϴ�.

      setSize(700, 500);
      setLocationRelativeTo(null); // ȭ�� ���߾� �ڵ�

      // ��ü�� ��� ū �г�
      JPanel panel4 = new JPanel();
      panel4.setLayout(new GridLayout(3, 0));

      // ������ ��ü��� ���� �ִ� �г�
      JPanel panel1 = new JPanel();
      panel1.setLayout(new FlowLayout());
      JLabel recLabel = new JLabel("�� �� ǥ");
      panel1.add(recLabel);

      // ����ǥ�� �����ִ� �г�
      JPanel panel2 = new JPanel();
      panel2.setLayout(new GridLayout(5, 0));

      DBConn db = DBConn.getInstance();
      ra = db.selectRank(); // ArrayList�� DB���� ������ ������ �����մϴ�.

      //������ �������� Label�� �־ ����մϴ�.
      for (int i = 0; i < ra.size(); i++) {
          rank = new JLabel(
                i + 1 + "��  -> " + ra.get(i).getScore());
          panel2.add(rank);
       }

      // ������ ��ư�� �ִ� �г�
      JPanel panel3 = new JPanel();
      ExitBtn = new JButton("������");
      panel3.setLayout(null);
      ExitBtn.setBounds(310, 100, 80, 25);
      panel3.add(ExitBtn);

      panel4.add(panel1);
      panel4.add(panel2);
      panel4.add(panel3);
      this.add(panel4);

      setVisible(true);

      ExitBtn.addActionListener(new ActionListener() { // ������ ��ư ������ ����
         @Override
         public void actionPerformed(ActionEvent e) {

            dispose();
         }
      });
   }
}