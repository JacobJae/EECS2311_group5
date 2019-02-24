import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import talkAppV1.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Fun with Git!");
		System.out.println("Peter Pushed this...");
		System.out.println("Karmit Pushed this...");
		System.out.println("Jacob Pushed this...");
		System.out.println("Creating develop branch");

//		createSetting();
	}
	
	public static void createSetting() {
		TalkBox talkbox = new TalkBox();
		talkbox.setNumberOfAudioButtons(6);
		talkbox.setNumberOfAudioSets(2);
		String [][] audioFileNames = new String[talkbox.getNumberOfAudioSets()][talkbox.getNumberOfAudioButtons()];
		audioFileNames[0][0] = "TalkBoxData/no.wav";
		audioFileNames[0][1] = "TalkBoxData/strong_no.wav";
		audioFileNames[0][2] = "TalkBoxData/yes.wav";
		audioFileNames[0][3] = "TalkBoxData/hell_yeah.wav";
		
		audioFileNames[1][0] = "TalkBoxData/KissesinParadise.wav";
		audioFileNames[1][1] = "TalkBoxData/Medley1.wav";
		audioFileNames[1][2] = "TalkBoxData/MoodyLoop.wav";
		audioFileNames[1][3] = "TalkBoxData/ShakeYourBootay.wav";
		audioFileNames[1][4] = "TalkBoxData/test1.wav";
		audioFileNames[1][5] = "TalkBoxData/UpbeatFunk.wav";
		
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
