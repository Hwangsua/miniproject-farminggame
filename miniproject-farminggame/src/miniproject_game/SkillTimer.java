package miniproject_game;

/**
 * @author SuaHwang
 * 
 */
public class SkillTimer implements Runnable{

	private FarmingPanel farmingPanel;
	
	private int waterLevel; 
	private int buffTime; // 스킬 효과 시간
	private int timeReduction; // 재설정할 시간 감소값
	static final int UNIT_SECOND = 1000;
	Seeds[][] seeds;
	/*
	 	물 스킬 레벨 1 효과 : 5초동안 1초 -> 2초
	 	물 스킬 레벨 2 효과 : 10초동안 1초 -> 3초
	 	물 스킬 레벨 3 효과 : 15초 1초 -> 5초
	*/
	
	void setWaterLevel(int level) {
		this.waterLevel = level;
	}
	
	void setBuff(int level) { // 버프 설정
		switch (level) {
		case 1:  // 분무기
			this.buffTime = 5*UNIT_SECOND;
			this.timeReduction = 2*UNIT_SECOND;
			break;
		case 2: // 호스
			this.buffTime = 10*UNIT_SECOND;
			this.timeReduction = 3*UNIT_SECOND;
			break;
		case 3: // 드론 
			this.buffTime = 15*UNIT_SECOND;
			this.timeReduction = 5*UNIT_SECOND;
			break;
		
		}
		
	}
	
	void changeTimeReduction(int time) {
		int cnt = 0;
		for(int i=0; i < seeds.length ; i++) {
			for(int j=0; j < seeds[i].length; j++) {
				if(seeds[i][j] != null) {
					seeds[i][j].setTimeReduction(time); // 1초동안 감소하는 시간 변경 
				}
	
			}
		}
	}
	
	@Override
	public void run() {
		
		try {
			for(int i = 0; i < buffTime/1000; ++i) {
				changeTimeReduction(timeReduction);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			changeTimeReduction(1000); // 1초로 다시 바꾼다.
			
		}
		farmingPanel.callback();
		
	}
	
	public SkillTimer(FarmingPanel farmingPanel, Seeds[][] seeds, int level) {
		this.seeds = seeds;
		this.farmingPanel = farmingPanel;
		setWaterLevel(level);
		setBuff(level);
	}



}

