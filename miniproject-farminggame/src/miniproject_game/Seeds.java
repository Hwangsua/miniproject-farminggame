package miniproject_game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static miniproject_game.CommonValue.*;

/**
 * 씨앗 객체 <br/>
 * 씨앗 이미지 생성 및 {@link FarmingPanel}에 add().
 * 
 * @author SuaHwang
 * 
 */ 
public class Seeds extends JLabel implements Runnable {

	/**
	 * 씨앗 이미지의 폭
	 */
	static final int IMG_SEED_WIDTH = 130;
	/**
	 * 씨앗 이미지의 높이
	 */
	static final int IMG_SEED_HEIGHT = 150;
	/**
	 * 시간 단위(초)
	 */
	static final int UNIT_SECOND = 1000;

	final FarmingPanel farmingPanel;
	private SeedMenuLabel infoLabel;

	/**
	 * 성장 시간 표시해주는 JLabel
	 */
	private JLabel remainingTimeLabel;

	/**
	 * 씨앗 이름
	 */
	private String seedName;
	/**
	 * 씨앗 성장 이미지의 총 개수
	 */
	private int imgCount;
	/**
	 * 이미지 경로
	 */
	private String path;

	/**
	 * 총 걸리는 씨앗의 성장 시간
	 */
	private int timeGrow;
	/**
	 * 씨앗 이미지가 변경되는 시간 간격
	 */
	private int timeGrowInterval;
	/**
	 * 자동으로 코인 증가하는 시간 간격
	 */
	private int timeAuto;

	/**
	 * 씨앗의 남은 성장 시간
	 */
	int leftTimeInMillis;

	/**
	 * 농장물 생성 시 자동으로 오르는 코인의 값
	 */
	private int coinAuto;

	/**
	 * 수확 시 얻는 코인의 값
	 */
	private int coinHarvest;
	/**
	 * 수확 시 얻는 경험치
	 */
	private int expHarvest;

	/**
	 * 씨앗이 다 성장했는지 여부
	 */
	private boolean growCheck = false;
	/**
	 * 수확 가능 여부
	 */
	private boolean harvestCheck = false;

	/**
	 * 씨앗이 심어진 밭의 위치 설정
	 */
	int row, col;

	/**
	 * 씨앗의 이름을 설정한다.
	 * 
	 * @param name
	 */
	void setSeedName(String name) {
		this.seedName = name;
	}

	/**
	 * 씨앗의 이름을 반환한다.
	 * 
	 * @return
	 */
	String getSeedName() {
		return seedName;
	}

	/**
	 * 씨앗의 남은 성장 시간을 설정한다.
	 * 
	 * @param time 남은 성장 시간
	 */
	void setLeftTimeInMillis(int time) {
		this.leftTimeInMillis = time;
	}

	/**
	 * 씨앗의 남은 성장 시간을 반환한다.
	 * 
	 * @return 남은 성장 시간
	 */
	int getLeftTimeInMillis() {
		return leftTimeInMillis;
	}

	/**
	 * 씨앗의 성장 시간을 time만큼 감소시킨다.
	 * 
	 * @param time 감소되는 시간
	 */
	public void setTimeReduction(int time) {
		leftTimeInMillis -= time;
	}

	/**
	 * 씨앗이 다 성장했는지 확인한다.
	 * 
	 * @return 다 성장했으면 true, 그렇지 않으면 false
	 */
	public boolean getGrowCheck() {
		return growCheck;
	}

	/**
	 * name에 해당하는 씨앗으로 설정을 초기화한다.
	 * 
	 * @param name 씨앗의 이름
	 */
	void initSeed(String name) {
		switch (name) {
		case TOMATO:
			this.imgCount = 7;
			this.timeGrow = 180 * UNIT_SECOND;
			this.leftTimeInMillis = this.timeGrow;
			this.timeGrowInterval = timeGrow / (imgCount - 1);
			this.timeAuto = 5 * UNIT_SECOND;
			this.coinAuto = farmingPanel.commonSeedInfo.getSeedLevel(name) + 1;
			this.coinHarvest = 750;
			this.expHarvest = 60;
			this.infoLabel = farmingPanel.main.menuPanel.tomatoLabel;

			break;

		case RICE:

			this.imgCount = 7;
			this.timeGrow = 1200 * UNIT_SECOND;
			this.leftTimeInMillis = this.timeGrow;
			this.timeGrowInterval = timeGrow / (imgCount - 1);
			this.timeAuto = 15 * UNIT_SECOND;
			this.coinAuto = farmingPanel.commonSeedInfo.getSeedLevel(name) + 6;
			this.coinHarvest = 4000;
			this.expHarvest = 100;
			this.infoLabel = farmingPanel.main.menuPanel.riceLabel;
			break;

		case EGGPLANT:

			this.imgCount = 7;
			this.timeGrow = 600 * UNIT_SECOND;
			this.leftTimeInMillis = this.timeGrow;
			this.timeGrowInterval = timeGrow / (imgCount - 1);
			this.timeAuto = 10 * UNIT_SECOND;
			this.coinAuto = farmingPanel.commonSeedInfo.getSeedLevel(name) + 5;
			this.coinHarvest = 1000;
			this.expHarvest = 200;
			this.infoLabel = farmingPanel.main.menuPanel.eggplantLabel;
			break;

		}
	}

	/**
	 * time을 시/분/초로 변환하여 반환한다.<br/>
	 * 단, time이 0일 경우에는 수확하기를 반환한다.
	 * 
	 * @param time 남은 성장 시간
	 * @return 변환한 시간을 문자열로 반환
	 */
	public String getRemainingTime(int time) {

		int minute, hour, second;
		second = time / 1000;
		minute = second / 60;
		hour = minute / 60;
		second %= 60;
		minute %= 60;

		if (hour == 0 && minute == 0 && second == 0) {
			return "수확하기";
		} else if (hour == 0 && minute == 0) {
			return second + "초 남음";
		} else if (hour == 0) {
			return minute + "분 " + second + "초 남음";
		}
		return hour + "시 " + minute + "분 " + second + "초 남음";
	}

	@Override
	public void run() {

		try {

			farmingPanel.fields[row][col].setVisible(false);
			harvestCheck = false;

			while (leftTimeInMillis > 0) {

				remainingTimeLabel.setText(getRemainingTime(leftTimeInMillis));
				path = String.format("image\\%s_0%d.png", seedName,
						(((timeGrow - leftTimeInMillis) / timeGrowInterval) + 1));

				repaint();
				if (leftTimeInMillis % timeAuto == 0 && leftTimeInMillis != timeGrow) {
					farmingPanel.calculateCoin(coinAuto);
				}
				Thread.sleep(1000);
				leftTimeInMillis -= 1000;

			}

			if (leftTimeInMillis < 0) {
				leftTimeInMillis = 0;
			}

			remainingTimeLabel.setText(getRemainingTime(leftTimeInMillis));

			growCheck = true;

			while (!harvestCheck) {

				path = String.format("image\\%s_0%d.png", seedName, imgCount - 1);
				repaint();
				Thread.sleep(200);
				path = String.format("image\\%s_0%d_harvest.png", seedName, imgCount - 1);
				repaint();
				Thread.sleep(500);

			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			farmingPanel.commonSeedInfo.increaseYield(seedName);
			infoLabel.seedLevelInfo.setText(infoLabel.setSeedStatus(seedName));
			((Field) farmingPanel.fields[row][col]).isEmptyField(true);
			farmingPanel.harvestCrops(expHarvest, coinHarvest);
			this.setVisible(false);
			farmingPanel.fields[row][col].setVisible(true);
			farmingPanel.fields[row][col].repaint();
		}

	}

	Seeds(FarmingPanel farmingField, String seedName) {

		this.farmingPanel = farmingField;

		setSeedName(seedName);
		initSeed(seedName);

		remainingTimeLabel = new JLabel();
		remainingTimeLabel.setBounds(0, 50, IMG_SEED_WIDTH, 20);
		remainingTimeLabel.setFont(new Font("맑은고딕", Font.BOLD, 15));
		remainingTimeLabel.setOpaque(true);
		remainingTimeLabel.setBackground(COLOR_BASE);
		remainingTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		add(remainingTimeLabel);

		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				if (getGrowCheck()) {
					harvestCheck = true;
				}
			}

			public void mouseEntered(MouseEvent e) {
				remainingTimeLabel.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				remainingTimeLabel.setVisible(false);
			}
		});

	}

	public void paintComponent(Graphics g) {
		g.drawImage(new ImageIcon(path).getImage(), 0, 0, IMG_SEED_WIDTH, IMG_SEED_HEIGHT, null);
	}

}