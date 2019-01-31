import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import talkAppV1.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Fun with Git!");
		System.out.println("Peter Pushed this...");
		System.out.println("Karmit Pushed this...");
		System.out.println("Jacob Pushed this...");
		System.out.println("Creating develop branch");

//		createSetting();
		
		String no = "TalkBoxData/no.wav";
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sound frame = new Sound();
					frame.playSound(no);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void createSetting() {
		TalkBox talkbox = new TalkBox();
		talkbox.setNumberOfAudioButtons(6);
		talkbox.setNumberOfAudioSets(1);
		String [][] audioFileNames = new String[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
		audioFileNames[0][0] = "TalkBoxData/no.wav";
		audioFileNames[0][1] = "TalkBoxData/strong_no.wav";
		audioFileNames[0][2] = "TalkBoxData/yes.wav";
		audioFileNames[0][3] = "TalkBoxData/hell_yeah.wav";
		
		talkbox.setAudioFileNames(audioFileNames);
		
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("TalkBoxData/configure.tbc");
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(talkbox);
		    objectOutputStream.flush();
		    objectOutputStream.close();
		     
		    FileInputStream fileInputStream = new FileInputStream("TalkBoxData/configure.tbc");
		    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		    TalkBox t = (TalkBox) objectInputStream.readObject();
		    objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
