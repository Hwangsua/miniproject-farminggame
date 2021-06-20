package miniproject_game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 게임 데이터를 관리하는 클래스<br/>
 * 다음 아래와 같은 정보를 저장하고 불러온다.
 * <ul>
 * <li>유저의 레벨, 보유하고 있는 코인, 경험치
 * <li>씨앗의 정보(레벨,가격, 수확량)
 * <li>스킬 정보(구매 여부)
 * <li>밭 정보(현재 열린 밭의 개수, 열리지않은 밭의 레벨 조건)
 * <li>밭에 심어져있는 씨앗의 정보(이름, 남은 시간)
 * <li>파일 저장한 시간과 불러오기한 시간
 * </ul>
 * 
 * @author SuaHwang
 */
public class FileUtils {
	/**
	 * user의 현재 레벨
	 */
	public static final String USER_LEVEL = "userLevel";
	/**
	 * user의 현재 보유하고 있는 코인
	 */
	public static final String COIN = "coin";
	/**
	 * user의 현재 경험치
	 */
	public static final String CURRENT_EXP = "currentExp";
	/*
	 * 토마토 정보 리스트
	 */
	public static final String TOMATO_LIST = "tomatoList";
	/**
	 * 벼 정보 리스트
	 */
	public static final String RICE_LIST = "riceList";
	/**
	 * 가지 정보 리스트
	 */
	public static final String EGGPLANT_LIST = "eggplantList";
	/**
	 * 스킬(레벨1) 구매여부
	 */
	public static final String SKILL_LV1 = "buySkill1";
	/**
	 * 스킬(레벨2) 구매여부
	 */
	public static final String SKILL_LV2 = "buySkill2";
	/**
	 * 스킬(레벨3) 구매여부
	 */
	public static final String SKILL_LV3 = "buySkill3";
	/**
	 * 현재 확장한 밭 개수
	 */
	public static final String FIELD_CNT = "extendFieldCnt";
	/**
	 * 밭 확장 기준 레벨
	 */
	public static final String FIELD_LEVEL = "fieldLevel";
	/**
	 * 기존에 심어둔 씨앗의 정보
	 */
	public static final String SEEDS = "seeds";
	/**
	 * 기존에 심어둔 씨앗의 정보
	 */
	public static final String SEEDS_NAME = "seedsName";
	/**
	 * 파일 저장 시간
	 */
	public static final String TIME_SAVE = "saveTime";

	/**
	 * 파일에 게임 데이터를 저장한다.
	 * 
	 * @param main MainFrame
	 * @param id   user의 id
	 */
	public static final void save(MainFrame main, String id) {
		try (FileOutputStream fOut = new FileOutputStream(id + ".dat");
				ObjectOutputStream oOut = new ObjectOutputStream(fOut)) {

			HashMap<String, Object> h = new HashMap<>();

			h.put(USER_LEVEL, main.farmingField.getUserLevel());
			h.put(COIN, main.farmingField.getCoin());
			h.put(CURRENT_EXP, main.farmingField.getCurrentExp());

			h.put(TOMATO_LIST, SeedInfo.getTomatoList());
			h.put(RICE_LIST, SeedInfo.getRiceList());
			h.put(EGGPLANT_LIST, SeedInfo.getEggplantList());

			h.put(SKILL_LV1, main.menuPanel.isBuySkill1());
			h.put(SKILL_LV2, main.menuPanel.isBuySkill2());
			h.put(SKILL_LV3, main.menuPanel.isBuySkill3());

			h.put(FIELD_CNT, FieldInfo.getExtendFieldCnt());
			h.put(FIELD_LEVEL, FieldInfo.getLevelCriterion());

			h.put(SEEDS, main.farmingField.getSeeds());
			h.put(SEEDS_NAME, main.farmingField.getSeedName());

			h.put(TIME_SAVE, System.currentTimeMillis());

			oOut.writeObject(h);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * 파일에서 게임 데이터 불러온다.
	 * 
	 * @param main MainFrame
	 * @param id   user의 id
	 */
	public static final void load(MainFrame main, String id) {

		try (FileInputStream fIn = new FileInputStream(id + ".dat");
				ObjectInputStream oIn = new ObjectInputStream(fIn)) {

			HashMap<String, Object> h = (HashMap<String, Object>) oIn.readObject();

			main.farmingField.setSaveTime((long) h.get(TIME_SAVE));

			main.farmingField.setUserLevel((int) h.get(USER_LEVEL));
			main.farmingField.setCoin((int) h.get(COIN));
			main.farmingField.setCurrentExp((int) h.get(CURRENT_EXP));

			SeedInfo.setTomatoList((List<Integer>) h.get(TOMATO_LIST));
			main.farmingField.commonSeedInfo.setSeedInfoLabel("tomato");
			SeedInfo.setRiceList((List<Integer>) h.get(RICE_LIST));
			main.farmingField.commonSeedInfo.setSeedInfoLabel("rice");
			SeedInfo.setEggplantList((List<Integer>) h.get(EGGPLANT_LIST));
			main.farmingField.commonSeedInfo.setSeedInfoLabel("eggplant");

			main.menuPanel.setBuySkill1((boolean) h.get(SKILL_LV1));
			main.menuPanel.setBuySkill2((boolean) h.get(SKILL_LV2));
			main.menuPanel.setBuySkill3((boolean) h.get(SKILL_LV3));

			FieldInfo.setExtendFieldCnt((int) h.get(FIELD_CNT));
			main.farmingField.setField((int) h.get(FIELD_CNT));
			FieldInfo.setLevelCriterion((int) h.get(FIELD_LEVEL));

			main.farmingField.setSeedName((String[]) h.get(SEEDS_NAME));
			main.farmingField.setSeeds((int[][]) h.get(SEEDS));

		} catch (Exception e) {

		}
	}
}
