package miniproject_game;

import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JFrame;

/**
 * 본 프로그램의 Main 프레임<br>
 * @author SuaHwang
 * @author EunkyungHwang
 *
 */

public class MainFrame extends JFrame implements Serializable, WindowListener{

	final String userID;

	public static final int MAIN_FRAME_WIDTH = 1280;
	public static final int MAIN_FRAME_HEIGHT = 720;

	FarmingPanel farmingField;
	MenuPanel menuPanel;

	private static String mainSeedName;

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

	private void initializeComponent() {

		farmingField.setBounds(0, 0, FarmingPanel.FARMING_PANEL_WIDTH, MAIN_FRAME_HEIGHT);
		menuPanel.setBounds(FarmingPanel.FARMING_PANEL_WIDTH, 0, 400, MAIN_FRAME_HEIGHT);

		add(farmingField);
		add(menuPanel);

		// 메뉴 1,3 클릭시 마우스리스너구현
		menuPanel.setMenu01Mouse(menuPanel.menu1, menuPanel.enterseedIcon, menuPanel.seedIcon);
		menuPanel.setMenu3Mouse(menuPanel.menu3, menuPanel.enterseedIcon);

		this.setLocation(new Point(0, 0));
	}

	public int getUserLevel() {
		return farmingField.getUserLevel();
	}

	public boolean checkCoin(int cost) { // 값을 지불할 수 있는지 확인
		if (farmingField.getCoin() < cost) { // 코인 부족할 경우 false
			FarmerMessage.getinsufficientCoinMsg();
			return false;
		}
		return true;
	}

	public void reduceCoin(int cost) { // 값을 지불
		farmingField.calculateCoin(-cost);
	}

	public void setPlantSeed(String string) { // 씨앗 이름 set
		mainSeedName = string;
	}

	public String getPlantSeed() {
		return this.mainSeedName;
	}

	public void createField(int col) { // 밭 생성
		farmingField.addField(col);
	}

	public void setWaterLevel(int skillLevel) { // 물 스킬레벨 set
		farmingField.runWaterSkill(skillLevel); // 물 스킬 실행
	}

	public Icon getEnterseedIcon() {
		return menuPanel.enterseedIcon;
	}

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

	@Override
	public void windowClosing(WindowEvent e) {
		FileUtils.save(this,userID);
	}

}
