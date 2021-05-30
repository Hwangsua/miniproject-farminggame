package miniproject_game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import static miniproject_game.CommonValue.*;
/**
 * 
 * @author SuaHwang
 * @author EunkyungHwang
 */
class SeedMenuLabel extends JLabel {
	
   JLabel seedImg; // 씨앗 이미지
   JButton seedUpgrade; // 씨앗 업그레이드 버튼
   JButton seedPurchase; // 씨앗 구매 버튼
   JLabel seedLevelInfo; // 현재 씨앗 레벨
   ImageIcon imagepath; // 이미지 경로
   int btnY = 100; // 버튼의 시작 y좌표
   String seedStatus; // 씨앗 상태 정보
   String seedName;
   SeedInfo info;
   
   SeedMenuLabel(String seedName) {
	
      inin(seedName);
      
      // 씨앗 버튼 배치
      seedImg = new JLabel(imagepath);
      seedImg.setBounds(40, btnY, 100, 100);

      // 씨앗 업그레이드 버튼 배치
      seedUpgrade = new JButton(new ImageIcon("image//UpgradeButton.png"));
      seedUpgrade.setBounds(40, btnY + 130, 120, 40);

      // 씨앗 구매 버튼 배치
      seedPurchase = new JButton((new ImageIcon("image//BuyButton.png")));
      seedPurchase.setBounds(200, btnY + 130, 120, 40);
      

      // 레벨 상태 구현
      seedLevelInfo = new JLabel(seedStatus.toString());
      seedLevelInfo.setBounds(205, btnY-10, 130, 120);
      
      //버튼 겉테두리 지우기
        btnSetting(seedPurchase);
        btnSetting(seedUpgrade);

   }
   
   String setSeedStatus(String name) {
	   info = new SeedInfo(null);
	   switch (name) {
	   case TOMATO:
		   return "<html><body>Lv."+ info.getSeedLevel(name)
		   			+"<br>Price : 500<br>Earned Exp : 60"
		   			+"<br>Earned Coin : 750"
		   			+"<br>Current yield : "+info.getSeedTotalYield(name)
		   			+"<br>Next yield :"+info.getUpgradeYield(name)+"</body></html>";
	   case RICE:
		   return "<html><body>Lv. "+info.getSeedLevel(name)
  					+"<br>Price :1000<br>Earned Exp : 100"
  					+"<br>Earned Coin : 4000"
  					+"<br>Current yield : "+info.getSeedTotalYield(name)
  					+"<br>Next yield : "+info.getUpgradeYield(name)+"</body></html>";
	   case EGGPLANT:
		   return "<html><body>Lv. : "+info.getSeedLevel(name)
		   			+"<br>Price : 800<br>Earned Exp : 200"
		   			+"<br>Earned Coin : 1000"
		   			+"<br>Current yield : "+info.getSeedTotalYield(name)
		   			+"<br>Next yield : "+info.getUpgradeYield(name)+"</body></html>";
		}
	   return "";
   	}
   
   void inin(String seedNameLabel) {
	  
      switch (seedNameLabel) {
      case "tomatoLabel":
    	 this.seedName = TOMATO;
         this.imagepath = new ImageIcon("image//tomatoSeed.png");
         this.btnY = 50;
         this.seedStatus = setSeedStatus(seedName);
         
         break;

      case "riceLabel":
    	 this.seedName = RICE;
         this.imagepath = new ImageIcon("image//riceSeed.png");
         this.btnY = 250;
         this.seedStatus = setSeedStatus(seedName);
         
         break;

      case "eggplantLabel":
    	 this.seedName = EGGPLANT;
         this.imagepath = new ImageIcon("image//eggplantSeed.png");
         this.btnY = 450;
         this.seedStatus = setSeedStatus(seedName);
         break;

      }

   }
   // 버튼 겉지우기
      void btnSetting(JButton jbtn){
         jbtn.setBorderPainted(false); // 버튼 겉에 선 지우기
         jbtn.setContentAreaFilled(false); //안에 지우기
         jbtn.setFocusPainted(false);// 클릭시 지우기
         
      }



}