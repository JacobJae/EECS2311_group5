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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The {@code TalkBox} class contains user setting of TalkBox.
 * Serialized object will be saved on "TalkBoxData" as '.tbc' extension
 * Object contains
 * <ul>
 * 	<li>Coffee</li>
 * 	<li>Tea</li>
 * 	<li>Milk</li>
 * </ul>
 *
 * @author  Sangheon Jae
 * @author  Karmit Patel
 * @author  Peter Saleeb
 */

public class TalkBox implements TalkBoxConfiguration {
	
	/**
	 * 
	 */
	private int numberOfAudioButtons;
	private int numberOfAudioSets;
	private String[][] audioFileNames;
	
	/**
     * Set the number of physical buttons that when pressed will play an audio file.
     * 
     * @param int numberOfAudioButtons positive integer
     */
	public void setNumberOfAudioButtons(int numberOfAudioButtons) {
		this.numberOfAudioButtons = numberOfAudioButtons;
	}

	/**
     * Set the number of sets of audio files that this configuration supports.
     * 
     * @param int numberOfAudioSets positive integer
     */
	public void setNumberOfAudioSets(int numberOfAudioSets) {
		this.numberOfAudioSets = numberOfAudioSets;
	}

	/**
     * Returns a 2-dimensional array of Strings that contains the names of all audio files.
     * Each row of the array is an audio set.
     * The dimensions of the array are given by {@link #getNumberOfAudioButtons() getNumberOfAudioButtons}
     * and {@link #getNumberOfAudioSets() getNumberOfAudioSets}
     * @param audioFileNames 2-dimensional array of Strings
     */
	public void setAudioFileNames(String[][] audioFileNames) {
		this.audioFileNames = audioFileNames;
	}

	/**
     * Returns the number of physical buttons that when pressed will play an audio file.
     * 
     * @return int A positive integer
     */
	@Override
	public int getNumberOfAudioButtons() {
		// TODO Auto-generated method stub
		return numberOfAudioButtons;
	}

	/**
     * Returns the number of sets of audio files that this configuration supports.
     * 
     * @return int A positive integer
     */
	@Override
	public int getNumberOfAudioSets() {
		// TODO Auto-generated method stub
		return numberOfAudioSets;
	}

	/**
     * Returns the total number of buttons in this TalkBox. 
     * 
     * @return int A positive integer
     */
	@Override
	public int getTotalNumberOfButtons() {
		// TODO Auto-generated method stub
		return numberOfAudioButtons * numberOfAudioSets;
	}

	/**
     * Returns a Path relative to this configuration object where all audio files can be found
     * @return Path A Path object that identifies the directory that contains the audio files
     */
	@Override
	public Path getRelativePathToAudioFiles() {
		// TODO Auto-generated method stub
		return Paths.get("TalkBoxData//");
	}

	/**
     * Returns a 2-dimensional array of Strings that contains the names of all audio files.
     * Each row of the array is an audio set.
     * The dimensions of the array are given by {@link #getNumberOfAudioButtons() getNumberOfAudioButtons}
     * and {@link #getNumberOfAudioSets() getNumberOfAudioSets}
     * @return A 2-dimensional array of Strings
     */
	@Override
	public String[][] getAudioFileNames() {
		// TODO Auto-generated method stub
		return audioFileNames;
	}

}
