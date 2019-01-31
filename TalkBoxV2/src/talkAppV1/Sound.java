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
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The {@code Sound} class provide sound features.
 *
 * @author Sangheon Jae
 * @author Karmit Patel
 * @author Peter Saleeb
 */

public class Sound {

	/**
	 * Private fields
	 */
	private AudioInputStream audioIn;
	private Clip clip = null;

	/**
	 * Initializes a newly created {@code Sound} object with null clip
	 */
	public Sound() throws LineUnavailableException {
		clip = AudioSystem.getClip();
	}

	/**
	 * Play sound file with '.wav' extension
	 * 
	 * @param audioFile relative path of audio file
	 */
	public void playSound(String audioFile) {
		clip.stop();
		clip.close();

		File audio = new File(audioFile);
		try {
			audioIn = AudioSystem.getAudioInputStream(audio.toURI().toURL());
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
			ee.printStackTrace();
		}
	}

	public void setVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = 0.25;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	/**
	 * Record sound file with '.wav' extension and save it into 'TalkBoxData'
	 * directory
	 * 
	 * @return String relative path of sound file
	 */
	public String recordSound() {
		String audioName = "";
		return audioName;
	}
}
