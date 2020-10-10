package game;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.BoxFrame.GamePanel;



public class box extends JFrame{
	Image imgBack2;
	
	public box() {
		
		setTitle("B E A R");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 1000, 700);
		setResizable(false);		
//		BgPanel p = new BgPanel();
//		JButton bt = new JButton("Start");
		JButton button = new JButton("");
		button.setBounds(70,520, 150, 40);
		button.setBorderPainted(false);
	    button.setContentAreaFilled(false);
	    
	    JButton button2 = new JButton("");
	    button2.setBounds(70,595, 150, 40);
		button2.setBorderPainted(false);
	    button2.setContentAreaFilled(false);
	    
		BackPanel pp = new BackPanel();
		pp.add(button);
		pp.add(button2);
		add(pp);
//		this.add(bt);
		
		setVisible(true);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BoxFrame();
				
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Rank_Frame();
			}
		});
	}
	
}
