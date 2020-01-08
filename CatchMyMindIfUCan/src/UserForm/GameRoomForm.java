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
	
	private UserMessageProcessor userMessageProcessor;	// message를 json으로 변환시켜주는 클래스
	private DisplayThread displayThread;				// jframe의 컨테이너, 우리가 만든 gameroom panel을 여기에 add
	private Socket socket;								// 통신 소켓
	private UserInputThread userInputThread;			// message를 서버로 보내는 thread
	
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
		
		b1=new JButton("게임준비");
		b2=new JButton("게임초대");
		b3=new JButton("친구추가");
		b4=new JButton("나가기");
		
		//1. 스케치북		
		grSketch.setBounds(280, 15, 880, 600);
		grSketch.setBackground(Color.BLACK);
		grp.add(grSketch);
		
		//2. 유저패널		
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
		
		//3.라운드표시기
		round.setBounds(15, 630, 250, 150);
		round.setBackground(Color.blue);
		grp.add(round);
		
		//4.차례표시기
		turn.setBounds(280, 630, 250, 105);
		turn.setBackground(Color.pink);
		grp.add(turn);
		
		//5.정답글자수표시기		
		answer.setBounds(545, 630, 250, 105);
		answer.setBackground(Color.yellow);
		grp.add(answer);
		
		//7.타이머		
		timerPanel.setBounds(810, 630, 350, 70);
		timerPanel.setLayout(null);
		timerPanel.setBackground(Color.gray);
		timerPanel.add(timeBar);
		grp.add(timerPanel);
		
		//6.채팅		
		chatBar.setBounds(810, 710, 350, 30);
		grp.add(chatBar);
				
		//8.경험치바
		exp.setBounds(280, 750, 880, 30);
		exp.setBackground(Color.black);
		grp.add(exp);
		
		//버튼		
		JPanel p=new JPanel();
		
		/* //칸 칸당 갯수 좌우간격 위아래간격
		 * //1줄표시(크기)
		 * p.setLayout(new GridLayout(1,2,5,0));
		 */
		
		p.setLayout(new GridLayout(4,1,0,10));
		p.add(b1);p.add(b2); p.add(b3); p.add(b4);
		  //b1. 게임준비
		  b1.setBackground(Color.white);
		  b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread t = new Thread(timeBar);
				t.start();
			}
		});
		  //b2. 게임초대
		  b2.setBackground(Color.white); 
		  //b3. 친구추가
		  b3.setBackground(Color.white); 
		  //b4. 나가기
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
		  
		/* //1줄표시(위치)
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
