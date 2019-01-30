/*
 * Copyright (c) 2019. All rights reserved.
 * Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package talkAppV1;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The {@code TalkBox} class contains user setting of TalkBox.
 * Serialized object will be saved on "TalkBoxData" as '.tbc' extension
 * Object contains
 * <ul>
 * 	<li>{@code int} numberOfAudioButtons</li>
 * 	<li>{@code int} numberOfAudioSets</li>
 * 	<li>{@code String[][]} audioFileNames</li>
 * </ul>
 *
 * @author  Sangheon Jae
 * @author  Karmit Patel
 * @author  Peter Saleeb
 */

public class Sound {
	private AudioInputStream audioIn;
	private Clip clip = null;
	
	public Sound() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playSound(String audioFile) {
		clip.stop();
		clip.close();
		
		File audio = new File(audioFile);
		try {
			System.out.println(audio.toURI().toURL());
			
			audioIn = AudioSystem.getAudioInputStream(audio.toURI().toURL());
			clip.open(audioIn);
			clip.start();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException ee) {
			ee.printStackTrace();
		}
	}
	
	public String recordSound() {
		String audioName = "";
		return audioName;
	}
	
//	private void getName(String audioFile) {
//		name = audioFile.substring(audioFile.indexOf("/") + 1, audioFile.indexOf("."));
//	}
}
