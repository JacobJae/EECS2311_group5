package main.java.TalkBox.model;

/*
 * Contains multiple check functions
*/
public class Check {
	public Check() {
		
	}
	
	/*
	 * Check if file is a .wav file
	 */
	public boolean isWav(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".wav"))
			return true;

		return false;
	}

	/*
	 * Check if the file is .tbc file
	 */
	public boolean isTbc(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".tbc"))
			return true;

		return false;
	}

	/*
	 * Check if the file is Image file
	 */
	public boolean isImg(String name) {
		String ext = "";
		ext = name.substring(name.indexOf("."), name.length());
		if (ext.equals(".jpg") || ext.equals(".png") || ext.equals(".bmp"))
			return true;

		return false;
	}
}
