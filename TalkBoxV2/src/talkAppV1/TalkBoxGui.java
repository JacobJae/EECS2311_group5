package talkAppV1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TalkBoxGui extends JFrame {
	/**
	 * 
	 */
	private TalkBox talkbox;
	private String[][] audioFileNames;
	private JToggleButton[][] audioFileButtons;
	private ButtonGroup btngroup;
	private Sound sound;
	private int currentBtnSet = 0, width, height;

	private JLabel display;
	private JButton btnExit, btnConfigure, btnSwap, btnStop, btnVolUp, btnVolDown;
	private JPanel contentPane;
	Font font;

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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth() / 2;
		height = (int) screenSize.getHeight() / 2;
		setResizable(true);
		setAlwaysOnTop(true);
		setTitle("Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( width, height);
		setLocationRelativeTo(null);
		font = new Font("Stencil", Font.BOLD, width / 50);

		getSetting();
		init();
		putButtons(currentBtnSet);
	}

	public void reset() {
		getSetting();
		init();
		putButtons(currentBtnSet);
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

	private void init() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		int gap = height / 30, btnWidth = (width - 8 * gap)	/ 6;
		btnSwap = new JButton("Swap");
		btnSwap.setFont(font);
		btnSwap.setBounds(gap + 1 * btnWidth + 1 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnSwap);
		
		btnStop = new JButton("Stop");
		btnStop.setFont(font);
		btnStop.setBounds(gap + 2 * btnWidth + 2 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnStop);
		
		btnVolUp = new JButton("Vol Up");
		btnVolUp.setFont(font);
		btnVolUp.setBounds(gap + 3 * btnWidth + 3 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnVolUp);
		
		btnVolDown = new JButton("Vol Down");
		btnVolDown.setFont(font);
		btnVolDown.setBounds(gap + 4 * btnWidth + 4 * gap, height / 3, btnWidth, height / 6);
		contentPane.add(btnVolDown);

		display = new JLabel("BUTTON PRESSED!");
		contentPane.add(display);
		display.setHorizontalAlignment(SwingConstants.CENTER);
		display.setFont(font);
		display.setBounds(width / 2 - width / 8, height / 5 * 3, width / 4, height / 10);

		btnExit = new JButton("Exit");
		btnExit.setFont(font);
		btnExit.setBounds(width / 30 * 23, height / 4 * 3, width / 30 * 5, height / 8);
		contentPane.add(btnExit);

		btnConfigure = new JButton("Configure");
		btnConfigure.setFont(font);
		btnConfigure.setBounds(width / 30, height / 4 * 3, width / 30 * 21, height / 8);
		contentPane.add(btnConfigure);

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnConfigure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sound.stopSound();
				createConfigure();
			}
		});

		btnSwap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sound.stopSound();
				for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
					JToggleButton btn = audioFileButtons[currentBtnSet][i];
					if (btn != null)
						getContentPane().remove(btn);
				}
				contentPane.repaint();
				currentBtnSet = (currentBtnSet + 1) % talkbox.getNumberOfAudioSets();
				putButtons(currentBtnSet);
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sound.stopSound();
			}
		});
	}

	/*
	 * create button in button set setNumber
	 */
	private void putButtons(int setNumber) {
		for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
			String fileName = audioFileNames[setNumber][i];
			if (fileName != null) {
				int gap = height / 30, btnWidth = (width - (talkbox.getNumberOfAudioButtons() + 2) * gap)
						/ talkbox.getNumberOfAudioButtons();
				JToggleButton btn = new JToggleButton(
						fileName.substring(fileName.indexOf("/") + 1, fileName.indexOf(".")));
				btn.setFont(font);
				btn.setBounds(gap + i * btnWidth + i * gap, height / 30, btnWidth, height / 5);
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sound.playSound(fileName);
					}
				});
				btngroup.add(btn);
				audioFileButtons[setNumber][i] = btn;
				contentPane.add(btn);
			}
		}
	}

	private void createConfigure() {
		ConfigurationGUI gui = new ConfigurationGUI(this);
//		setTitle("Simulator");
//		setVisible(false);
//		setVisible(true);
//		contentPane.removeAll();
//		contentPane.repaint();

		// JFrame parentFrame = this;
//		parentFrame.setVisible(false);
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				JFrame frame = new JFrame("Test");
//				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				JPanel panel = new JPanel();
//				panel.setOpaque(true);
//				JTextArea textArea = new JTextArea(15, 50);
//				textArea.setWrapStyleWord(true);
//				textArea.setEditable(false);
//				textArea.setFont(Font.getFont(Font.SANS_SERIF));
//				JScrollPane scroller = new JScrollPane(textArea);
//				scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//				scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//				JPanel inputpanel = new JPanel();
//				inputpanel.setLayout(new FlowLayout());
//				JTextField input = new JTextField(20);
//				JButton button = new JButton("Enter");
//				DefaultCaret caret = (DefaultCaret) textArea.getCaret();
//				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//				panel.add(scroller);
//				inputpanel.add(input);
//				inputpanel.add(button);
//				panel.add(inputpanel);
//				frame.getContentPane().add(BorderLayout.CENTER, panel);
//				frame.pack();
//				frame.setLocationByPlatform(true);
//				frame.setVisible(true);
//				frame.setResizable(false);
//				input.requestFocus();
//			}
//		});
	}
}
