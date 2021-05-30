package miniproject_game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * @author EunkyungHwang
 *
 */
class FieldMenuLabel extends JLabel {

	int skillBtnX = 140;
	int btnY = 50;
	JButton fieldImg; // 땅확장 이미지 meue3 버튼
	JLabel fieldImgLimit05; // 땅확장 이미지 레벨 5
	JLabel fieldImgLimit010; // 땅확장 이미지 레벨 10
	JLabel fieldImgLimit015; // 땅확장 이미지 레벨 15
	JLabel fieldImgLimit020; // 땅확장 이미지 레벨 20
	JLabel fieldImgLimit025; // 땅확장 이미지 레벨 25
	String imagepath05; // 밭이미지 05
	String imagepath010; // 밭이미지 010
	String imagepath015; // 밭이미지 015
	String imagepath020; // 밭이미지 020
	String imagepath025; // 밭이미지 025

	FieldMenuLabel(String fieldLabelName) {

		init(fieldLabelName);
		// 땅1 배치
		fieldImgLimit05 = new JLabel(new ImageIcon(imagepath05));
		fieldImgLimit05.setBounds(skillBtnX, btnY, 100, 100);

		// 땅2 배치
		fieldImgLimit010 = new JLabel(new ImageIcon(imagepath010));
		fieldImgLimit010.setBounds(skillBtnX, btnY + (130 * 1), 100, 100);

		// 땅3 배치
		fieldImgLimit015 = new JLabel(new ImageIcon(imagepath015));
		fieldImgLimit015.setBounds(skillBtnX, btnY + (130 * 2), 100, 100);

		// 땅4 배치
		fieldImgLimit020 = new JLabel(new ImageIcon(imagepath020));
		fieldImgLimit020.setBounds(skillBtnX, btnY + (130 * 3), 100, 100);

		// 땅5 배치
		fieldImgLimit025 = new JLabel(new ImageIcon(imagepath025));
		fieldImgLimit025.setBounds(skillBtnX, btnY + (130 * 4), 100, 100);

	}

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
