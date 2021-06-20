package miniproject_game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static miniproject_game.CommonValue.*;

/**
 * 씨앗 정보 객체<br/>
 * 씨앗의 가격, 레벨, 수확량을 설정 및 확인 할 수 있다.
 * 
 * @author SuaHwang
 *
 */
public class SeedInfo {

	final FarmingPanel farmingPanel;
	/**
	 * 농작물 정보 map <br/>
	 * key는 씨앗의 이름이 들어간다. value는 list로 list는 다음과 같은 정보를 갖는다. (0)씨앗의 가격, (1)레벨,
	 * (2)수확량
	 */
	private static final HashMap<String, List<Integer>> CROPS_MAP;

	static {
		CROPS_MAP = new HashMap<String, List<Integer>>();
		CROPS_MAP.put(TOMATO, Arrays.asList(300, 1, 100));
		CROPS_MAP.put(RICE, Arrays.asList(400, 1, 100));
		CROPS_MAP.put(EGGPLANT, Arrays.asList(500, 1, 100));
	}

	SeedMenuLabel infoLabel;

	List<Integer> list;
	/**
	 * 업그레이드를 위한 수확량 기준
	 */
	private int upgradeYield;

	/**
	 * 씨앗 레벨
	 */
	private int seedLevel;

	/**
	 * name을 가진 씨앗 정보를 list에 저장한다.
	 * 
	 * @param name
	 */
	void setList(String name) {
		list = CROPS_MAP.get(name);
	}

	/**
	 * name의 씨앗 정보를 갱신한다.
	 * 
	 * @param name 씨앗의 이름
	 */
	void setSeedInfoLabel(String name) {

		switch (name) {
		case TOMATO:
			infoLabel = farmingPanel.main.menuPanel.tomatoLabel;
			infoLabel.seedLevelInfo.setText(infoLabel.setSeedStatus(TOMATO));
			break;
		case RICE:
			infoLabel = farmingPanel.main.menuPanel.riceLabel;
			infoLabel.seedLevelInfo.setText(infoLabel.setSeedStatus(RICE));
			break;
		case EGGPLANT:
			infoLabel = farmingPanel.main.menuPanel.eggplantLabel;
			infoLabel.seedLevelInfo.setText(infoLabel.setSeedStatus(EGGPLANT));
			break;
		}
	}

	private static void setCropsList(String name, List<Integer> list) {
		CROPS_MAP.replace(name, list);
	}

	public static void setTomatoList(List<Integer> tomatoList) {
		setCropsList(TOMATO, tomatoList);
	}

	public static void setRiceList(List<Integer> riceList) {
		setCropsList(RICE, riceList);
	}

	public static void setEggplantList(List<Integer> eggplantList) {
		setCropsList(EGGPLANT, eggplantList);
	}

	public static List<Integer> getTomatoList() {
		return CROPS_MAP.get(TOMATO);
	}

	public static List<Integer> getRiceList() {
		return CROPS_MAP.get(RICE);
	}

	public static List<Integer> getEggplantList() {
		return CROPS_MAP.get(EGGPLANT);
	}

	/**
	 * name에 해당하는 씨앗의 레벨을 증가한다.
	 * 
	 * @param name 씨앗의 이름
	 */
	void upSeedLevel(String name) {
		setList(name);
		list.set(1, list.get(1) + 1);
	}

	/**
	 * name에 해당하는 씨앗의 수확량을 증가한다.
	 * 
	 * @param name 씨앗의 이름
	 */
	void increaseYield(String name) {
		setList(name);
		list.set(2, list.get(2) + 1);
	}

	/**
	 * name에 해당하는 씨앗의 레벨업 가능 여부를 확인하고 레벨업을 진행한다.
	 * 
	 * @param name 씨앗의 이름
	 * @return 씨앗 레벨을 올렸으면 true, 그렇지 않으면 false
	 * @see upSeedLevel(String name)
	 */
	boolean checkSeedLevelUp(String name) {
		if (checkYield(name)) {
			upSeedLevel(name);
			SoundsClip.play(SoundsClip.SUCCESS_SOUND);
			return true;
		}
		return false;
	}

	/**
	 * name에 해당하는 씨앗의 수확량이 레벨업 조건인 수확량을 만족하는지 확인한다.
	 * 
	 * @param name 씨앗의 이름
	 * @return 수확량 만족하면 true, 그렇지 않으면 false
	 */
	boolean checkYield(String name) {
		setList(name);
		if (getUpgradeYield(name) > list.get(2)) {
			FarmerMessage.getLackYieldMsg();
			return false;
		}
		return true;
	}

	/**
	 * name에 해당하는 씨앗의 가격을 반환한다.
	 * 
	 * @param name 씨앗의 이름
	 * @return 씨앗 가격
	 */
	int getSeedPrice(String name) {
		setList(name);
		return list.get(0);
	}

	/**
	 * name에 해당하는 씨앗의 레벨을 반환한다.
	 * 
	 * @param name 씨앗의 이름
	 * @return 씨앗 레벨
	 */
	int getSeedLevel(String name) {
		setList(name);
		return list.get(1);
	}

	/**
	 * name에 해당하는 씨앗의 총 수확량을 반환한다.
	 * 
	 * @param name 씨앗의 이름
	 * @return 씨앗의 총 수확량
	 */
	int getSeedTotalYield(String name) {
		setList(name);
		return list.get(2);
	}

	/**
	 * name에 해당하는 씨앗의 레벨업(업그레이드)을 위해 만족해야하는 수확량을 반환한다.
	 * 
	 * @param name 씨앗의 이름
	 * @return 만족해야하는 수확량 반환
	 */
	int getUpgradeYield(String name) {
		this.seedLevel = getSeedLevel(name);
		switch (name) {
		case "tomato":
			this.upgradeYield = 10 * (int) (Math.pow(seedLevel, 2));
			break;
		case "rice":
			this.upgradeYield = 20 * (int) (Math.pow(seedLevel, 2));
			break;
		case "eggplant":
			this.upgradeYield = 30 * (int) (Math.pow(seedLevel, 2));
			break;
		}
		return this.upgradeYield;
	}

	SeedInfo(FarmingPanel farmingPanel) {
		this.farmingPanel = farmingPanel;
	}

}
