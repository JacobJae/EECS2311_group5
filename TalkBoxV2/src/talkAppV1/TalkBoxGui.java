package talkAppV1;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
// delete this
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel Display;
	private JToggleButton btnSound1, btnSound2, btnSound3, btnSound4;
	private JButton btnExit, btnConfigure;
	private JPanel contentPane;
	private File audio;
	private String name, no = "TalkBoxData/no.wav", strong_no = "TalkBoxData/strong_no.wav",
			yes = "TalkBoxData/yes.wav", hell_yeah = "TalkBoxData/hell_yeah.wav", bruh = "TalkBoxData/bruh.mp3",
			bye_have_a_beautiful_time = "TalkBoxData/bye_have_a_beautiful_time.mp3",
			hello_cherie = "TalkBoxData/hello_cherie.mp3", hello_there = "TalkBoxData/hello_there.mp3",
			hello = "TalkBoxData/hello.mp3", just_do_it = "TalkBoxData/just_do_it.mp3",
			tadaah = "TalkBoxData/tadaah.mp3";
	private AudioInputStream audioIn;
	private Clip clip = null;
	private ArrayList<String> clips = new ArrayList<String>();
	private ArrayList<JToggleButton> tButtons = new ArrayList<JToggleButton>();

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
		setBounds(100, 100, 711, 622);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		init();
		addComp();

		btnSound1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBtn(0);
				getName(no);
				playSound(clips.get(0));

			}
		});

		btnSound2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectBtn(1);
				getName(strong_no);
				playSound(clips.get(1));
			}
		});

		btnSound3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBtn(2);
				getName(yes);
				playSound(clips.get(2));
			}
		});

		btnSound4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectBtn(3);
				getName(hell_yeah);
				playSound(clips.get(3));
			}
		});
		
		/*
		btnPlaySound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < tButtons.size(); i++) {
					if (tButtons.get(i).isSelected()) {
						playSound(clips.get(i));
						Display.setText(name + " Pressed!");
					}
				}

			}
		});
		 */
		
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

	protected void ConfigurationApp() {
		this.setVisible(false);
		new ConfigurationGUI(clips).setVisible(true);

	}

	private void selectBtn(int x) {

		for (int i = 0; i < tButtons.size(); i++) {
			if (i == x)
				tButtons.get(i).setSelected(true);
			else
				tButtons.get(i).setSelected(false);
		}

	}

	private void addComp() {
		clips.add(no);
		clips.add(strong_no);
		clips.add(yes);
		clips.add(hell_yeah);
		clips.add(bye_have_a_beautiful_time);
		clips.add(hello);
		clips.add(hello_cherie);
		clips.add(hello_there);
		clips.add(bruh);
		clips.add(tadaah);

		tButtons.add(btnSound1);
		tButtons.add(btnSound2);
		tButtons.add(btnSound3);
		tButtons.add(btnSound4);

	}

	private void init() {

		getClip();

		Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);
		Display.setHorizontalAlignment(SwingConstants.CENTER);
		Display.setFont(new Font("Stencil", Font.BOLD, 48));
		Display.setBounds(10, 320, 670, 157);

		btnSound1 = new JToggleButton("NO");
		btnSound1.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound1);
		btnSound1.setBounds(10, 11, 160, 100);

		btnSound2 = new JToggleButton("Strong NO");
		btnSound2.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound2);
		btnSound2.setBounds(180, 11, 160, 100);

		btnSound3 = new JToggleButton("YES");
		btnSound3.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound3);
		btnSound3.setBounds(350, 11, 160, 100);

		btnSound4 = new JToggleButton("HELL YEAH");
		btnSound4.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound4);
		btnSound4.setBounds(520, 11, 160, 100);

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Stencil", Font.BOLD, 20));
		btnExit.setBounds(591, 514, 89, 58);
		contentPane.add(btnExit);

		btnConfigure = new JButton("Configure");
		btnConfigure.setFont(new Font("Stencil", Font.BOLD, 20));
		btnConfigure.setBounds(10, 514, 582, 58);
		contentPane.add(btnConfigure);
	}

	private void getClip() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	private void playSound(String audioFile) {

		clip.stop();
		clip.close();

		getName(audioFile);
		audio = new File(audioFile);
		try {
			audioIn = AudioSystem.getAudioInputStream(audio.toURI().toURL());
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException ee) {
			ee.printStackTrace();
		} catch (IOException ee) {
			ee.printStackTrace();
		} catch (LineUnavailableException ee) {
			ee.printStackTrace();
		}
	}

	private void setVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = 0.25;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	private void getName(String audioFile) {
		name = audioFile.substring(audioFile.indexOf("/") + 1, audioFile.indexOf("."));
	}
}
