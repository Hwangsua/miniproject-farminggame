package miniproject_game;

import java.awt.Graphics;

import javax.swing.JLabel;
import static miniproject_game.CommonValue.*;

/**
 * 밭을 표현하는 객체<br/>
 * 밭 라벨을 생성 및 {@link FarmingPanel}에 add().
 * 
 * @author SuaHwang
 *
 */
public class Field extends JLabel {

	/**
	 * 밭의 시작 좌표
	 */
	private int x, y;

	/**
	 * 밭의 상태(비어있는지) 여부
	 */
	private boolean emptyFieldCheck = true;

	/**
	 * 밭(컴포넌트)의 시작 x 좌표 반환
	 */
	public int getX() {
		return x;
	}

	/**
	 * 밭(컴포넌트)의 시작 x 좌표 설정
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 밭(컴포넌트)의 시작 y 좌표 반환
	 */
	public int getY() {
		return y;
	}

	/**
	 * 밭(컴포넌트)의 시작 y좌표 설정
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
 
	/**
	 * 밭의 상태를 설정한다.<br/>
	 * 밭이 비어있으면 true, 그렇지 않으면 false로 설정한다.
	 * 
	 * @param fieldCheck 해당 밭의 상태
	 */
	public void isEmptyField(boolean fieldCheck) {
		this.emptyFieldCheck = fieldCheck;
	}

	/**
	 * 밭의 상태를 반환한다.<br/>
	 * 
	 * @return 해당 밭이 비어있으면 true, 그렇지않으면 false
	 */
	public boolean getEmptyField() {
		return emptyFieldCheck;
	}

	public void paintComponent(Graphics g) {

		if (emptyFieldCheck) {
			g.setColor(COLOR_DRY_FIELD);
			g.fillRoundRect(0, 0, FIELD_LENGTH, FIELD_LENGTH, 10, 10);
		}

	}

}
