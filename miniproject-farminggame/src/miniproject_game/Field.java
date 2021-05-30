package miniproject_game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import static miniproject_game.CommonValue.*;
/**
 * 
 * @author SuaHwang
 *
 */
public class Field extends JLabel {
	static final Color COLOR_DRY_FIELD = new Color(0x77, 0x4e, 0x24);

	boolean emptyFieldCheck = true; // 밭 비어있는지 확인
	// true : 비어있는 상태

	private int x, y; // 시작 x좌표, y좌표
	
	/**
	 * 시작 x 좌표 반환
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	Field() {
		super();

	}

	public void isEmptyField(boolean fieldCheck) {
		this.emptyFieldCheck = fieldCheck;
	}

	public boolean getEmptyField() {
		return emptyFieldCheck;
	}

	public void paintComponent(Graphics g) {

		if (emptyFieldCheck) {
			g.setColor(COLOR_DRY_FIELD);
			g.fillRoundRect(0, 0, FIELD_LENGTH, FIELD_LENGTH, 10, 10);

		} 

	}

	public void paintComponent(Graphics g, int width, int heigth) {

	}

}
