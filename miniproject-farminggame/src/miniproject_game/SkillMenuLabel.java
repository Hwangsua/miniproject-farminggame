package miniproject_game;

import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * 
 * 스킬 메뉴 JLabel<br/>
 * 스킬 image 및 구매/업그레이드 버튼 객체 생성 및 {@link MenuPanel}에 add().
 * 
 * @author EunkyungHawang
 *
 */ 
class SkillMenuLabel extends JLabel {

	int btnY = 100;
	/**
	 * 물이미지
	 */
	JLabel waterImg;
	/**
	 * 메뉴2의 물 사용버튼
	 */
	JButton menu2_waterUse;
	/**
	 * 메뉴2의 물 구매버튼
	 */
	JButton menu2_waterPurchase;
	/**
	 * 이미지 경로
	 */
	String imagepath;
	/**
	 * 물사용버튼 이미지경로
	 */
	String waterUseimagepath;
	/**
	 * 물구매버튼 이미지경로
	 */
	String waterPurchaseimagepath;
	/**
	 * 물스킬에 대한 정보라벨
	 */
	JLabel waterLevelInfo;

	String wateringStatus;
	TextArea contents;
	int waterlevel;
	boolean buySkill;

	private int waterSkillPrice;
	static int openSkillCnt = 0;

	/**
	 * 스킬가격을 반환한다.
	 * 
	 * @return 스킬가격
	 * @since 1.0
	 */
	int getwaterSkillPrice() {
		return waterSkillPrice;
	}

	final MainFrame main;

	/**
	 * SkillMenuLabel 생성자, MainFrame 의 skillname 부착한다.
	 * 
	 * @param main      MainFrame
	 * @param skillName 사용할 스킬이름
	 */
	SkillMenuLabel(MainFrame main, String skillName) {

		this.main = main;

		initSkillMenu(skillName);
		waterUseimagepath = "image//UseButton.png";
		waterPurchaseimagepath = "image//Buybutton.png";

		changeImgSize(waterUseimagepath, 40, 40);
		changeImgSize(waterPurchaseimagepath, 40, 40);

		waterImg = new JLabel(new ImageIcon(imagepath));
		waterImg.setBounds(40, btnY + 30, 100, 100);

		menu2_waterUse = new JButton(new ImageIcon(waterUseimagepath));
		menu2_waterUse.setBounds(200, btnY + 130, 120, 40);

		menu2_waterPurchase = new JButton(new ImageIcon(waterPurchaseimagepath));
		menu2_waterPurchase.setBounds(200, btnY + 130, 120, 40);

		waterLevelInfo = new JLabel(wateringStatus);
		waterLevelInfo.setBounds(205, btnY + 5, 130, 120);

		btnSetting(menu2_waterUse);
		btnSetting(menu2_waterPurchase);

		main.menuPanel.add(waterImg);
		main.menuPanel.add(menu2_waterUse);
		main.menuPanel.add(waterLevelInfo);
		main.menuPanel.add(menu2_waterPurchase);

		menu2_waterUse.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				main.setWaterLevel(waterlevel);
			}

		});

	}

	/**
	 * 스킬 초기값 설정한다.
	 * 
	 * @param name 사용할 스킬 이름
	 * @since 1.0
	 */

	void initSkillMenu(String name) {
		switch (name) {
		case "waterSkill1":
			this.imagepath = "image//waterSkill1.png";
			this.btnY = 50;
			this.wateringStatus = "<html>Price : 10,000<br>Duration : 5 sec<br>Effect :<br>Time reduction X 3<html>";
			this.waterSkillPrice = 10000;
			this.waterlevel = 1;
			this.buySkill = false;

			break;

		}
		switch (name) {
		case "waterSkill2":
			this.imagepath = "image//waterSkill2.png";
			this.btnY = 250;
			this.wateringStatus = "<html>Price : 300,000<br>Duration : 10 sec<br>Effect :<br>Time reduction X 4<html>";
			this.waterSkillPrice = 300000;
			this.waterlevel = 2;
			this.buySkill = false;

			break;

		}
		switch (name) {
		case "waterSkill3":
			this.imagepath = "image//waterSkill3.png";
			this.btnY = 450;
			this.wateringStatus = "<html>Price : 1,000,000<br>Duration : 15 sec<br>Effect :<br>Time reduction X 6<html>";
			this.waterSkillPrice = 1000000;
			this.buySkill = false;

			break;

		}
	}

	/**
	 * image size를 변환한다.
	 * 
	 * @param imagepath 이미지 경로
	 * @param weight    이미지 가로길이
	 * @param height    이미지 세로길이
	 * @return 변환된 크기의 이미지아이콘
	 * @since 1.0
	 */
	ImageIcon changeImgSize(String imagepath, int weight, int height) {
		ImageIcon origicon = new ImageIcon(imagepath);
		Image img = origicon.getImage();
		Image changeImg = img.getScaledInstance(weight, height, Image.SCALE_SMOOTH);
		return new ImageIcon(changeImg);
	}

	/**
	 * 
	 * 버튼 기본모양 설정한다.
	 * 
	 * @param jbtn 사용할 버튼
	 * @since 1.0
	 */
	void btnSetting(JButton jbtn) {
		jbtn.setBorderPainted(false);
		jbtn.setContentAreaFilled(false);
		jbtn.setFocusPainted(false);

	}

}