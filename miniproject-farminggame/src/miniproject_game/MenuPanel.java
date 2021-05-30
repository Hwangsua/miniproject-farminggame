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
 * 
 * @author HwangEunkyung
 *
 */
public class MenuPanel extends JPanel {

	final MainFrame main;

	boolean isField = false; // 밭이니?
	boolean selectbackbtn = false;// 백버튼 선택되었니?
	boolean isMainScreen = true; // 메인 화면이니?
	boolean BuySkill1 = false; // 스킬 1 구매하였니?
	boolean BuySkill2 = false; // 스킬 2 구매하였니?
	boolean BuySkill3 = false; // 스킬 3 구매하였니?

	private Image menuImage; //
	private Graphics screenGraphic;// 도화지
	private JButton backbtn; // 취소 버튼
	JButton menu1; // 메뉴1 씨앗
	JButton menu2; // 메뉴2 물
	JButton menu3; // 메뉴3 땅

	SkillMenuLabel skilllabel1; // 물스킬 버튼1 생성
	SkillMenuLabel skilllabel2; // 물스킬 버튼2 생성
	SkillMenuLabel skilllabel3; // 물스킬 버튼3 생성

	ImageIcon water1Icon; // 워터 스킬 1 아이콘
	ImageIcon water2Icon; // 워터 스킬 2 아이콘
	ImageIcon water3Icon; // 워터 스킬 3 아이콘

	ImageIcon enterseedIcon; // 메뉴 클릭시 아이콘
	ImageIcon seedIcon; // 메뉴 1 아이콘
	ImageIcon waterIcon; // 메뉴 2 아이콘
	ImageIcon ExtendField; // 메뉴 3 아이콘

	// 취소 이미지
	ImageIcon enterhbarIcon;// 마우스커서 올렸을때 이미지
	ImageIcon backIcon;// 뒤로가기 버튼 이미지

	// 라벨 정보 , 구매 , 업그레이드 버튼 등 클래스로 선언
	SeedMenuLabel tomatoLabel; // 씨앗 심기 클릭 시 토마토 라벨
	SeedMenuLabel riceLabel; // 씨앗 심기 클릭 시 벼 라벨
	SeedMenuLabel eggplantLabel; // 씨앗 심기 클릭 시 가지 라벨

	SkillMenuLabel menu2waterSkill; // 물 버튼 클릭시 라벨
	FieldMenuLabel menu3Field; // 밭 버튼 클릭시 라벨
	FieldMenuLabel fakemenu3Field; // 가짜 버튼 모양
	FieldInfo fieldInfo; // 밭 구매 조건 확인

	//// 파일로드 테스트 /////////////

	public boolean isBuySkill1() {
		return BuySkill1;
	}

	public void setBuySkill1(boolean buySkill1) {
		BuySkill1 = buySkill1;
	}

	public boolean isBuySkill2() {
		return BuySkill2;
	}

	public void setBuySkill2(boolean buySkill2) {
		BuySkill2 = buySkill2;
	}

	public boolean isBuySkill3() {
		return BuySkill3;
	}

	public void setBuySkill3(boolean buySkill3) {
		BuySkill3 = buySkill3;
	}
	//////////////////////////////

	public MenuPanel(MainFrame MainFrame) {
		this.main = MainFrame;
		setLayout(null);
		Size();

		// 메뉴1 이미지
		// 씨앗 버튼 마우스로 클릭시 변하는 이미지
		seedIcon = new ImageIcon("image//menu1.png");
		// 씨앗 버튼 기본이미지
		enterseedIcon = IMG_FARMER;

		// 메뉴2 이미지
		// 물 버튼 기본이미지
		waterIcon = new ImageIcon("image//menu1watering.png");

		// 밭 확장 버튼 이미지
		ExtendField = new ImageIcon("image//menu3.png");

		// 취소 버튼 마우스로 클릭시 변하는 이미지
		enterhbarIcon = new ImageIcon("image//apple.png");
		// 취소 버튼 기본이미지
		backIcon = new ImageIcon("image//button.png");

		menu1 = new JButton(seedIcon);// 씨앗심기 버튼 생성
		menu2 = new JButton(waterIcon);// 물 버튼 생성
		menu3 = new JButton(ExtendField);// 밭확장 버튼 생성

		// 메뉴 버튼 , 좌표 설정
		setMeue01Btn(menu1, 110);
		setMeue01Btn(menu2, 300); // 300 =버튼 y 의 좌표 설정된 버튼값 불러오기
		setMeue01Btn(menu3, 500);
		setBackbtn();
		// 메뉴1 ,2,3 버튼 마우스 모션 및 반환값
		setMenu2Mouse(menu2);// 메뉴 2 마우스

		// 씨앗 메뉴 위치 배치 및 생성
		tomatoLabel = new SeedMenuLabel("tomatoLabel");
		riceLabel = new SeedMenuLabel("riceLabel");
		eggplantLabel = new SeedMenuLabel("eggplantLabel");

	}// 기본생성자

	// 메뉴버튼1(씨앗 심기 버튼)
	void setMenu01Mouse(JButton menubtn, ImageIcon enterpicture, ImageIcon exitpicture) {
		
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
					farming();// 다른 버튼 가리기

					showLabel(tomatoLabel);// 토마도 라벨 보이기
					showLabel(riceLabel);// 쌀라벨 보이기
					showLabel(eggplantLabel);// 가지라벨 보이기

					plantingSeeds(tomatoLabel, TOMATO); // 씨앗 1 버튼
					plantingSeeds(riceLabel, RICE);// 씨앗 2 버튼
					plantingSeeds(eggplantLabel, EGGPLANT);// 씨앗 버튼
					
				}

			});
	}

	// 씨앗 심기 설정
	void plantingSeeds(SeedMenuLabel seedLabel, String seedname) {
		// 씨앗심기 버튼 클릭시 구현화면
		// 내부 라벨 위치 조정
		addMenuLabel(seedLabel);

		// 씨앗 업그레이드버튼 생성
		if (seedLabel.seedUpgrade.getMouseListeners().length == 1)
			seedLabel.seedUpgrade.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (main.farmingField.commonSeedInfo.checkSeedLevelUp(seedname)) { // 업그레이드 가능하진 확인하고 씨앗 레벨 업
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

	void setMenu2Mouse(JButton menu2) {
		// menu2 마우스 구현
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

				skilllabel1 = new SkillMenuLabel(main, "waterSkill1");// 워터스킬 1 생성
				skilllabel2 = new SkillMenuLabel(main, "waterSkill2");// 워터스킬 2 생성
				skilllabel3 = new SkillMenuLabel(main, "waterSkill3");// 워터스킬 3 생성

				setSkillUseBtn(skilllabel1); // 마우스 Skill1 Use 버튼 클릭시 설정
				setSkillUseBtn(skilllabel2); // 마우스 Skill2 Use 버튼 클릭시 설정
				setSkillUseBtn(skilllabel3); // 마우스 Skill3 Use 버튼 클릭시 설정

				if (!BuySkill1) {// skill 구매후 구매버튼 가리기
					skilllabel1.menu2_waterUse.setVisible(false);
				}

				// 물skill 구매 버튼 클릭
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

				if (!BuySkill2) {// skill 구매후 구매버튼 가리기
					skilllabel2.menu2_waterUse.setVisible(false);
				}

				// 물skill 구매 버튼 클릭
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

				if (!BuySkill3) {// skill 구매후 구매버튼 가리기
					skilllabel3.menu2_waterUse.setVisible(false);
				}

				// 물skill 구매 버튼 클릭
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

	// skillUse 버튼 사용시 마우스 설정
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

	// 뒤로가기 버튼 구현
	void setBackbtn() {

		// 백버튼 모양 설정
		backbtn = new JButton(backIcon);
		backbtn.setVisible(false);
		backbtn.setBounds(330, 15, 40, 40);
		add(backbtn);
		backbtn.setBorderPainted(false);
		backbtn.setContentAreaFilled(false);
		backbtn.setFocusPainted(false);

		// 뒤로가기 버튼 마우스 설정
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
				
				backMain();// 메인 화면 이동
				// 가지씨앗가리기
				if (tomatoLabel != null) {
					nonvisibleBtn(tomatoLabel);
				}
				// 토마토씨앗가리기
				if (riceLabel != null) {
					nonvisibleBtn(riceLabel);
				}
				// 벼씨앗가리기
				if (eggplantLabel != null) {
					nonvisibleBtn(eggplantLabel);
				}
				
			}

		});
	}

	// 씨앗버튼가리기
	void nonvisibleBtn(SeedMenuLabel btnname) {

		btnname.seedLevelInfo.setVisible(false);
		btnname.seedImg.setVisible(false);
		btnname.seedPurchase.setVisible(false);
		btnname.seedUpgrade.setVisible(false);

	}

	public void paint(Graphics g) {
		menuImage = createImage(400, MainFrame.MAIN_FRAME_WIDTH);
		screenGraphic = menuImage.getGraphics();
		screenDraw(screenGraphic);// 이미지 그려주기
		g.drawImage(menuImage, 0, 0, null);

	}

	// 배경 설정
	public void screenDraw(Graphics g) {
		if (isMainScreen) {
			g.setColor(COLOR_BASE);// 배경이미지 노랑색으로 설정
			g.fillRoundRect(0, 0, 400, 720, 5, 5);// 둥근네모 그리기

		} else {

			g.setColor(new Color(0xf2, 0xf2, 0xc2));// 배경이미지 노랑색으로 설정
			g.fillRoundRect(0, 0, 400, 720, 5, 5);// 둥근네모 그리기
		}

		paintComponents(g);// 하위 구성요소에 대해 페인트 호출

		this.repaint();// update() 메소드 자동호출

	}

	// 크기 설정
	void Size() {

		setPreferredSize(new Dimension(400, 720));

	}

	// 메뉴버튼1 클릭시 화면 구현
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

	// 나가기(x버튼) 버튼 클릭시 화면 구현
	public void backMain() {

		if (fakemenu3Field != null) {

			// 가짜버튼밭가리기
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
			// 밭가리기

			menu3Field.fieldImgLimit05.setVisible(false);
			menu3Field.fieldImgLimit010.setVisible(false);
			menu3Field.fieldImgLimit015.setVisible(false);
			menu3Field.fieldImgLimit020.setVisible(false);
			menu3Field.fieldImgLimit025.setVisible(false);
		}

		// 물스킬가리기
		hidewaterSkill(skilllabel1);
		hidewaterSkill(skilllabel2);
		hidewaterSkill(skilllabel3);

	}

	void hidewaterSkill(SkillMenuLabel skill) {
		if (skill != null) {
			// 물스킬가리기
			skill.waterImg.setVisible(false);
			skill.menu2_waterPurchase.setVisible(false);
			skill.menu2_waterUse.setVisible(false);
			skill.waterLevelInfo.setVisible(false);
		}
	}

	// menu1 버튼 모양 설정
	public void setMeue01Btn(JButton btn, int btnSetY) {
		btn.setBounds(150, btnSetY, 100, 100);// 버튼좌표 , 가로세로
		btn.setBorderPainted(false);// 버튼 테두리 없애기
		btn.setContentAreaFilled(false);// 버튼 속 없애기
		btn.setFocusPainted(false);// 버튼이 선택(Focus 되었을때 생기는 테두리 사용안함)
		add(btn);
	}

	// 각 menu3 영역 열기
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

				// 눈속임용 버튼
				fakemenu3Field = new FieldMenuLabel("fakefiedlBtn");

				// 땅필드선언
				menu3Field = new FieldMenuLabel("fiedlBtn");

				// 가짜버튼 붙이기
				addFieldLabel(fakemenu3Field);

				// 진짜 버튼
				addFieldLabel(menu3Field);

				// 생성된 밭 이미지 1만 보이기
				visibleFieldLabel(menu3Field, true, false, false, false, false);

				// 생성된 가짜 버튼 숨기기
				visibleFieldLabel(fakemenu3Field, false, false, false, false, false);

				if (FieldInfo.getExtendFieldCnt() == 1) { // 첫번째 땅이 열렸니??
					// 첫번째 가짜버튼 보이기
					visibleFieldLabel(fakemenu3Field, true, false, false, false, false);
					// 진짜 버튼 보이기
					menu3Field.fieldImgLimit010.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 2) {
					// 두번째 가짜버튼 보이기
					visibleFieldLabel(fakemenu3Field, true, true, false, false, false);
					// 진짜 버튼 보이기

					menu3Field.fieldImgLimit015.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 3) {
					// 세번째 가짜버튼 보이기
					visibleFieldLabel(fakemenu3Field, true, true, true, false, false);
					// 진짜 버튼 보이기
					menu3Field.fieldImgLimit020.setVisible(true);
				}
				if (FieldInfo.getExtendFieldCnt() == 4) {
					// 네번째 가짜버튼 보이기
					visibleFieldLabel(fakemenu3Field, true, true, true, true, false);

					// 진짜 버튼 보이기
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

							// 버튼 확장 조건
							if (fieldInfo.checkfieldPurchase()) {// 버튼 확장 조건 확인
								fieldInfo.fieldPurchase(); // 땅구매
								setvisibleFieldMouse(false, true, false, false, false);// 버튼 보이기 및 땅 확장
								fakemenu3Field.fieldImgLimit05.setVisible(true);// 가짜버튼1

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

							// 버튼 확장 조건
							if (fieldInfo.checkfieldPurchase()) { // 버튼 확장 조건 확인
								fieldInfo.fieldPurchase(); // 땅구매
								setvisibleFieldMouse(false, false, true, false, false);// 버튼 보이기 및 땅 확장
								fakemenu3Field.fieldImgLimit010.setVisible(true);// 가짜버튼2

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

							if (fieldInfo.checkfieldPurchase()) {// 버튼 확장 조건 확인
								fieldInfo.fieldPurchase(); // 땅구매
								setvisibleFieldMouse(false, false, false, true, false);// 버튼 보이기 및 땅 확장
								fakemenu3Field.fieldImgLimit015.setVisible(true);// 가짜버튼3

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

							// 버튼 확장 조건
							if (fieldInfo.checkfieldPurchase()) {// 버튼 확장 조건 밭구역
								fieldInfo.fieldPurchase(); // 땅구매
								setvisibleFieldMouse(false, false, false, false, true);// 버튼 보이기 및 땅 확장
								fakemenu3Field.fieldImgLimit020.setVisible(true);// 가짜버튼4

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
							// 버튼 확장 조건
							if (fieldInfo.checkfieldPurchase()) {// 버튼 확장 조건 밭구역
								fieldInfo.fieldPurchase(); // 땅구매
								setvisibleFieldMouse(false, false, false, false, false);// 버튼 보이기 및 땅 확장
								fakemenu3Field.fieldImgLimit025.setVisible(true);// 가짜버튼5

							}
						}

					});

				}

			}

		});

	}

// 씨앗1,2,3 보이기
	void showLabel(SeedMenuLabel menuLabel) {
		menuLabel.seedImg.setVisible(true);
		menuLabel.seedPurchase.setVisible(true);
		menuLabel.seedUpgrade.setVisible(true);
		menuLabel.seedLevelInfo.setVisible(true);

	}

	// 씨앗1,2,3 라벨 부착
	void addMenuLabel(SeedMenuLabel menuLabel) {
		add(menuLabel.seedImg);
		add(menuLabel.seedPurchase);
		add(menuLabel.seedUpgrade);
		add(menuLabel.seedLevelInfo);

	}

	// 땅 라벨 부착
	void addFieldLabel(FieldMenuLabel fieldLabel) {
		add(fieldLabel.fieldImgLimit05);
		add(fieldLabel.fieldImgLimit010);
		add(fieldLabel.fieldImgLimit015);
		add(fieldLabel.fieldImgLimit020);
		add(fieldLabel.fieldImgLimit025);

	}

	// 밭 라벨 버튼 보이기 설정
	void visibleFieldLabel(FieldMenuLabel fieldLabel, boolean fImg05, boolean fImg010, boolean fImg015, boolean fImg020,
			boolean fImg025) {

		fieldLabel.fieldImgLimit05.setVisible(fImg05);
		fieldLabel.fieldImgLimit010.setVisible(fImg010);
		fieldLabel.fieldImgLimit015.setVisible(fImg015);
		fieldLabel.fieldImgLimit020.setVisible(fImg020);
		fieldLabel.fieldImgLimit025.setVisible(fImg025);
	}

	void setvisibleFieldMouse(boolean fieldimage1, boolean fieldimage2, boolean fieldimage3, boolean fieldimage4,
			boolean fieldimage5) {

		menu3Field.fieldImgLimit05.setVisible(fieldimage1);// 현재 getOpenFieldCnt 땅이 열리는 카운트 0
		menu3Field.fieldImgLimit010.setVisible(fieldimage2);// 밭 보일지 말지 결정
		menu3Field.fieldImgLimit015.setVisible(fieldimage3);// 밭 보일지 말지 결정
		menu3Field.fieldImgLimit020.setVisible(fieldimage4);// 밭 보일지 말지 결정
		menu3Field.fieldImgLimit025.setVisible(fieldimage5);// 밭 보일지 말지 결정
		if (FieldInfo.getExtendFieldCnt() < 5) {
			main.createField(FieldInfo.getExtendFieldCnt());
			SoundsClip.play(SoundsClip.SUCCESS_SOUND);
			FieldInfo.setExtendFieldCnt(FieldInfo.getExtendFieldCnt() + 1);// 밭의 상태 변화
		}
		
	}

}