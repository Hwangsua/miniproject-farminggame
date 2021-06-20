package miniproject_game;

/**
 * 스킬 지속 시간을 설정하고 실행하는 Thread
 * 
 * @author SuaHwang
 */
public class SkillTimer implements Runnable {

	static final int UNIT_SECOND = 1000;
	final FarmingPanel farmingPanel;

	private int skillLevel;
	/**
	 * 스킬의 효과가 적용되는 시간
	 */
	private int buffTime;
	/**
	 * 감소되는 시간
	 */
	private int timeReduction;

	Seeds[][] seeds;

	/**
	 * level로 스킬 레벨를 설정한다.
	 * 
	 * @param level 레벨
	 */
	void setSkillLevel(int level) {
		this.skillLevel = level;
	}

	/**
	 * level에 맞게 스킬 효과를 설정한다.
	 * 
	 * @param level 스킬의 레벨
	 */
	void setBuff(int level) {
		switch (level) {
		case 1:
			this.buffTime = 5 * UNIT_SECOND;
			this.timeReduction = 2 * UNIT_SECOND;
			break;
		case 2:
			this.buffTime = 10 * UNIT_SECOND;
			this.timeReduction = 3 * UNIT_SECOND;
			break;
		case 3:
			this.buffTime = 15 * UNIT_SECOND;
			this.timeReduction = 5 * UNIT_SECOND;
			break;

		}

	}

	/**
	 * 심어진 모든 씨앗의 성장시간을 time만큼 추가적으로 감소시킨다.
	 * 
	 * @param time 감소되는 시간
	 */
	void addTimeReduction(int time) {
		int cnt = 0;
		for (int i = 0; i < seeds.length; i++) {
			for (int j = 0; j < seeds[i].length; j++) {
				if (seeds[i][j] != null) {
					seeds[i][j].setTimeReduction(time);
				}

			}
		}
	}

	@Override
	public void run() {

		try {
			for (int i = 0; i < buffTime / 1000; ++i) {
				Thread.sleep(1000);
				addTimeReduction(timeReduction);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		farmingPanel.callback();

	}

	public SkillTimer(FarmingPanel farmingPanel, Seeds[][] seeds, int level) {
		this.seeds = seeds;
		this.farmingPanel = farmingPanel;
		setSkillLevel(level);
		setBuff(level);
	}

}
