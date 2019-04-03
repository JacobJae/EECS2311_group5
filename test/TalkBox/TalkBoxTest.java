package TalkBox;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.*;

import main.java.TalkBox.TalkBox;

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
		String[][] names_2 = { { "hello", "mark" }, { "micheal",  } };
		assertNotEquals(names_2, box.getAudioFileNames());
		
	}
	@org.junit.jupiter.api.Test
	void RemoveAudio_test2_All() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" }, { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		for (int i = 0; i < box.getNumberOfAudioSets(); i++) {
			for (int j = 0; j < box.getNumberOfAudioButtons(); j++) {
			box.removeAudio(i,j);
		}
		}
		String[][] names_2 = {  };
		assertNotEquals(names_2, box.getAudioFileNames());
		
	}
	
	@org.junit.jupiter.api.Test
	void getImages_test() {
		TalkBox box = new TalkBox();
		
	}
	
	@org.junit.jupiter.api.Test
	void setImages_test() {
		TalkBox box = new TalkBox();
		ImageIcon[][] images = { { new ImageIcon("TalkBoxData/Images/dollar.jpg"), new ImageIcon("TalkBoxData/Images/engineering.jpg") } };
		box.setImages(images);
		assertEquals(box.getImages(), images);
	}
}

