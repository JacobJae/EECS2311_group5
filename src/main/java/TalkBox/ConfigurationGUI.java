package main.java.TalkBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.AbstractListModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.FlowLayout;

public class ConfigurationGUI extends JFrame {
	private JLabel display;
	private JToggleButton[][] audioFileButtons;
	private JPanel contentPane;
	private Font font;
	private ButtonGroup btngroup;
	private Sound sound;
	private TalkBox talkbox = new TalkBox();
	private int currentBtnSet = 0, width, height;
	private int selectedBtnIndex = -1, selectedListIndex = -1;
	private TalkBoxGui talkboxgui;
	private final ButtonGroup currentBtnGrp = new ButtonGroup();
	private JList<String> listAudioList;
	private JToggleButton btnSound1;
	private JToggleButton btnSound2;
	private JToggleButton btnSound3;
	private JToggleButton btnSound4;
	private JToggleButton btnSound5;
	private JToggleButton btnSound6;
	private JTextArea txtBtn1;
	private JTextArea txtBtn2;
	private JTextArea txtBtn3;
	private JTextArea txtBtn4;
	private JTextArea txtBtn5;
	private JTextArea txtBtn6;
	private JButton btn1I;
	private JButton btn1D;
	private JButton btn1S;
	private JButton btn2I;
	private JButton btn2D;
	private JButton btn2S;
	private JButton btn3I;
	private JButton btn3D;
	private JButton btn3S;
	private JButton btn4I;
	private JButton btn4D;
	private JButton btn4S;
	private JButton btn5I;
	private JButton btn5D;
	private JButton btn5S;
	private JButton btn6I;
	private JButton btn6D;
	private JButton btn6S;
	private JButton btnPreviousSet;
	private JButton btnNextSet;
	private JButton btnAddNewSet;
	private JButton btnDeleteSet;
	private JComboBox<String> setSelector;
	private JButton btnRecord_1;
	private JButton btnSave;
	private JButton btnSaveAs;
	private JButton btnExit;
	private JToggleButton[] currentAudioBtns = new JToggleButton[6];
	private JTextArea[] currentAudioText = new JTextArea[6];
	private JPanel[] btnCtrlPanel = new JPanel[6];
	private JPanel[] btnPanel = new JPanel[6];
	private JButton[] deleteBtns = new JButton[6], imageBtns = new JButton[6], swapBtns = new JButton[6];
	private JPanel btn1Panel;
	private JPanel btn2Panel;
	private JPanel btn3Panel;
	private JPanel btn4Panel;
	private JPanel btn5Panel;
	private JPanel btn6Panel;
	private JPanel cntBtn1;
	private JPanel cntBtn2;
	private JPanel cntBtn3;
	private JPanel cntBtn4;
	private JPanel cntBtn5;
	private JPanel cntBtn6;
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

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				talkboxgui.setVisible(true);
				talkboxgui.reset(currentSettings);
			}
		});

		init();
		this.currentSettings = currentSettings;
		setVariables();
		setDefaults();
		loadDefaults();
		addActions();
	}

	/*
	 * Adds every button to seperate Arrays for further use
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

		setSetList();
		setSettingsList();

		setButtons(currentBtnSet);

	}

	/*
	 * Set values to buttons
	 */
	private void setButtons(int currentSet) {

		for (int i = 0; i < 6; i++) {
			currentAudioBtns[i].setText(getName(audioFileNames[currentBtnSet][i]));
			currentAudioBtns[i].setText(getName(audioFileNames[currentBtnSet][i]));
		}

		List<String> finalNames = new ArrayList<String>();
		for (String s : names)
			finalNames.add(s);

		for (int i = 0; i < 6; i++) {
			if (hasSound[currentSet][i]) {
				finalNames.remove(currentAudioBtns[i].getText());
			}
		}

		setModel(finalNames);
		selectedBtnIndex = -1;
		selectedListIndex = -1;
		activateBtns();
		deslectBtns();

	}

	/*
	 * Add onClick Actions for every element
	 */
	private void addActions() {

		for (int i = 0; i < 6; i++) {
			currentAudioBtns[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					selectedBtnIndex = getSelectedIndex();
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

		btnSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// NEED TO CREATE A WINDOW MODULE WHERE YOU CAN NAME AND SAVE THE FILE JUST LIKE
				// LOADING ONE

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
	
	public void setCurrentSettings(String name)
	{
		this.currentSettings = name;
	}

	private void createSaveAs() {

		new SaveAsDialogue(this).setVisible(true);
		this.setEnabled(false);

	}

	protected void openRecord() {

		new RecordGUI(this).setVisible(true);
		this.setEnabled(false);

	}

	public void refresh() {
		loadDefaults();
	}

	private String getName(String string) {
		String name = "";
		name = string.substring(string.indexOf("\\") + 1, string.indexOf("."));
		return name;
	}

	private Boolean isWav(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".wav"))
			return true;

		return false;
	}

	private Boolean isTbc(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".tbc"))
			return true;

		return false;
	}

	private void setModel(List<String> finalNames) {

		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (int i = 0; i < finalNames.size(); i++) {
			listModel.addElement(finalNames.get(i));
		}
		listAudioList.setModel(listModel);
		listAudioList.setSelectedIndex(-1);

	}

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

	}

	private void changeSet() {

		if (currentBtnSet < 0)
			currentBtnSet = audioSets - 1;
		else if (currentBtnSet >= audioSets)
			currentBtnSet = 0;

		setSelector.setSelectedIndex(currentBtnSet);
		lblTitle.setText((String) setSelector.getSelectedItem());
		setButtons(currentBtnSet);

	}

	private void swap() {
		String temp = "TalkBoxData\\" + listAudioList.getSelectedValue().toString() + ".wav";
		audioFileNames[currentBtnSet][selectedBtnIndex] = temp;
		deslectBtns();
		setButtons(currentBtnSet);
	}

	private void delete() {
		audioFileNames[currentBtnSet][selectedBtnIndex] = "Press to Configure!.";
		deslectBtns();
		setButtons(currentBtnSet);
	}

	private void activateBtns() {

		int index = selectedBtnIndex;
		boolean type = (selectedBtnIndex != -1) && (selectedListIndex != -1);

		for (int i = 0; i < 6; i++) {
			if (i == index) {
				if (type)
					swapBtns[i].setEnabled(true);

				imageBtns[i].setEnabled(true);
				deleteBtns[i].setEnabled(true);
				btnCtrlPanel[i].setEnabled(true);
			} else {
				if (type)
					swapBtns[i].setEnabled(false);

				imageBtns[i].setEnabled(false);
				deleteBtns[i].setEnabled(false);
				btnCtrlPanel[i].setEnabled(false);

			}
		}

	}

	private int getSelectedIndex() {

		for (int i = 0; i < 6; i++) {
			if (currentAudioBtns[i].isSelected())
				return i;
		}
		return -1;

	}

	private void print(Object o) {
		System.out.println(o.toString());
	}

	private void setDefaults() {
		currentBtnSet = 0;
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel saveExitPanel = new JPanel();
		saveExitPanel.setBackground(Color.WHITE);

		JPanel crntSetPanel = new JPanel();
		crntSetPanel.setBackground(Color.LIGHT_GRAY);

		lblTitle = new JTextField("Set Audioset Title");

		JPanel audioSelPanel = new JPanel();
		audioSelPanel.setBackground(Color.WHITE);

		JPanel setSelPanel = new JPanel();
		setSelPanel.setBackground(Color.WHITE);

		JPanel setCtrlPanel = new JPanel();
		setCtrlPanel.setBackground(Color.WHITE);

		JPanel saveLoadPanel = new JPanel();
		saveLoadPanel.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
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
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(crntSetPanel, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(audioSelPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 354,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(setCtrlPanel, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(setSelPanel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
						.addComponent(saveLoadPanel, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE))
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

		btnSound2 = new JToggleButton("New button");
		currentBtnGrp.add(btnSound2);

		txtBtn2 = new JTextArea();

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

		btnSound3 = new JToggleButton("New button");
		currentBtnGrp.add(btnSound3);

		txtBtn3 = new JTextArea();

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

		btnSound4 = new JToggleButton("New button");
		currentBtnGrp.add(btnSound4);

		txtBtn4 = new JTextArea();

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

		btnSound5 = new JToggleButton("New button");
		currentBtnGrp.add(btnSound5);

		txtBtn5 = new JTextArea();

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

		btnSound6 = new JToggleButton("New button");
		currentBtnGrp.add(btnSound6);

		txtBtn6 = new JTextArea();

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

		btnSound1 = new JToggleButton("New button");
		currentBtnGrp.add(btnSound1);

		txtBtn1 = new JTextArea();

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
		/*
		 * recording = false; int gap = height / 30, btnWidth = (width - 8 * gap) / 6;
		 * 
		 * List<String> audioList = getAudioList(); listAreaAudio = new
		 * JList<String>(audioList.toArray(new String[audioList.size()]));
		 * listAreaAudio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * listAreaAudio.setFont(new Font("Serif", Font.PLAIN, 14));
		 * listAreaAudio.setLayoutOrientation(JList.VERTICAL);
		 * 
		 * scrollerAudio = new JScrollPane(listAreaAudio);
		 * scrollerAudio.setVerticalScrollBarPolicy(ScrollPaneConstants.
		 * VERTICAL_SCROLLBAR_ALWAYS); scrollerAudio.setBounds(gap + 0 * btnWidth + 0 *
		 * gap, height / 3, btnWidth, height / 3); contentPane.add(scrollerAudio);
		 * 
		 * List<Integer> btnList = new ArrayList<>(); for (int i = 0; i <
		 * talkbox.getNumberOfAudioButtons(); i++) { btnList.add(i + 1); } listAreaBtn =
		 * new JList<Integer>(btnList.toArray(new Integer[btnList.size()]));
		 * listAreaBtn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * listAreaBtn.setFont(new Font("Serif", Font.PLAIN, 14));
		 * listAreaBtn.setLayoutOrientation(JList.VERTICAL);
		 * 
		 * scrollerButtons = new JScrollPane(listAreaBtn);
		 * scrollerButtons.setVerticalScrollBarPolicy(ScrollPaneConstants.
		 * VERTICAL_SCROLLBAR_ALWAYS); scrollerButtons.setBounds(gap + 1 * btnWidth + 1
		 * * gap, height / 3, btnWidth, height / 6); contentPane.add(scrollerButtons);
		 * //-------------------------------------EDIT----------------------------------
		 * ------------------------------------------- btnIncBtnSet = new
		 * JButton("Inc Btn Set"); btnIncBtnSet.setFont(new Font("Serif", Font.PLAIN,
		 * width / 50)); btnIncBtnSet.setBounds(gap + 0 * btnWidth + 0 * gap, height /
		 * 64 * 17, btnWidth, height / 16); btnIncBtnSet.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { int currentSet =
		 * talkbox.getNumberOfAudioSets(); talkbox.incAudioSets(); audioFileButtons =
		 * new
		 * JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons
		 * ()]; init(); putButtons(currentBtnSet);
		 * setDisplay("Number of Button set has been increased from " + currentSet +
		 * " to " + (currentSet + 1)); } }); contentPane.add(btnIncBtnSet);
		 * 
		 * btnDecBtnSet = new JButton("Dec Btn Set"); btnDecBtnSet.setFont(new
		 * Font("Serif", Font.PLAIN, width / 50)); btnDecBtnSet.setBounds(gap + 1 *
		 * btnWidth + 1 * gap, height / 64 * 17, btnWidth, height / 16);
		 * btnDecBtnSet.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { int currentSet =
		 * talkbox.getNumberOfAudioSets(); if (currentSet < 2) {
		 * setDisplay("Number of Audio sets can not be lower than 1"); } else {
		 * talkbox.decAudioSets(); audioFileButtons = new
		 * JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons
		 * ()]; if (currentSet - 1 == currentBtnSet) { currentBtnSet = 0; putButtons(0);
		 * selectedBtnIndex = 0; } else { putButtons(currentBtnSet); selectedBtnIndex =
		 * 0; } init(); putButtons(currentBtnSet); setDisplay(
		 * "Number of Button set has been decreased from " + currentSet + " to " +
		 * (currentSet - 1)); contentPane.repaint(); } } });
		 * contentPane.add(btnDecBtnSet);
		 * 
		 * btnIncBtnNum = new JButton("Inc Btn Num"); btnIncBtnNum.setFont(new
		 * Font("Serif", Font.PLAIN, width / 50)); btnIncBtnNum.setBounds(gap + 2 *
		 * btnWidth + 2 * gap, height / 64 * 17, btnWidth, height / 16);
		 * btnIncBtnNum.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { int currentBtn =
		 * talkbox.getNumberOfAudioButtons(); for (int i = 0; i <
		 * talkbox.getNumberOfAudioButtons(); i++) { JToggleButton btn =
		 * audioFileButtons[currentBtnSet][i]; if (btn != null)
		 * getContentPane().remove(btn); } talkbox.incAudioButtons(); audioFileButtons =
		 * new
		 * JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons
		 * ()]; init(); putButtons(currentBtnSet); if (currentBtn < 8) {
		 * setDisplay("Number of Audio Buttons has been increased from " + currentBtn +
		 * " to " + (currentBtn + 1)); } else {
		 * setDisplay("Number of Audio Buttons can not exceed 8"); }
		 * contentPane.repaint(); selectedBtnIndex = 0;
		 * 
		 * } }); contentPane.add(btnIncBtnNum);
		 * 
		 * btnDecBtnNum = new JButton("Dec Btn Num"); btnDecBtnNum.setFont(new
		 * Font("Serif", Font.PLAIN, width / 50)); btnDecBtnNum.setBounds(gap + 3 *
		 * btnWidth + 3 * gap, height / 64 * 17, btnWidth, height / 16);
		 * btnDecBtnNum.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { int currentBtn =
		 * talkbox.getNumberOfAudioButtons(); if (currentBtn < 2) {
		 * setDisplay("Number of Audio buttons can not be lower than 1"); } else { for
		 * (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) { JToggleButton btn =
		 * audioFileButtons[currentBtnSet][i]; if (btn != null)
		 * getContentPane().remove(btn); } talkbox.decAudioButtons(); audioFileButtons =
		 * new
		 * JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons
		 * ()]; init(); putButtons(currentBtnSet); setDisplay(
		 * "Number of Audio Buttons has been decreased from " + currentBtn + " to " +
		 * (currentBtn - 1)); contentPane.repaint(); selectedBtnIndex = 0; } } });
		 * contentPane.add(btnDecBtnNum);
		 * //---------------------------------------------------------------------------
		 * -------------------------------------------- btnSwap = new JButton("Swap");
		 * btnSwap.setFont(font); btnSwap.setBounds(gap + 2 * btnWidth + 2 * gap, height
		 * / 3, btnWidth, height / 6); contentPane.add(btnSwap);
		 * btnSwap.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { for (int i = 0; i <
		 * talkbox.getNumberOfAudioButtons(); i++) { JToggleButton btn =
		 * audioFileButtons[currentBtnSet][i]; if (btn != null)
		 * getContentPane().remove(btn); } contentPane.repaint(); currentBtnSet =
		 * (currentBtnSet + 1) % talkbox.getNumberOfAudioSets();
		 * putButtons(currentBtnSet);
		 * setDisplay(String.format("Change to button set %d", currentBtnSet + 1));
		 * selectedBtnIndex = 0; } });
		 * 
		 * btnAdd = new JButton("Add"); btnAdd.setFont(font); btnAdd.setBounds(gap + 3 *
		 * btnWidth + 3 * gap, height / 3, btnWidth, height / 6);
		 * contentPane.add(btnAdd);
		 * 
		 * btnAdd.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if
		 * (listAreaAudio.getSelectedValue() == null) {
		 * setDisplay("Select Audio from list"); } else if
		 * (listAreaBtn.getSelectedValue() == null) {
		 * setDisplay("Select button position from list"); } else { String str =
		 * listAreaAudio.getSelectedValue(); int num = listAreaBtn.getSelectedValue();
		 * talkbox.addAudio(currentBtnSet, num, str);
		 * 
		 * init(); putButtons(currentBtnSet); setDisplay("Audio File \"" + str +
		 * "\" has been added to " + num); contentPane.repaint(); } } });
		 * 
		 * btnRemove = new JButton("Remove"); btnRemove.setFont(font);
		 * btnRemove.setBounds(gap + 4 * btnWidth + 4 * gap, height / 3, btnWidth,
		 * height / 6); contentPane.add(btnRemove);
		 * 
		 * btnRemove.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if (selectedBtnIndex
		 * == 0) { setDisplay("Choose Button First"); } else { String str =
		 * talkbox.getAudioFileNames()[currentBtnSet][selectedBtnIndex - 1];
		 * talkbox.removeAudio(currentBtnSet, selectedBtnIndex);
		 * 
		 * init(); putButtons(currentBtnSet); setDisplay("Audio File \"" + str +
		 * "\" was removed from set" + (currentBtnSet + 1)); contentPane.repaint(); } }
		 * });
		 * 
		 * btnRecord = new JButton("Record"); btnRecord.setFont(font);
		 * btnRecord.setBounds(gap + 5 * btnWidth + 5 * gap, height / 3, btnWidth,
		 * height / 6); contentPane.add(btnRecord);
		 * 
		 * btnRecord.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if (recording) {
		 * sound.stopRecording(); init(); putButtons(currentBtnSet);
		 * setDisplay("Record completed!"); contentPane.repaint(); } else { String name
		 * = textField.getText(); if (name.length() < 1) {
		 * setDisplay("Enter file name"); } else if (getAudioList().contains(name +
		 * ".wav")) { setDisplay("Audio file name " + name +
		 * ".wav already exist, try another name"); } else { recording = true;
		 * sound.startRecording(textField.getText()); setDisplay("Recording......"); } }
		 * } });
		 * 
		 * textField = new JTextField(); textField.setFont(new Font("Serif", Font.PLAIN,
		 * 14)); textField.setBounds(gap + 1 * btnWidth + 1 * gap, height / 16 * 10,
		 * btnWidth, height / 18); contentPane.add(textField);
		 * 
		 * JLabel nameDisplay = new JLabel("Audio file name");
		 * nameDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		 * nameDisplay.setFont(new Font("Serif", Font.PLAIN, 14));
		 * nameDisplay.setBounds(gap + 1 * btnWidth + 1 * gap, height / 16 * 9,
		 * btnWidth, height / 18); contentPane.add(nameDisplay);
		 * 
		 * display = new JLabel("Start Configuration! (Current button set: 1)");
		 * display.setHorizontalAlignment(SwingConstants.CENTER); display.setFont(new
		 * Font("Serif", Font.PLAIN, width / 50)); display.setBounds(gap + 2 * btnWidth
		 * + 2 * gap, height / 5 * 3, btnWidth * 4 + gap * 3, height / 10);
		 * contentPane.add(display);
		 * 
		 * btnSave = new JButton("Save Setting"); btnSave.setFont(font);
		 * btnSave.setBounds(width / 30, height / 4 * 3, width / 30 * 21, height / 8);
		 * contentPane.add(btnSave);
		 * 
		 * btnSave.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * setDisplay("New setting has been saved"); talkbox.finalize(); SetSetting(); }
		 * });
		 * 
		 * btnReturn = new JButton("Return"); btnReturn.setFont(font);
		 * btnReturn.setBounds(width / 30 * 23, height / 4 * 3, width / 30 * 5, height /
		 * 8); contentPane.add(btnReturn);
		 * 
		 * btnReturn.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * talkboxgui.setVisible(true); talkboxgui.reset(talkbox); dispose(); } });
		 */
	}

	/*
	 * create button in button set setNumber
	 */
	private void putButtons(int setNumber) {
		String AudioFileNames[][] = talkbox.getAudioFileNames();
		for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
			String fileName = AudioFileNames[setNumber][i];
			if (fileName != null) {
				int index = i + 1, gap = height / 30, btnWidth = (width - (talkbox.getNumberOfAudioButtons() + 2) * gap)
						/ talkbox.getNumberOfAudioButtons();
				JToggleButton btn = new JToggleButton(
						fileName.substring(fileName.indexOf("/") + 1, fileName.indexOf(".")));
				btn.setFont(font);
				btn.setBounds(gap + i * btnWidth + i * gap, height / 30, btnWidth, height / 5);
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedBtnIndex = index;
					}
				});
				btngroup.add(btn);
				audioFileButtons[setNumber][i] = btn;
				contentPane.add(btn);
			}
		}
	}

	public void setSetting() {
		try {
			talkbox.setAudioFileNames(audioFileNames);
			talkbox.setHasAudio(hasSound);
			talkbox.setNumberOfAudioSets(audioSets);
			talkbox.setNumberOfAudioButtons(6);
			talkbox.setsFile(sFile);

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

	public void setSetting(String name) {
		try {
			talkbox.setAudioFileNames(audioFileNames);
			talkbox.setHasAudio(hasSound);
			talkbox.setNumberOfAudioSets(audioSets);
			talkbox.setNumberOfAudioButtons(6);
			talkbox.setsFile(sFile);

			String filePath = "TalkBoxData/" + name + ".tbc";
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(talkbox);
			objectOutputStream.flush();
			objectOutputStream.close();

			talkboxgui.setVisible(true);
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

			if (path.equals(null)) {
				FileInputStream fileInputStream = new FileInputStream(
						Paths.get(System.getProperty("user.dir"), "TalkBoxData", currentSettings + ".tbc").toString());
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				talkbox = (TalkBox) objectInputStream.readObject();

				audioFileNames = talkbox.getAudioFileNames();
				totAudioBtns = talkbox.getNumberOfAudioButtons();
				audioSets = talkbox.getNumberOfAudioSets();
				hasSound = talkbox.getHasAudio();
				sFile = talkbox.getsFile();

				objectInputStream.close();
			} else {
				FileInputStream fileInputStream = new FileInputStream(new File(path));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				talkbox = (TalkBox) objectInputStream.readObject();

				audioFileNames = talkbox.getAudioFileNames();
				totAudioBtns = 6;
				audioSets = talkbox.getNumberOfAudioSets();
				hasSound = talkbox.getHasAudio();
				objectInputStream.close();
			}
			sound = new Sound();

		} catch (IOException | ClassNotFoundException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void setDisplay(String message) {
		display.setText(message);
	}

	private List<String> getAudioList() {
		File f = new File("TalkBoxData/");
		List<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		List<String> wavList = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			String str = names.get(i);
			if (str.substring(str.indexOf('.'), str.length()).equals(".wav"))
				wavList.add(str);
		}
		return wavList;
	}
}
