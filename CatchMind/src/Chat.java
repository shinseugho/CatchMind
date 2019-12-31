import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;

public class Chat extends JTextField{
	JTextField chatBar;
	
	Chat(){
		chatBar = new JTextField();
	}
	
	void printChat(int userNum, String message) {
		switch(userNum) {
		case 0:
			GameRoom.userChat[0].setBounds(250, 125+userNum*250, 15+message.length()*11, 50);
			break;
		case 1:
			GameRoom.userChat[1].setBounds(250, 125+userNum*250, 15+message.length()*11, 50);
			break;
		case 2:
			GameRoom.userChat[2].setBounds(250, 125+userNum*250, 15+message.length()*11, 50);
			break;
		case 3:
			GameRoom.userChat[3].setBounds(1190-(15+message.length()*11), 125+(userNum-3)*250, 15+message.length()*11, 50);
			break;
		case 4:
			GameRoom.userChat[4].setBounds(1190-(15+message.length()*11), 125+(userNum-3)*250, 15+message.length()*11, 50);
			break;
		case 5:
			GameRoom.userChat[5].setBounds(1190-(15+message.length()*11), 125+(userNum-3)*250, 15+message.length()*11, 50);
			break;
		}
		GameRoom.userChat[userNum].setText(message);
	}
}
