package UserForm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
	private UserInputThread unt;						// message�� ������ ������ thread
	
	private GameRoomForm(DisplayThread ds, Socket socket) {
		this.socket = socket;
		unt = UserInputThread.getInstance(socket);
		userMessageProcessor = new UserMessageProcessor();
		this.displayThread = ds;
		
		grp = new JPanel();
		grp.setSize(1446, 824);
		
		JPanel grSketch = new JPanel();
		JPanel[] userPanel = new JPanel[6];
		JPanel exp = new JPanel();
		JPanel timer = new JPanel();
		JPanel turn = new JPanel();
		JPanel answer = new JPanel();
		JPanel round = new JPanel();
		//JButton b1,b2,b3,b4;
		JButton[] b = new JButton[4];
		JPanel p = new JPanel();
		Label[] label = new Label[24];	
		JTextField tf;
		JProgressBar jb;
		TimeBar timeBar = new TimeBar(120);
		
		grp.setLayout(null);
		
		tf=new JTextField();
		
		for(int i=0; i<1; i++) {
			b[i] = new JButton("�����غ�");
			b[i+1] = new JButton("�����ʴ�");
			b[i+2] = new JButton("ģ���߰�");
			b[i+3] = new JButton("������");
		}
		/*
		 * b1=new JButton("�����غ�"); 
		 * b2=new JButton("�����ʴ�"); 
		 * b3=new JButton("ģ���߰�");
		 * b4=new JButton("������");
		 */
		
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
			userPanel[i].setLayout(null);
			userPanel[i].setBounds(15, 15+(205*i), 250, 190);
			grp.add(userPanel[i]);
		}
		for(int i=3; i<6; i++) {
			userPanel[i].setLayout(null);
			userPanel[i].setBounds(1175, 15+(205*(i-3)), 250, 190);
			grp.add(userPanel[i]);
		}
		
		//3.����ǥ�ñ�
		timer.setBounds(15, 630, 250, 150);
		timer.setBackground(Color.gray);
		grp.add(timer);
		
		//4.����ǥ�ñ�
		turn.setBounds(280, 630, 250, 105);
		turn.setBackground(Color.pink);
		grp.add(turn);
		
		//5.������ڼ�ǥ�ñ�		
		answer.setBounds(545, 630, 250, 105);
		answer.setBackground(Color.yellow);
		grp.add(answer);
		
		//7.Ÿ�̸�		
		round.setBounds(810, 630, 350, 70);
		round.setLayout(null);
		round.setBackground(Color.blue);
		round.add(timeBar);
		grp.add(round);
		
		//6.ä��		
		tf.setBounds(810, 710, 350, 30);
		grp.add(tf);
				
		//8.����ġ��
		/*
		 * exp.setBounds(280, 750, 880, 30); 
		 * exp.setBackground(Color.black);
		 * grp.add(exp);
		 */
		jb = new JProgressBar(0,100);
		jb.setBounds(280, 750, 880, 30);
		jb.setValue(0);
		jb.setStringPainted(true);
		grp.add(jb);
		
		
		
		//��ư					
		p.setLayout(new GridLayout(4,1,0,10));
		/*
		 * p.add(b1);p.add(b2); p.add(b3); p.add(b4); 
		 * //b1. �����غ�
		 * b1.setBackground(Color.white); 
		 * //b2. �����ʴ� 
		 * b2.setBackground(Color.white);
		 * //b3. ģ���߰� 
		 * b3.setBackground(Color.white); 
		 * //b4. ������
		 * b4.setBackground(Color.white);
		 */
		p.setBounds(1175, 630, 250, 150);
		grp.add(p);
		
		for(int i=0; i<1; i++) {
			b[i] = new JButton("�����غ�");
			b[i+1] = new JButton("�����ʴ�");
			b[i+2] = new JButton("ģ���߰�");
			b[i+3] = new JButton("������");
		}
		
		for (int i=0;i<1;i++)
		{  
			p.add(b[i]);      b[i].setBackground(Color.white);		
			p.add(b[i+1]);    b[i+1].setBackground(Color.white);
			p.add(b[i+2]);    b[i+2].setBackground(Color.white);
			p.add(b[i+3]);    b[i+3].setBackground(Color.white);
		}
		
		// ��
				for (int i = 0; i < 6; i++) {
					label[i] = new Label("�ƹ�Ÿ");			
					label[i].setBounds(10, 15, 120, 160);
					label[i].setBackground(Color.white);
					userPanel[i].add(label[i]);
					
					label[6+i] = new Label("Id");			
					label[6+i].setBounds(135, 15, 105, 50);
					label[6+i].setBackground(Color.white);
					userPanel[i].add(label[6+i]);
					
					label[12+i] = new Label("Level");			
					label[12+i].setBounds(135, 70, 105, 50);
					label[12+i].setBackground(Color.white);
					userPanel[i].add(label[12+i]);
					
					label[18+i] = new Label("�����");			
					label[18+i].setBounds(135, 125, 105, 50);
					label[18+i].setBackground(Color.white);
					userPanel[i].add(label[18+i]);
				}
				
		//b[0]. �����غ� �׼�
		b[0].setBackground(Color.white);
		b[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread t = new Thread(timeBar);
				t.start();
			}
		});
		
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
