package game;

import java.awt.Image;

import java.util.Random;

import javax.swing.JOptionPane;



public class Enemy {

	DBConn db;

	Image img; //이미지 참조변수

	int x, y; //이미지 중심 좌표
	int w, h; //이미지 절반폭, 절반높이	
	int dy; //적군의 변화량	
	int width, height; //화면(panel)의 사이즈
	//본인 객체가 죽었는지 여부!
	public boolean isDead = false;
	
	public Enemy(Image imgEnemy, int width, int height) {
		this.width = width;
		this.height = height;
		//멤버변수 값 초기화
		img = imgEnemy.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		w = 32; //이미지 절반넓이
		h = 32;

		Random rnd = new Random();
		x = rnd.nextInt(width - w * 2) + w; //w ~ width - w
		//x = rnd.nextInt( width + 10 ) + w;
		y = 550;
				//-h;		
		dy = rnd.nextInt(2)+1;//떨어지는 속도 랜덤하게  올라가는지 내려가는지 해주는것

	}

	

	void move(int score) { //Enemy의 움직이는 기능 메소드
		y -= dy; // += 내려가게 -= 올라가게
		//만약 화면 밖으로 나가버리면 게임끝
		if( y < -50){
				//> height + h ) { //ArrayList에서 제거
			isDead = true; //죽음 표식!
			while(isDead) {
				db = DBConn.getInstance();
				db.getConnection();
				System.out.println(score);
				db.insertScore(score);
				
				
				JOptionPane.showMessageDialog(null, "게임끝!", 
						"종료", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				
//			--------------------다시시작이 되긴하지만 오류가 나서 아예 없애기로 결정
//			int result= JOptionPane.showConfirmDialog(null, "게임끝!", 
//					"종료", JOptionPane.INFORMATION_MESSAGE);
//			if(result==0) {
//				//BoxFrame game = new BoxFrame();
//				//box b = new box(); 다시 시작이 되긴함
//				try {
//				//	b.wait(); 다시 시작이 되긴함
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