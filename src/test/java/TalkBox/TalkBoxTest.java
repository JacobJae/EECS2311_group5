package test.java.TalkBox;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.*;

import javafx.scene.shape.Path;
import main.java.TalkBox.model.TalkBox;

class TalkBoxTest {

	@org.junit.jupiter.api.Test
	void test() {
//		fail("Not yet implemented");
	}

	@BeforeEach
	void setUp() throws Exception {

	}

	@org.junit.jupiter.api.Test
	void getNumberOfAudioButtons_test() {
		TalkBox box = new TalkBox();
		box.setNumberOfAudioButtons(4);
		assertEquals(4, box.getNumberOfAudioButtons());
	}
	
	@org.junit.jupiter.api.Test
	void NumberOfAudioButtons_increase_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.setNumberOfAudioButtons(4);
		box.incAudioButtons();
		assertEquals(5, box.getNumberOfAudioButtons());
	}
	
	@org.junit.jupiter.api.Test
	void NumberOfAudioButtons_decrease_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.setNumberOfAudioButtons(4);
		box.decAudioButtons();
		assertEquals(3, box.getNumberOfAudioButtons());
	}

	@org.junit.jupiter.api.Test
	void setNumberOfAudioSets_test() {
		TalkBox box = new TalkBox();
		box.setNumberOfAudioSets(2);
		assertEquals(2, box.getNumberOfAudioSets());
	}
	
	@org.junit.jupiter.api.Test
	void NumberOfAudioSets_increase_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.setNumberOfAudioSets(2);
		box.incAudioSets();
		assertEquals(3, box.getNumberOfAudioSets());
	}
	
	@org.junit.jupiter.api.Test
	void NumberOfAudioSets_decrease_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.setNumberOfAudioSets(2);
		box.decAudioSets();
		assertEquals(1, box.getNumberOfAudioSets());
	}

	@org.junit.jupiter.api.Test
	void getTotalNumberOfButtons_test() {
		TalkBox box = new TalkBox();
		box.setNumberOfAudioSets(2);
		box.setNumberOfAudioButtons(4);
		box.setNumberOfAudioSets(7);
		assertEquals(28, box.getTotalNumberOfButtons());
	}

	@org.junit.jupiter.api.Test
	void setAudioFileNames_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		assertEquals(names, box.getAudioFileNames());
	}

	@org.junit.jupiter.api.Test
	void setAudioFileNames_test2() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		String[][] names_2 = { { "hello", "mark" }, { "micheal", "jones" }, { "pie" } };
		assertNotEquals(names_2, box.getAudioFileNames());
	}

	@org.junit.jupiter.api.Test
	void getRelativePathToAudioFiles_test() {
		TalkBox box = new TalkBox();
		assertEquals(Paths.get(System.getProperty("user.dir")), box.getRelativePathToAudioFiles());
	}
	
	@org.junit.jupiter.api.Test
	void RemoveAudio_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.removeAudio(1,1);
		String[][] names_2 = { { "hello", "mark" }, { "micheal", null } };
		assertArrayEquals(names_2, box.getAudioFileNames());
		
	}
	@org.junit.jupiter.api.Test
	void RemoveAudio_test2_All() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" }, { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.setNumberOfAudioSets(4);
		box.setNumberOfAudioButtons(2);
		for (int i = 0; i < box.getNumberOfAudioSets(); i++) {
			for (int j = 0; j < box.getNumberOfAudioButtons(); j++) {
			box.removeAudio(i,j);
		}
		}
		String[][] names_2 = { {null, null} , {null, null} , {null, null} , {null, null}};
		assertArrayEquals(names_2, box.getAudioFileNames());
		
	}
	
	@org.junit.jupiter.api.Test
	void getAbsolutePath_test() {
		TalkBox box = new TalkBox();
		File file = new File(".");
		String path = file.getAbsolutePath();
		assertEquals("C:\\Users\\peter\\git\\EECS2311_group5\\.", path);
	}
	
	@org.junit.jupiter.api.Test
	void setAndGetImages_test() {
		TalkBox box = new TalkBox();
		ImageIcon[][] images = { { new ImageIcon("TalkBoxData/Images/dollar.jpg"), new ImageIcon("TalkBoxData/Images/engineering.jpg") } };
		box.setImages(images);
		assertEquals(box.getImages(), images);
	}
	
	@org.junit.jupiter.api.Test
	void setAndGetHasAudio_test() {
		TalkBox box = new TalkBox();
		boolean[][] audio = { {true, false, true}, {false, false, true} };
		box.setHasAudio(audio);
		assertArrayEquals(box.getHasAudio(), audio);
	}
	
	@org.junit.jupiter.api.Test
	void setAndGetSetNames_test() {
		TalkBox box = new TalkBox();
		String[] names = {"first", "second", "third"};
		box.setSetNames(names);
		assertArrayEquals(box.getSetNames(), names);
	}
	
	@org.junit.jupiter.api.Test
	void setAndGetFile_test() {
		TalkBox box = new TalkBox();
		File[] file = { new File("."), new File(".2")  };
		box.setsFile(file);
		assertArrayEquals(box.getsFile(), file);
	}
	
	@org.junit.jupiter.api.Test
	void setAndGetSettingsList_test() {
		TalkBox box = new TalkBox();
		List<String> list = new ArrayList<String>();
		list.add("hello");
		list.add("goodbye");
		box.setSettingsList(list);
		assertEquals(box.getSettingsList(), list);
	}
	
	@org.junit.jupiter.api.Test
	void AddAudio_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" }, { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		box.setNumberOfAudioSets(4);
		box.setNumberOfAudioButtons(2);
		box.addAudio(1,1,"hello");
		String[][] expected = { { "hello", "mark" }, { "micheal", "TalkBoxData\\hello" }, { "hello", "mark" }, { "micheal", "jones" } };
		assertArrayEquals(box.getAudioFileNames(), expected );
	}
}
