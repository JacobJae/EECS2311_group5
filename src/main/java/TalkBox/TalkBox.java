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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;

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

public class TalkBox implements TalkBoxConfiguration {
	
	/**
	 * 
	 */
	/**
	 * Private fields
	 */
	private int numberOfAudioButtons;
	private int numberOfAudioSets;
	private String[][] audioFileNames;
	private Path absolutePath;
	private boolean[][] hasAudio;
	private String[] setNames;
	private File[] sFile;
	private List<String> settingsList;
	private ImageIcon[][] images;
	
	
	
	/**
	 * @return the images
	 */
	public ImageIcon[][] getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(ImageIcon[][] images) {
		this.images = images;
	}

	/**
	 * @return the settingsList
	 */
	public List<String> getSettingsList() {
		return settingsList;
	}

	/**
	 * @param tbcFiles the settingsList to set
	 */
	public void setSettingsList(List<String> tbcFiles) {
		this.settingsList = tbcFiles;
	}

	/**
	 * @return the sFile
	 */
	public File[] getsFile() {
		return sFile;
	}

	/**
	 * @param sFile the sFile to set
	 */
	public void setsFile(File[] sFile) {
		this.sFile = sFile;
	}

	/**
	 * @return the setNames
	 */
	public String[] getSetNames() {
		return setNames;
	}

	/**
	 * @param setNames the setNames to set
	 */
	public void setSetNames(String[] setNames) {
		this.setNames = setNames;
	}

	public boolean[][] getHasAudio() {
		return hasAudio;
	}

	public void setHasAudio(boolean[][] hasAudio) {
		this.hasAudio = hasAudio;
	}

	public void setAbsolutePath(Path absolutePath) {
		this.absolutePath = absolutePath;
	}
	
	public Path getAbsolutePath() {
		return this.absolutePath;
	}
	
	/**
     * Increase Number of audio sets.
     */
	public void incAudioSets() {
		setNumberOfAudioSets(getNumberOfAudioSets() + 1);
		if (getAudioFileNames().length < getNumberOfAudioSets()) {
			String pre[][] = getAudioFileNames();
			String newNames[][] = new String[pre.length + 1][pre[0].length];
			for (int i = 0; i < pre.length; i++) {
				for (int j = 0; j < pre[0].length; j++) {
					newNames[i][j] = pre[i][j];
				}
			}
			setAudioFileNames(newNames);
		}
	}
	
	/**
     * Decrease Number of audio sets.
     */
	public void decAudioSets() {
		if (getNumberOfAudioSets() > 1)
			setNumberOfAudioSets(getNumberOfAudioSets() - 1);
	}
	
	/**
     * Increase Number of audio sets.
     */
	public void incAudioButtons() {
		if (getNumberOfAudioButtons() < 8) {
			setNumberOfAudioButtons(getNumberOfAudioButtons() + 1);
			if (getAudioFileNames()[0].length < getNumberOfAudioButtons()) {
				String pre[][] = getAudioFileNames();
				String newNames[][] = new String[getNumberOfAudioSets()][getNumberOfAudioButtons()];
				for (int i = 0; i < getNumberOfAudioSets(); i++) {
					for (int j = 0; j < getNumberOfAudioButtons() - 1; j++) {
						newNames[i][j] = pre[i][j];
					}
				}
				setAudioFileNames(newNames);
			}
		}
	}
	
	/**
     * Decrease Number of audio sets.
     */
	public void decAudioButtons() {
		if (getNumberOfAudioButtons() > 1)
			setNumberOfAudioButtons(getNumberOfAudioButtons() - 1);
	}
	
	/**
     * Add audio file
     * 
     * @param int currentBtnSet positive integer
     * @param int btnPosition positive integer
     * @param String audioName Audio File Name
     */
	public void addAudio(int currentBtnSet, int btnPosition, String audioName) {
		String names[][] = getAudioFileNames();
		names[currentBtnSet][btnPosition - 1] = "TalkBoxData/" + audioName;
		setAudioFileNames(names);
	}
	
	/**
     * Remove audio file
     * 
     * @param int currentBtnSet positive integer
     * @param int selectedBtnIndex positive integer
     */
	public void removeAudio(int currentBtnSet, int selectedBtnIndex) {
		String names[][] = getAudioFileNames();
		names[currentBtnSet][selectedBtnIndex - 1] = null;
		setAudioFileNames(names);
	}
	
	/**
     * Finalize instance
     */
	public void finalize() {
		String names[][] = getAudioFileNames();
		String result[][] = new String[getNumberOfAudioSets()][getNumberOfAudioButtons()];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = names[i][j];
			}
		}
		setAudioFileNames(result);
	}
	
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
		return numberOfAudioButtons;
	}

	/**
     * Returns the number of sets of audio files that this configuration supports.
     * 
     * @return int A positive integer
     */
	@Override
	public int getNumberOfAudioSets() {
		return numberOfAudioSets;
	}

	/**
     * Returns the total number of buttons in this TalkBox. 
     * 
     * @return int A positive integer
     */
	@Override
	public int getTotalNumberOfButtons() {
		return numberOfAudioButtons * numberOfAudioSets;
	}

	/**
     * Returns a Path relative to this configuration object where all audio files can be found
     * @return Path A Path object that identifies the directory that contains the audio files
     */
	@Override
	public Path getRelativePathToAudioFiles() {
		return Paths.get(System.getProperty("user.dir"));
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
		return audioFileNames;
	}

}
