package game;

import java.awt.Image;

import java.util.Random;

import javax.swing.JOptionPane;



public class Enemy {

	DBConn db;

	Image img; //�̹��� ��������

	int x, y; //�̹��� �߽� ��ǥ
	int w, h; //�̹��� ������, ���ݳ���	
	int dy; //������ ��ȭ��	
	int width, height; //ȭ��(panel)�� ������
	//���� ��ü�� �׾����� ����!
	public boolean isDead = false;
	
	public Enemy(Image imgEnemy, int width, int height) {
		this.width = width;
		this.height = height;
		//������� �� �ʱ�ȭ
		img = imgEnemy.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		w = 32; //�̹��� ���ݳ���
		h = 32;

		Random rnd = new Random();
		x = rnd.nextInt(width - w * 2) + w; //w ~ width - w
		//x = rnd.nextInt( width + 10 ) + w;
		y = 550;
				//-h;		
		dy = rnd.nextInt(2)+1;//�������� �ӵ� �����ϰ�  �ö󰡴��� ���������� ���ִ°�

	}

	

	void move(int score) { //Enemy�� �����̴� ��� �޼ҵ�
		y -= dy; // += �������� -= �ö󰡰�
		//���� ȭ�� ������ ���������� ���ӳ�
		if( y < -50){
				//> height + h ) { //ArrayList���� ����
			isDead = true; //���� ǥ��!
			while(isDead) {
				db = DBConn.getInstance();
				db.getConnection();
				System.out.println(score);
				db.insertScore(score);
				
				
				JOptionPane.showMessageDialog(null, "���ӳ�!", 
						"����", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				
//			--------------------�ٽý����� �Ǳ������� ������ ���� �ƿ� ���ֱ�� ����
//			int result= JOptionPane.showConfirmDialog(null, "���ӳ�!", 
//					"����", JOptionPane.INFORMATION_MESSAGE);
//			if(result==0) {
//				//BoxFrame game = new BoxFrame();
//				//box b = new box(); �ٽ� ������ �Ǳ���
//				try {
//				//	b.wait(); �ٽ� ������ �Ǳ���
//					System.exit(0);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}else if(result==1) {
//				System.exit(0);
//			}else if(result==-1) {
//				System.exit(0);
//				}
			}
		}
	}
}