import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import talkAppV1.TalkBox;

public class Main {

	public static void main(String[] args) {
		System.out.println("Fun with Git!");
		System.out.println("Peter Pushed this...");
		System.out.println("Karmit Pushed this...");
		System.out.println("Jacob Pushed this...");
		System.out.println("Creating develop branch");

		try {
			TalkBox t = new TalkBox();
		     t.setNumberOfAudioButtons(4);
		     t.setNumberOfAudioSets(2);
		     
		    FileOutputStream fileOutputStream
		      = new FileOutputStream("TalkBoxData//configure.tbc");
		    ObjectOutputStream objectOutputStream 
		      = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(t);
		    objectOutputStream.flush();
		    objectOutputStream.close();
		     
		    FileInputStream fileInputStream
		      = new FileInputStream("TalkBoxData//configure.tbc");
		    ObjectInputStream objectInputStream
		      = new ObjectInputStream(fileInputStream);
		    TalkBox t2 = (TalkBox) objectInputStream.readObject();
		    objectInputStream.close(); 
		    
		    System.out.println(t2.getNumberOfAudioButtons());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
