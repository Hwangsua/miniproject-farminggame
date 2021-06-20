package miniproject_game;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**  
 * 본 프로그램의 StartFrame<br/>
 * 
 * 사용자의 마우스 입력에 따라 클릭소리 메소드를 호출하는 {@link MouseListener}를 포함한다.<br/>
 * 배경음악이 무한으로 재생되는 Thread를 포함한다.
 * 
 * @author EunKyungHwang
 * @author SuaHwang
 * 
 */

public class StartFrame extends JFrame implements MouseListener, Runnable {

	private MemberLogin loginPanel;
	private JButton loginBtn;
	private JButton signUpBtn;
	private Thread bgm;

	/**
	 * StartFrame 의 생성자<br/>
	 * MemberLogin panel 생성 및 add().
	 */
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
		this.setLocationRelativeTo(null);
		this.setResizable(false);
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
		while (true) {
			try {
				SoundsClip.play(SoundsClip.BACKGROUND_MUSIC);
				Thread.sleep(112000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}