import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import TalkBox.*;
import main.java.TalkBox.TalkBox;

public class Main {

	public static void main(String[] args) {
//		System.out.println("Fun with Git!");
//		System.out.println("Peter Pushed this...");
//		System.out.println("Karmit Pushed this...");
//		System.out.println("Jacob Pushed this...");
//		System.out.println("Creating develop branch");
		
		createSetting();
		try {
		FileInputStream fileInputStream = new FileInputStream("TalkBoxData/configure.tbc");
	    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	    TalkBox t = (TalkBox) objectInputStream.readObject();
	    System.out.println(t.getNumberOfAudioSets());
	    System.out.println(t.getRelativePathToAudioFiles().toRealPath());
	    objectInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createSetting() {
		TalkBox talkbox = new TalkBox();
		talkbox.setNumberOfAudioButtons(6);
		talkbox.setNumberOfAudioSets(2);
		String [][] audioFileNames = new String[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
		audioFileNames[0][0] = "TalkBoxData/ShakeYourBootay.wav";
		audioFileNames[0][1] = "TalkBoxData/MoodyLoop.wav";
		audioFileNames[0][2] = "TalkBoxData/Medley1.wav";
		audioFileNames[0][3] = "TalkBoxData/UpbeatFunk.wav";
		
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
		    System.out.println(t.getNumberOfAudioSets());
		    objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}