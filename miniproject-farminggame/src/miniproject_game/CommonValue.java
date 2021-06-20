package miniproject_game;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * 상수를 모아 놓은 클래스
 * 
 * @author SuaHwang
 */

public class CommonValue {

	/** 
	 * 토마토
	 */
	public static final String TOMATO = "tomato";
	/**
	 * 벼
	 */
	public static final String RICE = "rice";
	/**
	 * 가지
	 */
	public static final String EGGPLANT = "eggplant";

	/**
	 * 메인 기본 컬러
	 */
	public static final Color COLOR_BASE = new Color(0xfd, 0xff, 0xc2);

	/**
	 * 게임 배경 하늘 색
	 */
	public static final Color COLOR_BACKGROUND_SKY = new Color(0xce, 0xeb, 0xe6);

	/**
	 * 밭 색
	 */
	static final Color COLOR_DRY_FIELD = new Color(0x77, 0x4e, 0x24);

	/**
	 * 회원가입 TextField 테두리 색
	 */
	static final Color COLOR_LINEBORDER = new Color(0xfc, 0xff, 0xa1);

	/**
	 * 밭의 가로,세로 길이
	 */
	public static final int FIELD_LENGTH = 100;
	/**
	 * 밭 사이의 간격
	 */
	public static final int FIELD_BETWEEN_LENGTH = 30;
	/**
	 * 밭의 시작 x 좌표
	 */
	public static final int FIELD_START_X = 120;
	/**
	 * 밭의 시작 y 좌표
	 */
	public static final int FIELD_START_Y = 410;

	/**
	 * 농부 이미지 아이콘
	 */
	public static final ImageIcon IMG_FARMER = new ImageIcon("image//farmer.png");

}
