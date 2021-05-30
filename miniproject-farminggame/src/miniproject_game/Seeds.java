package miniproject_game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static miniproject_game.CommonValue.*;

/**
 * @author SuaHwang
 * 
 */
public class Seeds extends JLabel implements Runnable{
	
	final FarmingPanel farmingPanel;
	
	static final int IMG_SEED_WIDTH = 130; // 토마토 이미지 가로 길이
	static final  int IMG_SEED_HEIGHT = 150; // 토마토 이미지 세로 길이 
	static final int UNIT_SECOND = 1000; // 1초 
	
	private String seedName; // 씨앗 이름
	
	private int imgCount; // 이미지 총 개수 
	
	private int timeGrow; // 성장 시간
	private int timeGrowInterval; // 해당 초마다 이미지 변경 (성장 모습)
	private int timeAuto; // 해당 초마다 자동 코인 증가
	private int timeReduction = 1000; // 시간 감소하는 값
	
	private int coinAuto; // 농작물 생성 시 자동으로 오르는 코인의 값
	private int coinHarvest; // 수확 시 얻는 코인의 값
	private int expHarvest; // 수확 시 얻는 경험치

	int row, col;
	
	String path;
	int leftTimeInMillis;

	boolean growCheck = false; // 성장완료했는지 확인 
	boolean harvestCheck = false; // 수확가능한지 확인
	
	JLabel remainingTimeLabel;
	
	SeedMenuLabel infoLabel;
	
	void setSeedName(String name) { // 씨앗 이름 
		this.seedName = name;
	}
	
	String getSeedName() {
		return seedName;
	}
	
	void setLeftTimeInMillis(int time) {
		this.leftTimeInMillis = time;
	}
	
	int getLeftTimeInMillis() {
		return leftTimeInMillis;
	}
	
	void initSeed(String name) { // 씨앗 초기화 
		switch (name) {
		case TOMATO: 
			this.imgCount = 7;
			this.timeGrow = 10*UNIT_SECOND; // 3분
			this.leftTimeInMillis = this.timeGrow;
			this.timeGrowInterval = timeGrow/(imgCount-1);
			this.timeAuto = 5*UNIT_SECOND; // 5초
			this.coinAuto = farmingPanel.commonSeedInfo.getSeedLevel(name) + 1; // 레벨1 : 2코인씩 증가
			this.coinHarvest = 750;
			this.expHarvest = 60;
			this.infoLabel = farmingPanel.main.menuPanel.tomatoLabel;
			
			break;
			
		case RICE:
			
			this.imgCount = 7;
			this.timeGrow = 30*UNIT_SECOND; // 20분
			this.leftTimeInMillis = this.timeGrow;
			this.timeGrowInterval = timeGrow/(imgCount-1);
			this.timeAuto = 15*UNIT_SECOND; // 15초
			this.coinAuto = farmingPanel.commonSeedInfo.getSeedLevel(name) + 6; // 레벨1 : 7코인씩 증가 
			this.coinHarvest = 4000;
			this.expHarvest = 100;
			this.infoLabel = farmingPanel.main.menuPanel.riceLabel;
			break;
			
		case EGGPLANT:
		
			this.imgCount = 7;
			this.timeGrow = 20*UNIT_SECOND; // 10분
			this.leftTimeInMillis = this.timeGrow;
			this.timeGrowInterval = timeGrow/(imgCount-1);
			this.timeAuto = 10*UNIT_SECOND; // 10초
			this.coinAuto = farmingPanel.commonSeedInfo.getSeedLevel(name) + 5; // 레벨1 : 6코인씩 증가 
			this.coinHarvest = 1000;
			this.expHarvest = 200;
			this.infoLabel = farmingPanel.main.menuPanel.eggplantLabel;
			break;
	
		}
	}
	
	public void setTimeReduction(int time) {
		leftTimeInMillis -= time;
	}
	
	
	public boolean getGrowCheck() {
		return growCheck;
	}
	
	@Override
	public void run() {
		
		try {

			farmingPanel.fields[row][col].setVisible(false); // 밭 표시 x
			harvestCheck = false; // 수확 불가능
			
			
			while(leftTimeInMillis > 0) {
				
				remainingTimeLabel.setText(getRemainingTime(leftTimeInMillis));
				path = String.format("image\\%s_0%d.png", seedName,(((timeGrow-leftTimeInMillis)/ timeGrowInterval)+1));
				
				repaint();
				if(leftTimeInMillis % timeAuto == 0 && leftTimeInMillis != timeGrow) { // timeAuto마다 코인 증가 
					farmingPanel.calculateCoin(coinAuto);
				}
				Thread.sleep(1000); 
				leftTimeInMillis -= 1000;
				
			}
			
			if(leftTimeInMillis < 0) {leftTimeInMillis = 0;} // 음수로 시간나오는 것을 방지
			
			remainingTimeLabel.setText(getRemainingTime(leftTimeInMillis));
			
			growCheck = true; // 다 성장함
			
			while(!harvestCheck) {
				
				path = String.format("image\\%s_0%d.png",  seedName, imgCount-1);
				repaint();
				Thread.sleep(200); // 200 
				path = String.format("image\\%s_0%d_harvest.png", seedName, imgCount-1 );
				repaint();
				Thread.sleep(500); // 200 
	
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			farmingPanel.commonSeedInfo.increaseYield(seedName); // 수확량 증가 
			infoLabel.seedLevelInfo.setText(infoLabel.setSeedStatus(seedName));
			((Field)farmingPanel.fields[row][col]).isEmptyField(true); // 밭 비움으로 표시 
			farmingPanel.harvestCrops(expHarvest,coinHarvest);
			this.setVisible(false); 
			farmingPanel.fields[row][col].setVisible(true); // 밭 표시 o
			farmingPanel.fields[row][col].repaint();
		}
		
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(new ImageIcon(path).getImage(), 0, 0,  IMG_SEED_WIDTH,  IMG_SEED_HEIGHT, null);
	}
     
	public String getRemainingTime(int time) { // 남은 시간 시/분/초로 변환
		
		int minute, hour, second;
		second = time/1000; // 몇 초인지
		minute = second/60; // 몇 분인지
		hour = minute/60; // 몇 시간인지
		second %= 60;
		minute %=60;
		
		if(hour == 0 && minute == 0 && second == 0) {
			return "수확하기";
		}else if(hour == 0 && minute == 0) {
			return second+"초 남음";
		}else if(hour == 0) {
			return minute+"분 "+second+"초 남음";
		}
		return hour+"시 "+minute+"분 "+second+"초 남음";
	}
	
	Seeds(FarmingPanel farmingField, String seedName){
		
		
		this.farmingPanel = farmingField;

		setSeedName(seedName); 
		initSeed(seedName);
		
		remainingTimeLabel  = new JLabel();
		remainingTimeLabel.setBounds(0,50, IMG_SEED_WIDTH,20);
		remainingTimeLabel.setFont(new Font("맑은고딕", Font.BOLD, 15));
		remainingTimeLabel.setOpaque(true);
		remainingTimeLabel.setBackground(COLOR_BASE);
		remainingTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		add(remainingTimeLabel);
		
		this.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) { // 마우스로 눌렀을 때
		
				if(getGrowCheck()) { // 다 성장했으면 수확 가능 => true 
					harvestCheck = true;
				}
			}
			// 마우스가 해당 컴포넌트 영역 안으로 들어올 때 발생
			public void mouseEntered(MouseEvent e) {
				remainingTimeLabel.setVisible(true);
			}

			// 마우스가 해당 컴포넌트 영역 밖으로 나갈때 발생
			public void mouseExited(MouseEvent e) {
				remainingTimeLabel.setVisible(false);
			}
		});
		

	}

}