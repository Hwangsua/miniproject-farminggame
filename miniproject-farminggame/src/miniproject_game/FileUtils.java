package miniproject_game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * @author SuaHwang
 *
 */
public class FileUtils {

	public static final String USER_LEVEL = "userLevel";
	public static final String COIN = "coin";
	public static final String CURRENT_EXP = "currentExp";
	public static final String TOMATO_LIST = "tomatoList";
	public static final String RICE_LIST = "riceList";
	public static final String EGGPLANT_LIST = "eggplantList";
	public static final String SKILL_LV1 = "buySkill1";
	public static final String SKILL_LV2 = "buySkill2";
	public static final String SKILL_LV3 = "buySkill3";
	public static final String FIELD_CNT = "extendFieldCnt";
	public static final String FIELD_LEVEL = "fieldLevel";
	public static final String SEEDS = "seeds";
	public static final String SEEDS_NAME = "seedsName";
	public static final String TIME_SAVE = "saveTime";

	public static final void save(MainFrame main, String id) {
		try (FileOutputStream fOut = new FileOutputStream(id + ".dat");
				ObjectOutputStream oOut = new ObjectOutputStream(fOut)) {

			HashMap<String, Object> h = new HashMap<>();

			// 레벨, 코인, 경험치 저장
			h.put(USER_LEVEL, main.farmingField.getUserLevel()); // 유저레벨
			h.put(COIN, main.farmingField.getCoin()); // 코인
			h.put(CURRENT_EXP, main.farmingField.getCurrentExp()); // 경험치

			// 씨앗 정보 저장
			h.put(TOMATO_LIST, SeedInfo.getTomatoList());
			h.put(RICE_LIST, SeedInfo.getRiceList());
			h.put(EGGPLANT_LIST, SeedInfo.getEggplantList());

			// 물스킬 정보 저장
			h.put(SKILL_LV1, main.menuPanel.isBuySkill1());
			h.put(SKILL_LV2, main.menuPanel.isBuySkill2());
			h.put(SKILL_LV3, main.menuPanel.isBuySkill3());

			// 밭 추가 확장한 개수 저장
			h.put(FIELD_CNT, FieldInfo.getExtendFieldCnt());
			h.put(FIELD_LEVEL, FieldInfo.getLevelCriterion());

			// 씨앗 시간 저장하기
			h.put(SEEDS, main.farmingField.getSeeds());
			h.put(SEEDS_NAME, main.farmingField.getSeedName());

			h.put(TIME_SAVE, System.currentTimeMillis());

			oOut.writeObject(h);

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

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
