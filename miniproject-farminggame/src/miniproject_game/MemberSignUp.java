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
 * 회원가입 JFrame
 * 
 * @author SuaHwang
 *
 */
public class MemberSignUp extends JFrame implements ActionListener {

	/**
	 * component 시작 x 좌표
	 */
	static final int START_X = 5;
	/**
	 * component 시작 y 좌표
	 */
	static final int START_Y = 20;
	/**
	 * TextField 폭
	 */
	static final int TEXT_WIDTH = 250;
	/**
	 * component(Label, TextField) 높이
	 */
	static final int COMP_HEIGHT = 40;

	/**
	 * 아이디 정규식
	 */
	static final String ID_REGEX = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$";
	/**
	 * 비밀번호 정규식
	 */
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
		idLabel.setBounds(START_X, START_Y, 100, COMP_HEIGHT);

		idText = new HintTextField(" 영문으로 시작/ 영문,숫자만 가능/ 5~12자");
		idText.setBounds(START_X + 100, START_Y, TEXT_WIDTH, COMP_HEIGHT);
		idText.setBorder(new LineBorder(COLOR_LINEBORDER, 5));

		pwLabel = new JLabel("new PW", JLabel.CENTER);
		pwLabel.setBounds(START_X, START_Y + 45, 100, COMP_HEIGHT);

		pwText = new HintPasswordField(" 영문,숫자,특수문자 1개 이상사용/ 8~16자");
		pwText.setBounds(START_X + 100, START_Y + 45, TEXT_WIDTH, COMP_HEIGHT);
		pwText.setBorder(new LineBorder(COLOR_LINEBORDER, 5));

		rePwLabel = new JLabel("PW check", JLabel.CENTER);
		rePwLabel.setBounds(START_X, START_Y + 90, 100, COMP_HEIGHT);

		rePwText = new HintPasswordField(" 비밀번호 확인");
		rePwText.setBounds(START_X + 100, START_Y + 90, TEXT_WIDTH, COMP_HEIGHT);
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

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

		this.setDefaultCloseOperation(MemberSignUp.DISPOSE_ON_CLOSE);

	}

	/**
	 * id가 설정한 아이디 정규식(ID_REGEX)에 맞는지 확인한다.
	 * 
	 * @param id 입력한 아이디
	 * @return 정규식에 맞으면 true, 그렇지 않으면 false
	 */
	public boolean checkIdText(String id) {
		if (id.matches(ID_REGEX)) {
			return true;
		}
		return false;
	}

	/**
	 * pw가 설정한 비밀번호 정규식(PW_REGEX)에 맞는지 확인한다.
	 * 
	 * @param pw 입력한 비밀번호
	 * @return 정규식에 맞으면 true, 그렇지 않으면 false
	 */
	public boolean checkPwText(String pw) {
		if (pw.matches(PW_REGEX)) {
			return true;
		}
		return false;
	}

	/**
	 * newPw와 checkPw가 일치하는지 확인한다.
	 * 
	 * @param newPw   새로운 비밀번호
	 * @param checkPw 재입력한 비밀번호
	 * @return 일치하면 true, 그렇지 않으면 false
	 */
	public boolean checkRePw(String newPw, String checkPw) {
		if (newPw.equals(checkPw)) {
			return true;
		}
		return false;
	}

	/**
	 * 가입 버튼 클릭 시 발생하는 이벤트다.
	 */
	public void actionPerformed(ActionEvent e) {
		SoundsClip.play(SoundsClip.CLICK_SOUND);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("members.dat", true));
				BufferedReader br = new BufferedReader(new FileReader("members.dat"))) {

			String s = null;
			/**
			 * signUpCheck는 회원가입 가능 여부를 표시 false면 가능, 그렇지 않으면 true
			 */
			boolean signUpCheck = false;
			/**
			 * idDuplicate는 id 중복 여부를 표시 false면 중복이 아닌 상태고, 그렇지 않으면 true
			 */
			boolean idDuplicate = false;
			/**
			 * pwMatch는 새로운 비밀번호와 재입력한 비밀번호가 일치하는지 표시 false면 입력한 두 개의 비밀번호가 일치하는 것이고, 그렇지
			 * 않으면 true
			 */
			boolean pwMatch = false;

			if (e.getSource() == signUpBtn) {

				while ((s = br.readLine()) != null) {

					String[] array = s.split("/");
					if (array[0].equals(idText.getText())) {
						idDuplicate = true;
						break;
					}
				}

				if (!checkIdText(idText.getText()) || !checkPwText(pwText.getText())) {
					signUpCheck = true;
				}

				if (!checkRePw(pwText.getText(), rePwText.getText())) {
					pwMatch = true;
				}

				if (!idDuplicate && !signUpCheck && !pwMatch) {

					bw.write(idText.getText() + "/");
					bw.write(pwText.getText() + "\n");
					FarmerMessage.getSingUpMsg();
					dispose();

				} else if (idDuplicate) {
					FarmerMessage.getDuplicateIdMsg();
				} else if (pwMatch) {
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

/**
 * 힌트 텍스트를 넣은 JPasswordField
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 */
class HintPasswordField extends JPasswordField {

	/**
	 * 포커스가 잡혔을 때 폰트
	 */
	Font gainFont = new Font("맑은고딕", Font.PLAIN, 16);
	/**
	 * 포커스를 잃었을 때 폰트
	 */
	Font lostFont = new Font("맑은고딕", Font.PLAIN, 12);

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

/**
 * 힌트 텍스트를 넣은 JTextField
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 */
class HintTextField extends JTextField {

	/**
	 * 포커스가 잡혔을 때 폰트
	 */
	Font gainFont = new Font("맑은고딕", Font.PLAIN, 14);
	/**
	 * 포커스를 잃었을 때 폰트
	 */
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
