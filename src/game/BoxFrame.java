package game;
import java.awt.BorderLayout;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.Image;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import java.util.ArrayList;

import java.util.Random;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoxFrame extends JFrame {
	GamePanel panel;
	GameThread gThread;
	public BoxFrame() {		
		setTitle("B E A R");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 1000, 700);
		setResizable(false);		
		panel = new GamePanel();
		add(panel,BorderLayout.CENTER);	
		setVisible(true);
		//게임 진행시키는 스레드 객체 생성 및 스타트
		gThread = new GameThread();
		gThread.start(); //run() 메소드 자동실행!!		
		//프레임에 키보드 입력에 반응하는 keyListner 등록
		addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub			
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				//눌러진 키가 무엇인지 알아내기 
				int keyCode = e.getKeyCode();
				switch( keyCode ) {
				case KeyEvent.VK_LEFT:
					panel.dx = 0; 
					break;
				case KeyEvent.VK_RIGHT:
					panel.dx = 0;
					break;
				case KeyEvent.VK_UP:
					panel.dy = 0;
					break;
				case KeyEvent.VK_DOWN:
					panel.dy = 0;
					break;				
				}
				//방향키 4개 구분
			}			
			@Override
			public void keyPressed(KeyEvent e) {
				//눌러진 키가 무엇인지 알아내기 
				int keyCode = e.getKeyCode();
				switch( keyCode ) {
				case KeyEvent.VK_LEFT:
					panel.dx = -8; 
					break;
				case KeyEvent.VK_RIGHT:
					panel.dx = 8;
					break;
				case KeyEvent.VK_UP:
					panel.dy = -8;
					break;
				case KeyEvent.VK_DOWN:
					panel.dy = 8;
					break;				
				}
				//방향키 4개 구분				
			}
		});
	}//생성자

	
	class GamePanel extends JPanel { //게임화면 그려낼 Panel		


		Image imgBack, imgPlayer, imgEnemy;
		int width, height;//패널 사이즈 가져오기
		int x, y, w, h;//xy : 플레이어의 중심 좌표 / wh : 이미지 절반폭;
		int dx = 0, dy = 0;//플레이어 이미지의 이동속도, 이동방향
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		int score; //점수	
		
		public GamePanel() {
			//GUI 관련 프로그램의 편의를 위해 만들어진 도구상자(Toolkit) 객체 
			Toolkit toolkit = Toolkit.getDefaultToolkit();		
			imgBack = toolkit.getImage("img/image.jpg");//배경 이미지
			imgPlayer = toolkit.getImage("img/image1.png");//플레이어 이미지 객체
			imgEnemy = toolkit.getImage("img/image2.png");//적군 이미지 객체 			
		}//생성자		
		@Override
			protected void paintComponent(Graphics g) {		
			if( width == 0 || height == 0) { //처음 호출시엔 느려서 안보이다 이후 보임
				width = getWidth();
				height = getHeight();		
				imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				imgPlayer = imgPlayer.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
				x = width/2;//플레이어의 좌표 계산
				y = height - 1000; // 곰이 위에
						//height - 100; 곰이 밑에
				w = 40;
				h = 35;
			}			

					
			g.drawImage(imgBack, 0, 0, this);//배경 그리기			
			for(Enemy t : enemies) {
				g.drawImage(t.img,t.x,t.y+t.h, this);
						//t.x-t.w, t.y+t.h, this);-> 밑으로 내려가게
				//g.drawImage(t.img,t.x-t.w, t.y+t.h, this); -> 위로 올라가게
			}				
			g.drawImage(imgPlayer, x - w, y - h, this);//플레이어
			g.setFont(new Font(null, Font.BOLD, 20));//점수 표시하기
			g.drawString("Score : " + score,10, 30);
			//여러장면 만들기 위해 일정시간마다 다시 그리기(re painting)
		}
		
		void move() { //플레이어 움직이기(좌표 변경)
			//적군들 움직이기
			//중간에 배열의 개수 변경될 여지가 있다면
			//맨 마지막 요소부터 거꾸로 0번 요소까지 역으로 처리해야함.
			for(int i = enemies.size()-1; i >= 0; i--) {
				Enemy t = enemies.get(i);
				t.move(score);
				if(t.isDead)  //ArrayList에서 제거
					enemies.remove(i);
			}								
			x += dx;
			y += dy;
			//플레이어 좌표가 화면 밖으로 나가지 않도록
			if(x < w) {
				x = w;
			}
			if(x > width - w) {
				x = width - w;
			}
			if(y < h) { 
				y = h;
			}
			if(y > height - h) {			
				y = height - h;
			}

		}
		
			
		//충돌체크 작업 계산 메소드
		void checkCollision() { //플레이어와 적군의 충돌						
			for(Enemy t : enemies) {
				int left = t.x - t.w;
				int right = t.x + t.w;
				int top = t.y - t.h;
				int bottom = t.y + t.h;
				
				if(x > left && x < right && y > top && y < bottom) {
					t.isDead = true; //충돌했음
					score += 5;
				}				
			}			
		}	
		void makeEnemy() { //적군 생성 메소드
//			if(width == -1000 || height == -1000) { 
//				return;			
//			}
			Random rnd = new Random();//50번에 한번꼴로 만들기
			int n = rnd.nextInt(20);
			if( n == 0 ) {
				enemies.add(new Enemy(imgEnemy, width, height));
			}
		}	
	}

	//일정 시간마다 개엠화면을 갱신시키는 작업 수행하는 별도 스레드 클래스
	class GameThread extends Thread {
		@Override
		public void run() {
			while(true) {
				//적군 객체 만들어내는 기능 메소드 호출
				panel.makeEnemy();
				panel.move();				
				panel.checkCollision();//충돌 체크 기능 호출				
				panel.repaint();//GamePanel의 화면 갱신
				try { //너무 빨리 돌아서 천천히 돌도록
					sleep(20);
				} catch (InterruptedException e) {
					
				}
			}
		}
	}
}