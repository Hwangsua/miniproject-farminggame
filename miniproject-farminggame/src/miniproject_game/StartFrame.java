
package miniproject_game;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument.Content;

/**
 * @author SuaHwang
 * @author EunkyungHwang
 */
public class StartFrame extends JFrame implements MouseListener, Runnable{

	private MemberLogin loginPanel;
	private JButton loginBtn;
	private JButton signUpBtn;
	private Thread bgm;
	StartFrame() {	
		
		loginPanel = new MemberLogin();
		loginBtn = new JButton();
		signUpBtn = new JButton();

		loginBtn.setBounds(517 + 80, 293 + 220, 80, 30);
		loginBtn.addMouseListener(this);
		loginBtn.setBorderPainted(false);
		loginBtn.setContentAreaFilled(false);
		loginBtn.setFocusPainted(false);

		signUpBtn.setBounds(517 + 80, 293 + 274, 80, 30);
		signUpBtn.addMouseListener(this);
		signUpBtn.setBorderPainted(false);
		signUpBtn.setContentAreaFilled(false);
		signUpBtn.setFocusPainted(false);

		this.setBounds(0, 0, 1280, 720);
		this.setLocationRelativeTo(null);// 창이 가운데 나오게
		this.setResizable(false);// 창의 크기를 변경하지 못하게
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);

		this.add(loginPanel);
		this.add(loginBtn);
		this.add(signUpBtn);
		this.setVisible(true);
		
		bgm = new Thread(this);
		bgm.setDaemon(true);
		bgm.start();

	}

	
	public static void main(String[] args) {
		new StartFrame();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		SoundsClip.play(SoundsClip.CLICK_SOUND);
		if (e.getSource() == loginBtn) {
			
			if (loginPanel.checkLogin()) {
				new MainFrame(loginPanel.getUserID());
				this.setVisible(false);
				this.dispose();
			} else {
				FarmerMessage.getLoginFailMsg();
			}
		}

		if (e.getSource() == signUpBtn) {
			new MemberSignUp();

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void run() {
		while(true) {
			try {
				SoundsClip.play(SoundsClip.BACKGROUND_MUSIC);
				Thread.sleep(112000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}