package miniproject_game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import static miniproject_game.CommonValue.*;

/**
 * menu1 , 2 , 3 를 출력하기 위한 JPanel
 * 
 * @author EunKyungHwang
 *
 */
public class MenuPanel extends JPanel {

	final MainFrame main;
	/**
	 * 밭 생성여부
	 */
	boolean isField = false;
	/**
	 * 백버튼 선택여부
	 */
	boolean selectbackbtn = false;
	/**
	 * mainscreen 선택여부
	 */
	boolean isMainScreen = true;
	/**
	 * skill1 구매여부
	 */
	boolean BuySkill1 = false;
	/**
	 * skill2 구매여부
	 */
	boolean BuySkill2 = false;
	/**
	 * skill3 구매여부
	 */
	boolean BuySkill3 = false;

	private Image menuImage;
	private Graphics screenGraphic;
	private JButton backbtn;
	JButton menu1;
	JButton menu2;
	JButton menu3;

	SkillMenuLabel skilllabel1;
	SkillMenuLabel skilllabel2;
	SkillMenuLabel skilllabel3;

	ImageIcon water1Icon;
	ImageIcon water2Icon;
	ImageIcon water3Icon;

	ImageIcon enterseedIcon;
	ImageIcon seedIcon;
	ImageIcon waterIcon;
	ImageIcon ExtendField;

	ImageIcon backIcon;

	SeedMenuLabel tomatoLabel;
	SeedMenuLabel riceLabel;
	SeedMenuLabel eggplantLabel;

	SkillMenuLabel menu2waterSkill;
	FieldMenuLabel menu3Field;
	FieldMenuLabel fakemenu3Field;
	FieldInfo fieldInfo;

	public boolean isBuySkill1() {
		return BuySkill1;
	}

	public boolean isBuySkill2() {
		return BuySkill2;
	}

	public boolean isBuySkill3() {
		return BuySkill3;
	}

	public void setBuySkill1(boolean buySkill1) {
		BuySkill1 = buySkill1;
	}

	public void setBuySkill2(boolean buySkill2) {
		BuySkill2 = buySkill2;
	}

	public void setBuySkill3(boolean buySkill3) {
		BuySkill3 = buySkill3;
	}

	/**
	 * MenuPanel 의 생성자 이미지 및 menu1,2,3, SeedMenuLabel 객체를 생성한다.
	 * 
	 * @param mainFrame MainFrame
	 */
	public MenuPanel(MainFrame mainFrame) {
		this.main = mainFrame;
		setLayout(null);
		Size();

		/**
		 * menu1 이미지생성
		 */
		seedIcon = new ImageIcon("image//menu1.png");
		/**
		 * 씨앗 버튼 마우스로 클릭시 변하는 이미지
		 */
		enterseedIcon = IMG_FARMER;

		/**
		 * menu2 이미지생성
		 */
		waterIcon = new ImageIcon("image//menu1watering.png");

		/**
		 * menu3 이미지생성
		 */
		ExtendField = new ImageIcon("image//menu3.png");

		/**
		 * 뒤로가기 버튼 생성
		 */
		backIcon = new ImageIcon("image//button.png");

		menu1 = new JButton(seedIcon);
		menu2 = new JButton(waterIcon);
		menu3 = new JButton(ExtendField);

		/**
		 * 메뉴 버튼 1의 y좌표 설정
		 */
		setMeue01Btn(menu1, 110);
		/**
		 * 메뉴 버튼 2의 y좌표 설정
		 */
		setMeue01Btn(menu2, 300);
		/**
		 * 메뉴 버튼 3의 y좌표 설정
		 */
		setMeue01Btn(menu3, 500);
		setBackbtn();

		setMenu2Mouse(menu2);

		tomatoLabel = new SeedMenuLabel("tomatoLabel");
		riceLabel = new SeedMenuLabel("riceLabel");
		eggplantLabel = new SeedMenuLabel("eggplantLabel");

	}

	/**
	 * 
	 * menu1 버튼에 MouseListener를 add().
	 * 
	 * @param menubtn
	 * @param enterpicture
	 * @param exitpicture
	 * @since 1.0
	 */
	void setMenu01Mouse(JButton menubtn, ImageIcon enterpicture, ImageIcon exitpicture) {
		System.out.println(Arrays.toString(menubtn.getMouseListeners()));
		if (menubtn.getMouseListeners().length == 1)
			menubtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					JButton menubtn = (JButton) e.getSource();
					menubtn.setIcon(enterpicture);
					menubtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JButton menubtn = (JButton) e.getSource();
					menubtn.setIcon(exitpicture);
					menubtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) {

					SoundsClip.play(SoundsClip.CLICK_SOUND);
					JButton menubtn = (JButton) e.getSource();
					farming();

					showLabel(tomatoLabel);
					showLabel(riceLabel);
					showLabel(eggplantLabel);

					plantingSeeds(tomatoLabel, TOMATO);
					plantingSeeds(riceLabel, RICE);
					plantingSeeds(eggplantLabel, EGGPLANT);

				}

			});
	}

	/**
	 * SeedMenuLabel 버튼에 MouseListener를 add().
	 * 
	 * @param seedLabel 다른 클래스의 객체들을 호출하기 위한 {@link SeedMenuLabel}형 레퍼런스
	 * @param seedname  씨앗 이름
	 * @since 1.0
	 */
	void plantingSeeds(SeedMenuLabel seedLabel, String seedname) {
		/**
		 * 메뉴라벨 부착
		 */
		addMenuLabel(seedLabel);

		if (seedLabel.seedUpgrade.getMouseListeners().length == 1)
			seedLabel.seedUpgrade.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (main.farmingField.commonSeedInfo.checkSeedLevelUp(seedname)) {
						seedLabel.seedLevelInfo.setText(seedLabel.setSeedStatus(seedname));
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		if (seedLabel.seedPurchase.getMouseListeners().length == 1)
			seedLabel.seedPurchase.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					SoundsClip.play(SoundsClip.CLICK_SOUND);
					main.setPlantSeed(seedname);
					FarmerMessage.getSeedSelectMessage();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
	}

	/**
	 * menu2 버튼에 MouseListener를 add().
	 * 
	 * @param menu2
	 * @since 1.0
	 */
	void setMenu2Mouse(JButton menu2) {

		menu2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				menu2.setIcon(IMG_FARMER);
				menu2.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				menu2.setIcon(new ImageIcon("image//menu1watering.png"));
				menu2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				SoundsClip.play(SoundsClip.CLICK_SOUND);
				farming();

				skilllabel1 = new SkillMenuLabel(main, "waterSkill1");
				skilllabel2 = new SkillMenuLabel(main, "waterSkill2");
				skilllabel3 = new SkillMenuLabel(main, "waterSkill3");

				setSkillUseBtn(skilllabel1);
				setSkillUseBtn(skilllabel2);
				setSkillUseBtn(skilllabel3);

				if (!BuySkill1) {
					skilllabel1.menu2_waterUse.setVisible(false);
				}

				skilllabel1.menu2_waterPurchase.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if (main.checkCoin(skilllabel1.getwaterSkillPrice())) {
							main.reduceCoin(skilllabel1.getwaterSkillPrice());
							skilllabel1.menu2_waterPurchase.setVisible(false);
							skilllabel1.menu2_waterUse.setVisible(true);
							SoundsClip.play(SoundsClip.PURCHASE_SOUND);
							BuySkill1 = true;
							FarmerMessage.getSkillOpenMsg();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

				});

				if (!BuySkill2) {
					skilllabel2.menu2_waterUse.setVisible(false);
				}

				skilllabel2.menu2_waterPurchase.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if (main.checkCoin(skilllabel2.getwaterSkillPrice())) {
							main.reduceCoin(skilllabel2.getwaterSkillPrice());
							skilllabel2.menu2_waterPurchase.setVisible(false);
							skilllabel2.menu2_waterUse.setVisible(true);
							SoundsClip.play(SoundsClip.PURCHASE_SOUND);
							BuySkill2 = true;
							FarmerMessage.getSkillOpenMsg();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

				});

				if (!BuySkill3) {
					skilllabel3.menu2_waterUse.setVisible(false);
				}

				skilllabel3.menu2_waterPurchase.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if (main.checkCoin(skilllabel3.getwaterSkillPrice())) {
							main.reduceCoin(skilllabel3.getwaterSkillPrice());
							skilllabel3.menu2_waterPurchase.setVisible(false);
							skilllabel3.menu2_waterUse.setVisible(true);
							SoundsClip.play(SoundsClip.PURCHASE_SOUND);
							BuySkill3 = true;
							FarmerMessage.getSkillOpenMsg();
						}

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				});

			}
		});
	}

	/**
	 * 스킬 use버튼에 버튼에 MouseListener를 add().
	 * 
	 * @param skillmenu 다른 클래스의 객체들을 호출하기 위한 {@link SkillMenuLabel}형 레퍼런스
	 * @since 1.0
	 */
	void setSkillUseBtn(SkillMenuLabel skillmenu) {
		skillmenu.menu2_waterUse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				skillmenu.menu2_waterUse.setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				skillmenu.menu2_waterUse.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 백버튼 객체 생성/초기화 및 MouseListener를 add().
	 * 
	 * @since 1.0
	 */
	void setBackbtn() {

		backbtn = new JButton(backIcon);
		backbtn.setVisible(false);
		backbtn.setBounds(330, 15, 40, 40);
		add(backbtn);
		backbtn.setBorderPainted(false);
		backbtn.setContentAreaFilled(false);
		backbtn.setFocusPainted(false);

		backbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backbtn.setIcon(enterseedIcon);
				backbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				backbtn.setIcon(backIcon);
				backbtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				SoundsClip.play(SoundsClip.CLICK_SOUND);

				backMain();
				if (tomatoLabel != null) {
					nonvisibleBtn(tomatoLabel);
				}

				if (riceLabel != null) {
					nonvisibleBtn(riceLabel);
				}

				if (eggplantLabel != null) {
					nonvisibleBtn(eggplantLabel);
				}

			}

		});
	}

	/**
	 * 
	 * 씨앗라벨 객체 가린다.
	 * 
	 * @param btnname 다른 클래스의 객체를 호출하기 위한 {@link SeedMenuLabel}형 레퍼런스
	 * @since 1.0
	 */
	void nonvisibleBtn(SeedMenuLabel btnname) {

		btnname.seedLevelInfo.setVisible(false);
		btnname.seedImg.setVisible(false);
		btnname.seedPurchase.setVisible(false);
		btnname.seedUpgrade.setVisible(false);

	}

	/**
	 * menuImage 를 menuPanel에 출력한다.
	 * 
	 * @param g 출력할 Graphics 객체
	 */

	public void paint(Graphics g) {
		menuImage = createImage(400, MainFrame.MAIN_FRAME_WIDTH);
		screenGraphic = menuImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(menuImage, 0, 0, null);

	}

	/**
	 * 더블 버퍼링 기술 구현한다. isMain의 값에 따라 repaint 되여 출력한다.
	 * 
	 * @param g 출력할 Graphics객체
	 */

	public void screenDraw(Graphics g) {
		if (isMainScreen) {
			g.setColor(COLOR_BASE);
			g.fillRoundRect(0, 0, 400, 720, 5, 5);

		} else {

			g.setColor(new Color(0xf2, 0xf2, 0xc2));
			g.fillRoundRect(0, 0, 400, 720, 5, 5);
		}

		paintComponents(g);

		this.repaint();

	}

	/**
	 * 
	 * MainFrame의 size를 설정한다.
	 * 
	 * @since 1.0
	 */
	void Size() {

		setPreferredSize(new Dimension(400, 720));

	}

	/**
	 * 
	 * menu1,2,3 가리기 설정한다.
	 * 
	 * @since 1.0
	 */
	public void farming() {

		isMainScreen = false;
		if (menu1 != null) {
			menu1.setVisible(false);
		}
		if (menu2 != null) {
			menu2.setVisible(false);
		}
		if (menu3 != null) {
			menu3.setVisible(false);
		}

		if (backbtn != null) {
			backbtn.setVisible(true);
		}

	}

	/**
	 * 메인으로 돌아왔을때 화면 설정한다.
	 * 
	 * @since 1.0
	 */
	public void backMain() {

		if (fakemenu3Field != null) {

			fakemenu3Field.fieldImgLimit05.setVisible(false);
			fakemenu3Field.fieldImgLimit010.setVisible(false);
			fakemenu3Field.fieldImgLimit015.setVisible(false);
			fakemenu3Field.fieldImgLimit020.setVisible(false);
			fakemenu3Field.fieldImgLimit025.setVisible(false);
		}

		if (menu1 != null) {
			menu1.setVisible(true);
		}
		if (menu2 != null) {
			menu2.setVisible(true);
		}
		if (menu3 != null) {
			menu3.setVisible(true);
		}
		if (backbtn != null) {
			backbtn.setVisible(false);
		}

		isMainScreen = true;
		main.setPlantSeed(null);

		if (menu3Field != null) {

			menu3Field.fieldImgLimit05.setVisible(false);
			menu3Field.fieldImgLimit010.setVisible(false);
			menu3Field.fieldImgLimit015.setVisible(false);
			menu3Field.fieldImgLimit020.setVisible(false);
			menu3Field.fieldImgLimit025.setVisible(false);
		}

		hidewaterSkill(skilllabel1);
		hidewaterSkill(skilllabel2);
		hidewaterSkill(skilllabel3);

	}

	/**
	 * 
	 * 스킬메뉴라벨 가리기 설정한다.
	 * 
	 * @param skill 다른 클래스의 객체를 호출하기 위한 {@link SkillMenuLabel}형 레퍼런스
	 * @since 1.0
	 */
	void hidewaterSkill(SkillMenuLabel skill) {
		if (skill != null) {

			skill.waterImg.setVisible(false);
			skill.menu2_waterPurchase.setVisible(false);
			skill.menu2_waterUse.setVisible(false);
			skill.waterLevelInfo.setVisible(false);
		}
	}

	/**
	 * 
	 * menu1 버튼 초기값 설정한다.
	 * 
	 * @param btn
	 * @param btnSetY 버튼의 Y 좌표
	 * @since 1.0
	 */
	public void setMeue01Btn(JButton btn, int btnSetY) {
		btn.setBounds(150, btnSetY, 100, 100);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		add(btn);
	}

	/**
	 * 
	 * menu3 의 MouseListener를 add().
	 * 
	 * @param menubtn
	 * @param enterpicture
	 * @since 1.0
	 */
	void setMenu3Mouse(JButton menubtn, ImageIcon enterpicture) {
		fieldInfo = new FieldInfo(this);
		menubtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				menubtn.setIcon(enterseedIcon);
				menubtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				menubtn.setIcon(ExtendField);
				menubtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				SoundsClip.play(SoundsClip.CLICK_SOUND);
				farming();

				fakemenu3Field = new FieldMenuLabel("fakefiedlBtn");

				menu3Field = new FieldMenuLabel("fiedlBtn");

				addFieldLabel(fakemenu3Field);

				addFieldLabel(menu3Field);

				visibleFieldLabel(menu3Field, true, false, false, false, false);

				visibleFieldLabel(fakemenu3Field, false, false, false, false, false);

				if (FieldInfo.getExtendFieldCnt() == 1) {

					visibleFieldLabel(fakemenu3Field, true, false, false, false, false);

					menu3Field.fieldImgLimit010.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 2) {

					visibleFieldLabel(fakemenu3Field, true, true, false, false, false);

					menu3Field.fieldImgLimit015.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 3) {

					visibleFieldLabel(fakemenu3Field, true, true, true, false, false);

					menu3Field.fieldImgLimit020.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 4) {

					visibleFieldLabel(fakemenu3Field, true, true, true, true, false);

					menu3Field.fieldImgLimit025.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 5) {
					visibleFieldLabel(fakemenu3Field, true, true, true, true, true);

				}

				if (menu3Field != null) {
					menu3Field.fieldImgLimit05.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {

							menu3Field.fieldImgLimit05.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {

							menu3Field.fieldImgLimit05.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}

						public void mousePressed(MouseEvent e) {

							if (fieldInfo.checkfieldPurchase()) {
								fieldInfo.fieldPurchase();
								setvisibleFieldMouse(false, true, false, false, false);
								fakemenu3Field.fieldImgLimit05.setVisible(true);

							}
						}
					});

					menu3Field.fieldImgLimit010.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {

							menu3Field.fieldImgLimit010.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {

							menu3Field.fieldImgLimit010.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}

						public void mousePressed(MouseEvent e) {

							if (fieldInfo.checkfieldPurchase()) {
								fieldInfo.fieldPurchase();
								setvisibleFieldMouse(false, false, true, false, false);
								fakemenu3Field.fieldImgLimit010.setVisible(true);

							}

						}
					});

					menu3Field.fieldImgLimit015.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {

							menu3Field.fieldImgLimit015.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {

							menu3Field.fieldImgLimit015.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}

						public void mousePressed(MouseEvent e) {

							if (fieldInfo.checkfieldPurchase()) {
								fieldInfo.fieldPurchase();
								setvisibleFieldMouse(false, false, false, true, false);
								fakemenu3Field.fieldImgLimit015.setVisible(true);

							}

						}

					});

					menu3Field.fieldImgLimit020.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {

							menu3Field.fieldImgLimit020.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {

							menu3Field.fieldImgLimit020.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}

						public void mousePressed(MouseEvent e) {

							if (fieldInfo.checkfieldPurchase()) {
								fieldInfo.fieldPurchase();
								setvisibleFieldMouse(false, false, false, false, true);
								fakemenu3Field.fieldImgLimit020.setVisible(true);

							}

						}

					});
					menu3Field.fieldImgLimit025.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {

							menu3Field.fieldImgLimit025.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {

							menu3Field.fieldImgLimit025.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}

						public void mousePressed(MouseEvent e) {

							if (fieldInfo.checkfieldPurchase()) {
								fieldInfo.fieldPurchase();
								setvisibleFieldMouse(false, false, false, false, false);
								fakemenu3Field.fieldImgLimit025.setVisible(true);

							}
						}

					});

				}

			}

		});

	}

	/**
	 * 
	 * menuLabel 객체를 화면에 출력한다.
	 * 
	 * @param menuLabel 다른 클래스의 객체를 호출하기 위한 {@link SeedMenuLabel}형 레퍼런스
	 * @since 1.0
	 */
	void showLabel(SeedMenuLabel menuLabel) {
		menuLabel.seedImg.setVisible(true);
		menuLabel.seedPurchase.setVisible(true);
		menuLabel.seedUpgrade.setVisible(true);
		menuLabel.seedLevelInfo.setVisible(true);

	}

	/**
	 * 
	 * menuLabel의 객체들을 {@link MenuPanel}에 add().
	 * 
	 * @param menuLabel 다른 클래스의 객체를 호출하기 위한 {@link SeedMenuLabel}형 레퍼런스
	 * @since 1.0
	 */
	void addMenuLabel(SeedMenuLabel menuLabel) {
		add(menuLabel.seedImg);
		add(menuLabel.seedPurchase);
		add(menuLabel.seedUpgrade);
		add(menuLabel.seedLevelInfo);

	}

	/**
	 * 
	 * FieldMenuLabel의 객체들을 {@link MenuPanel}에 add().
	 * 
	 * @param fieldLabel 다른 클래스의 객체를 호출하기 위한 {@link FieldMenuLabel}형 레퍼런스
	 * @since 1.0
	 */

	void addFieldLabel(FieldMenuLabel fieldLabel) {
		add(fieldLabel.fieldImgLimit05);
		add(fieldLabel.fieldImgLimit010);
		add(fieldLabel.fieldImgLimit015);
		add(fieldLabel.fieldImgLimit020);
		add(fieldLabel.fieldImgLimit025);

	}

	/**
	 * 
	 * 밭 이미지들의 display 여부를 설정한다.
	 * 
	 * @param fieldLabel 다른 클래스의 객체를 호출하기 위한 {@link FieldMenuLabel}형 레퍼런스
	 * @param fImg05     밭05 이미지display 여부
	 * @param fImg010    밭010 이미지display 여부
	 * @param fImg015    밭015 이미지display 여부
	 * @param fImg020    밭020 이미지display 여부
	 * @param fImg025    밭025 이미지display 여부
	 * @since 1.0
	 */
	void visibleFieldLabel(FieldMenuLabel fieldLabel, boolean fImg05, boolean fImg010, boolean fImg015, boolean fImg020,
			boolean fImg025) {

		fieldLabel.fieldImgLimit05.setVisible(fImg05);
		fieldLabel.fieldImgLimit010.setVisible(fImg010);
		fieldLabel.fieldImgLimit015.setVisible(fImg015);
		fieldLabel.fieldImgLimit020.setVisible(fImg020);
		fieldLabel.fieldImgLimit025.setVisible(fImg025);
	}

	/**
	 * 
	 * 밭 클릭시 이미지display 여부 및 밭 생성 설정한다.
	 * 
	 * @param fieldimage1 밭01 이미지display 여부
	 * @param fieldimage2 밭02 이미지display 여부
	 * @param fieldimage3 밭03 이미지display 여부
	 * @param fieldimage4 밭04 이미지display 여부
	 * @param fieldimage5 밭05 이미지display 여부
	 * @since 1.0
	 */
	void setvisibleFieldMouse(boolean fieldimage1, boolean fieldimage2, boolean fieldimage3, boolean fieldimage4,
			boolean fieldimage5) {

		menu3Field.fieldImgLimit05.setVisible(fieldimage1);
		menu3Field.fieldImgLimit010.setVisible(fieldimage2);
		menu3Field.fieldImgLimit015.setVisible(fieldimage3);
		menu3Field.fieldImgLimit020.setVisible(fieldimage4);
		menu3Field.fieldImgLimit025.setVisible(fieldimage5);
		if (FieldInfo.getExtendFieldCnt() < 5) {
			main.createField(FieldInfo.getExtendFieldCnt());
			SoundsClip.play(SoundsClip.SUCCESS_SOUND);
			FieldInfo.setExtendFieldCnt(FieldInfo.getExtendFieldCnt() + 1);
		}
		System.out.println(FieldInfo.getLevelCriterion());
	}

}