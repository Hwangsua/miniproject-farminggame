package miniproject_game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * @author EunkyungHwang
 *
 */
public class SoundsClip {
	   static final String PLANTING_SOUND= "music\\planting.wav"; // 심을 때
	   static final String PURCHASE_SOUND= "music\\purchase.wav"; // 구매
	   static final String SUCCESS_SOUND= "music\\success.wav"; // 업그레이드 성공, 밭 확장 
	   static final String CLICK_SOUND = "music\\click.wav"; // 클릭했을 때
	   static final String BACKGROUND_MUSIC =  "music\\backgroundMusic.wav";
	   
	   
	   static  final void play(String fileName)
	    {
	        try
	        {
	            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
	            Clip clip = AudioSystem.getClip();
	            clip.stop();
	            clip.open(ais);
	            clip.start();
	        }
	        catch (Exception ex)
	        {
	        }
	    }

	}