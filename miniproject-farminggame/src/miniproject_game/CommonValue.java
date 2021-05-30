package miniproject_game;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * 이 클래스는 자주 사용돠는 변수를 상수로 선언한 클래스다. 
 * @author SuaHwang
 */

public class CommonValue {

	public static final String TOMATO = "tomato";
	public static final String RICE = "rice";
	public static final String EGGPLANT = "eggplant";

	public static final Color COLOR_BASE = new Color(0xfd, 0xff, 0xc2); // 기본으로 사용되는 노란색상

	public static final int FIELD_LENGTH = 100; // 밭 가로,세로 길이 (정사각형)
	public static final int FIELD_BETWEEN_LENGTH = 30; // 밭사이의 간격
	public static final int FIELD_START_X = 120; // 밭의 시작 X 좌표
	public static final int FIELD_START_Y = 410; // 밭의 시작 Y 좌표

	public static final ImageIcon IMG_FARMER = new ImageIcon("image//farmer.png");

}
