package main.java.TalkBox.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.TalkBox.controller.Main;
import main.java.TalkBox.model.Check;
import main.java.TalkBox.model.Sound;
import main.java.TalkBox.model.TalkBox;

import javax.swing.UIManager;

public class TalkBoxGui extends JFrame {
	/**
	 * 
	 */
//	private static final long serialVersionUID = 8569879131969549758L;
	/**
	 * 
	 */
	private TalkBox talkbox;
	private JComboBox<String> setSelector;
	private JPanel contentPane, btnPanel, configExitPanel, mainPanel, loadPanel;
	private JLabel lblTitle;
	private JTextPane TextPane0, TextPane1, TextPane2, TextPane3, TextPane4, TextPane5;
	private JTextPane[] currentText = new JTextPane[6];
	private JButton Button0, Button1, Button2, Button3, Button4, Button5, btnPrevSet, btnStop, btnConfig, btnExit,
			btnLoad;
	private JButton[] currentBtn = new JButton[6];
	private String currentSettings = "default", path = "TalkBoxData/", defaultText = "Press to Configure!";
	private String[] setNames;
	private String[][] audioFileNames;
	private Sound sound;
	private Check check = new Check();
	private File[] sFile;
	private ImageIcon[][] imageButtons;
	private List<String> tbcFiles = new ArrayList<String>();
	private List<File> allFiles;
	private List<String> imgFiles = new ArrayList<>();
	private boolean[][] hasSound;
	private int currentBtnSet = 0, audioSets, totAudioBtns;
	private JLabel lblSettingTitle;

	/**
	 * Launch the application.
	 */

	/**
	 * Create default Simulator the frame.
	 */
	public TalkBoxGui() {
		Main.LOG.info("TalkBoxGUI App started");
		setVisible(true);
		setSize(new Dimension(1030, 768));
//		setAlwaysOnTop(true);
		setBackground(Color.WHITE);
		setTitle("TalkBox Simulator V2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		init();

		setVariables();
		loadDefaults();
		setDefaults();
		setSettingsList();
		setSetList();
		setButtons();
		setActions();
	}

	/*
	 * Load default layout with all the files selected
	 */
	private void loadDefaults() {

		int j = getAllFiles();

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
		currentBtnSet = 0;
		setNames = new String[audioSets];

		for (int i = 0; i < audioSets; i++)
			setNames[i] = "Audio Set " + (i + 1);
		lblTitle.setText(setNames[currentBtnSet]);

		j = 0;
		for (int i = 0; i < audioSets; i++) {
			for (int k = 0; k < 6; k++) {
				if (sFile[j] == null) {
					audioFileNames[i][k] = defaultText;
					hasSound[i][k] = false;
				} else {
					audioFileNames[i][k] = sFile[j].toString();
					hasSound[i][k] = true;
				}
				j++;
			}
		}

	}

	private int getAllFiles() {
		path = "TalkBoxData/Sounds/";
		allFiles = Arrays.asList(new File(path).listFiles());
		sFile = new File[allFiles.size() * 2];
		String file;
		int j = 0;
		for (int i = 0; i < allFiles.size(); i++) {
			file = allFiles.get(i).toString();
			if (check.isWav(file)) {
				sFile[j] = allFiles.get(i);
				j++;
			}
		}

		path = "TalkBoxData/Images/";
		allFiles = Arrays.asList(new File(path).listFiles());
		for (int i = 0; i < allFiles.size(); i++) {
			file = allFiles.get(i).toString();
			if (check.isImg(file))
				imgFiles.add(file);
		}

		path = "TalkBoxData/Settings/";
		allFiles = Arrays.asList(new File(path).listFiles());
		for (int i = 0; i < allFiles.size(); i++) {
			file = allFiles.get(i).toString();
			if (check.isTbc(file))
				tbcFiles.add(file);
		}

		return j;

	}

	/*
	 * Set Default values to certain variables
	 */
	private void setDefaults() {

		imageButtons = new ImageIcon[audioSets][6];

		for (int i = 0; i < hasSound.length; i++) {
			for (int k = 0; k < 6; k++) {
				if (hasSound[i][k]) {
					imageButtons[i][k] = new ImageIcon("TalkBoxData/Images/smiley_face.jpg");
				} else {
					imageButtons[i][k] = new ImageIcon("TalkBoxData/Images/Empty_Btn.png");
				}
			}
		}
		String title = currentSettings + ".tbc";
		lblSettingTitle.setText(title.toUpperCase());

	}

	/*
	 * Set variables to arrays
	 */
	private void setVariables() {

		currentBtn[0] = Button0;
		currentBtn[1] = Button1;
		currentBtn[2] = Button2;
		currentBtn[3] = Button3;
		currentBtn[4] = Button4;
		currentBtn[5] = Button5;

		currentText[0] = TextPane0;
		currentText[1] = TextPane1;
		currentText[2] = TextPane2;
		currentText[3] = TextPane3;
		currentText[4] = TextPane4;
		currentText[5] = TextPane5;

	}

	/*
	 * Add Actionlisteners to buttons
	 */
	private void setActions() {
		for (int i = 0; i < 6; i++) {
			currentBtn[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					makeSound((JButton) e.getComponent());
				}
			});
		}

		btnStop.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				sound.stopSound();
			}

		});

		setSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentBtnSet = setSelector.getSelectedIndex();
				changeSet();
			}
		});

		btnLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createLoad();
			}
		});

	}

	/*
	 * Set Buttons
	 */
	private void setButtons() {

		for (int i = 0; i < 6; i++) {
			currentBtn[i].setIcon(resizeImg(imageButtons[currentBtnSet][i].toString()));
			currentBtn[i].setToolTipText(findName(getName(audioFileNames[currentBtnSet][i])));
			currentText[i].setText(toDisplayCase((findName(getName(audioFileNames[currentBtnSet][i])))));
		}
		lblTitle.setText(setNames[currentBtnSet]);

	}

	/*
	 * Set settings List in load ComboBox
	 */
	private void setSettingsList() {

		DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<>();
		for (int i = 0; i < tbcFiles.size(); i++) {
			aModel.addElement(getName(tbcFiles.get(i)));
		}

	}

	/*
	 * Sets the set list
	 */
	private void setSetList() {
		DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<>();
		for (int i = 0; i < audioSets; i++) {
			aModel.addElement(setNames[i]);
		}
		setSelector.setModel(aModel);
		lblTitle.setText((String) setSelector.getSelectedItem());
	}

	private void createLoad() {
		sound.stopSound();

		JFileChooser j = new JFileChooser("TalkBoxData/Settings/");
		j.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Settings", "tbc");
		j.setFileFilter(filter);
		j.showOpenDialog(this);

		File file = j.getSelectedFile();
		if (file != null) {
			String name = file.getName();
			currentSettings = getName(name);
			// setTitle("TalkBox Simulator V2.0 [" + name + "]");
			String title = currentSettings + ".tbc";
			lblSettingTitle.setText(title.toUpperCase());
			getSetting(name);
		}

	}

	/*
	 * Set CurrentSettings
	 */
	public void setCurrentSettings(String name) {
		this.currentSettings = name;
	}

	/*
	 * Change Current Set
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
	 * Play sound on button press
	 */
	private void makeSound(JButton button) {

		String text = button.getToolTipText();
		int currentSelected = -1;
		boolean found = false;
		for (int i = 0; i < 6; i++) {
			if (text.equals(currentBtn[i].getToolTipText())) {
				currentSelected = i;
				if (hasSound[currentBtnSet][currentSelected]) {
					sound.playSound(audioFileNames[currentBtnSet][currentSelected]);
					found = true;
				}
			}
		}

		if (!found) {
			sound.stopSound();
			createConfigure();
		}
	}

	/*
	 * Return the name of the file
	 */
	private String getName(String string) {
		if (string.equals(defaultText))
			return string;

		String name = "";
		name = string.substring(string.indexOf("\\") + 1, string.indexOf("."));
		return name;
	}

	/*
	 * Resize the Images to fit buttons
	 */
	private ImageIcon resizeImg(String path) {

		ImageIcon icon = new ImageIcon(path);
		Image scaleImage = icon.getImage().getScaledInstance(145, 120, Image.SCALE_SMOOTH);
		ImageIcon ic = new ImageIcon(scaleImage);

		return ic;
	}

	/*
	 * Gives the name of the Sound File
	 */
	private String findName(String name) {

		if (name.equals(defaultText))
			return name;

		String[] words = name.split("_");
		String str = "";
		for (String w : words)
			str += toDisplayCase(w) + " ";
		return str.substring(7, str.length());
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
	 * Intialize display
	 */
	private void init() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnStop = new JButton("STOP");
		btnStop.setFont(new Font("Dialog", Font.BOLD, 24));

		lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));

		configExitPanel = new JPanel();
		configExitPanel.setBackground(Color.WHITE);

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);

		loadPanel = new JPanel();
		loadPanel.setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		lblSettingTitle = new JLabel("Title");
		lblSettingTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnStop, GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE).addGap(20))
						.addGroup(
								gl_contentPane.createSequentialGroup()
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 976,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(28, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 304,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(lblSettingTitle, GroupLayout.PREFERRED_SIZE, 289,
												GroupLayout.PREFERRED_SIZE)
										.addGap(28).addComponent(loadPanel, GroupLayout.PREFERRED_SIZE, 277,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)))
								.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(configExitPanel, GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
								.addContainerGap()))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
										.addComponent(lblSettingTitle, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
								.addComponent(loadPanel, GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(235).addComponent(configExitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		btnLoad = new JButton("LOAD");
		btnLoad.setFont(new Font("Dialog", Font.BOLD, 18));
		loadPanel.add(btnLoad);

		JLabel lblSet = new JLabel("Select Audio Set:");
		lblSet.setFont(new Font("Dialog", Font.BOLD, 18));
		lblSet.setBackground(Color.WHITE);
		panel.add(lblSet);

		setSelector = new JComboBox<String>();
		setSelector.setFont(new Font("Dialog", Font.BOLD, 18));
		panel.add(setSelector);

		btnPrevSet = new JButton("<");
		btnPrevSet.setFont(new Font("Tahoma", Font.BOLD, 24));

		btnPanel = new JPanel();
		btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnPanel.setBackground(Color.WHITE);

		Button0 = new JButton("");
		Button0.setAlignmentX(Component.CENTER_ALIGNMENT);
		Button0.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize() + 4));
		Button0.setBackground(new Color(0, 0, 0));

		Button1 = new JButton("");
		Button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		Button1.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button1.setBackground(new Color(0, 0, 0));

		Button2 = new JButton("");
		Button2.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button2.setBackground(new Color(0, 0, 0));

		Button3 = new JButton("");
		Button3.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button3.setBackground(new Color(0, 0, 0));

		Button4 = new JButton("");
		Button4.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button4.setBackground(new Color(0, 0, 0));

		Button5 = new JButton("");
		Button5.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button5.setBackground(new Color(0, 0, 0));

		TextPane0 = new JTextPane();
		TextPane0.setContentType("plain/text\r\n");
		TextPane0.setFont(new Font("Dialog", Font.BOLD, 18));
		TextPane0.setDisabledTextColor(Color.BLACK);
		TextPane0.setEnabled(false);
		TextPane0.setEditable(false);
		TextPane0.setBackground(Color.WHITE);

		TextPane1 = new JTextPane();
		TextPane1.setContentType("plain/text\r\n");
		TextPane1.setEnabled(false);
		TextPane1.setFont(new Font("Dialog", Font.BOLD, 18));
		TextPane1.setDisabledTextColor(Color.BLACK);
		TextPane1.setEditable(false);
		TextPane1.setBackground(Color.WHITE);

		TextPane2 = new JTextPane();
		TextPane2.setContentType("plain/text\r\n");
		TextPane2.setEnabled(false);
		TextPane2.setFont(new Font("Dialog", Font.BOLD, 18));
		TextPane2.setDisabledTextColor(Color.BLACK);
		TextPane2.setEditable(false);
		TextPane2.setBackground(Color.WHITE);

		TextPane3 = new JTextPane();
		TextPane3.setContentType("plain/text\r\n");
		TextPane3.setEnabled(false);
		TextPane3.setFont(new Font("Dialog", Font.BOLD, 18));
		TextPane3.setDisabledTextColor(Color.BLACK);
		TextPane3.setEditable(false);
		TextPane3.setBackground(Color.WHITE);

		TextPane4 = new JTextPane();
		TextPane4.setContentType("plain/text\r\n");
		TextPane4.setEnabled(false);
		TextPane4.setFont(new Font("Dialog", Font.BOLD, 18));
		TextPane4.setDisabledTextColor(Color.BLACK);
		TextPane4.setEditable(false);
		TextPane4.setBackground(Color.WHITE);

		TextPane5 = new JTextPane();
		TextPane5.setContentType("plain/text\r\n");
		TextPane5.setEnabled(false);
		TextPane5.setFont(new Font("Dialog", Font.BOLD, 18));
		TextPane5.setDisabledTextColor(Color.BLACK);
		TextPane5.setEditable(false);
		TextPane5.setBackground(Color.WHITE);

		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBtnSet++;
				changeSet();
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 24));
		GroupLayout gl_btnPanel = new GroupLayout(btnPanel);
		gl_btnPanel.setHorizontalGroup(gl_btnPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_btnPanel
				.createSequentialGroup().addGap(2)
				.addGroup(gl_btnPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_btnPanel
						.createSequentialGroup().addGap(2)
						.addComponent(Button0, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(Button1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(Button2, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(Button3, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(Button4, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(Button5, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_btnPanel.createSequentialGroup()
								.addComponent(TextPane0, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addGap(8)
								.addComponent(TextPane1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addGap(8)
								.addComponent(TextPane2, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addGap(8)
								.addComponent(TextPane3, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addGap(8)
								.addComponent(TextPane4, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addGap(8).addComponent(TextPane5, GroupLayout.PREFERRED_SIZE, 134,
										GroupLayout.PREFERRED_SIZE)))));
		gl_btnPanel.setVerticalGroup(gl_btnPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_btnPanel
				.createSequentialGroup()
				.addGroup(gl_btnPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_btnPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(Button1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
								.addComponent(Button0, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
						.addComponent(Button2, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(Button3, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(Button4, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(Button5, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGap(8)
				.addGroup(gl_btnPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(TextPane0, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(TextPane1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(TextPane2, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(TextPane3, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(TextPane4, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(TextPane5, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))));
		gl_btnPanel.setHonorsVisibility(false);
		btnPanel.setLayout(gl_btnPanel);
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setAutoCreateGaps(true);
		gl_mainPanel.setHorizontalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addGap(10).addComponent(btnPrevSet).addGap(6)
						.addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 848, GroupLayout.PREFERRED_SIZE).addGap(6)
						.addComponent(button)));
		gl_mainPanel.setVerticalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnPrevSet, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
				.addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addComponent(button, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE));
		mainPanel.setLayout(gl_mainPanel);
		// mainPanel.setFocusTraversalPolicy(
		// new FocusTraversalOnArray(new Component[] { btnPrevSet, btnPanel, Button0,
		// Button1, Button2, Button3,
		// Button4, Button5, TextPane0, TextPane1, TextPane2, TextPane3, TextPane4,
		// TextPane5, button }));
		btnPrevSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentBtnSet--;
				changeSet();
			}
		});

		btnConfig = new JButton("Configure");
		btnConfig.setFont(new Font("Dialog", Font.BOLD, 24));

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Dialog", Font.BOLD, 24));
		GroupLayout gl_configExitPanel = new GroupLayout(configExitPanel);
		gl_configExitPanel.setHorizontalGroup(gl_configExitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_configExitPanel.createSequentialGroup().addContainerGap()
						.addComponent(btnConfig, GroupLayout.PREFERRED_SIZE, 887, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnExit).addContainerGap()));
		gl_configExitPanel.setVerticalGroup(gl_configExitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_configExitPanel.createSequentialGroup().addGap(5).addGroup(gl_configExitPanel
						.createParallelGroup(Alignment.BASELINE).addComponent(btnExit).addComponent(btnConfig))));
		gl_configExitPanel.setAutoCreateContainerGaps(true);
		configExitPanel.setLayout(gl_configExitPanel);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});
		btnConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createConfigure();
			}
		});
		contentPane.setLayout(gl_contentPane);

	}

	/*
	 * Set .tbc settings based on configuration
	 */
	private void setSetting() {

		try {
			talkbox = new TalkBox();

			talkbox.setAudioFileNames(audioFileNames);
			talkbox.setHasAudio(hasSound);
			talkbox.setNumberOfAudioSets(audioSets);
			talkbox.setNumberOfAudioButtons(6);
			talkbox.setSetNames(setNames);
			talkbox.setImages(imageButtons);

			String filePath = "TalkBoxData/Settings/" + currentSettings + ".tbc";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(talkbox);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get .tbc settings and initiate sound, buttongroup, talkbox object
	 */
	private void getSetting(String name) {

		try {
			System.out.println(name);
			String path = "TalkBoxData/Settings/" + name;
			FileInputStream fileInputStream = new FileInputStream(new File(path));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			talkbox = (TalkBox) objectInputStream.readObject();

			audioFileNames = talkbox.getAudioFileNames();
			totAudioBtns = talkbox.getNumberOfAudioButtons();
			audioSets = talkbox.getNumberOfAudioSets();
			hasSound = talkbox.getHasAudio();
			setNames = talkbox.getSetNames();
			imageButtons = talkbox.getImages();

			sound = new Sound();
			currentBtnSet = 0;
			setSetList();
			changeSet();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		String string = "";
		string += "Audio Sets: " + this.audioSets + "\tTBC Files:\n";
		for (String f : this.tbcFiles)
			string += f + "\n";
		string += "AudioFileNames:\n";
		for (String[] s : this.audioFileNames) {
			for (String f : s)
				string += f + "\n";
		}
		string += "Current Settings: " + currentSettings + "\tCurrent Audio Set: " + currentBtnSet;
		return string;

	}

	/*
	 * Open Configuration App
	 */
	private void createConfigure() {
		sound.stopSound();
		setSetting();
		new ConfigurationGUI(this, currentSettings);
	}
}
