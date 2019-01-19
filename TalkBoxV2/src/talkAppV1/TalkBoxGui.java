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

public class TalkBoxGui extends JFrame {

	private JLabel disp;
	private JButton b1, b2, b3, b4;
	private JPanel contentPane;
	private File audio;
	private String name, noS = "TalkBoxData/no.wav", sNoS = "TalkBoxData/strong_no.wav", yesS = "TalkBoxData/yes.wav";
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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Display = new JLabel("BUTTON PRESSED!");
		Display.setHorizontalAlignment(SwingConstants.CENTER);
		Display.setFont(new Font("Stencil", Font.BOLD, 48));
		Display.setBounds(10, 136, 670, 200);
		contentPane.add(Display);

		JButton btnImage1 = new JButton("NO");
		btnImage1.setBounds(10, 10, 160, 100);
		btnImage1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound(noS);
				Display.setText("NO Pressed!");
			}
		});
		contentPane.add(btnImage1);

		JButton btnImage2 = new JButton("Strong NO");
		btnImage2.setBounds(180, 10, 160, 100);
		btnImage2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playSound(sNoS);
				Display.setText("Strong NO Pressed!");
			}
		});
		btnImage2.setActionCommand("IMAGE 1");
		contentPane.add(btnImage2);

		JButton btnImage3 = new JButton("YES");
		btnImage3.setBounds(350, 10, 160, 100);
		btnImage3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound(yesS);
				Display.setText("YES Pressed!");
			}
		});
		contentPane.add(btnImage3);

		JButton btnImageSimulator = new JButton("Simulator");
		btnImageSimulator.setEnabled(false);
		btnImageSimulator.setBounds(520, 10, 160, 100);
		btnImageSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Simulator simulator = new Simulator(contentPane);
				simulator.setVisible(true);
			}
		});
		contentPane.add(btnImageSimulator);
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

		audio = new File(audioFile);
		try {
			audioIn = AudioSystem.getAudioInputStream(audio.toURI().toURL());
			clip = AudioSystem.getClip();
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


}
