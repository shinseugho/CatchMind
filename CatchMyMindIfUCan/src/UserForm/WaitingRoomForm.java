package UserForm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Client.DisplayThread;

public class WaitingRoomForm implements UserForm{

	private volatile static WaitingRoomForm uniqueInstance;
	private JPanel jpanel;
	private DisplayThread displayThread;
	
	private WaitingRoomForm(DisplayThread dt) {
		this.displayThread = dt;
		
		jpanel = new JPanel();
		jpanel.setBackground(Color.lightGray);
		
		JButton btn = new JButton("로그인으로");
		jpanel.add(btn);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayThread.getCardLayout().show(
						displayThread.getContentPane(), "login");
				
			}
		});
		
		JButton btn2 = new JButton("방들어가기");
		jpanel.add(btn2);
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				displayThread.setBounds(300, 0, 1446, 824);
				displayThread.getCardLayout().show(displayThread.getContentPane(),
						"gameRoom");
			}
		});
		
	}
	
	public static WaitingRoomForm getInstance(DisplayThread dt) {
		if(uniqueInstance == null) {
			synchronized (WaitingRoomForm.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new WaitingRoomForm(dt);
				}
			}
		}
		return uniqueInstance;
	}
	@Override
	public void display() {
		
	}
	public JPanel getJPanel() {
		return jpanel;
	}
}
