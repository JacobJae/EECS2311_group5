package talkAppV1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTree;

public class TalkBoxGui extends JFrame {

	private JLabel disp;
	private JButton b1, b2, b3, b4;
	private JPanel contentPane;
	private File audio;
	private String name, no = "TalkBoxData/no.wav", strong_no = "TalkBoxData/strong_no.wav",
			yes = "TalkBoxData/yes.wav", hell_yeah = "TalkBoxData/hell_yeah.wav";
	private AudioInputStream audioIn;
	private Clip clip = null;

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
		setBounds(100, 100, 711, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// this.setLocationRelativeTo(null);
		// this.setExtendedState(MAXIMIZED_BOTH);

		getClip();

		JLabel Display = new JLabel("BUTTON PRESSED!");
		Display.setHorizontalAlignment(SwingConstants.CENTER);
		Display.setFont(new Font("Stencil", Font.BOLD, 48));
		Display.setBounds(10, 301, 670, 200);
		contentPane.add(Display);

		JButton btnImage1 = new JButton("NO");
		btnImage1.setBounds(10, 175, 160, 100);
		btnImage1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound(no);
				Display.setText(name + " Pressed!");
			}
		});
		contentPane.add(btnImage1);

		JButton btnImage2 = new JButton("Strong NO");
		btnImage2.setBounds(180, 175, 160, 100);
		btnImage2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playSound(strong_no);
				Display.setText(name + " Pressed!");
			}
		});
		btnImage2.setActionCommand("IMAGE 1");
		contentPane.add(btnImage2);

		JButton btnImage3 = new JButton("YES");
		btnImage3.setBounds(350, 175, 160, 100);
		btnImage3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound(yes);
				Display.setText(name + " Pressed!");
			}
		});
		contentPane.add(btnImage3);

		JButton btnImageSimulator = new JButton("Simulator");
		btnImageSimulator.setEnabled(false);
		btnImageSimulator.setBounds(520, 175, 160, 100);
		btnImageSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Simulator simulator = new Simulator(contentPane);
				simulator.setVisible(true);
			}
		});
		contentPane.add(btnImageSimulator);

		JButton btnImage4 = new JButton("HELL YEAH");
		btnImage4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playSound(hell_yeah);
				Display.setText(name + " Pressed!");
			}
		});
		btnImage4.setBounds(10, 48, 160, 100);
		contentPane.add(btnImage4);

		// Needs some work.
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if (arg0.equals(KeyEvent.VK_N)) {
					playSound(no);
					Display.setText(name + " Pressed!");
				} else if (arg0.equals(KeyEvent.VK_Y)) {
					playSound(yes);
					Display.setText(name + " Pressed!");
				} else {
					playSound(strong_no);
					Display.setText(name + " Pressed!");
				}
			}
		});
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
