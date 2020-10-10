package game;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BgPanel extends JPanel{
	Image imgBack2;
	public BgPanel() {
		setLayout(null);
	}
	//public BgPanel() {		
		//setVisible(true);
	//}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		imgBack2 = Toolkit.getDefaultToolkit().getImage("img/image4.jpg");
		//imgBack2 = toolkit.getImage();
		//imgBack2 = imgBack2.getScaledInstance(1000, 700, Image.SCALE_SMOOTH);	
		g.drawImage(imgBack2, 0, 0, this);
	}
}
