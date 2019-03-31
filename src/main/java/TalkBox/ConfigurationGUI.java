package main.java.TalkBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ConfigurationGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8521259104660128253L;
	private JPanel contentPane;
	private Sound sound;
	private TalkBox talkbox = new TalkBox();
	private int currentBtnSet = 0;
	private int selectedBtnIndex = -1, selectedListIndex = -1;
	private TalkBoxGui talkboxgui;
	private final ButtonGroup currentBtnGrp = new ButtonGroup();
	private JList<String> listAudioList;
	private JToggleButton btnSound1, btnSound2, btnSound3, btnSound4, btnSound5, btnSound6;
	private JTextArea txtBtn1, txtBtn2, txtBtn3, txtBtn4, txtBtn5, txtBtn6;
	private JButton btn1I, btn1D, btn1S;
	private JButton btn2I, btn2D, btn2S;
	private JButton btn3I, btn3D, btn3S;
	private JButton btn4I, btn4D, btn4S;
	private JButton btn5I, btn5D, btn5S;
	private JButton btn6I, btn6D, btn6S;
	private JButton btnPreviousSet, btnNextSet, btnAddNewSet, btnDeleteSet;
	private JComboBox<String> setSelector;
	private JButton btnRecord_1, btnSave, btnSaveAs, btnExit;
	private JToggleButton[] currentAudioBtns = new JToggleButton[6];
	private JTextArea[] currentAudioText = new JTextArea[6];
	private JPanel[] btnCtrlPanel = new JPanel[6];
	private JPanel[] btnPanel = new JPanel[6];
	private JButton[] deleteBtns = new JButton[6], imageBtns = new JButton[6], swapBtns = new JButton[6];
	private JPanel btn1Panel, btn2Panel, btn3Panel, btn4Panel, btn5Panel, btn6Panel;
	private JPanel cntBtn1, cntBtn2, cntBtn3, cntBtn4, cntBtn5, cntBtn6;
	private int totAudioBtns;
	private int audioSets;
	private String[][] audioFileNames;
	private boolean[][] hasSound;
	private List<File> allFiles;
	private File[] sFile;
	private ArrayList<String> names;
	private String currentSettings, path;
	private String[] setNames;
	private JTextField lblTitle;
	private JComboBox<String> tbcLoader;
	private List<String> tbcFiles = new ArrayList<String>();
	private ImageIcon[][] imageButtons;
	private ImageIcon defaultImg = new ImageIcon("TalkBoxData/smiley_face.jpg"),
			emptyImg = new ImageIcon("TalkBoxData/Empty_Btn.png");
	private List<String> imgFiles = new ArrayList<>();
	private JLabel disp;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ConfigurationGUI frame = new
	 * ConfigurationGUI(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	/**
	 * Create the frame.
	 */
	public ConfigurationGUI(TalkBoxGui t, String currentSettings) {

		setResizable(false);
		talkboxgui = t;
		talkboxgui.setVisible(false);
		setVisible(true);
		setTitle("Configuration");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(950, 800);
		setLocationRelativeTo(null);
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sound.stopSound();
				e.getWindow().dispose();
			}
		});

		init();
		this.currentSettings = currentSettings;
		setVariables();

		if (currentSettings.equals("default")) {
			loadDefaults();
			btnSave.setEnabled(false);
		} else {
			getSetting();
		}
		setDefaults();
		setSetList();
		setSettingsList();
		addActions();
		setButtons();
	}

	/*
	 * Adds every button to separate Arrays for further use
	 */
	private void setVariables() {

		// Add Current Buttons and TextPanes
		currentAudioBtns[0] = btnSound1;
		currentAudioBtns[1] = btnSound2;
		currentAudioBtns[2] = btnSound3;
		currentAudioBtns[3] = btnSound4;
		currentAudioBtns[4] = btnSound5;
		currentAudioBtns[5] = btnSound6;

		currentAudioText[0] = txtBtn1;
		currentAudioText[1] = txtBtn2;
		currentAudioText[2] = txtBtn3;
		currentAudioText[3] = txtBtn4;
		currentAudioText[4] = txtBtn5;
		currentAudioText[5] = txtBtn6;

		// Add Control Buttons
		deleteBtns[0] = btn1D;
		deleteBtns[1] = btn2D;
		deleteBtns[2] = btn3D;
		deleteBtns[3] = btn4D;
		deleteBtns[4] = btn5D;
		deleteBtns[5] = btn6D;

		imageBtns[0] = btn1I;
		imageBtns[1] = btn2I;
		imageBtns[2] = btn3I;
		imageBtns[3] = btn4I;
		imageBtns[4] = btn5I;
		imageBtns[5] = btn6I;

		swapBtns[0] = btn1S;
		swapBtns[1] = btn2S;
		swapBtns[2] = btn3S;
		swapBtns[3] = btn4S;
		swapBtns[4] = btn5S;
		swapBtns[5] = btn6S;

		// Add Button Panels
		btnPanel[0] = btn1Panel;
		btnPanel[1] = btn2Panel;
		btnPanel[2] = btn3Panel;
		btnPanel[3] = btn4Panel;
		btnPanel[4] = btn5Panel;
		btnPanel[5] = btn6Panel;

		btnCtrlPanel[0] = cntBtn1;
		btnCtrlPanel[1] = cntBtn2;
		btnCtrlPanel[2] = cntBtn3;
		btnCtrlPanel[3] = cntBtn4;
		btnCtrlPanel[4] = cntBtn5;
		btnCtrlPanel[5] = cntBtn6;

	}

	/*
	 * Loads default values with all the buttons and everything
	 */
	private void loadDefaults() {

		path = "TalkBoxData/";
		allFiles = Arrays.asList(new File(path).listFiles());
		sFile = new File[allFiles.size() * 2];
		int j = 0;
		for (int i = 0; i < allFiles.size(); i++) {
			String file = allFiles.get(i).toString();
			if (isWav(file)) {
				sFile[j] = allFiles.get(i);
				j++;
			}
			if (isTbc(file))
				tbcFiles.add(file);
			if (isImg(file))
				imgFiles.add(file);
		}

		names = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			names.add(getName(sFile[i].toString()));
		}

		try {
			sound = new Sound();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		totAudioBtns = j;
		int as = totAudioBtns / 6;
		double fas = totAudioBtns / 6f;
		if (fas > as)
			audioSets = (totAudioBtns / 6) + 1;
		else
			audioSets = (totAudioBtns / 6);
		audioFileNames = new String[audioSets][6];
		hasSound = new boolean[audioSets][6];
		setNames = new String[audioSets];

		j = 0;
		for (int i = 0; i < audioSets; i++) {
			for (int k = 0; k < 6; k++) {
				if (sFile[j] == null) {
					audioFileNames[i][k] = "Press to Configure!.";
					hasSound[i][k] = false;
				} else {
					audioFileNames[i][k] = sFile[j].toString();
					hasSound[i][k] = true;
				}
				j++;
			}
		}

		imageButtons = new ImageIcon[audioSets][6];

		for (int i = 0; i < hasSound.length; i++) {
			for (int k = 0; k < 6; k++) {
				if (hasSound[i][k]) {
					imageButtons[i][k] = new ImageIcon("TalkBoxData/smiley_face.jpg");
				} else {
					imageButtons[i][k] = new ImageIcon("TalkBoxData/Empty_Btn.png");
				}
			}
		}

	}

	/*
	 * Set values to buttons
	 */
	private void setButtons() {

		for (int i = 0; i < 6; i++) {
			currentAudioBtns[i].setIcon(resizeImg(imageButtons[currentBtnSet][i].toString()));
			currentAudioText[i].setText(findName(getName(audioFileNames[currentBtnSet][i])));
			currentAudioBtns[i].setToolTipText(getName(audioFileNames[currentBtnSet][i]));
		}

		List<String> finalNames = new ArrayList<String>();
		for (String s : names)
			finalNames.add(s);

		for (int i = 0; i < 6; i++) {
			if (hasSound[currentBtnSet][i]) {
				finalNames.remove(currentAudioBtns[i].getToolTipText());
			}
		}

		setModel(finalNames);
		selectedBtnIndex = -1;
		selectedListIndex = -1;
		activateBtns();
		deslectBtns();

		disp.setText("Select a button to configure!");

	}

	/*
	 * Set default values to certain variables
	 */
	private void setDefaults() {

		path = "TalkBoxData/";
		allFiles = Arrays.asList(new File(path).listFiles());
		sFile = new File[allFiles.size() * 2];
		int j = 0;
		for (int i = 0; i < allFiles.size(); i++) {
			String file = allFiles.get(i).toString();
			if (isWav(file)) {
				sFile[j] = allFiles.get(i);
				j++;
			}
			if (isTbc(file))
				tbcFiles.add(file);
			if (isImg(file))
				imgFiles.add(file);
		}
		names = new ArrayList<String>();
		for (int i = 0; i < j; i++) {
			names.add(getName(sFile[i].toString()));
		}

		imageButtons = new ImageIcon[audioSets][6];

		for (int i = 0; i < hasSound.length; i++) {
			for (int k = 0; k < 6; k++) {
				if (hasSound[i][k]) {
					imageButtons[i][k] = new ImageIcon("TalkBoxData/smiley_face.jpg");
				} else {
					imageButtons[i][k] = new ImageIcon("TalkBoxData/Empty_Btn.png");
				}
			}
		}

	}

	/*
	 * Add actionlisteners to buttons
	 */
	private void addActions() {

		for (int i = 0; i < 6; i++) {
			currentAudioBtns[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int tempIndex = getSelectedIndex();
					if (tempIndex == selectedBtnIndex) {
						sound.stopSound();
						deslectBtns();
					} else {
						selectedBtnIndex = getSelectedIndex();
						makeSound((JToggleButton) e.getComponent());
					}
					if (hasSound[currentBtnSet][selectedBtnIndex])
						activateBtns();
				}
			});
		}

		listAudioList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedListIndex = listAudioList.getSelectedIndex();
				activateBtns();
			}
		});

		for (int i = 0; i < 6; i++) {
			swapBtns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					swap();
				}
			});
		}

		setSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentBtnSet = setSelector.getSelectedIndex();
				changeSet();

			}
		});

		for (int i = 0; i < 6; i++) {
			deleteBtns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					delete();

				}
			});
		}

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSetting();
			}
		});

		btnRecord_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openRecord();
			}
		});

		btnSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				createSaveAs();

			}
		});

		for (int i = 0; i < 6; i++) {
			imageBtns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					createImagechooser();
				}
			});
		}

		lblTitle.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				setNames[currentBtnSet] = lblTitle.getText();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

	}

	/*
	 * Play sound on button press
	 */
	private void makeSound(JToggleButton button) {

		String text = button.getToolTipText();
		int currentSelected = -1;
		boolean found = false;
		for (int i = 0; i < 6; i++) {
			if (text.equals(currentAudioBtns[i].getToolTipText())) {
				currentSelected = i;
				if (hasSound[currentBtnSet][currentSelected]) {
					sound.playSound(audioFileNames[currentBtnSet][currentSelected]);
					found = true;
				}
			}
		}

		if (!found) {
			sound.stopSound();
		}
	}

	/*
	 * Open ImageChooser Dialogue
	 */
	protected void createImagechooser() {

		JFileChooser j = new JFileChooser(path);
		j.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
		j.setFileFilter(filter);
		j.showOpenDialog(null);

		if (j.getSelectedFile() != null) {
			File file = j.getSelectedFile();
			imageButtons[currentBtnSet][selectedBtnIndex] = new ImageIcon("TalkBoxData/" + file.getName());
			currentAudioBtns[selectedBtnIndex].setIcon(resizeImg("TalkBoxData/" + file.getName()));
		}

	}

	/*
	 * Sets the Set List
	 */
	private void setSetList() {
		for (int i = 0; i < audioSets; i++) {
			setNames[i] = "Audio Set " + (i + 1);
		}

		DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<>();
		for (int i = 0; i < setNames.length; i++) {
			aModel.addElement(setNames[i]);
		}
		setSelector.setModel(aModel);
		setSelector.setSelectedIndex(currentBtnSet);
		lblTitle.setText((String) setSelector.getSelectedItem());
	}

	/*
	 * Sets the Settings list
	 */
	private void setSettingsList() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (int i = 0; i < tbcFiles.size(); i++) {
			model.addElement(getName(tbcFiles.get(i)));
		}
		tbcLoader.setModel(model);
		tbcLoader.setSelectedItem(currentSettings);

	}

	/*
	 * Sets currentSettings
	 */
	public void setCurrentSettings(String name) {
		this.currentSettings = name;
	}

	/*
	 * Open SavAs Dialogue
	 */
	private void createSaveAs() {

		new SaveAsDialogue(this).setVisible(true);
		this.setEnabled(false);

	}

	/*
	 * Open Record Dialogue
	 */
	protected void openRecord() {
		new RecordGUI(this).setVisible(true);
		this.setEnabled(false);
	}

	/*
	 * Get the name of the file
	 */
	private String getName(String string) {
		String name = "";
		name = string.substring(string.indexOf("\\") + 1, string.indexOf("."));
		return name;
	}

	/*
	 * Check if the file is a .wav file
	 */
	private Boolean isWav(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".wav"))
			return true;

		return false;
	}

	/*
	 * Check if the file is SettingsFile
	 */
	private Boolean isTbc(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".tbc"))
			return true;

		return false;
	}

	/*
	 * Check if the file is Image File
	 */
	private boolean isImg(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".jpg") || ext.equals(".png") || ext.equals(".bmp"))
			return true;

		return false;
	}

	/*
	 * Resize the Image to fit the button
	 */
	private ImageIcon resizeImg(String path) {
		ImageIcon icon = new ImageIcon(path);
		Image scaleImage = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
		ImageIcon ic = new ImageIcon(scaleImage);

		return ic;
	}

	/*
	 * Sets the model for list
	 */
	private void setModel(List<String> finalNames) {

		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (int i = 0; i < finalNames.size(); i++) {
			listModel.addElement(finalNames.get(i));
		}
		listAudioList.setModel(listModel);
		listAudioList.setSelectedIndex(-1);

	}

	/*
	 * Deselect Buttons
	 */
	private void deslectBtns() {

		selectedBtnIndex = -1;
		selectedListIndex = -1;

		for (int i = 0; i < 6; i++) {
			currentAudioBtns[i].setSelected(false);
			swapBtns[i].setEnabled(false);
			imageBtns[i].setEnabled(false);
			deleteBtns[i].setEnabled(false);
			btnCtrlPanel[i].setEnabled(false);

		}

		disp.setText("Press a button to Configure!");

	}

	/*
	 * Change current set
	 */
	private void changeSet() {

		if (currentBtnSet < 0)
			currentBtnSet = audioSets - 1;
		else if (currentBtnSet >= audioSets)
			currentBtnSet = 0;

		setSelector.setSelectedIndex(currentBtnSet);
		lblTitle.setText((String) setSelector.getSelectedItem());
		setButtons();

	}

	/*
	 * Perform SWAP action
	 */
	private void swap() {
		String temp = "TalkBoxData\\" + listAudioList.getSelectedValue().toString() + ".wav";
		audioFileNames[currentBtnSet][selectedBtnIndex] = temp;
		hasSound[currentBtnSet][selectedBtnIndex] = true;
		imageButtons[currentBtnSet][selectedBtnIndex] = defaultImg;

		deslectBtns();
		setButtons();
	}

	/*
	 * Perform DELETE Action
	 */
	private void delete() {
		sound.stopSound();
		audioFileNames[currentBtnSet][selectedBtnIndex] = "Press to Configure!.";
		hasSound[currentBtnSet][selectedBtnIndex] = false;
		imageButtons[currentBtnSet][selectedBtnIndex] = emptyImg;
		deslectBtns();
		setButtons();
	}

	/*
	 * Activate Buttons
	 */
	private void activateBtns() {

		int index = selectedBtnIndex;
		boolean type = (selectedBtnIndex != -1) && (selectedListIndex != -1);

		for (int i = 0; i < 6; i++) {
			if (i == index) {
				disp.setText("Select a File from the List to Swap!");
				imageBtns[i].setEnabled(true);
				deleteBtns[i].setEnabled(true);
				btnCtrlPanel[i].setEnabled(true);
				if (type) {
					swapBtns[i].setEnabled(true);
					disp.setText("Click \"S\" Button to Swap");
				}
			} else {
				disp.setText("Select a File from the List to Swap!");
				imageBtns[i].setEnabled(false);
				deleteBtns[i].setEnabled(false);
				btnCtrlPanel[i].setEnabled(false);
				if (type) {
					swapBtns[i].setEnabled(false);
					disp.setText("Click \"S\" Button to Swap");
				}
			}
		}

	}

	/*
	 * Return selected Button index
	 */
	private int getSelectedIndex() {

		for (int i = 0; i < 6; i++) {
			if (currentAudioBtns[i].isSelected())
				return i;
		}
		return -1;

	}

	/*
	 * Gives the name of the Sound File
	 */
	private String findName(String name) {
		String[] words = name.split("_");
		String str = "";
		for (String w : words)
			str += toDisplayCase(w) + " ";
		return str;
	}

	/*
	 * Capitalize First Letter
	 */
	private static String toDisplayCase(String s) {

		final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
														// to be capitalized

		StringBuilder sb = new StringBuilder();
		boolean capNext = true;

		for (char c : s.toCharArray()) {
			c = (capNext) ? Character.toUpperCase(c) : Character.toLowerCase(c);
			sb.append(c);
			capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
		}
		return sb.toString();
	}

	/*
	 * Initialize the Display
	 */
	private void init() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel saveExitPanel = new JPanel();
		saveExitPanel.setBackground(Color.WHITE);

		JPanel crntSetPanel = new JPanel();
		crntSetPanel.setBackground(Color.WHITE);

		lblTitle = new JTextField("Set Audioset Title");

		JPanel audioSelPanel = new JPanel();
		audioSelPanel.setBackground(Color.WHITE);

		JPanel setSelPanel = new JPanel();
		setSelPanel.setBackground(Color.WHITE);

		JPanel setCtrlPanel = new JPanel();
		setCtrlPanel.setBackground(Color.WHITE);

		JPanel saveLoadPanel = new JPanel();
		saveLoadPanel.setBackground(Color.WHITE);

		disp = new JLabel("");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(disp, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(
										audioSelPanel, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(setCtrlPanel, 0, 0, Short.MAX_VALUE)
										.addComponent(setSelPanel, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(saveLoadPanel,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(crntSetPanel, GroupLayout.PREFERRED_SIZE, 923, Short.MAX_VALUE).addComponent(
								saveExitPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(37)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(disp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(crntSetPanel, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(audioSelPanel, GroupLayout.PREFERRED_SIZE, 354,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(setCtrlPanel, GroupLayout.PREFERRED_SIZE, 284,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(setSelPanel,
														GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
								.addComponent(saveLoadPanel, GroupLayout.PREFERRED_SIZE, 354,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(saveExitPanel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)));

		btnSave = new JButton("Save");

		btnRecord_1 = new JButton("Record");

		JPanel loadPanel = new JPanel();
		loadPanel.getLayout();
		loadPanel.setBackground(Color.WHITE);
		GroupLayout gl_saveLoadPanel = new GroupLayout(saveLoadPanel);
		gl_saveLoadPanel.setHorizontalGroup(gl_saveLoadPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_saveLoadPanel.createSequentialGroup().addContainerGap().addGroup(gl_saveLoadPanel
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(loadPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
						.addComponent(btnRecord_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
						.addComponent(btnSave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
						.addContainerGap()));
		gl_saveLoadPanel.setVerticalGroup(gl_saveLoadPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_saveLoadPanel.createSequentialGroup().addContainerGap()
						.addComponent(btnRecord_1, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(loadPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		JLabel lblLoad = new JLabel("Load:");
		lblLoad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loadPanel.add(lblLoad);

		tbcLoader = new JComboBox<String>();
		tbcLoader.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loadPanel.add(tbcLoader);
		saveLoadPanel.setLayout(gl_saveLoadPanel);

		JLabel lblSetControls = new JLabel("Set Controls");

		btnPreviousSet = new JButton("<<");
		btnPreviousSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentBtnSet--;
				changeSet();
			}
		});

		btnNextSet = new JButton(">>");
		btnNextSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentBtnSet++;
				changeSet();
			}
		});

		btnAddNewSet = new JButton("Add New Audio Set");

		btnDeleteSet = new JButton("Delete Current Audio Set");
		GroupLayout gl_setCtrlPanel = new GroupLayout(setCtrlPanel);
		gl_setCtrlPanel.setHorizontalGroup(gl_setCtrlPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_setCtrlPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_setCtrlPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAddNewSet, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
								.addGroup(gl_setCtrlPanel.createSequentialGroup()
										.addComponent(btnPreviousSet, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnNextSet, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
								.addComponent(lblSetControls, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
								.addComponent(btnDeleteSet, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
						.addGap(9)));
		gl_setCtrlPanel.setVerticalGroup(gl_setCtrlPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_setCtrlPanel
				.createSequentialGroup().addContainerGap().addComponent(lblSetControls).addGap(18)
				.addGroup(gl_setCtrlPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNextSet, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPreviousSet, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(btnAddNewSet, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnDeleteSet, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		setCtrlPanel.setLayout(gl_setCtrlPanel);

		JLabel lblAudioSetSelector = new JLabel("Audio Set Selector");

		JLabel lblCurrentAudioSet = new JLabel("Current Audio Set:");

		setSelector = new JComboBox<String>();
		GroupLayout gl_setSelPanel = new GroupLayout(setSelPanel);
		gl_setSelPanel.setHorizontalGroup(gl_setSelPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_setSelPanel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_setSelPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAudioSetSelector, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addGroup(gl_setSelPanel.createSequentialGroup()
								.addComponent(lblCurrentAudioSet, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(setSelector, 0, 178, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_setSelPanel.setVerticalGroup(gl_setSelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_setSelPanel.createSequentialGroup().addContainerGap().addComponent(lblAudioSetSelector)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_setSelPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCurrentAudioSet).addComponent(setSelector, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		setSelPanel.setLayout(gl_setSelPanel);

		JLabel lblAudioSelector = new JLabel("Audio Selector");

		listAudioList = new JList<String>();
		GroupLayout gl_audioSelPanel = new GroupLayout(audioSelPanel);
		gl_audioSelPanel.setHorizontalGroup(gl_audioSelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_audioSelPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_audioSelPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(listAudioList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 269,
										Short.MAX_VALUE)
								.addComponent(lblAudioSelector, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 269,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_audioSelPanel.setVerticalGroup(gl_audioSelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_audioSelPanel.createSequentialGroup().addContainerGap().addComponent(lblAudioSelector)
						.addGap(12).addComponent(listAudioList, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
						.addContainerGap()));
		audioSelPanel.setLayout(gl_audioSelPanel);

		btn1Panel = new JPanel();
		btn1Panel.setBackground(Color.WHITE);

		btn2Panel = new JPanel();
		btn2Panel.setBackground(Color.WHITE);

		btnSound2 = new JToggleButton("");
		currentBtnGrp.add(btnSound2);

		txtBtn2 = new JTextArea();
		txtBtn2.setWrapStyleWord(true);
		txtBtn2.setLineWrap(true);
		txtBtn2.setEnabled(false);
		txtBtn2.setEditable(false);
		txtBtn2.setFont(new Font("Monospaced", Font.BOLD, 18));

		cntBtn2 = new JPanel();
		cntBtn2.setBackground(Color.WHITE);
		GroupLayout gl_btn2Panel = new GroupLayout(btn2Panel);
		gl_btn2Panel.setHorizontalGroup(gl_btn2Panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSound2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(cntBtn2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(txtBtn2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE));
		gl_btn2Panel.setVerticalGroup(gl_btn2Panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btn2Panel.createSequentialGroup()
						.addComponent(btnSound2, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBtn2, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cntBtn2, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)));

		btn2I = new JButton("I");
		btn2I.setEnabled(false);
		cntBtn2.add(btn2I);

		btn2D = new JButton("D");
		btn2D.setEnabled(false);
		cntBtn2.add(btn2D);

		btn2S = new JButton("S");
		btn2S.setEnabled(false);
		cntBtn2.add(btn2S);
		btn2Panel.setLayout(gl_btn2Panel);

		btn3Panel = new JPanel();
		btn3Panel.setBackground(Color.WHITE);

		btnSound3 = new JToggleButton("");
		currentBtnGrp.add(btnSound3);

		txtBtn3 = new JTextArea();
		txtBtn3.setWrapStyleWord(true);
		txtBtn3.setLineWrap(true);
		txtBtn3.setEnabled(false);
		txtBtn3.setEditable(false);
		txtBtn3.setFont(new Font("Monospaced", Font.BOLD, 18));

		cntBtn3 = new JPanel();
		cntBtn3.setBackground(Color.WHITE);
		GroupLayout gl_btn3Panel = new GroupLayout(btn3Panel);
		gl_btn3Panel.setHorizontalGroup(gl_btn3Panel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtBtn3, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
				.addComponent(cntBtn3, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
				.addComponent(btnSound3, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE));
		gl_btn3Panel.setVerticalGroup(gl_btn3Panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btn3Panel.createSequentialGroup()
						.addComponent(btnSound3, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBtn3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cntBtn3, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)));

		btn3I = new JButton("I");
		btn3I.setEnabled(false);
		cntBtn3.add(btn3I);

		btn3D = new JButton("D");
		btn3D.setEnabled(false);
		cntBtn3.add(btn3D);

		btn3S = new JButton("S");
		btn3S.setEnabled(false);
		cntBtn3.add(btn3S);
		btn3Panel.setLayout(gl_btn3Panel);

		btn4Panel = new JPanel();
		btn4Panel.setBackground(new Color(255, 255, 255));

		btnSound4 = new JToggleButton("");
		currentBtnGrp.add(btnSound4);

		txtBtn4 = new JTextArea();
		txtBtn4.setWrapStyleWord(true);
		txtBtn4.setLineWrap(true);
		txtBtn4.setEnabled(false);
		txtBtn4.setEditable(false);
		txtBtn4.setFont(new Font("Monospaced", Font.BOLD, 18));

		cntBtn4 = new JPanel();
		cntBtn4.setBackground(Color.WHITE);
		GroupLayout gl_btn4Panel = new GroupLayout(btn4Panel);
		gl_btn4Panel.setHorizontalGroup(gl_btn4Panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSound4, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(cntBtn4, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(txtBtn4, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE));
		gl_btn4Panel.setVerticalGroup(gl_btn4Panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btn4Panel.createSequentialGroup()
						.addComponent(btnSound4, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBtn4, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cntBtn4, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)));

		btn4I = new JButton("I");
		btn4I.setEnabled(false);
		cntBtn4.add(btn4I);

		btn4D = new JButton("D");
		btn4D.setEnabled(false);
		cntBtn4.add(btn4D);

		btn4S = new JButton("S");
		btn4S.setEnabled(false);
		cntBtn4.add(btn4S);
		btn4Panel.setLayout(gl_btn4Panel);

		btn5Panel = new JPanel();
		btn5Panel.setBackground(Color.WHITE);

		btnSound5 = new JToggleButton("");
		currentBtnGrp.add(btnSound5);

		txtBtn5 = new JTextArea();
		txtBtn5.setWrapStyleWord(true);
		txtBtn5.setLineWrap(true);
		txtBtn5.setEnabled(false);
		txtBtn5.setEditable(false);
		txtBtn5.setFont(new Font("Monospaced", Font.BOLD, 18));

		cntBtn5 = new JPanel();
		cntBtn5.setBackground(Color.WHITE);
		GroupLayout gl_btn5Panel = new GroupLayout(btn5Panel);
		gl_btn5Panel.setHorizontalGroup(gl_btn5Panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSound5, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(cntBtn5, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(txtBtn5, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE));
		gl_btn5Panel.setVerticalGroup(gl_btn5Panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btn5Panel.createSequentialGroup()
						.addComponent(btnSound5, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBtn5, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cntBtn5, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)));

		btn5I = new JButton("I");
		btn5I.setEnabled(false);
		cntBtn5.add(btn5I);

		btn5D = new JButton("D");
		btn5D.setEnabled(false);
		cntBtn5.add(btn5D);

		btn5S = new JButton("S");
		btn5S.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn5S.setEnabled(false);
		cntBtn5.add(btn5S);
		btn5Panel.setLayout(gl_btn5Panel);

		btn6Panel = new JPanel();
		btn6Panel.setBackground(Color.WHITE);

		btnSound6 = new JToggleButton("");
		currentBtnGrp.add(btnSound6);

		txtBtn6 = new JTextArea();
		txtBtn6.setWrapStyleWord(true);
		txtBtn6.setLineWrap(true);
		txtBtn6.setEnabled(false);
		txtBtn6.setEditable(false);
		txtBtn6.setFont(new Font("Monospaced", Font.BOLD, 18));

		cntBtn6 = new JPanel();
		cntBtn6.setBackground(Color.WHITE);
		GroupLayout gl_btn6Panel = new GroupLayout(btn6Panel);
		gl_btn6Panel.setHorizontalGroup(gl_btn6Panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSound6, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(cntBtn6, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				.addComponent(txtBtn6, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE));
		gl_btn6Panel.setVerticalGroup(gl_btn6Panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btn6Panel.createSequentialGroup()
						.addComponent(btnSound6, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBtn6, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cntBtn6, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)));

		btn6I = new JButton("I");
		btn6I.setEnabled(false);
		cntBtn6.add(btn6I);

		btn6D = new JButton("D");
		btn6D.setEnabled(false);
		cntBtn6.add(btn6D);

		btn6S = new JButton("S");
		btn6S.setEnabled(false);
		cntBtn6.add(btn6S);
		btn6Panel.setLayout(gl_btn6Panel);
		GroupLayout gl_crntSetPanel = new GroupLayout(crntSetPanel);
		gl_crntSetPanel
				.setHorizontalGroup(
						gl_crntSetPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_crntSetPanel.createSequentialGroup()
												.addComponent(btn1Panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn2Panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn3Panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn4Panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn5Panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn6Panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_crntSetPanel.setVerticalGroup(gl_crntSetPanel.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_crntSetPanel.createSequentialGroup().addGroup(gl_crntSetPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn6Panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(btn4Panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
						.addComponent(btn3Panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
						.addComponent(btn2Panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
						.addComponent(btn1Panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
						.addComponent(btn5Panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 287,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		btnSound1 = new JToggleButton("");
		currentBtnGrp.add(btnSound1);

		txtBtn1 = new JTextArea();
		txtBtn1.setWrapStyleWord(true);
		txtBtn1.setLineWrap(true);
		txtBtn1.setEnabled(false);
		txtBtn1.setEditable(false);
		txtBtn1.setFont(new Font("Monospaced", Font.BOLD, 18));

		cntBtn1 = new JPanel();
		cntBtn1.setBackground(Color.WHITE);
		GroupLayout gl_btn1Panel = new GroupLayout(btn1Panel);
		gl_btn1Panel.setHorizontalGroup(gl_btn1Panel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtBtn1, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
				.addComponent(cntBtn1, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
				.addComponent(btnSound1, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE));
		gl_btn1Panel.setVerticalGroup(gl_btn1Panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_btn1Panel.createSequentialGroup()
						.addComponent(btnSound1, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtBtn1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cntBtn1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)));

		btn1I = new JButton("I");
		btn1I.setEnabled(false);
		cntBtn1.add(btn1I);

		btn1D = new JButton("D");
		btn1D.setEnabled(false);
		cntBtn1.add(btn1D);

		btn1S = new JButton("S");
		btn1S.setEnabled(false);
		cntBtn1.add(btn1S);
		btn1Panel.setLayout(gl_btn1Panel);
		crntSetPanel.setLayout(gl_crntSetPanel);

		btnSaveAs = new JButton("Save As");

		btnExit = new JButton("EXIT");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				talkboxgui.setVisible(true);
				sound.stopSound();
				dispose();
			}
		});
		GroupLayout gl_saveExitPanel = new GroupLayout(saveExitPanel);
		gl_saveExitPanel.setHorizontalGroup(gl_saveExitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_saveExitPanel.createSequentialGroup()
						.addComponent(btnSaveAs, GroupLayout.PREFERRED_SIZE, 811, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnExit, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)));
		gl_saveExitPanel.setVerticalGroup(gl_saveExitPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSaveAs, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
				.addComponent(btnExit, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE));
		saveExitPanel.setLayout(gl_saveExitPanel);
		contentPane.setLayout(gl_contentPane);
	}

	/*
	 * Save Settings
	 */
	public void setSetting() {
		try {
			talkbox.setAudioFileNames(audioFileNames);
			talkbox.setHasAudio(hasSound);
			talkbox.setNumberOfAudioSets(audioSets);
			talkbox.setNumberOfAudioButtons(6);
			talkbox.setSetNames(setNames);
			talkbox.setImages(imageButtons);

			String filePath = "TalkBoxData/" + currentSettings + ".tbc";
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(talkbox);
			objectOutputStream.flush();
			objectOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Save Settings with a given Name
	 */
	public void setSetting(String name) {
		try {
			talkbox.setAudioFileNames(audioFileNames);
			talkbox.setHasAudio(hasSound);
			talkbox.setNumberOfAudioSets(audioSets);
			talkbox.setNumberOfAudioButtons(6);
			talkbox.setSetNames(setNames);
			talkbox.setImages(imageButtons);

			String filePath = "TalkBoxData/" + name + ".tbc";
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(talkbox);
			objectOutputStream.flush();
			objectOutputStream.close();

			talkboxgui.dispose();
			dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get .tbc settings and initiate sound, buttongroup, talkbox object
	 */
	private void getSetting() {
		try {
			String path = "TalkBoxData/" + currentSettings + ".tbc";

			FileInputStream fileInputStream = new FileInputStream(new File(path));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			talkbox = (TalkBox) objectInputStream.readObject();

			audioFileNames = talkbox.getAudioFileNames();
			totAudioBtns = talkbox.getNumberOfAudioButtons();
			audioSets = talkbox.getNumberOfAudioSets();
			hasSound = talkbox.getHasAudio();
			setNames = talkbox.getSetNames();
			imageButtons = talkbox.getImages();

			objectInputStream.close();
			sound = new Sound();

		} catch (IOException | ClassNotFoundException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
