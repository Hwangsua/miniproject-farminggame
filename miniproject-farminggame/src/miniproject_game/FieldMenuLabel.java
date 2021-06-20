package miniproject_game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * 밭 메뉴 JLabel <br/>
 * 밭 image 객체 생성 및 {@link MenuPanel}에 add().
 * 
 * @author EunkyungHawang
 *
 */
class FieldMenuLabel extends JLabel {
	/**
	 * 밭라벨의 X 좌표
	 */
	int fieldBtnX = 140;
	/**
	 * 밭라벨의 Y 좌표
	 */
	int btnY = 50;
	/**
	 * 땅확장 이미지 meue3 버튼
	 */
	JButton fieldImg;
	/**
	 * 땅확장 이미지 레벨 5
	 */
	JLabel fieldImgLimit05;
	/**
	 * 땅확장 이미지 레벨 10
	 */
	JLabel fieldImgLimit010;
	/**
	 * 땅확장 이미지 레벨 15
	 */
	JLabel fieldImgLimit015;
	/**
	 * 땅확장 이미지 레벨 20
	 */
	JLabel fieldImgLimit020;
	/**
	 * 땅확장 이미지 레벨 25
	 */
	JLabel fieldImgLimit025;
	/**
	 * 밭이미지 주소 05
	 */
	String imagepath05;
	/**
	 * 밭이미지 주소 010
	 */
	String imagepath010;
	/**
	 * 밭이미지 주소 015
	 */
	String imagepath015;
	/**
	 * 밭이미지 주소 020
	 */
	String imagepath020;
	/**
	 * 밭이미지 주소 025
	 */
	String imagepath025;

	/**
	 * FieldMenuLabel의 생성자
	 * 
	 * @param fieldLabelName
	 */
	FieldMenuLabel(String fieldLabelName) {
		/**
		 * 밭 라벨 초기값설정 함수
		 */
		init(fieldLabelName);
		/**
		 * 땅 1 객체생성 및 좌표설정
		 */
		fieldImgLimit05 = new JLabel(new ImageIcon(imagepath05));
		fieldImgLimit05.setBounds(fieldBtnX, btnY, 100, 100);

		/**
		 * 땅 2 객체생성 및 좌표설정
		 */
		fieldImgLimit010 = new JLabel(new ImageIcon(imagepath010));
		fieldImgLimit010.setBounds(fieldBtnX, btnY + (130 * 1), 100, 100);
		/**
		 * 땅 3 객체생성 및 좌표설정
		 */
		fieldImgLimit015 = new JLabel(new ImageIcon(imagepath015));
		fieldImgLimit015.setBounds(fieldBtnX, btnY + (130 * 2), 100, 100);
		/**
		 * 땅 4 객체생성 및 좌표설정
		 */
		fieldImgLimit020 = new JLabel(new ImageIcon(imagepath020));
		fieldImgLimit020.setBounds(fieldBtnX, btnY + (130 * 3), 100, 100);
		/**
		 * 땅 5 객체생성 및 좌표설정
		 */
		fieldImgLimit025 = new JLabel(new ImageIcon(imagepath025));
		fieldImgLimit025.setBounds(fieldBtnX, btnY + (130 * 4), 100, 100);

	}

	/**
	 * 밭라벨의 초기값을 생성한다.
	 * 
	 * @param fieldLabelName
	 * @since 1.0
	 */
	private void init(String fieldLabelName) {
		switch (fieldLabelName) {
		case "fiedlBtn":
			this.imagepath05 = "image//extendField_limit05.png";
			this.imagepath010 = "image//extendField_limit010.png";
			this.imagepath015 = "image//extendField_limit015.png";
			this.imagepath020 = "image//extendField_limit020.png";
			this.imagepath025 = "image//extendField_limit025.png";

			break;

		case "fakefiedlBtn":

			this.imagepath05 = "image//extendField.png";
			this.imagepath010 = "image//extendField.png";
			this.imagepath015 = "image//extendField.png";
			this.imagepath020 = "image//extendField.png";
			this.imagepath025 = "image//extendField.png";

			break;

		}

	}

}