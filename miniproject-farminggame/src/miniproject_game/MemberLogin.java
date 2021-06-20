package miniproject_game;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

/**
 * 회원로그인 JPanel
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 *
 */
public class MemberLogin extends JPanel {

	private Image background = new ImageIcon("image\\startLogin.png").getImage();

	/**
	 * 기본 폰트 설정
	 */
	static final Font TEXT_FONT = new Font("맑은고딕", Font.PLAIN, 15);

	/**
	 * TextField의 시작 x 좌표
	 */
	final int loginTextX = 517;
	/**
	 * TextField의 시작 y 좌표
	 */
	final int loginTextY = 293;
	/**
	 * TextField의 기본 길이
	 */
	static final int TEXT_LENGTH = 245;
	/**
	 * TextField의 기본 폭
	 */
	static final int TEXT_HEIGHT = 50;

	/**
	 * 아이디 입력란
	 */
	private JTextField idText;
	/**
	 * 비밀번호 입력란
	 */
	private HintPasswordField pwText;

	MemberLogin() {

		idText = new HintTextField(" 아이디를 입력하세요.");
		idText.setBounds(loginTextX, loginTextY, TEXT_LENGTH, TEXT_HEIGHT);
		idText.setOpaque(false);

		pwText = new HintPasswordField(" 비밀번호를 입력하세요.");
		pwText.setBounds(loginTextX, loginTextY + 125, TEXT_LENGTH, TEXT_HEIGHT);
		pwText.setOpaque(false);

		add(idText);
		add(pwText);
		setSize(1280, 720);
		setLayout(null);
		setVisible(true);

	}

	/**
	 * ID textField의 text값을 반환한다.
	 * 
	 * @return id 아이디 입력란에 작성한 text
	 */
	public String getUserID() {
		return idText.getText();
	}

	public void paint(Graphics g) {

		g.drawImage(background, 0, 0, 1280, 720, null);
		paintComponents(g);
		this.repaint();

	} 

	/**
	 * id와 pw를 통해 가입여부를 확인한다.
	 * 
	 * @param id user id
	 * @param pw user pw
	 * @return 회원이면 true, 그렇지 않으면 false
	 */
	public boolean campare(String id, String pw) {
		if (id.equals(idText.getText()) && pw.equals(new String(pwText.getPassword()))) {
			return true;
		}
		return false;
	}

	/**
	 * 로그인 가능한지 확인한다.
	 * 
	 * @return 로그인 가능하면 false, 그렇지 않으면 true
	 */
	public boolean checkLogin() {
		boolean loginCheck = false;

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
