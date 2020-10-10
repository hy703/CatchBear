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
		//���� �����Ű�� ������ ��ü ���� �� ��ŸƮ
		gThread = new GameThread();
		gThread.start(); //run() �޼ҵ� �ڵ�����!!		
		//�����ӿ� Ű���� �Է¿� �����ϴ� keyListner ���
		addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub			
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				//������ Ű�� �������� �˾Ƴ��� 
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
				//����Ű 4�� ����
			}			
			@Override
			public void keyPressed(KeyEvent e) {
				//������ Ű�� �������� �˾Ƴ��� 
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
				//����Ű 4�� ����				
			}
		});
	}//������

	
	class GamePanel extends JPanel { //����ȭ�� �׷��� Panel		


		Image imgBack, imgPlayer, imgEnemy;
		int width, height;//�г� ������ ��������
		int x, y, w, h;//xy : �÷��̾��� �߽� ��ǥ / wh : �̹��� ������;
		int dx = 0, dy = 0;//�÷��̾� �̹����� �̵��ӵ�, �̵�����
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		int score; //����	
		
		public GamePanel() {
			//GUI ���� ���α׷��� ���Ǹ� ���� ������� ��������(Toolkit) ��ü 
			Toolkit toolkit = Toolkit.getDefaultToolkit();		
			imgBack = toolkit.getImage("img/image.jpg");//��� �̹���
			imgPlayer = toolkit.getImage("img/image1.png");//�÷��̾� �̹��� ��ü
			imgEnemy = toolkit.getImage("img/image2.png");//���� �̹��� ��ü 			
		}//������		
		@Override
			protected void paintComponent(Graphics g) {		
			if( width == 0 || height == 0) { //ó�� ȣ��ÿ� ������ �Ⱥ��̴� ���� ����
				width = getWidth();
				height = getHeight();		
				imgBack = imgBack.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				imgPlayer = imgPlayer.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
				x = width/2;//�÷��̾��� ��ǥ ���
				y = height - 1000; // ���� ����
						//height - 100; ���� �ؿ�
				w = 40;
				h = 35;
			}			

					
			g.drawImage(imgBack, 0, 0, this);//��� �׸���			
			for(Enemy t : enemies) {
				g.drawImage(t.img,t.x,t.y+t.h, this);
						//t.x-t.w, t.y+t.h, this);-> ������ ��������
				//g.drawImage(t.img,t.x-t.w, t.y+t.h, this); -> ���� �ö󰡰�
			}				
			g.drawImage(imgPlayer, x - w, y - h, this);//�÷��̾�
			g.setFont(new Font(null, Font.BOLD, 20));//���� ǥ���ϱ�
			g.drawString("Score : " + score,10, 30);
			//������� ����� ���� �����ð����� �ٽ� �׸���(re painting)
		}
		
		void move() { //�÷��̾� �����̱�(��ǥ ����)
			//������ �����̱�
			//�߰��� �迭�� ���� ����� ������ �ִٸ�
			//�� ������ ��Һ��� �Ųٷ� 0�� ��ұ��� ������ ó���ؾ���.
			for(int i = enemies.size()-1; i >= 0; i--) {
				Enemy t = enemies.get(i);
				t.move(score);
				if(t.isDead)  //ArrayList���� ����
					enemies.remove(i);
			}								
			x += dx;
			y += dy;
			//�÷��̾� ��ǥ�� ȭ�� ������ ������ �ʵ���
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
		
			
		//�浹üũ �۾� ��� �޼ҵ�
		void checkCollision() { //�÷��̾�� ������ �浹						
			for(Enemy t : enemies) {
				int left = t.x - t.w;
				int right = t.x + t.w;
				int top = t.y - t.h;
				int bottom = t.y + t.h;
				
				if(x > left && x < right && y > top && y < bottom) {
					t.isDead = true; //�浹����
					score += 5;
				}				
			}			
		}	
		void makeEnemy() { //���� ���� �޼ҵ�
//			if(width == -1000 || height == -1000) { 
//				return;			
//			}
			Random rnd = new Random();//50���� �ѹ��÷� �����
			int n = rnd.nextInt(20);
			if( n == 0 ) {
				enemies.add(new Enemy(imgEnemy, width, height));
			}
		}	
	}

	//���� �ð����� ����ȭ���� ���Ž�Ű�� �۾� �����ϴ� ���� ������ Ŭ����
	class GameThread extends Thread {
		@Override
		public void run() {
			while(true) {
				//���� ��ü ������ ��� �޼ҵ� ȣ��
				panel.makeEnemy();
				panel.move();				
				panel.checkCollision();//�浹 üũ ��� ȣ��				
				panel.repaint();//GamePanel�� ȭ�� ����
				try { //�ʹ� ���� ���Ƽ� õõ�� ������
					sleep(20);
				} catch (InterruptedException e) {
					
				}
			}
		}
	}
}