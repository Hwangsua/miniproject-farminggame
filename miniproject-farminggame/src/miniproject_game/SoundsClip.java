package miniproject_game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * 
 * 음악 사운드 출력을 위한 클래스
 * 
 * @author EunKyungHwang
 * 
 */
public class SoundsClip {
	/**
	 * 심는 소리
	 */
	static final String PLANTING_SOUND = "music\\planting.wav";
	/**
	 * 구매소리
	 */
	static final String PURCHASE_SOUND = "music\\purchase.wav";
	/**
	 * 업그레이드및 밭 확장소리
	 */
	static final String SUCCESS_SOUND = "music\\success.wav";
	/**
	 * 클릭 소리
	 */
	static final String CLICK_SOUND = "music\\click.wav";
	/**
	 * 배경소리
	 */
	static final String BACKGROUND_MUSIC = "music\\backgroundMusic.wav";

	/**
	 * 
	 * 음악파일을 불러와 음악을 실행시킨다.
	 * 
	 * @param fileName 사용할 음악파일이름
	 * @since 1.0
	 */
	static final void play(String fileName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			Clip clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
		} catch (Exception ex) {
		}
	}

}

