package UserForm;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Client.UserInputThread;
import Client.UserMessageProcessor;

public class SignUpForm implements UserForm{

	private volatile static SignUpForm uniqueInstance;
	
	private UserMessageProcessor userMessageProcessor;
	private Socket socket;
	private UserInputThread unt;
	
	private JDialog jdialog;
	private JLabel idLabel;
	private JLabel pwdLabel;
	private JLabel pwdOkLabel;
	private JTextField idTF;
	private JPasswordField pwdTF;
	private JPasswordField pwdOkTF;
	private JButton isExist;
	private JButton check;
	private JButton cencel;
	
	private String inputedId;
	private boolean isChecked;
	private String currentId;
	
	private SignUpForm(Socket socket) {
		this.socket = socket;
		unt = UserInputThread.getInstance(socket);
		userMessageProcessor = new UserMessageProcessor();
		
		inputedId = "";
		isChecked = false;
		
		jdialog = new JDialog();
		idLabel = new JLabel("���̵�:");
		pwdLabel = new JLabel("��й�ȣ:");
		pwdOkLabel = new JLabel("��й�ȣ Ȯ��:");
		idTF = new JTextField();
		pwdTF = new JPasswordField();
		pwdOkTF = new JPasswordField();
		isExist = new JButton("�ߺ�üũ");
		check = new JButton("Ȯ��");
		cencel = new JButton("���");
		
		jdialog.setLayout(null);
		jdialog.setModal(true);
		jdialog.setTitle("ȸ������");
		
		jdialog.setSize(350,350);
		idLabel.setBounds(30, 40, 50, 30);
		pwdLabel.setBounds(30, 110, 50, 30);
		pwdOkLabel.setBounds(30, 180, 50, 30);
		idTF.setBounds(100, 40, 100, 30);
		pwdTF.setBounds(100, 110, 100, 30);
		pwdOkTF.setBounds(100, 180, 100, 30);
		isExist.setBounds(220, 40, 100, 30);
		check.setBounds(130, 250, 80, 30);
		cencel.setBounds(220, 250, 80, 30);
		
		jdialog.add(idLabel);
		jdialog.add(pwdLabel);
		jdialog.add(pwdOkLabel);
		jdialog.add(idTF);
		jdialog.add(pwdTF);
		jdialog.add(pwdOkTF);
		jdialog.add(isExist);
		jdialog.add(check);
		jdialog.add(cencel);
		
		isExist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isGoodId = true;
				for(int i = 0; i < idTF.getText().length(); i++) {
					char s = idTF.getText().charAt(i);
					if(!((s >= 'a' && s <= 'z') ||(s >= 'A' && s <= 'Z') || (s >= '0' && s <= '9'))) {
						isGoodId = false; break;
					}
				}
				if(idTF.getText().equals("")) {
					//1. dialog ����
					getDialog("���̵� �Է��ϼ���", 150, 100);
				} else if(isGoodId == false) {
					getDialog("���̵�� ��ҹ��ڿ� ������ �������θ� �����մϴ�.", 350, 100);
				}else {
					currentId = idTF.getText();
					//1. json data�� �����.
					String sendData = "{";
					sendData += userMessageProcessor.getJSONData("method", "1100");
					sendData += "," + userMessageProcessor.getJSONData("id", idTF.getText());
					sendData += "}";
					//2. ������ ������.
					unt.setInputData(sendData);
					Runnable userInputThread = unt;
					Thread userThread = new Thread(userInputThread);
					userThread.start();
				}
				
			}
		});
		
		cencel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setDialLogInvisible();
			}
		});
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("current ID : " + idTF.getText() + ", inputedID : " + inputedId + ", isChecked : " + isChecked);
				if(idTF.getText().equals("")) {
					getDialog("���̵� �Է��ϼ���",250,100);
				}else if(inputedId.equals("") || !(inputedId.equals(idTF.getText()) && isChecked == true)) {
					getDialog("���̵� �ߺ�üũ�� �ϼ���", 250, 100);
				}else if(String.valueOf(pwdTF.getPassword()).equals("") || String.valueOf(pwdOkTF.getPassword()).equals("")) {
					getDialog("�н����带 �Է��ϼ���!", 250, 100);
				}else if(!String.valueOf(pwdTF.getPassword()).equals(String.valueOf(pwdOkTF.getPassword()))){
					getDialog("�н����尡 �ٸ��ϴ�", 250, 100);
				}else {
					//1. json data�� �����.
					String sendData = "{";
					sendData += userMessageProcessor.getJSONData("method", "1200");
					sendData += "," + userMessageProcessor.getJSONData("id", idTF.getText());
					sendData += "," + userMessageProcessor.getJSONData("pwd", String.valueOf(pwdTF.getPassword()));
					sendData += "}";
					//2. ������ ������.
					unt.setInputData(sendData);
					Runnable userInputThread = unt;
					Thread userThread = new Thread(userInputThread);
					userThread.start();
				}
			}
		});
	}
	public static SignUpForm getInstance(Socket socket) {
		if(uniqueInstance == null) {
			synchronized (SignUpForm.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new SignUpForm(socket);
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
		return null;
	}
	public void setDialLogVisible() {
		jdialog.setVisible(true);
	}
	public void setDialLogInvisible() {
		jdialog.setVisible(false);
	}
	public void getDialog(String s,int width, int height) {
		JDialog jd = new JDialog();
		jd.setLayout(new FlowLayout());
		jd.setModal(true);
		
		JLabel jl = new JLabel(s);
		JButton jb = new JButton("Ȯ��");
		
		jd.add(jl);
		jd.add(jb);
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jd.setVisible(false);
			}
		});
		
		jd.setSize(width, height);
		jd.setVisible(true);
	}
	public void setIsCheck(boolean value) {
		isChecked = value;
		if(value == true) {
			inputedId = currentId;
			getDialog("����� �� �ִ� ���̵� �Դϴ�!", 250, 100);
		} else {
			getDialog("����� �� ���� ���̵� �Դϴ�!", 250, 100);
		}
	}
	
	/*
	 * private JTextField idTF; private JPasswordField pwdTF; private JPasswordField
	 * pwdOkTF;
	 */
	public void dialogClear() {
		idTF.setText(""); pwdTF.setText(""); pwdOkTF.setText("");
	}
}
