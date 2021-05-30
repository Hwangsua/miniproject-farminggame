package miniproject_game;


import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * @author EunkyungHwang
 *
 */
class SkillMenuLabel extends JLabel {

    int btnY =100;
    JLabel waterImg; // 물 이미지
    JButton menu2_waterUse; // 물 업그레이그
    JButton menu2_waterPurchase; // 물 구매
    String imagepath; // 이미지 경로
    String waterUseimagepath; // 이미지 경로
    String waterPurchaseimagepath; // 이미지 경로
    JLabel waterLevelInfo; // 현재 물주기 정보
    String wateringStatus; // 물주기 상태
    TextArea contents;//물주기 정보
    int waterlevel ; // 물 레벨 
    boolean buySkill; // 스킬 구매하였니?
   
 
 private int waterSkillPrice; // 스킬 오픈 가격
 static int openSkillCnt = 0;
 
 int getwaterSkillPrice () {
    return waterSkillPrice;
 }
 
 int getOpenSkillCnt() {
    return openSkillCnt;
 }
 
 final MainFrame main;

 SkillMenuLabel(MainFrame main, String skillName) {
     
	    
     this.main = main;
      
      initSkillMenu(skillName);
      waterUseimagepath = "image//UseButton.png";
      waterPurchaseimagepath = "image//Buybutton.png";
   
      changeImgSize(waterUseimagepath , 40 ,40); 
      changeImgSize(waterPurchaseimagepath , 40 ,40); 
      
      /**
       * 스킬 라벨의 image 아이콘을 add()할 {@link JPanel}   
       */
      waterImg = new JLabel(new ImageIcon(imagepath));
      waterImg.setBounds(40, btnY+30, 100, 100);


      /**
       * 스킬 라벨의 사용버튼을 add()할 {@link JPanel}   
       */
      menu2_waterUse = new JButton(new ImageIcon(waterUseimagepath));
      menu2_waterUse.setBounds(200, btnY + 130, 120, 40);
     

      /**
       * 스킬 라벨의 구매버튼아이콘을 add()할 {@link JPanel}   
       */
      menu2_waterPurchase = new JButton(new ImageIcon(waterPurchaseimagepath));
      menu2_waterPurchase.setBounds(200, btnY + 130, 120, 40);
      
      /**
       * 스킬 라벨의 현재 스킬 구매정보를 add()할 {@link JPanel}   
       */
      waterLevelInfo = new JLabel(wateringStatus);
      waterLevelInfo.setBounds(205, btnY+5, 130, 120);
      

     
      btnSetting(menu2_waterUse);
      btnSetting(menu2_waterPurchase);


     
      main.menuPanel.add(waterImg);
      main.menuPanel.add(menu2_waterUse);
      main.menuPanel.add(waterLevelInfo);
      main.menuPanel.add(menu2_waterPurchase);
     
 
 
       
     menu2_waterUse.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
               main.setWaterLevel(waterlevel);
         }

      });
   

  } void initSkillMenu(String name) {
      switch (name) {
      case "waterSkill1":
         this.imagepath ="image//waterSkill1.png";
         this.btnY = 50;
         this.wateringStatus = "<html>Price : 10,000<br>Duration : 5 sec<br>Effect :<br>Time reduction X 3<html>";
         this.waterSkillPrice = 10000; 
         this.waterlevel =1;
         this.buySkill = false;
         
         break;

      }switch (name) {
      case "waterSkill2":
         this.imagepath = "image//waterSkill2.png";
         this.btnY = 250;
         this.wateringStatus = "<html>Price : 300,000<br>Duration : 10 sec<br>Effect :<br>Time reduction X 4<html>";
         this.waterSkillPrice = 300000; 
         this.waterlevel =2;
         this.buySkill = false;
        
         break;

      }switch (name) {
      case "waterSkill3":
         this.imagepath ="image//waterSkill3.png";
         this.btnY = 450;
         this.wateringStatus =  "<html>Price : 1,000,000<br>Duration : 15 sec<br>Effect :<br>Time reduction X 6<html>";
         this.waterSkillPrice = 1000000; 
         this.buySkill = false;
       
         break;

      }
   }
  
   /**
 * image size를 변환
 * @param imagepath 이미지 경로
 * @param weight 이미지 가로길이
 * @param height 이미지 세로길이
 * @return 변환된 크기의 이미지아이콘
 * @since 1.0
 */
ImageIcon changeImgSize(String imagepath ,int weight ,int height) {
      ImageIcon origicon = new ImageIcon(imagepath);
      Image img = origicon.getImage();
      Image changeImg = img.getScaledInstance( weight, height, Image.SCALE_SMOOTH);
      return new ImageIcon(changeImg);
   }
   

   void btnSetting(JButton jbtn){
      jbtn.setBorderPainted(false); 
      jbtn.setContentAreaFilled(false); 
      jbtn.setFocusPainted(false);
      
   }

}