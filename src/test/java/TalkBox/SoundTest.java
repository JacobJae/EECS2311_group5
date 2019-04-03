package test.java.TalkBox;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.*;

import main.java.TalkBox.model.Sound;

class SoundTest {

	@org.junit.jupiter.api.Test
	void test() {
//		fail("Not yet implemented");
	}
	
	private Sound sound;

	@BeforeEach
	void setUp() throws Exception {
		sound = new Sound();
	}

//	@org.junit.jupiter.api.Test
//	void testPlaySound() {
//		assertThrows(FileNotFoundException.class, () -> {
//			sound.playSound("h");
//		});
//	}

	@org.junit.jupiter.api.Test
	void testExpectedExceptionFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			Integer.parseInt("1d");
		});
	}
	
	@org.junit.jupiter.api.Test
	void testTrue() {
		assertTrue(true);
	}
}
