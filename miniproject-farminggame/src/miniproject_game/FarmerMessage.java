package miniproject_game;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * 게임의 진행을 돕기위한 메시지 창을 띄우는 클래스
 * 
 * @author SuaHwang
 *
 */
public class FarmerMessage {

	static final StringBuilder MSG = new StringBuilder();

	/**
	 * 이미지의 사이즈를 줄여서 ImageIcon으로 반환한다.
	 * 
	 * @param path 이미지의 경로
	 * @return 이미지의 사이즈가 변경된 ImageIcon을 반환
	 */
	static final ImageIcon changeImgSize(String path) {
		ImageIcon origicon = new ImageIcon(path);
		Image img = origicon.getImage();
		Image changeImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		return new ImageIcon(changeImg);
	}

	/**
	 * 메시지 창을 생성한다.<br/>
	 * 메시지 창에 적힐 내용을 인수로 받는다.
	 * 
	 * @param text 생성되는 메시지창의 내용
	 */
	static final void getMessageWindow(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("맑은고딕", Font.BOLD, 15));
		JOptionPane.showMessageDialog(null, label, "총장님의 말씀", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon("image\\farmer.png"));
		MSG.setLength(0);
	}

	/**
	 * 도움말 메시지 창을 얻는다.
	 */
	static final void getGuideMsg() {
		MSG.append("<html>반가워요! 나는 이 마을의 총장입니다.<br>");
		MSG.append("초보 농부인 당신을 위해 내가 농사 방법을 알려줄게요!<br>");
		MSG.append("오른쪽 [ 메뉴창 ]에 버튼 3가지를 이용해 농사를 할 수 있어요.<br>");
		MSG.append("3가지의 버튼 기능은 아래에 적어둘게요.<br><br>");
		MSG.append("[ 씨앗 심기 ]<br>");
		MSG.append("-[Buy]버튼을 누른 후, 밭을 클릭하여 씨앗을 심을 수 있다.<br>");
		MSG.append("-일정 수확량을 만족하면 [Upgrade]버튼을 통해 씨앗 레벨을 올릴 수 있다.<br>");
		MSG.append("-씨앗 레벨이 높을수록 식물이 일정 시간마다 자동으로 얻을 수 있는 코인이 증가한다.<br><br>");
		MSG.append("[ 물 주기 ]<br>");
		MSG.append("-[Buy]버튼을 누르면 [Use]버튼을 통해 물을 줄 수 있다.<br>");
		MSG.append("-[Use]버튼을 누르면 식물들의 시간이 더 빠르게 감소된다.<br>");
		MSG.append("-단! 식물이 자동으로 얻는 코인의 합은 줄어든다.<br><br>");
		MSG.append("[ 밭 확장하기 ]<br>");
		MSG.append("-버튼에 표시된 Level을 만족하면 30,000coin으로 밭을 구매할 수 있다.<br>");
		MSG.append("</html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 코인이 부족하다는 메시지 창을 얻는다.
	 */
	static final void getinsufficientCoinMsg() {
		MSG.append("<html>지금은 돈이 부족해서 구매할 수 없어요!<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 레벨이 부족하다는 메시지 창을 얻는다.
	 */
	static final void getNotachievedLevelMsg() {
		MSG.append("<html>지금은 레벨이 부족해서 구매할 수 없어요!<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 수확량이 부족하다는 메시지 창을 얻는다.
	 */
	static final void getLackYieldMsg() {
		MSG.append("<html>수확량이 부족해요!<br>조금 더 열심히 수확해주세요!!</br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 스킬(물 주기) 구매를 성공했다는 메시지 창을 얻는다.
	 */
	static final void getSkillOpenMsg() {
		MSG.append("<html>구매 완료!<br>이제 [Use]버튼을 통해 농작물을 빠르게 키워주세요!</br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 스킬(물 주기) 사용시 스킬 효과시간을 알려주는 메시지 창을 얻는다.
	 * 
	 * @param skillLevel 사용하려는 스킬(물 주기)의 레벨
	 */
	static final void getSkillUseMsg(int skillLevel) {
		MSG.append("<html>").append(skillLevel * 5).append("초 동안 모든 밭의 시간감소량이 증가합니다.</br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 씨앗이 선택되었다는 메시지 창을 얻는다.
	 */
	static final void getSeedSelectMessage() {
		MSG.append("<html>씨앗이 선택됐어요!<br>밭을 클릭하여 씨앗을 심어주세요!</br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 회원가입을 성공했다는 메시지 창을 얻는다.
	 */
	static final void getSingUpMsg() {
		MSG.append("<html>회원가입을 축하합니다!<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 회원가입을 실패했다는 메시지 창을 얻는다.
	 */
	static final void getSingUpFailMsg() {
		MSG.append("<html> 회원가입에 실패했습니다.<br>가입 조건을 다시 확인해주세요.<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 로그인을 실패했다는 메시지 창을 얻는다.
	 */
	static final void getLoginFailMsg() {
		MSG.append("<html> 회원 정보가 없습니다.<br>아이디와 비밀번호를 다시 확인해주세요.<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 아이디가 이미 존재한다는 메시지 창을 얻는다.
	 */
	static final void getDuplicateIdMsg() {
		MSG.append("<html> 이미 존재하는 아이디 입니다.<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

	/**
	 * 새 비밀번호와 확인하기 위한 비밀번호가 일치하지 않다는 메시지 창을 얻는다.
	 */
	static final void getMismatchPwMsg() {
		MSG.append("<html> 비밀번호가 일치하지 않습니다.<br></html>");
		getMessageWindow(String.valueOf(MSG));
	}

}
