package miniproject_game;

/**
 * @author SuaHwang
 * @author EunkyungHwang
 */
class FieldInfo {

	static final int FIELD_CNT_MAX = 5;
	final MenuPanel menuPanel;
	// 사용자 레벨
	int userLevel;

	private static int LevelCriterion = 5;

	private static int extendFieldCnt = 0; // 확장된 밭의 개수

	private static int fieldBuyPrice; // 밭구매비용

	public static int getLevelCriterion() {
		return LevelCriterion;
	}

	public static void setLevelCriterion(int levelCriterion) {
		LevelCriterion = levelCriterion;
	}

	static int getExtendFieldCnt() {
		return extendFieldCnt;
	}

	static void setExtendFieldCnt(int openFieldCnt) {
		FieldInfo.extendFieldCnt = openFieldCnt;
	}

	FieldInfo(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
		fieldBuyPrice = 30000; // 구매 비용 : 3만원
	}

	boolean checkLevelCriterion() {
		userLevel = menuPanel.main.getUserLevel();
		if (userLevel >= LevelCriterion) { // 조건 레벨보다 크면 true
			return true;
		}
		return false;
	}

	// 땅구매 조건
	boolean checkfieldPurchase() {

		if (checkLevelCriterion() && menuPanel.main.checkCoin(fieldBuyPrice)) {
			return true;
		}
		return false;
	}

	void fieldPurchase() {
		if (checkfieldPurchase()) {
			menuPanel.main.reduceCoin(fieldBuyPrice);
			LevelCriterion += 5;
			fieldBuyPrice = extendFieldCnt < FIELD_CNT_MAX ? fieldBuyPrice : 0;
		}
	}
}
