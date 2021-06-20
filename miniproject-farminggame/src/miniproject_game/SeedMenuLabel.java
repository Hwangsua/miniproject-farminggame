package miniproject_game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import static miniproject_game.CommonValue.*;

/**
 * 씨앗 메뉴 JLabel<br/>
 * 씨앗 메뉴라벨을 생성 및 {@link MenuPanel}에 add().
 * 
 * @author EunKyungHwang
 *
 */
class SeedMenuLabel extends JLabel {

	/**
	 * 씨앗 이미지
	 */
	JLabel seedImg;

	/**
	 * 씨앗 업그레이드 버튼
	 */
	JButton seedUpgrade;
	/**
	 * 씨앗 구매버튼
	 */
	JButton seedPurchase;

	/**
	 * 현재 씨앗 레벨정보
	 */
	JLabel seedLevelInfo;
	/**
	 * 이미지 경로
	 */
	ImageIcon imagepath;
	/**
	 * 씨앗이미지 버튼의 y좌표
	 */
	int btnY = 100;
	/**
	 * 씨앗 현재상태
	 */
	String seedStatus;
	/**
	 * 씨앗 이름
	 */
	String seedName;
	/**
	 * 씨앗에 대한 정보들
	 */
	SeedInfo info;

	/**
	 * {@link SeedMenuLabel} 객체 생성자<br>
	 * 
	 * @param seedName 씨앗이름
	 */
	SeedMenuLabel(String seedName) {

		init(seedName);

		/**
		 * 씨앗이미지 객체 생성 및 배치
		 */
		seedImg = new JLabel(imagepath);
		seedImg.setBounds(40, btnY, 100, 100);

		/**
		 * 씨앗업그레이드 버튼객체 생성 및 배치
		 */
		seedUpgrade = new JButton(new ImageIcon("image//UpgradeButton.png"));
		seedUpgrade.setBounds(40, btnY + 130, 120, 40);

		/**
		 * 씨앗구매 버튼객체 생성 및 배치
		 */
		seedPurchase = new JButton((new ImageIcon("image//BuyButton.png")));
		seedPurchase.setBounds(200, btnY + 130, 120, 40);

		/**
		 * 씨앗정보 라벨객체 생성 및 배치
		 */
		seedLevelInfo = new JLabel(seedStatus.toString());
		seedLevelInfo.setBounds(205, btnY - 10, 130, 120);

		/**
		 * 구매 업그레이드 버튼모양 설정한다.
		 */
		btnSetting(seedPurchase);
		btnSetting(seedUpgrade);

	}

	/**
	 * 
	 * name에 해당하는 씨앗의 정보를 반환한다.
	 * 
	 * @param name
	 * @return 씨앗의 현재 상태
	 * @since 1.0
	 */
	String setSeedStatus(String name) {
		info = new SeedInfo(null);
		switch (name) {
		case TOMATO:
			return "<html><body>Lv." + info.getSeedLevel(name) + "<br>Price : 500<br>Earned Exp : 60"
					+ "<br>Earned Coin : 750" + "<br>Current yield : " + info.getSeedTotalYield(name)
					+ "<br>Next yield :" + info.getUpgradeYield(name) + "</body></html>";
		case RICE:
			return "<html><body>Lv. " + info.getSeedLevel(name) + "<br>Price :1000<br>Earned Exp : 100"
					+ "<br>Earned Coin : 4000" + "<br>Current yield : " + info.getSeedTotalYield(name)
					+ "<br>Next yield : " + info.getUpgradeYield(name) + "</body></html>";
		case EGGPLANT:
			return "<html><body>Lv. : " + info.getSeedLevel(name) + "<br>Price : 800<br>Earned Exp : 200"
					+ "<br>Earned Coin : 1000" + "<br>Current yield : " + info.getSeedTotalYield(name)
					+ "<br>Next yield : " + info.getUpgradeYield(name) + "</body></html>";
		}
		return "";
	}

	/**
	 * 씨앗이름라벨 초기값 설정한다.
	 * 
	 * @param seedNameLabel
	 * @since 1.0
	 */
	void init(String seedNameLabel) {

		switch (seedNameLabel) {
		case "tomatoLabel":
			this.seedName = TOMATO;
			this.imagepath = new ImageIcon("image//tomatoSeed.png");
			this.btnY = 50;
			this.seedStatus = setSeedStatus(seedName);

			break;

		case "riceLabel":
			this.seedName = RICE;
			this.imagepath = new ImageIcon("image//riceSeed.png");
			this.btnY = 250;
			this.seedStatus = setSeedStatus(seedName);

			break;

		case "eggplantLabel":
			this.seedName = EGGPLANT;
			this.imagepath = new ImageIcon("image//eggplantSeed.png");
			this.btnY = 450;
			this.seedStatus = setSeedStatus(seedName);
			break;

		}

	}

	/**
	 * 버튼 기본모양을 설정한다.
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