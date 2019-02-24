package talkAppV1;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.EventQueue;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TalkBoxTest extends TalkBox {

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void getNumberOfAudioButtons_test() {
		TalkBox box = new TalkBox();
		box.setNumberOfAudioButtons(4);
		assertEquals(4, box.getNumberOfAudioButtons());
	}

	@Test
	void setNumberOfAudioSets_test() {
		TalkBox box = new TalkBox();
		box.setNumberOfAudioSets(2);
		assertEquals(2, box.getNumberOfAudioSets());
	}

	@Test
	void getTotalNumberOfButtons_test() {
		TalkBox box = new TalkBox();
		box.setNumberOfAudioSets(2);
		box.setNumberOfAudioButtons(4);
		box.setNumberOfAudioSets(7);
		assertEquals(28, box.getTotalNumberOfButtons());
	}

	@Test
	void setAudioFileNames_test() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		assertEquals(names, box.getAudioFileNames());
	}

	@Test
	void setAudioFileNames_test2() {
		TalkBox box = new TalkBox();
		String[][] names = { { "hello", "mark" }, { "micheal", "jones" } };
		box.setAudioFileNames(names);
		String[][] names_2 = { { "hello", "mark" }, { "micheal", "jones" }, { "pie" } };
		assertNotEquals(names_2, box.getAudioFileNames());
	}

	@Test
	void getRelativePathToAudioFiles_test() {
		TalkBox box = new TalkBox();
		assertEquals(Paths.get("TalkBoxData//"), box.getRelativePathToAudioFiles());
	}
}
