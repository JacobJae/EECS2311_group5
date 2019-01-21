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
import javax.swing.AbstractButton;
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

	private JLabel Display;
	private JButton btnSound1, btnSound2, btnSound3, btnSound4, btnPlaySound;
	private JPanel contentPane;
	private File audio;
	private String name, no = "TalkBoxData/no.wav", strong_no = "TalkBoxData/strong_no.wav",
			yes = "TalkBoxData/yes.wav", hell_yeah = "TalkBoxData/hell_yeah.wav";
	private AudioInputStream audioIn;
	private Clip clip = null;
	private ArrayList<String> clips = new ArrayList();
	private ArrayList<JButton> sButtons = new ArrayList();

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

		init();
		addComp();

		btnSound1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBtn(0);
				//playSound(no);
				//Display.setText(name + " Pressed!");
			}
		});

		btnSound2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectBtn(1);
				//playSound(strong_no);
				//Display.setText(name + " Pressed!");
			}
		});

		btnSound3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBtn(2);
				//playSound(yes);
				//Display.setText(name + " Pressed!");
			}
		});

		btnSound4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectBtn(3);
				//playSound(hell_yeah);
				//Display.setText(name + " Pressed!");
			}
		});

		btnPlaySound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<sButtons.size();i++)
				{
					if(sButtons.get(i).isSelected())
					{
						playSound(clips.get(i));
						Display.setText(name + " Pressed!");						
					}						
				}
				
			}
		});
		
	}

	private void selectBtn(int x) {
		
		for (int i = 0; i < sButtons.size(); i++) {
			if (i == x)
				sButtons.get(i).setSelected(true);
			else
				sButtons.get(i).setSelected(false);
		}
		
	}

	private void addComp() {
		clips.add(no);
		clips.add(strong_no);
		clips.add(yes);
		clips.add(hell_yeah);

		sButtons.add(btnSound1);
		sButtons.add(btnSound2);
		sButtons.add(btnSound3);
		sButtons.add(btnSound4);

	}

	private void init() {

		getClip();

		Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);
		Display.setHorizontalAlignment(SwingConstants.CENTER);
		Display.setFont(new Font("Stencil", Font.BOLD, 48));
		Display.setBounds(10, 301, 670, 200);

		btnSound1 = new JButton("NO");
		btnSound1.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound1);
		btnSound1.setBounds(10, 11, 160, 100);

		btnSound2 = new JButton("Strong NO");
		btnSound2.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound2);
		btnSound2.setBounds(180, 11, 160, 100);

		btnSound3 = new JButton("YES");
		btnSound3.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound3);
		btnSound3.setBounds(350, 11, 160, 100);

		btnSound4 = new JButton("HELL YEAH");
		btnSound4.setFont(new Font("Stencil", Font.BOLD, 18));
		contentPane.add(btnSound4);
		btnSound4.setBounds(520, 11, 160, 100);

		btnPlaySound = new JButton("Play Sound");
		btnPlaySound.setFont(new Font("Stencil", Font.BOLD, 20));
		btnPlaySound.setBounds(10, 233, 670, 100);
		contentPane.add(btnPlaySound);
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
