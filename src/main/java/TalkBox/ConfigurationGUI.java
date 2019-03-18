package main.java.TalkBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ButtonGroup;
import java.awt.Dimension;

public class ConfigurationGUI extends JFrame {
	private JScrollPane scrollerAudio, scrollerButtons;
	private JList<String> listAreaAudio;
	private JList<Integer> listAreaBtn;
	private JTextField textField;
	private JLabel display;
	private JButton btnIncBtnSet, btnDecBtnSet, btnIncBtnNum, btnDecBtnNum, btnSwap, btnAdd, btnRemove, btnRecord,
			btnSave, btnReturn;
	private JToggleButton[][] audioFileButtons;
	private JPanel contentPane;
	private Font font;
	private ButtonGroup btngroup;
	private Sound sound;
	private TalkBox talkbox;
	private int currentBtnSet = 0, width, height;
	private int selectedBtnIndex = 0;
	private boolean recording;
	private TalkBoxGui talkboxgui;

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
	public ConfigurationGUI(TalkBoxGui t, TalkBox talkB) {
		talkboxgui = t;
		talkboxgui.setVisible(false);
		setVisible(true);
		setTitle("Configuration");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// create custom close operation
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				talkboxgui.setVisible(true);
				talkboxgui.reset(talkbox);
			}

			@Override
			public void windowStateChanged(WindowEvent e) {
				super.windowStateChanged(e);
				
				width = (int) e.getWindow().getSize().getWidth();
				height = (int) e.getWindow().getSize().getHeight();
			}
			
			
		});
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth() / 2;
		height = (int) screenSize.getHeight() / 2;
		setSize(width, height);
		setLocationRelativeTo(null);
		font = new Font("Stencil", Font.BOLD, width / 50);
		getSetting(talkB);
		init();
		putButtons(currentBtnSet);
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		recording = false;
		int gap = height / 30, btnWidth = (width - 8 * gap) / 6;

		List<String> audioList = getAudioList();
		listAreaAudio = new JList<String>(audioList.toArray(new String[audioList.size()]));
		listAreaAudio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAreaAudio.setFont(new Font("Serif", Font.PLAIN, 14));
		listAreaAudio.setLayoutOrientation(JList.VERTICAL);

		scrollerAudio = new JScrollPane(listAreaAudio);
		scrollerAudio.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerAudio.setBounds(gap + 0 * btnWidth + 0 * gap, height / 3, btnWidth, height / 3);
		contentPane.add(scrollerAudio);

		List<Integer> btnList = new ArrayList<>();
		for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
			btnList.add(i + 1);
		}
		listAreaBtn = new JList<Integer>(btnList.toArray(new Integer[btnList.size()]));
		listAreaBtn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAreaBtn.setFont(new Font("Serif", Font.PLAIN, 14));
		listAreaBtn.setLayoutOrientation(JList.VERTICAL);

		scrollerButtons = new JScrollPane(listAreaBtn);
		scrollerButtons.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerButtons.setBounds(gap + 1 * btnWidth + 1 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(scrollerButtons);
//-------------------------------------EDIT-----------------------------------------------------------------------------
		btnIncBtnSet = new JButton("Inc Btn Set");
		btnIncBtnSet.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnIncBtnSet.setBounds(gap + 0 * btnWidth + 0 * gap, height / 64 * 17, btnWidth, height / 16);
		btnIncBtnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentSet = talkbox.getNumberOfAudioSets();
				talkbox.incAudioSets();
				audioFileButtons = new JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
				init();
				putButtons(currentBtnSet);
				setDisplay("Number of Button set has been increased from " + currentSet + " to " + (currentSet + 1));
			}
		});
		contentPane.add(btnIncBtnSet);

		btnDecBtnSet = new JButton("Dec Btn Set");
		btnDecBtnSet.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnDecBtnSet.setBounds(gap + 1 * btnWidth + 1 * gap, height / 64 * 17, btnWidth, height / 16);
		btnDecBtnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentSet = talkbox.getNumberOfAudioSets();
				if (currentSet < 2) {
					setDisplay("Number of Audio sets can not be lower than 1");
				} else {
					talkbox.decAudioSets();
					audioFileButtons = new JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
					if (currentSet - 1 == currentBtnSet) {
						currentBtnSet = 0;
						putButtons(0);
						selectedBtnIndex = 0;
					} else {
						putButtons(currentBtnSet);
						selectedBtnIndex = 0;
					}
					init();
					putButtons(currentBtnSet);
					setDisplay(
							"Number of Button set has been decreased from " + currentSet + " to " + (currentSet - 1));
					contentPane.repaint();
				}
			}
		});
		contentPane.add(btnDecBtnSet);

		btnIncBtnNum = new JButton("Inc Btn Num");
		btnIncBtnNum.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnIncBtnNum.setBounds(gap + 2 * btnWidth + 2 * gap, height / 64 * 17, btnWidth, height / 16);
		btnIncBtnNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentBtn = talkbox.getNumberOfAudioButtons();
				for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
					JToggleButton btn = audioFileButtons[currentBtnSet][i];
					if (btn != null)
						getContentPane().remove(btn);
				}
				talkbox.incAudioButtons();
				audioFileButtons = new JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
				init();
				putButtons(currentBtnSet);
				if (currentBtn < 8) {
					setDisplay("Number of Audio Buttons has been increased from " + currentBtn + " to " + (currentBtn + 1));
				} else {
					setDisplay("Number of Audio Buttons can not exceed 8");
				}
				contentPane.repaint();
				selectedBtnIndex = 0;

			}
		});
		contentPane.add(btnIncBtnNum);

		btnDecBtnNum = new JButton("Dec Btn Num");
		btnDecBtnNum.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnDecBtnNum.setBounds(gap + 3 * btnWidth + 3 * gap, height / 64 * 17, btnWidth, height / 16);
		btnDecBtnNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentBtn = talkbox.getNumberOfAudioButtons();
				if (currentBtn < 2) {
					setDisplay("Number of Audio buttons can not be lower than 1");
				} else {
					for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
						JToggleButton btn = audioFileButtons[currentBtnSet][i];
						if (btn != null)
							getContentPane().remove(btn);
					}
					talkbox.decAudioButtons();
					audioFileButtons = new JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
					init();
					putButtons(currentBtnSet);
					setDisplay(
							"Number of Audio Buttons has been decreased from " + currentBtn + " to " + (currentBtn - 1));
					contentPane.repaint();
					selectedBtnIndex = 0;
				}
			}
		});
		contentPane.add(btnDecBtnNum);
//-----------------------------------------------------------------------------------------------------------------------
		btnSwap = new JButton("Swap");
		btnSwap.setFont(font);
		btnSwap.setBounds(gap + 2 * btnWidth + 2 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnSwap);
		btnSwap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
					JToggleButton btn = audioFileButtons[currentBtnSet][i];
					if (btn != null)
						getContentPane().remove(btn);
				}
				contentPane.repaint();
				currentBtnSet = (currentBtnSet + 1) % talkbox.getNumberOfAudioSets();
				putButtons(currentBtnSet);
				setDisplay(String.format("Change to button set %d", currentBtnSet + 1));
				selectedBtnIndex = 0;
			}
		});

		btnAdd = new JButton("Add");
		btnAdd.setFont(font);
		btnAdd.setBounds(gap + 3 * btnWidth + 3 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listAreaAudio.getSelectedValue() == null) {
					setDisplay("Select Audio from list");
				} else if (listAreaBtn.getSelectedValue() == null) {
					setDisplay("Select button position from list");
				} else {
					String str = listAreaAudio.getSelectedValue();
					int num = listAreaBtn.getSelectedValue();
					talkbox.addAudio(currentBtnSet, num, str);

					init();
					putButtons(currentBtnSet);
					setDisplay("Audio File \"" + str + "\" has been added to " + num);
					contentPane.repaint();
				}
			}
		});

		btnRemove = new JButton("Remove");
		btnRemove.setFont(font);
		btnRemove.setBounds(gap + 4 * btnWidth + 4 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnRemove);

		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedBtnIndex == 0) {
					setDisplay("Choose Button First");
				} else {
					String str = talkbox.getAudioFileNames()[currentBtnSet][selectedBtnIndex - 1];
					talkbox.removeAudio(currentBtnSet, selectedBtnIndex);
					
					init();
					putButtons(currentBtnSet);
					setDisplay("Audio File \"" + str + "\" was removed from set" + (currentBtnSet + 1));
					contentPane.repaint();
				}
			}
		});

		btnRecord = new JButton("Record");
		btnRecord.setFont(font);
		btnRecord.setBounds(gap + 5 * btnWidth + 5 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnRecord);

		btnRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (recording) {
					sound.stopRecording();
					init();
					putButtons(currentBtnSet);
					setDisplay("Record completed!");
					contentPane.repaint();
				} else {
					String name = textField.getText();
					if (name.length() < 1) {
						setDisplay("Enter file name");
					} else if (getAudioList().contains(name + ".wav")) {
						setDisplay("Audio file name " + name + ".wav already exist, try another name");
					} else {
						recording = true;
						sound.startRecording(textField.getText());
						setDisplay("Recording......");
					}
				}
			}
		});

		textField = new JTextField();
		textField.setFont(new Font("Serif", Font.PLAIN, 14));
		textField.setBounds(gap + 1 * btnWidth + 1 * gap, height / 16 * 10, btnWidth, height / 18);
		contentPane.add(textField);

		JLabel nameDisplay = new JLabel("Audio file name");
		nameDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		nameDisplay.setFont(new Font("Serif", Font.PLAIN, 14));
		nameDisplay.setBounds(gap + 1 * btnWidth + 1 * gap, height / 16 * 9, btnWidth, height / 18);
		contentPane.add(nameDisplay);

		display = new JLabel("Start Configuration! (Current button set: 1)");
		display.setHorizontalAlignment(SwingConstants.CENTER);
		display.setFont(new Font("Serif", Font.PLAIN, width / 50));
		display.setBounds(gap + 2 * btnWidth + 2 * gap, height / 5 * 3, btnWidth * 4 + gap * 3, height / 10);
		contentPane.add(display);

		btnSave = new JButton("Save Setting");
		btnSave.setFont(font);
		btnSave.setBounds(width / 30, height / 4 * 3, width / 30 * 21, height / 8);
		contentPane.add(btnSave);

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDisplay("New setting has been saved");
				talkbox.finalize();
				SetSetting();
			}
		});

		btnReturn = new JButton("Return");
		btnReturn.setFont(font);
		btnReturn.setBounds(width / 30 * 23, height / 4 * 3, width / 30 * 5, height / 8);
		contentPane.add(btnReturn);

		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				talkboxgui.setVisible(true);
				talkboxgui.reset(talkbox);
				dispose();
			}
		});
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

	/*
	 * Get .tbc settings and initiate sound, buttongroup, talkbox object
	 */
	private void getSetting(TalkBox talkB) {
		try {
			talkbox = talkB;
			sound = new Sound();
			btngroup = new ButtonGroup();
			audioFileButtons = new JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Set .tbc settings based on configuration
	 */
	private void SetSetting() {
		try {
//			FileOutputStream fileOutputStream = new FileOutputStream("../TalkBoxData/configure.tbc");
			FileOutputStream fileOutputStream = new FileOutputStream("TalkBoxData/configure.tbc");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(talkbox);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDisplay(String message) {
		display.setText(message);
	}

	private List<String> getAudioList() {
//		File f = new File("../TalkBoxData/");
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
