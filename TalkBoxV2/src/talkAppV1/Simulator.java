package talkAppV1;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Simulator extends JFrame{
	public Simulator(JPanel contentPane) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);
		
		new File("/TalkBoxData").mkdirs();
		File file = new File("/TalkBoxData");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
		
		for (int i = 0; i < files.size(); i++) {
			String extension = ".wav";
			if (extension.equals(getFileExtension(files.get(i)))) {
				File audio = files.get(i);
				String name = audio.getName().substring(0, audio.getName().length() - 4);
				JButton btnImage = new JButton(name);
				btnImage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
					         // Open an audio input stream.           
					         //you could also get the sound file with an URL
					          AudioInputStream audioIn = AudioSystem.getAudioInputStream(audio);              
					         // Get a sound clip resource.
					         Clip clip = AudioSystem.getClip();
					         // Open audio clip and load samples from the audio input stream.
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
				contentPane.add(btnImage);
			}
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
}
