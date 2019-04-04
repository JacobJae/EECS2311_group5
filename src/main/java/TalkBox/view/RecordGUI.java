package main.java.TalkBox.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import main.java.TalkBox.model.Sound;
import javax.swing.JToggleButton;

public class RecordGUI extends JFrame {

	private JPanel contentPane;
	private boolean recording = false;
	private String path = "TalkBoxData/Sounds/", fileName;
	private Sound sound;
	private JTextField nameText;
	private JButton btnCancel;
	private JLabel lblRecord;
	private ConfigurationGUI confGui;
	private ImageIcon recordBtn = new ImageIcon("TalkBoxData/SystemFiles/record_btn.png"),
			playBtn = new ImageIcon("TalkBoxData/SystemFiles/play_btn.png"),
			stop_btn = new ImageIcon("TalkBoxData/SystemFiles/stop_btn.png"),
			preview_btn = new ImageIcon("TalkBoxData/SystemFiles/preview_btn.png"),
			stop_preview_btn = new ImageIcon("TalkBoxData/SystemFiles/stop_preview_btn.png"),
			delete_btn = new ImageIcon("TalkBoxData/SystemFiles/delete_btn.png");
	private JLabel disp;
	private Timer t;
	private int counter = 0;
	private JToggleButton btnPreview;
	private JLabel lblFileName;
	private JButton btnDelete;
	private JPanel controlPanel;

	/*
	 * Launch the application. public static void main(String[] args) {
	 * EventQueue.invokeLater(new Runnable() { public void run() { try { RecordGUI
	 * frame = new RecordGUI(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public RecordGUI(ConfigurationGUI confGui) {
		setVisible(true);

		this.confGui = confGui;

		setSize(new Dimension(600, 600));
		setResizable(false);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				recording = false;
				sound.stopRecording();
				confGui.setEnabled(true);
				confGui.setVisible(true);
				dispose();
			}
		});

		try {
			sound = new Sound();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		init();
		lblRecord.setIcon(resizeImg(playBtn.toString(), 50, 50));
		btnPreview.setEnabled(false);
		btnDelete.setEnabled(false);
		btnPreview.setIcon(resizeImg(preview_btn.toString(), 20, 20));
		btnDelete.setIcon(resizeImg(delete_btn.toString(), 20, 20));
		addAction();

	}

	private void addAction() {

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				recording = false;
				sound.stopSound();
				sound.stopRecording();
				confGui.setEnabled(true);
				confGui.setVisible(true);
				confGui.refresh();
				dispose();
			}
		});

		lblRecord.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				fileName = nameText.getText();
				if (nameText.getText().isEmpty())
					disp.setText("Please enter a name first!");
				else {
					if (recording) {
						stopRecording();
						disp.setText("Recording has stopped!");
						nameText.setText("");
						t = new Timer(100, new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								disp.setText("Recording has been Saved!");
								counter++;
								if (counter >= 30) {
									disp.setText("");
									t.stop();
								}
							}
						});
						t.start();
						updateDisplay();
					} else {
						disp.setText("Recording has started!");
						disp.setText("");
						disp.setText("");
						lblFileName.setText("");
						btnPreview.setIcon(resizeImg(preview_btn.toString(), 20, 20));
						btnPreview.setSelected(false);
						controlPanel.setEnabled(false);
						btnPreview.setEnabled(false);
						btnDelete.setEnabled(false);
						startRecording();
					}
				}
			}

		});

		btnPreview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!btnPreview.isSelected()) {
					sound.stopSound();
					btnPreview.setIcon(resizeImg(preview_btn.toString(), 20, 20));
				} else {
					sound.playSound(path + fileName + ".wav");
					btnPreview.setIcon(resizeImg(stop_preview_btn.toString(), 20, 20));
				}

			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFile();
			}
		});

	}

	protected void deleteFile() {

		File f = new File(path + fileName + ".wav");
		f.delete();
		t = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				disp.setText("Recording has been Deleted!");
				counter++;
				if (counter >= 30) {
					disp.setText("");
					lblFileName.setText("");
					btnPreview.setIcon(resizeImg(preview_btn.toString(), 20, 20));
					btnPreview.setSelected(false);
					controlPanel.setEnabled(false);
					btnPreview.setEnabled(false);
					btnDelete.setEnabled(false);
					t.stop();
				}

			}
		});
		t.start();
	}

	protected void updateDisplay() {
		controlPanel.setEnabled(true);
		btnPreview.setEnabled(true);
		btnDelete.setEnabled(true);
		lblFileName.setText(fileName);
	}

	protected void startRecording() {
		sound.startRecording(nameText.getText());
		recording = true;
		lblRecord.setIcon(resizeImg(stop_btn.toString(), 50, 50));

	}

	protected void stopRecording() {
		sound.stopRecording();
		recording = false;
		lblRecord.setIcon(resizeImg(playBtn.toString(), 50, 50));

	}

	/*
	 * Resize the Image to fit the button
	 */
	private ImageIcon resizeImg(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image scaleImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon ic = new ImageIcon(scaleImage);

		return ic;
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblRecordWindow = new JLabel("Record Window");
		lblRecordWindow.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRecordWindow.setHorizontalAlignment(SwingConstants.CENTER);

		lblRecord = new JLabel("");
		lblRecord.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel saveCancelPanel = new JPanel();
		saveCancelPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		disp = new JLabel("");
		disp.setFont(new Font("Dialog", Font.BOLD, 18));
		disp.setHorizontalTextPosition(SwingConstants.CENTER);
		disp.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanel = new JPanel();
		controlPanel.setEnabled(false);
		controlPanel.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblRecordWindow, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(saveCancelPanel, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(lblRecord, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE).addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE).addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(disp, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblRecordWindow).addGap(18)
						.addComponent(saveCancelPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(disp, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE).addGap(18)
						.addComponent(lblRecord, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)));

		lblFileName = new JLabel("Name");
		lblFileName.setFont(new Font("Dialog", Font.BOLD, 18));
		controlPanel.add(lblFileName);

		btnPreview = new JToggleButton("");
		btnPreview.setFont(new Font("Dialog", Font.BOLD, 18));
		controlPanel.add(btnPreview);

		btnDelete = new JButton("");
		btnDelete.setFont(new Font("Dialog", Font.BOLD, 18));
		controlPanel.add(btnDelete);

		JLabel lblSave = new JLabel("Name:");
		lblSave.setFont(new Font("Dialog", Font.BOLD, 18));

		nameText = new JTextField();
		nameText.setToolTipText("Name the file");
		nameText.setFont(new Font("Dialog", Font.BOLD, 18));
		nameText.setColumns(10);

		btnCancel = new JButton("Return");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 18));
		GroupLayout gl_saveCancelPanel = new GroupLayout(saveCancelPanel);
		gl_saveCancelPanel.setHorizontalGroup(gl_saveCancelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_saveCancelPanel.createSequentialGroup().addContainerGap().addComponent(lblSave)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(nameText, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_saveCancelPanel.setVerticalGroup(gl_saveCancelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_saveCancelPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_saveCancelPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 32, Short.MAX_VALUE)
								.addGroup(gl_saveCancelPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSave, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
										.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		saveCancelPanel.setLayout(gl_saveCancelPanel);
		contentPane.setLayout(gl_contentPane);

	}
}
