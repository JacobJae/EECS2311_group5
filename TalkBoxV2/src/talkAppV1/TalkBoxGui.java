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

public class TalkBoxGui extends JFrame {

	private JLabel disp;
	private JButton b1, b2, b3, b4;
	private JPanel contentPane;
	private File audio;
	private String name, noS = "TalkBoxData/no.wav", sNoS = "TalkBoxData/strong_no.wav", yesS = "TalkBoxData/yes.wav";

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
		setBounds(100, 100, 770, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);


		audio = new File(noS);
		name = new File(noS).getName().substring(0, new File(noS).getName().length() - 4);

		JButton btnImage1 = new JButton(name);
		btnImage1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(noS).toURI().toURL());
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException ee) {
					ee.printStackTrace();
				} catch (IOException ee) {
					ee.printStackTrace();
				} catch (LineUnavailableException ee) {
					ee.printStackTrace();
				}
				Display.setText(name + " Pressed!");
			}
		});
		contentPane.add(btnImage1);

		audio = new File(sNoS);
		name = new File(sNoS).getName().substring(0, new File(sNoS).getName().length() - 4);
		JButton btnImage2 = new JButton(name);
		btnImage2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(sNoS).toURI().toURL());
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException ee) {
					ee.printStackTrace();
				} catch (IOException ee) {
					ee.printStackTrace();
				} catch (LineUnavailableException ee) {
					ee.printStackTrace();
				}
				Display.setText(name + " Pressed!");
			}
		});
		btnImage2.setActionCommand("IMAGE 1");
		contentPane.add(btnImage2);

		audio = new File(yesS);
		name = new File(yesS).getName().substring(0, new File(yesS).getName().length() - 4);
		JButton btnImage3 = new JButton(name);
		btnImage3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(yesS).toURI().toURL());
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException ee) {
					ee.printStackTrace();
				} catch (IOException ee) {
					ee.printStackTrace();
				} catch (LineUnavailableException ee) {
					ee.printStackTrace();
				}
				Display.setText(name + " Pressed!");
			}
		});
		contentPane.add(btnImage3);

	
		JButton btnImageSimulator = new JButton("Simulator");
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

}
