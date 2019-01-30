import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import talkAppV1.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Fun with Git!");
		System.out.println("Peter Pushed this...");
		System.out.println("Karmit Pushed this...");
		System.out.println("Jacob Pushed this...");
		System.out.println("Creating develop branch");

		String no = "TalkBoxData/hello.mp3";
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
}
