package Client;

import java.io.IOException;
import java.net.Socket;

public class Client {
	
	final static int portNum = 16789;
	public static void main(String[] args) {
		try {
			Socket socket;
			socket = new Socket("211.238.142.202",portNum);
			//socket = new Socket("localhost", portNum);
			
			//Runnable userInputThread = new UserInputThread(socket);
			//Runnable userInputThread = UserInputThread.getInstance(socket);
			//Thread userThread = new Thread(userInputThread);
			//Runnable userServerInputThread = new UserServerInputThread(socket);
			Runnable userServerInputThread = UserServerInputThread.getInstance(socket);
			Thread serverThread = new Thread(userServerInputThread);
			Runnable displayThread = DisplayThread.getInstance(socket);
			Thread display = new Thread(displayThread);
			//userThread.start();
			serverThread.start();
			display.start();
			
		}catch(IOException e) {
			System.out.println("in Client -> error : socket");
		}
	}

}
