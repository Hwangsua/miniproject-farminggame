package miniproject_game;

import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JFrame;

/**
 * MainFrame <br/>
 * 본 프레임과 다른 객체들을 연결하는 컨트롤러역할을 한다.<br/>
 * WindowListener를 포함한다.
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 *
 */

public class MainFrame extends JFrame implements Serializable, WindowListener {

	final String userID;

	/**
	 * MainFrame 의 폭
	 */
	public static final int MAIN_FRAME_WIDTH = 1280;
	/**
	 * MainFrame 의 넓이
	 */
	public static final int MAIN_FRAME_HEIGHT = 720;

	FarmingPanel farmingField;
	MenuPanel menuPanel;

	private static String mainSeedName;

	/**
	 * MainFrame 의 생성자<br>
	 * WindowListener를 add().
	 * 
	 * @param userID
	 */
	MainFrame(String userID) {

		this.userID = userID;
		farmingField = new FarmingPanel(this);
		menuPanel = new MenuPanel(this);

		setLayout(null);
		setTitle("귀농하자");
		setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		initializeComponent();
		setVisible(true);
		setLocationRelativeTo(null);

		this.addWindowListener(this);
	}

	/**
	 * FarmingPanel 및 MenuPanel을 MainFrame 에 add().
	 */
	private void initializeComponent() {

		farmingField.setBounds(0, 0, FarmingPanel.FARMING_PANEL_WIDTH, MAIN_FRAME_HEIGHT);
		menuPanel.setBounds(FarmingPanel.FARMING_PANEL_WIDTH, 0, 400, MAIN_FRAME_HEIGHT);

		add(farmingField);
		add(menuPanel);

		menuPanel.setMenu01Mouse(menuPanel.menu1, menuPanel.enterseedIcon, menuPanel.seedIcon);
		menuPanel.setMenu3Mouse(menuPanel.menu3, menuPanel.enterseedIcon);

		this.setLocation(new Point(0, 0));
	}

	/**
	 * 
	 * {@link FarmingPanel} 객체에 대한 user의 레벨을 반환한다.
	 * 
	 * @return user의레벨
	 */
	public int getUserLevel() {
		return farmingField.getUserLevel();
	}

	/**
	 * 
	 * {@link FarmingPanel} 객체에 대한 coin이 충분한지 확인한다.
	 * 
	 * @param cost 비용(지출 코인)
	 * @return cost를 지불할 coin이 부족하면 false, 그렇지 않으면 true
	 */
	public boolean checkCoin(int cost) {
		if (farmingField.getCoin() < cost) {
			FarmerMessage.getinsufficientCoinMsg();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * {@link FarmingPanel} 객체에 대한 coin을 계산한다.
	 * 
	 * @param cost 비용(수입/지출 될 코인)
	 */
	public void reduceCoin(int cost) {
		farmingField.calculateCoin(-cost);
	}

	/**
	 * 
	 * MainFrame 에서 씨앗 이름을 설정한다.
	 * 
	 * @param string 씨앗이름
	 */
	public void setPlantSeed(String string) {
		mainSeedName = string;
	}

	/**
	 * 
	 * MainFrame에서 설정한 씨앗 이름을 반환환다.
	 * 
	 * @return 씨앗이름
	 */
	public String getPlantSeed() {
		return this.mainSeedName;
	}

	/**
	 * {@link FarmingPanel} 객체에 메소드를 이용하여 밭을 추가로 생성한다.
	 * 
	 * @param col 추가할 밭의 열
	 */
	public void createField(int col) {
		farmingField.addField(col);
	} 

	/**
	 * 
	 * {@link FarmingPanel} 객체에 메소드를 이용하여 스킬을 실행한다.
	 * 
	 * @param skillLevel 스킬레벨
	 */
	public void setWaterLevel(int skillLevel) {
		farmingField.runWaterSkill(skillLevel);
	}

	/**
	 * 
	 * {@link MenuPanel} 객체에 이미지 아이콘을 반환한다.
	 * 
	 * @return enterseedIcon 이미지아이콘
	 */
	public Icon getEnterseedIcon() {
		return menuPanel.enterseedIcon;
	}

	/**
	 * 윈도우창을 열 때 {@link FileUtils}의 파일 load 메소드를 실행한다.
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		FileUtils.load(this, userID);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	/**
	 * 윈도우 창 종료시 {@link FileUtils}의 파일 save 메소드를 실행한다.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		FileUtils.save(this, userID);
	}

}