package talkAppV1;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

public class SoundTest {
	private Sound sound;

	@BeforeEach
	void setUp() throws Exception {
		sound = new Sound();
	}

	@Test
	void testPlaySound() {
		assertThrows(FileNotFoundException.class, () -> {
			sound.playSound("aaaaaaa");
		});
	}

	@Test
	void testExpectedExceptionFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			Integer.parseInt("1d");
		});
	}
	
	@Test
	void testTrue() {
		assertTrue(true);
	}
}
