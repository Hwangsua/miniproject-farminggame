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
 * 전반적으로 게임이 진행되는 모습을 보여주는 JPanel
 * 
 * @author SuaHwang
 *
 */
public class FarmingPanel extends JPanel {

	final MainFrame main;

	/**
	 * FarmingPanel의 폭
	 */
	static final int FARMING_PANEL_WIDTH = 880;
	/**
	 * FarmingPanel의 높이
	 */
	static final int FARMING_PANEL_HEIGHT = 720;
	/**
	 * 경험치 바의 가로 최대 길이
	 */
	static final int EXPBAR_MAX = 300;

	private ExpBarGraphic expBar;
	/**
	 * user의 레벨을 표시하기 위한 JLabel
	 */
	private JLabel levelLabel;
	/**
	 * user의 코인을 표시하기 위한 JLabel
	 */
	private JLabel coinLabel;
	/**
	 * user의 현재 경험치를 수치로 보여주기 위한 JLabel
	 */
	private JLabel expDisplay;
	/**
	 * 도움말 버튼
	 */
	private JButton questionBtn;

	/**
	 * 배경 이미지
	 */
	private Image background = new ImageIcon("image\\mainBackground.png").getImage();
	/**
	 * 도움말 버튼 이미지 아이콘
	 */
	private ImageIcon question = new ImageIcon("image\\question.png");

	/**
	 * user의 레벨
	 */
	private int userLevel = 1;
	/**
	 * user의 현재 경험치
	 */
	private int currentExp = 0;
	/**
	 * 레벨 업을 위한 목표 경험치
	 */
	private int maxExp = 500 * userLevel;
	/**
	 * user의 코인
	 */
	private int coin = 15000;
	DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###");

	/**
	 * 밭
	 */
	JLabel[][] fields = new Field[2][5];
	/**
	 * 검색한 밭의 행, 열
	 */
	private int selectRow, selectCol;
	/**
	 * 기본 밭 생성 여부
	 */
	private static boolean basicFieldCreate = false;

	/**
	 * 씨앗
	 */
	Seeds[][] seeds = new Seeds[2][5];
	/**
	 * 공통적으로 필요한 씨앗 정보
	 */
	SeedInfo commonSeedInfo;
	/**
	 * 씨앗 이름 배열(저장용)
	 */
	String[] saveSeedsName = new String[seeds.length * seeds[0].length];

	/**
	 * 마지막으로 파일 저장한 시간
	 */
	private long beforeTime;

	/**
	 * 스킬 타이머
	 */
	private SkillTimer th;

	/**
	 * i번째 밭에 심어진 씨앗 name 저장한다.
	 * 
	 * @param i    몇 번째 밭의 씨앗인지 나타낸 인덱스값 (0~9)
	 * @param name 씨앗의 이름
	 */
	public void setSeedNameSave(int i, String name) {
		saveSeedsName[i] = name;
	}

	/**
	 * 각 밭에 심어진 씨앗들의 이름을 문자열 배열로 반환한다.
	 * 
	 * @return 씨앗들의 이름
	 */
	public String[] getSeedName() {
		return saveSeedsName;
	}

	/**
	 * 각 밭에 심어진 씨앗들의 이름을 설정한다.
	 * 
	 * @param seedNameSave 씨앗들의 이름
	 */
	public void setSeedName(String[] seedNameSave) {
		saveSeedsName = seedNameSave;
	}

	/**
	 * 마지막으로 파일 저장한 시간을 설정한다.
	 * 
	 * @param time 마지막으로 파일 저장한 시간
	 */
	public void setSaveTime(long time) {
		beforeTime = time;
	}

	/**
	 * 마지막으로 파일 저장한 시간을 반환한다.
	 * 
	 * @return 마지막으로 파일 저장한 시간
	 */
	public long getSaveTime() {
		return beforeTime;
	}

	/**
	 * before로부터 after까지 흐른 시간을 계산하여 반환한다.
	 * 
	 * @param after  마지막으로 파일 불러오기한 시간
	 * @param before 마지막으로 파일 저장한 시간
	 * @return 계산한 시간(초)
	 */
	public int calculateTime(long after, long before) {
		int time = (int) ((after - before) / 1000);
		return time;
	}

	/**
	 * 씨앗 심어진 밭의 정보를 반환한다.
	 * 
	 * @return 씨앗 심어진 밭의 좌표와 남은 시간
	 */
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

	/**
	 * 씨앗 심어진 밭들을 설정한다.
	 * 
	 * @param ary 씨앗 심어진 밭의 좌표와 남은 시간
	 */
	public void setSeeds(int[][] ary) {
		int indexNum;
		int lastTime = calculateTime(System.currentTimeMillis(), getSaveTime()) * 1000;
		int leftTime;

		for (int i = 0; i < seeds.length; i++) {
			for (int j = 0; j < seeds[i].length; j++) {
				if (ary[i][j] > 0) {
					indexNum = (i == 0) ? j : j + 4;
					leftTime = ary[i][j] - lastTime < 0 ? 0 : ary[i][j] - lastTime;
					createSeed(saveSeedsName[indexNum], i, j, leftTime);

				}
			}
		}

	}

	/**
	 * user의 레벨을 설정한다.
	 * 
	 * @param level user 레벨
	 */
	public void setUserLevel(int level) {
		this.userLevel = level;
		levelLabel.setText(String.format("Lv. %d", userLevel));
	}

	/**
	 * user의 레벨을 반환한다.
	 * 
	 * @return user 레벨
	 */
	public int getUserLevel() {
		return this.userLevel;
	}

	/**
	 * 농작물물 수확 시 user의 코인과 경험치 설정한다.
	 * 
	 * @param exp  수확 시 얻는 경험치
	 * @param coin 수확 시 얻는 코인
	 */
	public void harvestCrops(int exp, int coin) {
		setCurrentExp(exp);
		calculateCoin(coin);
	}

	/**
	 * user의 경험치를 설정한다.
	 * 
	 * @param exp 증가할 경험치
	 */
	public void setCurrentExp(int exp) {
		currentExp += exp;

		if (currentExp >= maxExp) {
			currentExp -= maxExp;
			setUserLevel(++userLevel);
			maxExp = 500 * userLevel;

		}
		expBar.repaint();
	}

	/**
	 * user의 경험치를 반환한다.
	 * 
	 * @return user 경험치
	 */
	public int getCurrentExp() {
		return this.currentExp;
	}

	/**
	 * user의 코인을 설정한다.
	 * 
	 * @param coin user의 코인
	 */
	public void setCoin(int coin) {
		this.coin = coin;
		coinLabel.setText(String.format("%s ", formatter.format(this.coin)));
	}

	/**
	 * 수입/지출에 따른 유저의 코인 설정한다.<br/>
	 * coin이 음수면 지출, 그렇지 않으면 수입이다.
	 * 
	 * @param coin 수입/지출 될 코인
	 */
	public void calculateCoin(int coin) {
		this.coin += coin;
		coinLabel.setText(String.format("%s ", formatter.format(this.coin)));
	}

	/**
	 * user의 코인을 반환한다.
	 * 
	 * @return user의 코인
	 */
	public int getCoin() {
		return this.coin;
	}

	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, FARMING_PANEL_WIDTH, FARMING_PANEL_HEIGHT, this);
		paintComponents(g);
	}
 
	/**
	 * 밭 추가한다.
	 * 
	 * @param i 몇번 째 밭인지 나타내는 인덱스 값(0~4)
	 */
	void addField(int i) {

		fields[1][i] = new Field();

		((Field) fields[1][i]).setX(FIELD_START_X + ((FIELD_LENGTH + FIELD_BETWEEN_LENGTH) * i));
		((Field) fields[1][i]).setY(FIELD_START_Y + FIELD_LENGTH + FIELD_BETWEEN_LENGTH);

		fields[1][i].setBounds(fields[1][i].getX(), fields[1][i].getY(), FIELD_LENGTH, FIELD_LENGTH);
		add(fields[1][i]);
		fields[1][i].repaint();

	}

	/**
	 * 경험치 바를 표시하기위한 JLabel
	 * 
	 * @author SuaHwang
	 */
	class ExpBarGraphic extends JLabel {

		Color gage = new Color(0xf8, 0xff, 0x38);
		Color gageBar = COLOR_BASE;
		int expBar;

		public void paintComponent(Graphics g) {

			expBar = (int) ((double) EXPBAR_MAX / maxExp * currentExp);

			g.setColor(gageBar);
			g.fillRoundRect(0, 0, EXPBAR_MAX, 30, 5, 5);
			g.setColor(gage);
			g.fillRoundRect(0, 0, expBar, 30, 5, 5);

		}
	}

	/**
	 * ImageIcon에 넣을 이미지 사이즈를 줄여서 반환한다.
	 * 
	 * @param path 이미지의 경로
	 * @return 사이즈를 변경한 ImageIcon
	 */
	ImageIcon changeImgSize(String path) {
		ImageIcon origicon = new ImageIcon(path);
		Image img = origicon.getImage();
		Image changeImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		return new ImageIcon(changeImg);
	}

	JLabel getLevelLable(int level) {
		levelLabel = new JLabel(String.format("Lv. %d", level));
		levelLabel.setFont(new Font("맑은고딕", Font.BOLD, 30));
		levelLabel.setBounds(15, 15, 110, 30);
		levelLabel.setOpaque(true);
		levelLabel.setBackground(COLOR_BACKGROUND_SKY);
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

			public void mouseEntered(MouseEvent e) {
				expDisplay.setText("[ " + getCurrentExp() + " / " + getUserLevel() * 500 + " ]");
				expDisplay.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				expDisplay.setVisible(false);
			}
		});
		return expBar;
	}

	JLabel getCoinImg() {
		JLabel coinImg = new JLabel(changeImgSize("image\\coin.png"));
		coinImg.setBounds(17, 92, 35, 35);
		return coinImg;
	}

	JLabel getCoinLabel() {
		coinLabel = new JLabel(String.format("%s ", formatter.format(this.coin)));
		coinLabel.setFont(new Font("맑은고딕", Font.BOLD, 20));
		coinLabel.setHorizontalAlignment(JLabel.RIGHT);
		coinLabel.setBorder(new LineBorder(COLOR_BASE, 3, true));
		coinLabel.setBounds(15, 90, 300, 38);
		return coinLabel;
	}

	JButton getQuestionbtn(ImageIcon imageIcon, int x, int y) {

		questionBtn = new JButton();
		questionBtn.setIcon(imageIcon);
		questionBtn.setVisible(true);
		questionBtn.setBounds(x, y, 40, 40);
		add(questionBtn);
		questionBtn.setBorderPainted(false);
		questionBtn.setContentAreaFilled(false);
		questionBtn.setFocusPainted(false);

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

	/**
	 * 밭의 범위가 맞는지 확인한다.
	 * 
	 * @param x x 좌표
	 * @param y y 좌표
	 * @return 밭에 해당하는 범위면 true, 그렇지 않으면 false
	 */
	boolean checkFieldRange(int x, int y) {
		int minX, minY;

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] == null) {
					return false;
				}

				minX = fields[i][j].getX();
				minY = fields[i][j].getY();

				if (minX <= x && x <= (minX + FIELD_LENGTH) && minY <= y && y <= (minY + FIELD_LENGTH)) {
					setSelectMatrix(i, j);
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * 검색한 밭의 row, col 설정한다.
	 * 
	 * @param row 밭의 행(0~1)
	 * @param col 밭의 열(0~4)
	 */
	void setSelectMatrix(int row, int col) {
		selectRow = row;
		selectCol = col;

	}

	/**
	 * 검색한 밭의 row 반환한다.
	 * 
	 * @return 밭의 행(0~1)
	 */
	int getSelectRow() {
		return selectRow;
	}

	/**
	 * 검색한 밭의 col 반환한다.
	 * 
	 * @return 밭의 열(0~4)
	 */
	int getSelectCol() {
		return selectCol;
	}

	/**
	 * 씨앗을 생성한다.<br/>
	 * time이 음수면 새로운 씨앗을 생성하는 것이고,<br/>
	 * 그렇지 않으면 기존(파일을 저장했을 시점)에 심어져 있던 씨앗을 다시 생성하는 것이다.
	 * 
	 * @param seedName 씨앗의 이름
	 * @param row      밭의 행(0~1)
	 * @param col      밭의 열(0~4)
	 * @param time     씨앗의 남은 성장 시간
	 */
	void createSeed(String seedName, int row, int col, int time) {

		Seeds seed = new Seeds(this, seedName);
		seed.row = row;
		seed.col = col;

		int seedImgX = fields[row][col].getX() + (FIELD_LENGTH / 2) - (Seeds.IMG_SEED_WIDTH / 2);
		int seedImgY = fields[row][col].getY() + (FIELD_LENGTH) - (Seeds.IMG_SEED_HEIGHT);

		seed.setBounds(seedImgX, seedImgY, Seeds.IMG_SEED_WIDTH, Seeds.IMG_SEED_HEIGHT);

		((Field) fields[row][col]).isEmptyField(false);

		seeds[seed.row][seed.col] = seed;

		if (time >= 0) {
			seed.leftTimeInMillis = time;
		}

		add(seed);
		new Thread(seed).start();
	}

	/**
	 * 좌표에 해당하는 밭에 씨앗을 생성할 수 있는지 여부를 확인한다.
	 * 
	 * @param seedName 씨앗의 이름
	 * @param x        x 좌표
	 * @param y        y 좌표
	 */
	void checkCreateSeed(String seedName, int x, int y) {

		int cost = commonSeedInfo.getSeedPrice(seedName);

		if (checkFieldRange(x, y) && ((Field) fields[getSelectRow()][getSelectCol()]).getEmptyField()
				&& main.checkCoin(cost)) {
			calculateCoin(-(cost));
			SoundsClip.play(SoundsClip.PLANTING_SOUND);
			createSeed(seedName, selectRow, selectCol, -1);
		}

	}

	/**
	 * 밭을 생성한다.
	 * 
	 * @param extendFieldCnt 확장된 밭의 개수(기본 밭 제외)
	 */
	void setField(int extendFieldCnt) {
		if (!basicFieldCreate) {
			basicFieldCreate = true;
			for (int i = 0; i < 5; i++) {

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
		this.setLayout(null);

		commonSeedInfo = new SeedInfo(this);

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

	/**
	 * 스킬을 실행한다.
	 * 
	 * @param skillLevel 스킬 레벨
	 */
	void runWaterSkill(int skillLevel) {
		if (th == null) {
			SoundsClip.play(SoundsClip.SUCCESS_SOUND);
			th = new SkillTimer(this, seeds, skillLevel);
			new Thread(th).start();
			FarmerMessage.getSkillUseMsg(skillLevel);
		}
	}

	/**
	 * SkillTimer의 스레드가 종료되었음을 표시한다.
	 */
	public void callback() {
		th = null;
	}

}
