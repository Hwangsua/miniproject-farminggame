package miniproject_game;

/**
 * 밭정보를 포함하는 객체
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 */
class FieldInfo {
	/**
	 * 확장 할 수 있는 밭의 최대 갯수
	 */
	static final int FIELD_CNT_MAX = 5;
	final MenuPanel menuPanel;
	/**
	 * 사용자레벨
	 */
	int userLevel;
	/**
	 * 밭 확장을 위한 조건레벨
	 */
	private static int LevelCriterion = 5;
	/**
	 * 현재 확장된 밭의 갯수
	 */
	private static int extendFieldCnt = 0;
	/**
	 * 밭 구매가격
	 */
	private static int fieldBuyPrice;

	/**
	 * 밭확장에 필요한 기준 레벨을 반환한다.
	 * 
	 * @return 기준레벨
	 */
	public static int getLevelCriterion() {
		return LevelCriterion;
	}

	/**
	 * 밭확장에 필요한 기준 레벨을 설정한다.
	 * 
	 * @param levelCriterion 기준레벨
	 */
	public static void setLevelCriterion(int levelCriterion) {
		LevelCriterion = levelCriterion;
	}

	/**
	 * 확장된 밭의 갯수를 반환한다.
	 * 
	 * @return 밭의 갯수
	 */
	static int getExtendFieldCnt() {
		return extendFieldCnt;
	}

	/**
	 * 확장된 밭의 갯수를 설정한다.
	 * 
	 * @param openFieldCnt 확장된 밭의 갯수
	 */
	static void setExtendFieldCnt(int openFieldCnt) {
		FieldInfo.extendFieldCnt = openFieldCnt;
	}

	FieldInfo(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
		fieldBuyPrice = 30000;
	}

	/**
	 * 유저레벨이 밭구매 레벨조건을 확인한다.
	 * 
	 * @return 조건에 충족하면 true, 그렇지않으면 false
	 */
	boolean checkLevelCriterion() {
		userLevel = menuPanel.main.getUserLevel();
		if (userLevel >= LevelCriterion) {
			return true;
		}
		return false;
	}

	/**
	 * 밭 구매여부를 반환한다.
	 * 
	 * @return 밭구매 성공하면 true, 그렇지않으면 false
	 */
	boolean checkfieldPurchase() {

		if (checkLevelCriterion() && menuPanel.main.checkCoin(fieldBuyPrice)) {
			return true;
		}
		return false;
	}

	/**
	 * 조건 확인 후 밭구매를 실행 후 밭구매 기준레벨을 올린다.
	 */
	void fieldPurchase() {
		if (checkfieldPurchase()) {
			menuPanel.main.reduceCoin(fieldBuyPrice);
			LevelCriterion += 5;
			fieldBuyPrice = extendFieldCnt < FIELD_CNT_MAX ? fieldBuyPrice : 0;
		}
	}
}