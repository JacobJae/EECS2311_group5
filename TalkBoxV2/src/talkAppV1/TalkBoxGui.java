package talkAppV1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private int currentBtnSet = 0, width = 711, height = 622;

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
	 * Create the frame.
	 */
	public TalkBoxGui() {
		setAlwaysOnTop(true);
		setTitle("Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		getSetting();
		init();
		putButtons(currentBtnSet);
//		btnConfigure.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				ConfigurationApp();
//			}
//		});
	}

	private void getSetting() {
		try {
			btngroup = new ButtonGroup();
			clip = AudioSystem.getClip();
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
		btnSwap = new JButton("Swap");
		btnSwap.setFont(new Font("Stencil", Font.BOLD, 20));
		btnSwap.setBounds(300, 250, 100, 58);
		contentPane.add(btnSwap);

		Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);
		Display.setHorizontalAlignment(SwingConstants.CENTER);
		Display.setFont(new Font("Stencil", Font.BOLD, 48));
		Display.setBounds(10, 320, 670, 157);

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Stencil", Font.BOLD, 20));
		btnExit.setBounds(591, 514, 89, 58);
		contentPane.add(btnExit);

		btnConfigure = new JButton("Configure");
		btnConfigure.setFont(new Font("Stencil", Font.BOLD, 20));
		btnConfigure.setBounds(10, 514, 582, 58);
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
				int btnWidth = (width - talkbox.getNumberOfAudioButtons() * 10 - 10)
						/ talkbox.getNumberOfAudioButtons();
				JToggleButton btn = new JToggleButton(getName(fileName));
				btn.setFont(new Font("Stencil", Font.BOLD, 18));
				btn.setBounds(10 + i * btnWidth + i * 10, 11, btnWidth, 100);
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						playSound(fileName);
					}
				});
				btngroup.add(btn);
				audioFileButtons[setNumber][i] = btn;
				contentPane.add(btn);
			}
		}
	}

	private void playSound(String audioFile) {
		clip.stop();
		clip.close();

		audio = new File(audioFile);
		try {
			audioIn = AudioSystem.getAudioInputStream(audio.toURI().toURL());
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
			ee.printStackTrace();
		}
	}

	private String getName(String audioFile) {
		return audioFile.substring(audioFile.indexOf("/") + 1, audioFile.indexOf("."));
	}

	protected void ConfigurationApp() {
		this.setVisible(false);
		new ConfigurationGUI(clips).setVisible(true);
	}

	private void setVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = 0.25;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	private void createConfigure() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Test");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel panel = new JPanel();
				panel.setOpaque(true);
				JTextArea textArea = new JTextArea(15, 50);
				textArea.setWrapStyleWord(true);
				textArea.setEditable(false);
				textArea.setFont(Font.getFont(Font.SANS_SERIF));
				JScrollPane scroller = new JScrollPane(textArea);
				scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				JPanel inputpanel = new JPanel();
				inputpanel.setLayout(new FlowLayout());
				JTextField input = new JTextField(20);
				JButton button = new JButton("Enter");
				DefaultCaret caret = (DefaultCaret) textArea.getCaret();
				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				panel.add(scroller);
				inputpanel.add(input);
				inputpanel.add(button);
				panel.add(inputpanel);
				frame.getContentPane().add(BorderLayout.CENTER, panel);
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
				frame.setResizable(false);
				input.requestFocus();
			}
		});
	}
}
