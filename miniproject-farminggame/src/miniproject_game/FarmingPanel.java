package miniproject_game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import static miniproject_game.CommonValue.*;

/**
 * 이 클래스는 
 * @author SuaHwang
 *
 */
public class FarmingPanel extends JPanel {

	final MainFrame main;

	static final int FARMING_PANEL_WIDTH = 880; // this JPanel의 가로폭
	static final int FARMING_PANEL_HEIGHT = 720; // this JPanel의 세로폭
	static final int EXPBAR_MAX = 300; // 최대 경험치

	private ExpBarGraphic expBar;
	private JLabel levelLabel;
	private JLabel coinLabel;
	private JLabel expDisplay;
	private JButton questionBtn;

	private Image background = new ImageIcon("image\\mainBackground.png").getImage(); // 배경 이미지
	private ImageIcon question = new ImageIcon("image\\question.png"); // 도움말 버튼 이미지

	// 레벨, 경험치
	private int userLevel = 99;
	private int currentExp = 25000; // 현재 경험치
	private int maxExp = 500 * userLevel;

	// 코인
	private int coin = 3060000; // 기본 코인 3000 초기화
	DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###"); // 금액에 콤마 , 표시

	// 밭
	JLabel[][] fields = new Field[2][5];
	int selectFieldX, selectFieldY; // 검색한 밭의 시작 x, y 좌표 기록
	private int selectRow, selectCol; // 검색한 밭의 행, 열
	private static boolean basicFieldCreate = false; // 밭 한번만 생성되는지 확인

	// 씨앗
	Seeds[][] seeds = new Seeds[2][5];
	SeedInfo commonSeedInfo; // 공통적으로 필요한 씨앗 정보
	String[] saveSeedsName = new String[seeds.length * seeds[0].length]; // 씨앗 이름 저장용 배열

	long beforeTime; // 마지막으로 파일 저장한 시간
	private SkillTimer th; // 스킬 타이머

	/**
	 * 각 밭에 어떤 씨앗이 심어져있는지 문자열 배열로 저장한다.
	 * @param i	몇 번째 밭의 씨앗인지 나타낸 인덱스값 (0~9)
	 * @param name	씨앗의 이름
	 */
	public void setSeedNameSave(int i, String name) {
		saveSeedsName[i] = name;
	}
	
	/**
	 * 파일을 저장하는 용도로 사용한다.
	 * 각 밭에 어떤 씨앗이 심어졌는지 문자열 배열로 씨앗이름들을 얻는다.
	 * @return 
	 */
	// 파일 save용 (getSeeds 한 뒤에 실행해야한다.)
	public String[] getSeedName() {
		return saveSeedsName;
	}

	
	// 파일 load (setSeeds 하기 전에 먼저 실행해야한다.)
	/**
	 * 파일을 불러오는 용도로 사용한다.
	 * 
	 * @param seedNameSave
	 */
	public void setSeedName(String[] seedNameSave) {
		saveSeedsName = seedNameSave;
	}

	public void setSaveTime(long time) {
		beforeTime = time;
	}

	public long getSaveTime() {
		return beforeTime;
	}

	public int calculateTime(long after, long before) {
		int time = (int) ((after - before) / 1000);
		return time;
	}

	// 각 밭의 남은 시간을 저장하기 위한
	public int[][] getSeeds() {
		int[][] ary = new int[2][5];
		for (int i = 0; i < seeds.length; i++) {
			for (int j = 0; j < seeds[i].length; j++) {
				if (seeds[i][j] != null) {
				
					ary[i][j] = seeds[i][j].getLeftTimeInMillis();
					setSeedNameSave(i == 0 ? j : j + 4, seeds[i][j].getSeedName());
				
				}
			}
		}
		return ary;
	}

	// 각 밭의 남은 시간을 가져오기 위한
	public void setSeeds(int[][] ary) {
		int indexNum;
		int lastTime = calculateTime(System.currentTimeMillis(), getSaveTime()) * 1000;
		int leftTime;

		loop: for (int i = 0; i < seeds.length; i++) {
			for (int j = 0; j < seeds[i].length; j++) {
				if (ary[i][j] > 0) {
					indexNum = (i == 0) ? j : j + 4;
					
					leftTime = ary[i][j] - lastTime < 0 ? 0 : ary[i][j] - lastTime;
					createSeed(saveSeedsName[indexNum], i, j, leftTime);
			
				}
			}
		}
	
	}

	public void setUserLevel(int level) { // 유저 레벨 get
		this.userLevel = level;
		levelLabel.setText(String.format("Lv. %d", userLevel));
	}

	public int getUserLevel() { // 유저 레벨 get
		return this.userLevel;
	}

	// 농작물 수확 시 경험치와 코인 증가
	public void harvestCrops(int exp, int coin) { // 농작물 수확시 실행
		setCurrentExp(exp);
		calculateCoin(coin);
	}

	// 경험치 set
	public void setCurrentExp(int exp) {
		currentExp += exp;
		
		if (currentExp >= maxExp) {
			currentExp -= maxExp;
			setUserLevel(++userLevel); // 레벨 증가
			maxExp = 500 * userLevel;
			
		}
		expBar.repaint();
	}

	// 경험치 get
	public int getCurrentExp() {
		return this.currentExp;
	}

	// 코인 set
	public void setCoin(int coin) {
		this.coin = coin;
		coinLabel.setText(String.format("%s ", formatter.format(this.coin)));
	}

	// 코인 계산
	public void calculateCoin(int coin) {
		this.coin += coin;
		coinLabel.setText(String.format("%s ", formatter.format(this.coin)));
	}

	// 코인 get
	public int getCoin() {
		return this.coin;
	}

	public void paint(Graphics g) {// 배경JPanel 그리는 함수
		g.drawImage(background, 0, 0, FARMING_PANEL_WIDTH, FARMING_PANEL_HEIGHT, this); // background를 그려줌
		paintComponents(g);
	}

	// 5의 배수마다 밭 1개 추가
	void addField(int i) { // @ 버튼에 따라 i값을 다르게 넣는다.

		fields[1][i] = new Field();

		((Field) fields[1][i]).setX(FIELD_START_X + ((FIELD_LENGTH + FIELD_BETWEEN_LENGTH) * i));
		((Field) fields[1][i]).setY(FIELD_START_Y + FIELD_LENGTH + FIELD_BETWEEN_LENGTH);
		
		fields[1][i].setBounds(fields[1][i].getX(), fields[1][i].getY(), FIELD_LENGTH, FIELD_LENGTH);
		add(fields[1][i]);
		fields[1][i].repaint();

	}

	class ExpBarGraphic extends JLabel {

		Color gage = new Color(0xf8, 0xff, 0x38); // 경험치로 채워질 컬러
		Color gageBar = COLOR_BASE; // 정해진 경험치 범위 컬러
		int expBar;

		public void paintComponent(Graphics g) {

			expBar = (int) ((double) EXPBAR_MAX / maxExp * currentExp); // EXPBAR_MAX 크기에 경험치를 맞추기 위해

			g.setColor(gageBar);
			g.fillRoundRect(0, 0, EXPBAR_MAX, 30, 5, 5);
			g.setColor(gage);
			g.fillRoundRect(0, 0, expBar, 30, 5, 5);
			

		}
	}

	// icon에 넣을 이미지 사이즈를 줄이기 위한 함수
	ImageIcon changeImgSize(String path) {
		ImageIcon origicon = new ImageIcon(path);
		Image img = origicon.getImage();
		Image changeImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		return new ImageIcon(changeImg);
	}

	JLabel getLevelLable(int level) {
		levelLabel = new JLabel(String.format("Lv. %d", level));
		levelLabel.setFont(new Font("맑은고딕", Font.BOLD, 30)); // Label 폰트 설정
		levelLabel.setBounds(15, 15, 110, 30); // (x,y,넓이,높이)
		levelLabel.setOpaque(true); // 불투명하도록 설정
		levelLabel.setBackground(new Color(0xce, 0xeb, 0xe6)); // 라벨 색상 오류로 바탕색으로 지정함.
		return levelLabel;
	}

	JLabel getExpDisplay() {

		expDisplay = new JLabel();
		expDisplay.setBounds(130, 20, 100, 20);
		expDisplay.setFont(new Font("맑은고딕", Font.BOLD, 15));
		expDisplay.setHorizontalAlignment(JLabel.CENTER);

		return expDisplay;
	}

	JLabel getExpBar() {
		expBar = new ExpBarGraphic();
		expBar.setBounds(15, 50, 300, 30);
		expBar.addMouseListener(new MouseAdapter() {

			// 마우스가 해당 컴포넌트 영역 안으로 들어올 때 발생
			public void mouseEntered(MouseEvent e) {
				expDisplay.setText("[ " + getCurrentExp() + " / " + getUserLevel() * 500 + " ]");
				expDisplay.setVisible(true);
			}

			// 마우스가 해당 컴포넌트 영역 밖으로 나갈때 발생
			public void mouseExited(MouseEvent e) {
				expDisplay.setVisible(false);
			}
		});
		return expBar;
	}

	JLabel getCoinImg() { // 코인 모양 이미지
		JLabel coinImg = new JLabel(changeImgSize("image\\coin.png"));
		coinImg.setBounds(17, 92, 35, 35);
		// x : expBar의 넓이 + x좌표 이상
		return coinImg;
	}

	JLabel getCoinLabel() { // 코인이 얼마있는지 나타내는 Label
		coinLabel = new JLabel(String.format("%s ", formatter.format(this.coin)));
		coinLabel.setFont(new Font("맑은고딕", Font.BOLD, 20));
		coinLabel.setHorizontalAlignment(JLabel.RIGHT); // 왼쪽 정렬
		coinLabel.setBorder(new LineBorder(COLOR_BASE, 3, true)); // 테두리 컬러설정, 두께
		coinLabel.setBounds(15, 90, 300, 38);
		return coinLabel;
	}

	JButton getQuestionbtn(ImageIcon imageIcon, int x, int y) {

		questionBtn = new JButton();
		// 물음표모양 설정
		questionBtn.setIcon(imageIcon);
		questionBtn.setVisible(true);
		questionBtn.setBounds(x, y, 40, 40);
		add(questionBtn);
		questionBtn.setBorderPainted(false);
		questionBtn.setContentAreaFilled(false);
		questionBtn.setFocusPainted(false);

		// 물음표버튼 마우스 설정
		questionBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				questionBtn.setIcon(main.getEnterseedIcon());
				questionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				questionBtn.setIcon(imageIcon);
				questionBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				SoundsClip.play(SoundsClip.CLICK_SOUND);
				FarmerMessage.getGuideMsg();
			}

		});
		return questionBtn;
	}

	boolean checkFieldRange(int x, int y) { // 밭 범위 확인
		int minX, minY;

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] == null) {
					return false;
				}

				minX = fields[i][j].getX();
				minY = fields[i][j].getY();

				if (minX <= x && x <= (minX + FIELD_LENGTH) && minY <= y && y <= (minY + FIELD_LENGTH)) {

					setSelectMatrix(i, j); // 시작 좌표를 통해 몇 번째 밭인지 저장한다.
					selectFieldX = minX; // 해당 밭의 시작 x 좌표 저장
					selectFieldY = minY; // 해당 밭의 시작 y 좌표 저장
					return true;
				}

			}
		}
		return false;
	}

	void setSelectMatrix(int row, int col) {
		selectRow = row;
		selectCol = col;
	
	}

	int getSelectRow() {
		return selectRow;
	}

	int getSelectCol() {
		return selectCol;
	}

	void createSeed(String seedName, int row, int col, int time) {

		Seeds seed = new Seeds(this, seedName);
		seed.row = row;
		seed.col = col;

		int seedImgX = fields[row][col].getX() + (FIELD_LENGTH / 2) - (Seeds.IMG_SEED_WIDTH / 2);
		int seedImgY = fields[row][col].getY() + (FIELD_LENGTH) - (Seeds.IMG_SEED_HEIGHT);

		seed.setBounds(seedImgX, seedImgY, Seeds.IMG_SEED_WIDTH, Seeds.IMG_SEED_HEIGHT);

		((Field) fields[row][col]).isEmptyField(false);
		// 밭을 사용중임을 알린다. false로 바꾼다.

		// @ 스킬 타이머를 위해 기록
		seeds[seed.row][seed.col] = seed;

		if (time >= 0) {
			seed.leftTimeInMillis = time;
		}

		add(seed);
		new Thread(seed).start();
	}

	void checkCreateSeed(String seedName, int x, int y) { // 씨앗 이름, X좌표 , Y좌표

		int cost = commonSeedInfo.getSeedPrice(seedName);

		if (checkFieldRange(x, y) && ((Field) fields[getSelectRow()][getSelectCol()]).getEmptyField()
				&& main.checkCoin(cost)) {
			// 밭 범위 확인 && 해당 밭이 비어있는지 확인 (밭이 비어있으면 true)&& 씨앗 살 코인있는지 확인
			calculateCoin(-(cost)); // 씨앗 가격 빼기
			SoundsClip.play(SoundsClip.PLANTING_SOUND);
			createSeed(seedName, selectRow, selectCol, -1);

		} 

	}

	void setField(int extendFieldCnt) { // (기본 보다)확장된 밭의 개수를 받는다.
		if (!basicFieldCreate) {
			basicFieldCreate = true;
			for (int i = 0; i < 5; i++) { // 기본 밭 5개

				fields[0][i] = new Field();

				((Field) fields[0][i]).setX(FIELD_START_X + ((FIELD_LENGTH + FIELD_BETWEEN_LENGTH) * i));
				((Field) fields[0][i]).setY(FIELD_START_Y);

				fields[0][i].setBounds(fields[0][i].getX(), fields[0][i].getY(), FIELD_LENGTH, FIELD_LENGTH);

				this.add(fields[0][i]);
				fields[0][i].repaint();
			}
		}
		for (int i = 0; i < extendFieldCnt; i++) {
			addField(i);
		}
	}

	FarmingPanel(MainFrame main) {
		this.main = main;
		this.setLayout(null); // 좌표로 배치하기 위해 layout null로 설정

		commonSeedInfo = new SeedInfo(this);

		// 초기 밭 생성
		setField(FieldInfo.getExtendFieldCnt());

		this.add(getLevelLable(userLevel));
		this.add(getExpBar());
		this.add(getExpDisplay());
		this.add(getCoinImg());
		this.add(getCoinLabel());
		this.add(getQuestionbtn(question, 830, 15));

		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (main.getPlantSeed() != null) {
					checkCreateSeed(main.getPlantSeed(), e.getX(), e.getY());
				}
			}

		});

	}

	void runWaterSkill(int skillLevel) {
		if (th == null) {
			SoundsClip.play(SoundsClip.SUCCESS_SOUND);
			th = new SkillTimer( this, seeds, skillLevel);
			new Thread(th).start();
	         FarmerMessage.getSkillUseMsg(skillLevel);
		}
	}

	public void callback() {
		th = null;
	}

}
