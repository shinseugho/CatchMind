package UserForm;

import java.awt.Color;

import javax.swing.JProgressBar;

public class TimeBar extends JProgressBar implements Runnable{
	
	int timelimit;
	
	public TimeBar(int timelimit) {
		this.timelimit = timelimit;
		setMinimum(0);
		setMaximum(timelimit);
		setSize(350,70);
		setBackground(Color.gray);
		setForeground(Color.gray);
		setValue(timelimit);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		setForeground(Color.green);
		while(getValue()>0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setValue(getValue()-1);
			if(getValue()==30) setForeground(Color.red);
		}
	}
}