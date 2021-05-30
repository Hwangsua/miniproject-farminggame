package miniproject_game;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static miniproject_game.CommonValue.*;
/**
 * 
 * @author SuaHwang
 *
 */
public class SeedInfo{
	
	final FarmingPanel farmingPanel;

	private static final HashMap<String, List<Integer>> CROPS_MAP; // 농작물 MAP
	
	static {
		// list => (0)씨앗 가격, (1)레벨, (2)수확량
		CROPS_MAP = new HashMap<String, List<Integer>>();
		CROPS_MAP.put(TOMATO, Arrays.asList(300,1,100));
		CROPS_MAP.put(RICE, Arrays.asList(400,1,100));
		CROPS_MAP.put(EGGPLANT, Arrays.asList(500,1,100));
	}
	
	
	SeedMenuLabel infoLabel;
	
	List<Integer> list;
	
	private int upgradeYield; // 업그레이드를 위한 수확량 기준
	private int seedUpgradePrice;  // 씨앗 업그레이드 비용
	private int seedLevel;
	
	
	void setList(String name){
		list = CROPS_MAP.get(name);
	}
	
	
	/////////// 파일 load ////////////////////////////////// 
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
	/////////// 파일 load ////////////////////////////////// 	

	void upSeedLevel(String name) { // 씨앗 레벨업
		setList(name);
		list.set(1, list.get(1)+1);
	}
	
	void increaseYield(String name) { // 씨앗 수확량 증가
		setList(name);
		list.set(2, list.get(2)+1);
	}
	
	boolean checkSeedLevelUp(String name) { // 레벨업 가능한지 확인하고 레벨업시킨다.
		if(checkYield(name)){
			 upSeedLevel(name);
			 SoundsClip.play(SoundsClip.SUCCESS_SOUND);
		     return true;
		}
		return false;
	}
	
	boolean checkYield(String name) {
		setList(name);
		if(getUpgradeYield(name) > list.get(2)) { // 수확량 부족할 경우 false
	         FarmerMessage.getLackYieldMsg();
	         return false;
		}
		return true;
	}
	
	SeedInfo(FarmingPanel farmingPanel) {
		this.farmingPanel = farmingPanel;		
	}
	
	
	int getSeedPrice(String name) { // 씨앗 가격 get
		setList(name);
		return list.get(0);
	}
	
	int getSeedLevel(String name) { // 씨앗 레벨 get
		setList(name);
		return list.get(1);
	}
   

	int getSeedTotalYield(String name) { // 씨앗 총 수확량 get
		setList(name);
		return list.get(2);
	}
	
   int getUpgradePrice(String name) { // 씨앗 업그레이드 가격 정하는 부분
	   this.seedLevel = getSeedLevel(name);
	   this.seedUpgradePrice = seedLevel*50000; // 1 -> 2 비용 : 5만원
	   return this.seedUpgradePrice;
   }

   int getUpgradeYield(String name) { // 씨앗 업그레이드를 위한 수확량 
	   this.seedLevel = getSeedLevel(name);
	   switch (name) {
	      case "tomato":
	    	  this.upgradeYield= 10*(int)(Math.pow(seedLevel, 2));
	    	  break;
	      case "rice":
	    	  this.upgradeYield=  20*(int)(Math.pow(seedLevel, 2));
	          break;
	      case "eggplant":
	    	  this.upgradeYield =  30*(int)(Math.pow(seedLevel, 2));
	          break;
	      }
	   return this.upgradeYield;
   }
	
}



