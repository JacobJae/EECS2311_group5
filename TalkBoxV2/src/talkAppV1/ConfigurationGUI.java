package talkAppV1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.AbstractListModel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ButtonGroup;
import java.awt.Dimension;

public class ConfigurationGUI extends JFrame {

	private int currentBtnSet = 0, width, height;
	private JLabel display;
	private JButton btnIncBtnSet, btnDecBtnSet, btnIncBtnNum, btnDecBtnNum, btnSwap, btnAdd, btnRemove, btnRecord, btnSave, btnReturn;
	private ButtonGroup btngroup;
	private JToggleButton[][] audioFileButtons;
	private JScrollPane scrollerAudio, scrollerButtons;
	private JList<String> listAreaAudio;
	private JList<Integer> listAreaBtn;
	private JTextField textField;
	private Font font;
	private boolean recording;
	private int selectedBtnIndex = 0;

	private JPanel contentPane;
	private JButton btnBack;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private ArrayList<String> clips = new ArrayList<String>(), selected = new ArrayList(),
			nonSelected = new ArrayList();
	private String name;
	private Sound sound;
	private TalkBox talkbox;
	private String[][] audioFileNames;

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
	public ConfigurationGUI(TalkBoxGui talkboxgui) {
		talkboxgui.setVisible(false);
		setVisible(true);
		setTitle("Configuration");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// create custom close operation
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				talkboxgui.setVisible(true);
				talkboxgui.reset();
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		test Full HD resolution
//		width = 1920 / 2;
//		height = 1080 / 2;
		width = (int) screenSize.getWidth() / 2;
		height = (int) screenSize.getHeight() / 2;
		setBounds(100, 100, width, height);
		font = new Font("Stencil", Font.BOLD, width / 50);
//		contentPane = new JPanel();
//		contentPane.setSize(new Dimension(400, 400));
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);

//		this.clips = clips;
//		addClips();
		getSetting();
		init();
		putButtons(currentBtnSet);
//		addAction();
//		setNames();
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		recording = false;
		int gap = height / 30, btnWidth = (width - 8 * gap)	/ 6;
		
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
		
		btnIncBtnSet = new JButton("Inc Btn Set");
		btnIncBtnSet.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnIncBtnSet.setBounds(gap + 0 * btnWidth + 0 * gap, height / 64 * 17, btnWidth, height / 16);
		btnIncBtnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentSet = talkbox.getNumberOfAudioSets();
				talkbox.setNumberOfAudioSets(currentSet + 1);
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
					talkbox.setNumberOfAudioSets(currentSet + 1);
				}
			}
		});
		contentPane.add(btnDecBtnSet);
		
		btnIncBtnNum = new JButton("Inc Btn Num");
		btnIncBtnNum.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnIncBtnNum.setBounds(gap + 2 * btnWidth + 2 * gap, height / 64 * 17, btnWidth, height / 16);
		contentPane.add(btnIncBtnNum);
		
		btnDecBtnNum = new JButton("Dec Btn Num");
		btnDecBtnNum.setFont(new Font("Serif", Font.PLAIN, width / 50));
		btnDecBtnNum.setBounds(gap + 3 * btnWidth + 3 * gap, height / 64 * 17, btnWidth, height / 16);
		contentPane.add(btnDecBtnNum);
		
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
//					sound.stopSound();
					String names[][] = talkbox.getAudioFileNames();
					String str = listAreaAudio.getSelectedValue();
					int num = listAreaBtn.getSelectedValue();
					names[currentBtnSet][num - 1] = "TalkBoxData/" + str;
					talkbox.setAudioFileNames(names);
					
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
//					sound.stopSound();
					String names[][] = talkbox.getAudioFileNames();
					String str = names[currentBtnSet][selectedBtnIndex - 1];
					names[currentBtnSet][selectedBtnIndex - 1] = null;
					talkbox.setAudioFileNames(names);
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
//				sound.stopSound();
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
				SetSetting();
				setDisplay("New setting has been saved");
			}
		});
		
		btnReturn = new JButton("Return");
		btnReturn.setFont(font);
		btnReturn.setBounds(width / 30 * 23, height / 4 * 3, width / 30 * 5, height / 8);
		contentPane.add(btnReturn);
		
		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
//		contentPane.setLayout(new FormLayout(
//				new ColumnSpec[] {
//						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("217px"), ColumnSpec.decode("128px"),
//						ColumnSpec.decode("37px"), ColumnSpec.decode("88px"), ColumnSpec.decode("237px"), },
//				new RowSpec[] { RowSpec.decode("37px"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
//						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
//						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
//						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
//						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
//						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
//						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("25px"),
//						FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("25px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC,
//						RowSpec.decode("313px"), FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("34px"), }));
//
//		JToggleButton btnButton2 = new JToggleButton("STRONG NO");
//		buttonGroup_1.add(btnButton2);
//		btnButton2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				removeSound(1);
//			}
//		});
//		contentPane.add(btnButton2, "2, 3, fill, fill");
//
//		JButton btnNewButton_1 = new JButton("ADD");
//		contentPane.add(btnNewButton_1, "4, 3, fill, default");
//
//		JToggleButton btnHello = new JToggleButton("HELLO");
//		buttonGroup.add(btnHello);
//		contentPane.add(btnHello, "6, 3, default, fill");
//
//		JToggleButton btnButton3 = new JToggleButton("YES");
//		btnButton3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				removeSound(2);
//			}
//		});
//		buttonGroup_1.add(btnButton3);
//		contentPane.add(btnButton3, "2, 5, fill, fill");
//
//		JButton btnRemove = new JButton("REMOVE");
//		contentPane.add(btnRemove, "4, 5, fill, default");
//
//		JToggleButton btnCherie = new JToggleButton("HELLO CHERIE");
//		buttonGroup.add(btnCherie);
//		contentPane.add(btnCherie, "6, 5, default, fill");
//
//		JToggleButton btnButton4 = new JToggleButton("HELL YEAH");
//		btnButton4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				removeSound(3);
//			}
//		});
//		buttonGroup_1.add(btnButton4);
//		contentPane.add(btnButton4, "2, 7, fill, fill");
//
//		JToggleButton btnHelloThere = new JToggleButton("HELLO THERE");
//		buttonGroup.add(btnHelloThere);
//		contentPane.add(btnHelloThere, "6, 7, default, fill");
//
//		JToggleButton btnBruh = new JToggleButton("BRUH");
//		buttonGroup.add(btnBruh);
//		contentPane.add(btnBruh, "6, 9, default, fill");
//
//		JToggleButton btnTadaah = new JToggleButton("TADAAH");
//		buttonGroup.add(btnTadaah);
//		contentPane.add(btnTadaah, "6, 11");
//		btnBack = new JButton("BACK");
//		btnBack.setFont(new Font("Stencil", Font.BOLD, 20));
//		contentPane.add(btnBack, "2, 27, 5, 1, fill, top");
//
//		JToggleButton btnButton1 = new JToggleButton("NO");
//		btnButton1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				removeSound(0);
//			}
//		});
//		buttonGroup_1.add(btnButton1);
//		contentPane.add(btnButton1, "2, 1, fill, bottom");
//
//		JToggleButton btnNewButton = new JToggleButton("BYE HAVE A BEAUTIFUL TIME");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				addSound(0);
//			}
//		});
//		buttonGroup.add(btnNewButton);
//		contentPane.add(btnNewButton, "6, 1, fill, bottom");
	}

	/*
	 * create button in button set setNumber
	 */
	private void putButtons(int setNumber) {
		for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
			String fileName = audioFileNames[setNumber][i];
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
	private void getSetting() {
		try {
			sound = new Sound();
			btngroup = new ButtonGroup();
			FileInputStream fileInputStream = new FileInputStream("TalkBoxData/configure.tbc");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			talkbox = (TalkBox) objectInputStream.readObject();
			audioFileNames = talkbox.getAudioFileNames();
			audioFileButtons = new JToggleButton[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Set .tbc settings based on configuration
	 */
	private void SetSetting() {
		try {
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

	private void addClips() {
		for (int i = 0; i < clips.size(); i++) {
			if (i < 4)
				selected.add(clips.get(i));
			else
				nonSelected.add(clips.get(i));
		}
	}

	private void addAction() {
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxApp();
			}
		});

	}

	protected void TalkBoxApp() {
		this.setVisible(false);
		new TalkBoxGui().setVisible(true);

	}
}
