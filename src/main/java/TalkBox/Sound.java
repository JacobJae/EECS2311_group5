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

package main.java.TalkBox;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
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
	protected final AudioFileFormat.Type FILE_TYPE = AudioFileFormat.Type.WAVE;
	private TargetDataLine line;

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
	
	/**
	 * Stop sound file
	 */
	public void stopSound() {
		clip.stop();
		clip.close();
	}

	public void setVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = 0.25;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	public AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	/**
	 * Record sound file with '.wav' extension and save it into 'TalkBoxData'
	 * directory
	 * 
	 * @return String relative path of sound file
	 */
	public void startRecording(String fileName) {
		if (line == null) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						AudioFormat format = getAudioFormat();
						DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
						// checks if system supports the data line
						if (!AudioSystem.isLineSupported(info)) {
							System.out.println("Line not supported");
							System.exit(0);
						}
						line = (TargetDataLine) AudioSystem.getLine(info);
						line.open(format);
						line.start(); // start capturing
						AudioInputStream ais = new AudioInputStream(line);
						// start recording
						AudioSystem.write(ais, FILE_TYPE, new File("../TalkBoxData/" + fileName + ".wav"));
					} catch (LineUnavailableException ex) {
						ex.printStackTrace();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

	public void stopRecording() {
		if (line != null) {
			line.stop();
			line.close();
			line = null;
		}
	}
	
	public boolean isPlaying()
	{
		return clip.isRunning();
	}
}
