package talkAppV1;

import java.awt.EventQueue;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TalkBoxGui extends JFrame {
	/**
	 * 
	 */
	private TalkBox talkbox;
	private String[][] audioFileNames;
	private int width = 711, height = 622;
	
	
	
	private JLabel Display;
	private JButton btnExit, btnConfigure;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		getSetting();
		audioFileNames = talkbox.getAudioFileNames();
		init();
		putButtons(0);
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnConfigure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfigurationApp();
			}
		});
	}

	/*
	 * create button in set i
	*/
	private void putButtons(int setNumber) {
		for (int i = 0; i < talkbox.getNumberOfAudioButtons(); i++) {
			String fileName = audioFileNames[setNumber][i];
			if (fileName != null) {
				int btnWidth = (width - talkbox.getNumberOfAudioButtons() * 10 - 10) / talkbox.getNumberOfAudioButtons();
				JToggleButton btn = new JToggleButton(getName(fileName));
				btn.setFont(new Font("Stencil", Font.BOLD, 18));
				contentPane.add(btn);
				btn.setBounds(10 + i * btnWidth + i * 10, 11, btnWidth, 100);
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						playSound(fileName);
					}
				});
			}
		}
	}

	private void getSetting() {
		try {
			clip = AudioSystem.getClip();
		    FileInputStream fileInputStream = new FileInputStream("TalkBoxData/configure.tbc");
		    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		    talkbox = (TalkBox) objectInputStream.readObject();
		    objectInputStream.close();
		} catch (IOException | ClassNotFoundException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	protected void ConfigurationApp() {
		this.setVisible(false);
		new ConfigurationGUI(clips).setVisible(true);
	}

	private void init() {
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

	private void setVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = 0.25;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	private String getName(String audioFile) {
		return audioFile.substring(audioFile.indexOf("/") + 1, audioFile.indexOf("."));
	}
}
