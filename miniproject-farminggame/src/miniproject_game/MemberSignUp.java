package miniproject_game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import static miniproject_game.CommonValue.*;

/**
 * 
 * @author SuaHwang
 *
 */
public class MemberSignUp extends JFrame implements ActionListener {

	static final Color COLOR_LINEBORDER = new Color(0xfc, 0xff, 0xa1);

	static final int START_X = 5;
	static final int START_Y = 20;
	static final int TEXT_LENGTH = 250;
	static final int SIGNUP_HEIGHT = 40;

	// ID : 영문으로 시작해야하며, 영문 숫자로만 이루워진 5~12자
	// PW : 숫자, 영문, 특수문자 무조건 1개 이상, 비밀번호 최소 8자에서 최대 16자까지 허용,특수문자는 정의된 특수문자만 사용 가능
	static final String ID_REGEX = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$";
	static final String PW_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

	JPanel panel;
	JLabel idLabel, pwLabel, rePwLabel;
	JButton signUpBtn;
	JTextField idText, pwText, rePwText;

	MemberSignUp() {
		setTitle("회원가입");
		setSize(400, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(COLOR_BASE);

		idLabel = new JLabel("ID", JLabel.CENTER);
		idLabel.setBounds(START_X, START_Y, 100, SIGNUP_HEIGHT);

		idText = new HintTextField(" 영문으로 시작/ 영문,숫자만 가능/ 5~12자");
		idText.setBounds(START_X + 100, START_Y, TEXT_LENGTH, SIGNUP_HEIGHT);
		idText.setBorder(new LineBorder(COLOR_LINEBORDER, 5));

		pwLabel = new JLabel("new PW", JLabel.CENTER);
		pwLabel.setBounds(START_X, START_Y + 45, 100, SIGNUP_HEIGHT);

		pwText = new HintPasswordField(" 영문,숫자,특수문자 1개 이상사용/ 8~16자");
		pwText.setBounds(START_X + 100, START_Y + 45, TEXT_LENGTH, SIGNUP_HEIGHT);
		pwText.setBorder(new LineBorder(COLOR_LINEBORDER, 5));

		rePwLabel = new JLabel("PW check", JLabel.CENTER);
		rePwLabel.setBounds(START_X, START_Y + 90, 100, SIGNUP_HEIGHT);

		rePwText = new HintPasswordField(" 비밀번호 확인");
		rePwText.setBounds(START_X + 100, START_Y + 90, TEXT_LENGTH, SIGNUP_HEIGHT);
		rePwText.setBorder(new LineBorder(COLOR_LINEBORDER, 5));

		signUpBtn = new JButton("가입하기");
		signUpBtn.setBounds(90, START_Y + 200, 200, 50);
		signUpBtn.setFont(new Font("맑은고딕", Font.BOLD, 15));
		signUpBtn.setBackground(COLOR_BASE);
		signUpBtn.setBorder(new LineBorder(COLOR_LINEBORDER, 5));
		signUpBtn.addActionListener(this);
		signUpBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				signUpBtn.setIcon(IMG_FARMER);
				signUpBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signUpBtn.setIcon(new ImageIcon());
				signUpBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
		});

		panel.add(idLabel);
		panel.add(idText);
		panel.add(pwLabel);
		panel.add(pwText);
		panel.add(rePwLabel);
		panel.add(rePwText);
		panel.add(signUpBtn);

		this.add(panel);

		this.setLocationRelativeTo(null);// 창이 가운데 나오게
		this.setResizable(false);// 창의 크기를 변경하지 못하게
		this.setVisible(true);

		this.setDefaultCloseOperation(MemberSignUp.DISPOSE_ON_CLOSE);

	}

	public boolean checkIdText(String text) {
		if (text.matches(ID_REGEX)) { // 숫자랑 영어만 가능
			return true;
		}
		return false;
	}

	public boolean checkPwText(String text) {
		if (text.matches(PW_REGEX)) {
			return true;
		}
		return false;
	}

	public boolean checkRePw(String newPw, String checkPw) {
		if (newPw.equals(checkPw)) {
			return true;
		}
		return false;
	}

	public void actionPerformed(ActionEvent e) {
		SoundsClip.play(SoundsClip.CLICK_SOUND);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("members.dat", true));
				BufferedReader br = new BufferedReader(new FileReader("members.dat"))) {

			String s = null;
			boolean signUpCheck = false; // false를 유지하면 회원가입 가능
			boolean idDuplicate = false; // id 중복 체크, false면 중복 아님
			boolean pwMatch = false; // 비밀번호 일치하는지 확인 
			
			if (e.getSource() == signUpBtn) {

				while ((s = br.readLine()) != null) {

					// 아이디 중복
					String[] array = s.split("/");
					if (array[0].equals(idText.getText())) {
						idDuplicate = true;
						break;
					}
				}

				if (!checkIdText(idText.getText()) || !checkPwText(pwText.getText())) {
					signUpCheck = true;
				}

				// 새로운 비밀번호와 재입력한 비밀번호가 일치한지 확인
				if (!checkRePw(pwText.getText(), rePwText.getText())) {
					pwMatch = true;
				}

				// 정보 입력시 아이디 중복이 없고 조건이 맞으면 데이터 보냄
				if (!idDuplicate && !signUpCheck && !pwMatch) {

					bw.write(idText.getText() + "/");
					bw.write(pwText.getText() + "\n");
					FarmerMessage.getSingUpMsg();
					dispose(); // 현재 프레임만 종료한다.

				} else if (idDuplicate) {
					FarmerMessage.getDuplicateIdMsg();
				}else if (pwMatch) {
					FarmerMessage.getMismatchPwMsg();
				} else {
					FarmerMessage.getSingUpFailMsg();
				}

			}
		} catch (IOException es) {
			es.printStackTrace();
		}
	}

}

class HintPasswordField extends JPasswordField {

	Font gainFont = new Font("맑은고딕", Font.PLAIN, 16);
	Font lostFont = new Font("맑은고딕", Font.PLAIN, 12);

	final Stack<Character> originalText = new Stack<Character>();

	public HintPasswordField(final String hint) {
		super();
		setText(hint);
		setFont(lostFont);
		setForeground(Color.GRAY);
		setEchoChar('\0');

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (getText().equals(hint)) {
					setText("");
					setFont(gainFont);
					setEchoChar('\u2022');

				} else {
					setFont(gainFont);
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().equals(hint) || getText().length() == 0) {
					setText(hint);
					setFont(lostFont);
					setForeground(Color.GRAY);
					setEchoChar('\0');

				} else {
					setText(getText());
					setFont(gainFont);
					setForeground(Color.BLACK);

				}

			}

		});

	}

	@Override
	public String getText() {
		return new String(getPassword());
	}

}

class HintTextField extends JTextField {
	Font gainFont = new Font("맑은고딕", Font.PLAIN, 14);
	Font lostFont = new Font("맑은고딕", Font.PLAIN, 12);

	public HintTextField(final String hint) {
		super();
		setText(hint);
		setFont(lostFont);
		setForeground(Color.GRAY);

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (getText().equals(hint)) {
					setText("");
					setFont(gainFont);

				} else {
					setText(getText());
					setFont(gainFont);
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().equals(hint) || getText().length() == 0) {

					setText(hint);
					setFont(lostFont);
					setForeground(Color.GRAY);

				} else {

					setText(getText());
					setFont(gainFont);
					setForeground(Color.BLACK);

				}

			}

		});

	}
}

