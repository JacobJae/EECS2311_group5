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

	private JLabel Display;
	private JButton btnExit, btnConfigure, btnSwap;
	private JPanel contentPane;
	private File audio;
	private AudioInputStream audioIn;
	private Clip clip = null;
	private ArrayList<String> clips = new ArrayList<String>();

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
//		test Full HD resolution
//		width = 1920 / 2;
//		height = 1080 / 2;
		width = (int) screenSize.getWidth() / 2;
		height = (int) screenSize.getHeight() / 2;
		setAlwaysOnTop(true);
		setTitle("Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);

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

		btnSwap = new JButton("Swap");
		btnSwap.setFont(new Font("Stencil", Font.BOLD, width / 50));
		btnSwap.setBounds(width / 2 - width / 16, height / 2 - height / 20, width / 8, height / 10);
		contentPane.add(btnSwap);

		Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);
		Display.setHorizontalAlignment(SwingConstants.CENTER);
		Display.setFont(new Font("Stencil", Font.BOLD, width / 50));
		Display.setBounds(width / 2 - width / 8, height / 2 + height / 20, width / 4, height / 10);

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Stencil", Font.BOLD, width / 50));
		btnExit.setBounds(width / 30 * 23, height - height / 3, width / 30 * 5, height / 10);
		contentPane.add(btnExit);

		btnConfigure = new JButton("Configure");
		btnConfigure.setFont(new Font("Stencil", Font.BOLD, width / 50));
		btnConfigure.setBounds(width / 30, height - height / 3, width / 30 * 21, height / 10);
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
				createConfigure();
			}
		});

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
				int gap = width / 30, btnWidth = (width - (talkbox.getNumberOfAudioButtons() + 2) * gap)
						/ talkbox.getNumberOfAudioButtons();
				JToggleButton btn = new JToggleButton(
						fileName.substring(fileName.indexOf("/") + 1, fileName.indexOf(".")));
				btn.setFont(new Font("Stencil", Font.BOLD, width / 50));
				btn.setBounds(gap + i * btnWidth + i * gap, height / 30, btnWidth, height / 10);
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
