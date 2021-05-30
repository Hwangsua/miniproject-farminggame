package miniproject_game;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
/**
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 *
 */
public class MemberLogin extends JPanel {

	int num = 0, result = 0;
	String inputId = null, inputPw = null;

	private Image background = new ImageIcon("image\\startLogin.png").getImage();

	static final Font LABEL_FONT = new Font("맑은고딕", Font.BOLD, 15);
	static final Font TEXT_FONT = new Font("맑은고딕", Font.PLAIN, 15);

	final int loginTextX = 517;
	final int loginTextY = 293;

	static final int LABEL_LENGTH = 30;
	static final int TEXT_LENGTH = 245;
	static final int LOGIN_HEIGHT = 50;

	private JTextField idText;
	private HintPasswordField pwText;

	MemberLogin() {

		idText = new HintTextField(" 아이디를 입력하세요."); // 아이디 적는 칸
		idText.setBounds(loginTextX, loginTextY, TEXT_LENGTH, LOGIN_HEIGHT);
		idText.setOpaque(false);

		pwText = new HintPasswordField(" 비밀번호를 입력하세요."); // 비밀번호 적는 칸
		pwText.setBounds(loginTextX, loginTextY + 125, TEXT_LENGTH, LOGIN_HEIGHT);
		pwText.setOpaque(false);

		add(idText);
		add(pwText);
		setSize(1280, 720);
		setLayout(null);
		setVisible(true);

	}

	public String getUserID() {
		return idText.getText();
	}

	public void paint(Graphics g) {

		g.drawImage(background, 0, 0, 1280, 720, null); // background를 그려줌
		paintComponents(g);
		this.repaint();// update() 메소드 자동호출

	}

	public boolean campare(String id, String pw) { // 아이디와 비밀번호 일치하는지 확인
		if (id.equals(idText.getText()) && pw.equals(new String(pwText.getPassword()))) {
			return true;
		}
		return false;
	}

	public boolean checkLogin() {
		boolean loginCheck = false; // false를 유지하면 로그인 가능

		try (BufferedReader br = new BufferedReader(new FileReader("members.dat"))) {

			String s;
			while ((s = br.readLine()) != null) {

				String[] array = s.split("/");
				if (campare(array[0], array[1])) {
					loginCheck = true;
					break;
				}

			}

		} catch (IOException es) {
			es.printStackTrace();
		}

		return loginCheck;

	}

}