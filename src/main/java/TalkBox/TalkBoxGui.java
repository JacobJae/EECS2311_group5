package main.java.TalkBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import javax.swing.JComboBox;

public class TalkBoxGui extends JFrame  {
	/**
	 * 
	 */
	private TalkBox talkbox;
	private String[][] audioFileNames;
	private Sound sound;
	private int currentBtnSet = 0, width, height, audioSets, totAudioBtns;
	private JPanel contentPane;
	private Font font;
	private Path path;
	private JButton Button0, Button1, Button2, Button3, Button4, Button5;
	private JTextPane TextPane0, TextPane1, TextPane2, TextPane3, TextPane4, TextPane5;
	private JButton btnPrevSet;
	private JButton btnStop;
	private JButton btnConfig;
	private JButton btnExit;
	private ImageIcon[][] imageButtons;
	private JButton[] currentBtn = new JButton[6];
	private JTextPane[] currentText = new JTextPane[6];
	private boolean[][] hasSound;
	private JPanel btnPanel;
	private JPanel configExitPanel;
	private JPanel mainPanel;
	private JPanel loadPanel;
	private JLabel lblLoad;
	private JComboBox<String> tbcLoader;
	private List<String> tbcFiles = new ArrayList<String>();
	private String[] setNames;
	private JLabel lblTitle;
	private String currentSettings = "default";
	private File[] sFile;
	private List<File> allFiles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkBoxGui frame = new TalkBoxGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create default Simulator the frame.
	 */
	public TalkBoxGui() {
		setVisible(true);
		setSize(new Dimension(1030, 768));
		setAlwaysOnTop(true);
		setBackground(Color.WHITE);
		setTitle("TalkBox Simulator V2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		init();
		setVariables();
		getSetting();
		setActions();
	}

	/*
	 * Load default layout with all the files selected
	 */
	private void loadDefaults() {
		// ----- Load every file in the system -----//
		/*String path = "TalkBoxData/";
		allFiles = Arrays.asList(new File(path).listFiles());
		sFile = new File[allFiles.size() * 2];

		int j = 0;
		for (int i = 0; i < allFiles.size(); i++) {
			String file = allFiles.get(i).toString();
			if (isWav(file)) {
				sFile[j] = allFiles.get(i);
				j++;
			}
		}
		// ----- Activate Sound Variable -----//
		try {
			sound = new Sound();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		// ----- Set number of audio buttons and audio sets -----//
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
					audioFileNames[i][k] = "Press to Confiugure!.";
					hasSound[i][k] = false;
				} else {
					audioFileNames[i][k] = sFile[j].toString();
					hasSound[i][k] = true;
				}
				j++;
			}
		}
		
		setSettingsList();

		setButtons(currentBtnSet);*/
		
		

	}

	private void setVariables(){

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

		tbcLoader.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				currentSettings = tbcLoader.getSelectedItem().toString().toLowerCase();
				getSetting();
				setButtons(0);
			}

		});

	}

	private void setButtons(int currentSet) {

		for (int i = 0; i < 6; i++) {
			currentBtn[i].setText(getName(audioFileNames[currentBtnSet][i]));
			currentText[i].setText(getName(audioFileNames[currentBtnSet][i]));
		}
		lblTitle.setText(setNames[currentBtnSet]);

	}

	private void setSettingsList() {
		for (int i = 0; i < allFiles.size(); i++) {
			String file = allFiles.get(i).toString();
			if (isTbc(file))
				tbcFiles.add(file);
		}
		
		DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<>();
		for (int i = 0; i < tbcFiles.size(); i++) {
			aModel.addElement(getName(tbcFiles.get(i)));
		}
		tbcLoader.setModel(aModel);
		tbcLoader.setSelectedItem(currentSettings);

	}

	public void setCurrentSettings(String name) {
		this.currentSettings = name;
	}

	private void changeSet(int t) {

		currentBtnSet += t;

		if (currentBtnSet < 0)
			currentBtnSet = audioSets - 1;
		else if (currentBtnSet >= audioSets)
			currentBtnSet = 0;

		setButtons(currentBtnSet);

	}

	private void makeSound(JButton button) {
		String text = button.getText();
		System.out.println(text);
		int currentSelected = -1;
		boolean found = false;
		for (int i = 0; i < 6; i++) {
			if (text.equals(currentBtn[i].getText())) {
				currentSelected = i;
				if (hasSound[currentBtnSet][currentSelected]) {
					sound.playSound(audioFileNames[currentBtnSet][currentSelected]);
					if (sound.isPlaying())
						btnStop.setEnabled(true);
					found = true;
				}
			}
		}

		if (!found) {
			sound.stopSound();
			createConfigure();
		}
	}

	public void reset(String name) {
		setCurrentSettings(name);
		setSettingsList();
		getSetting();
		init();
	}

	private String getName(String string) {
		String name = "";
		name = string.substring(string.indexOf("\\") + 1, string.indexOf("."));
		return name;
	}

	private void print(Object[] obj) {
		for (Object o : obj) {
			System.out.println(o.toString());
		}
	}

	private boolean isWav(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".wav"))
			return true;

		return false;
	}

	private boolean isTbc(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".tbc"))
			return true;

		return false;
	}

	private String getExt(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		return ext;
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnStop = new JButton("STOP");
		btnStop.setEnabled(false);
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 32));

		lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));

		configExitPanel = new JPanel();
		configExitPanel.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) configExitPanel.getLayout();

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);

		loadPanel = new JPanel();
		loadPanel.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(64)
								.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
								.addGap(351)
								.addComponent(loadPanel, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addContainerGap().addComponent(configExitPanel,
										GroupLayout.DEFAULT_SIZE, 996, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnStop, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 978,
												Short.MAX_VALUE)
										.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGap(10)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
								.addComponent(loadPanel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
						.addGap(143).addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addGap(171).addComponent(configExitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		lblLoad = new JLabel("Load:");
		lblLoad.setFont(new Font("Tahoma", Font.BOLD, 20));
		loadPanel.add(lblLoad);

		tbcLoader = new JComboBox();
		loadPanel.add(tbcLoader);

		btnPrevSet = new JButton("<");
		btnPrevSet.setFont(new Font("Tahoma", Font.BOLD, 24));

		btnPanel = new JPanel();
		btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setLayout(new GridLayout(2, 6, 8, 8));

		Button0 = new JButton("Button 1");
		Button0.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPanel.add(Button0);
		Button0.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize() + 4));
		Button0.setBackground(new Color(0, 102, 204));

		Button1 = new JButton("Button 2");
		Button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPanel.add(Button1);
		Button1.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button1.setBackground(new Color(0, 102, 204));

		Button2 = new JButton("Button 3");
		btnPanel.add(Button2);
		Button2.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button2.setBackground(new Color(0, 102, 204));

		Button3 = new JButton("Button 4");
		btnPanel.add(Button3);
		Button3.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button3.setBackground(new Color(0, 102, 204));

		Button4 = new JButton("Button 5");
		btnPanel.add(Button4);
		Button4.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button4.setBackground(new Color(0, 102, 204));

		Button5 = new JButton("Button 6");
		btnPanel.add(Button5);
		Button5.setFont(new Font("Calibri", Button0.getFont().getStyle(), Button0.getFont().getSize()));
		Button5.setBackground(new Color(0, 102, 204));
		btnPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { Button0, Button1, Button2, Button4,
				Button3, Button5, TextPane0, TextPane1, TextPane2, TextPane3, TextPane4, TextPane5 }));

		TextPane0 = new JTextPane();
		TextPane0.setFont(new Font("Calibri", TextPane0.getFont().getStyle(), TextPane0.getFont().getSize() + 2));
		TextPane0.setDisabledTextColor(Color.BLACK);
		TextPane0.setEnabled(false);
		TextPane0.setEditable(false);
		btnPanel.add(TextPane0);
		TextPane0.setBackground(Color.PINK);

		TextPane1 = new JTextPane();
		TextPane1.setFont(new Font("Calibri", TextPane0.getFont().getStyle(), TextPane0.getFont().getSize() + 2));
		TextPane1.setDisabledTextColor(Color.BLACK);
		TextPane1.setEnabled(false);
		TextPane1.setEditable(false);
		btnPanel.add(TextPane1);
		TextPane1.setBackground(Color.PINK);

		TextPane2 = new JTextPane();
		TextPane2.setFont(new Font("Calibri", TextPane0.getFont().getStyle(), TextPane0.getFont().getSize() + 2));
		TextPane2.setDisabledTextColor(Color.BLACK);
		TextPane2.setEnabled(false);
		TextPane2.setEditable(false);
		btnPanel.add(TextPane2);
		TextPane2.setBackground(Color.PINK);

		TextPane3 = new JTextPane();
		TextPane3.setFont(new Font("Calibri", TextPane0.getFont().getStyle(), TextPane0.getFont().getSize() + 2));
		TextPane3.setDisabledTextColor(Color.BLACK);
		TextPane3.setEnabled(false);
		TextPane3.setEditable(false);
		btnPanel.add(TextPane3);
		TextPane3.setBackground(Color.PINK);

		TextPane4 = new JTextPane();
		TextPane4.setFont(new Font("Calibri", TextPane0.getFont().getStyle(), TextPane0.getFont().getSize() + 2));
		TextPane4.setDisabledTextColor(Color.BLACK);
		TextPane4.setEnabled(false);
		TextPane4.setEditable(false);
		btnPanel.add(TextPane4);
		TextPane4.setBackground(Color.PINK);

		TextPane5 = new JTextPane();
		TextPane5.setFont(new Font("Calibri", TextPane0.getFont().getStyle(), TextPane0.getFont().getSize() + 2));
		TextPane5.setDisabledTextColor(Color.BLACK);
		TextPane5.setEnabled(false);
		TextPane5.setEditable(false);
		btnPanel.add(TextPane5);
		TextPane5.setBackground(Color.PINK);

		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeSet(1);
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 24));
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addContainerGap().addComponent(btnPrevSet)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 848, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button).addContainerGap()));
		gl_mainPanel.setVerticalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
				.addComponent(button, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
				.addComponent(btnPrevSet, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE));
		mainPanel.setLayout(gl_mainPanel);
		mainPanel.setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { btnPrevSet, btnPanel, Button0, Button1, Button2, Button3,
						Button4, Button5, TextPane0, TextPane1, TextPane2, TextPane3, TextPane4, TextPane5, button }));
		btnPrevSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeSet(-1);
			}
		});

		btnConfig = new JButton("Configure");
		configExitPanel.add(btnConfig);
		btnConfig.setFont(new Font("Tahoma", Font.BOLD, 24));

		btnExit = new JButton("Exit");
		configExitPanel.add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 24));
		configExitPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { btnConfig, btnExit }));
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

		int gap = height / 30, btnWidth = (width - 8 * gap) / 6;

	}

	/*
	 * Set .tbc settings based on configuration
	 */
	private void setSetting() {
		try {
			talkbox.setAudioFileNames(audioFileNames);
			talkbox.setHasAudio(hasSound);
			talkbox.setNumberOfAudioSets(audioSets);
			talkbox.setNumberOfAudioButtons(6);
			talkbox.setsFile(sFile);
			talkbox.setSetNames(setNames);
			talkbox.setSettingsList(tbcFiles);

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
				setNames = talkbox.getSetNames();
				tbcFiles = talkbox.getSettingsList();

				objectInputStream.close();
			} else {
				FileInputStream fileInputStream = new FileInputStream(new File(path));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				talkbox = (TalkBox) objectInputStream.readObject();

				audioFileNames = talkbox.getAudioFileNames();
				totAudioBtns = talkbox.getNumberOfAudioButtons();
				audioSets = talkbox.getNumberOfAudioSets();
				hasSound = talkbox.getHasAudio();
				sFile = talkbox.getsFile();
				setNames = talkbox.getSetNames();
				tbcFiles = talkbox.getSettingsList();

				objectInputStream.close();
			}
			sound = new Sound();
			setSettingsList();
			setButtons(currentBtnSet);

		} catch (IOException | ClassNotFoundException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void createConfigure() {
		setSetting();
		ConfigurationGUI gui = new ConfigurationGUI(this, currentSettings);
	}
}
