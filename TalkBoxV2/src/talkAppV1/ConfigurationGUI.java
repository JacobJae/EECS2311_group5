package talkAppV1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.AbstractListModel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import java.awt.Dimension;

public class ConfigurationGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnBack;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private ArrayList<String> clips = new ArrayList<String>(), selected = new ArrayList(),
			nonSelected = new ArrayList();
	private String name;
	private Sound sound;
	private TalkBox talkbox;
	private String[][] audioFileNames;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ConfigurationGUI frame = new
	 * ConfigurationGUI(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	/**
	 * Create the frame.
	 */
	public ConfigurationGUI(TalkBoxGui talkbox) {
		talkbox.setVisible(false);
		setVisible(true);
		setTitle("Configuration");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// create custom close operation
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				talkbox.setVisible(true);
				talkbox.reset();
			}
		});
		setBounds(100, 100, 717, 535);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(400, 400));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

//		this.clips = clips;
//		addClips();
//		init();
//		addAction();
//		setNames();
	}
	
	/*
	 * Get .tbc settings and initiate sound, buttongroup, talkbox object
	 */
	private void getSetting() {
		try {
			sound = new Sound();
			FileInputStream fileInputStream = new FileInputStream("TalkBoxData/configure.tbc");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			talkbox = (TalkBox) objectInputStream.readObject();
			audioFileNames = talkbox.getAudioFileNames();
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Set .tbc settings based on configuration
	 */
	private void SetSetting() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("TalkBoxData/configure.tbc");
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(talkbox);
		    objectOutputStream.flush();
		    objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setNames() {

	}

	private void addClips() {
		for (int i = 0; i < clips.size(); i++) {
			if (i < 4)
				selected.add(clips.get(i));
			else
				nonSelected.add(clips.get(i));
		}
	}

	private void addAction() {
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxApp();
			}
		});

	}

	protected void TalkBoxApp() {
		this.setVisible(false);
		new TalkBoxGui().setVisible(true);

	}

	private void init() {
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] {
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("217px"), ColumnSpec.decode("128px"),
						ColumnSpec.decode("37px"), ColumnSpec.decode("88px"), ColumnSpec.decode("237px"), },
				new RowSpec[] { RowSpec.decode("37px"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("25px"),
						FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("25px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						RowSpec.decode("313px"), FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("34px"), }));

		JToggleButton btnButton2 = new JToggleButton("STRONG NO");
		buttonGroup_1.add(btnButton2);
		btnButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSound(1);
			}
		});
		contentPane.add(btnButton2, "2, 3, fill, fill");

		JButton btnNewButton_1 = new JButton("ADD");
		contentPane.add(btnNewButton_1, "4, 3, fill, default");

		JToggleButton btnHello = new JToggleButton("HELLO");
		buttonGroup.add(btnHello);
		contentPane.add(btnHello, "6, 3, default, fill");

		JToggleButton btnButton3 = new JToggleButton("YES");
		btnButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSound(2);
			}
		});
		buttonGroup_1.add(btnButton3);
		contentPane.add(btnButton3, "2, 5, fill, fill");

		JButton btnRemove = new JButton("REMOVE");
		contentPane.add(btnRemove, "4, 5, fill, default");

		JToggleButton btnCherie = new JToggleButton("HELLO CHERIE");
		buttonGroup.add(btnCherie);
		contentPane.add(btnCherie, "6, 5, default, fill");

		JToggleButton btnButton4 = new JToggleButton("HELL YEAH");
		btnButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSound(3);
			}
		});
		buttonGroup_1.add(btnButton4);
		contentPane.add(btnButton4, "2, 7, fill, fill");

		JToggleButton btnHelloThere = new JToggleButton("HELLO THERE");
		buttonGroup.add(btnHelloThere);
		contentPane.add(btnHelloThere, "6, 7, default, fill");

		JToggleButton btnBruh = new JToggleButton("BRUH");
		buttonGroup.add(btnBruh);
		contentPane.add(btnBruh, "6, 9, default, fill");

		JToggleButton btnTadaah = new JToggleButton("TADAAH");
		buttonGroup.add(btnTadaah);
		contentPane.add(btnTadaah, "6, 11");
		btnBack = new JButton("BACK");
		btnBack.setFont(new Font("Stencil", Font.BOLD, 20));
		contentPane.add(btnBack, "2, 27, 5, 1, fill, top");

		JToggleButton btnButton1 = new JToggleButton("NO");
		btnButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSound(0);
			}
		});
		buttonGroup_1.add(btnButton1);
		contentPane.add(btnButton1, "2, 1, fill, bottom");

		JToggleButton btnNewButton = new JToggleButton("BYE HAVE A BEAUTIFUL TIME");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSound(0);
			}
		});
		buttonGroup.add(btnNewButton);
		contentPane.add(btnNewButton, "6, 1, fill, bottom");

	}

	protected void addSound(int i) {

	}

	protected void removeSound(int i) {

	}

	private void getName(String audioFile) {
		name = audioFile.substring(audioFile.indexOf("/") + 1, audioFile.indexOf("."));
	}
}
