package UserForm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.DisplayThread;
import Client.UserInputThread;
import Client.UserMessageProcessor;

public class GameRoomForm implements UserForm{
	
	private JPanel grp;
	
	private volatile static GameRoomForm uniqueInstance; 
	
	private UserMessageProcessor userMessageProcessor;	// message�� json���� ��ȯ�����ִ� Ŭ����
	private DisplayThread displayThread;				// jframe�� �����̳�, �츮�� ���� gameroom panel�� ���⿡ add
	private Socket socket;								// ��� ����
	private UserInputThread userInputThread;			// message�� ������ ������ thread
	
	private GameRoomForm(DisplayThread ds, Socket socket) {
		this.socket = socket;
		userInputThread = UserInputThread.getInstance(socket);
		userMessageProcessor = new UserMessageProcessor();
		this.displayThread = ds;
		
		grp = new JPanel();
		grp.setSize(1446, 824);
		
		JPanel grSketch = new JPanel();
		JPanel[] userPanel = new JPanel[6];
		JPanel exp = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel turn = new JPanel();
		JPanel answer = new JPanel();
		JPanel round = new JPanel();
		JButton b1,b2,b3,b4;
		JTextField chatBar=new JTextField();
		TimeBar timeBar = new TimeBar(120);
		
		grp.setLayout(null);
		
		b1=new JButton("�����غ�");
		b2=new JButton("�����ʴ�");
		b3=new JButton("ģ���߰�");
		b4=new JButton("������");
		
		//1. ����ġ��		
		grSketch.setBounds(280, 15, 880, 600);
		grSketch.setBackground(Color.BLACK);
		grp.add(grSketch);
		
		//2. �����г�		
		for(int i=0; i<6; i++) {
			userPanel[i] = new JPanel();
			userPanel[i].setBackground(Color.BLACK);
		}
		for(int i=0; i<3; i++) {
			userPanel[i].setBounds(15, 15+(205*i), 250, 190);
			grp.add(userPanel[i]);
		}
		for(int i=3; i<6; i++) {
			userPanel[i].setBounds(1175, 15+(205*(i-3)), 250, 190);
			grp.add(userPanel[i]);
		}
		
		//3.����ǥ�ñ�
		round.setBounds(15, 630, 250, 150);
		round.setBackground(Color.blue);
		grp.add(round);
		
		//4.����ǥ�ñ�
		turn.setBounds(280, 630, 250, 105);
		turn.setBackground(Color.pink);
		grp.add(turn);
		
		//5.������ڼ�ǥ�ñ�		
		answer.setBounds(545, 630, 250, 105);
		answer.setBackground(Color.yellow);
		grp.add(answer);
		
		//7.Ÿ�̸�		
		timerPanel.setBounds(810, 630, 350, 70);
		timerPanel.setLayout(null);
		timerPanel.setBackground(Color.gray);
		timerPanel.add(timeBar);
		grp.add(timerPanel);
		
		//6.ä��		
		chatBar.setBounds(810, 710, 350, 30);
		grp.add(chatBar);
				
		//8.����ġ��
		exp.setBounds(280, 750, 880, 30);
		exp.setBackground(Color.black);
		grp.add(exp);
		
		//��ư		
		JPanel p=new JPanel();
		
		/* //ĭ ĭ�� ���� �¿찣�� ���Ʒ�����
		 * //1��ǥ��(ũ��)
		 * p.setLayout(new GridLayout(1,2,5,0));
		 */
		
		p.setLayout(new GridLayout(4,1,0,10));
		p.add(b1);p.add(b2); p.add(b3); p.add(b4);
		  //b1. �����غ�
		  b1.setBackground(Color.white);
		  b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread t = new Thread(timeBar);
				t.start();
			}
		});
		  //b2. �����ʴ�
		  b2.setBackground(Color.white); 
		  //b3. ģ���߰�
		  b3.setBackground(Color.white); 
		  //b4. ������
		  b4.setBackground(Color.white);
		  b4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					displayThread.setBounds(300, 0, 1440, 900);
					displayThread.getCardLayout().show(displayThread.getContentPane(),
							"waitingRoom");
				}
			});
		  
		/* //1��ǥ��(��ġ)
		 * p.setBounds(671, 625, 500, 30);
		 */	  
		p.setBounds(1175, 630, 250, 150);
		grp.add(p);
		
		displayThread.add(grp);
	}
	
	public static GameRoomForm getInstance(DisplayThread ds,Socket socket) {
		if(uniqueInstance == null) {
			synchronized (GameRoomForm.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new GameRoomForm(ds, socket);
				}
			}
		}
		return uniqueInstance;
	}
	
	@Override
	public void display() {
		
	}

	@Override
	public JPanel getJPanel() {
		// TODO Auto-generated method stub
		return grp;
	}
}
