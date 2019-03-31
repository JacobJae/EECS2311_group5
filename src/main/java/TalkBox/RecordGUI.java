package main.java.TalkBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class RecordGUI extends JFrame {

	private JPanel contentPane;
	private boolean recording = false;
	private String path;
	private Sound sound;
	private JTextField nameText;
	private JButton btnCancel;
	private JLabel lblRecord;
	private ConfigurationGUI confGui;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
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
		addAction();

	}

	private void addAction() {

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				recording = false;
				sound.stopRecording();
				confGui.setEnabled(true);
				confGui.setVisible(true);
				dispose();
			}
		});

		lblRecord.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(recording)
					stopRecording();
				else
					startRecording();
			}
			
		});
		
	}

	protected void startRecording() {
		sound.startRecording(nameText.getText());
		recording = true;
		lblRecord.setText("Pause");
		
	}

	protected void stopRecording() {
		sound.stopRecording();
		recording = false;
		lblRecord.setText("Record");
		
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblRecordWindow = new JLabel("Record Window");
		lblRecordWindow.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRecordWindow.setHorizontalAlignment(SwingConstants.CENTER);

		lblRecord = new JLabel("Record");
		lblRecord.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel saveCancelPanel = new JPanel();
		saveCancelPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblRecordWindow, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(lblRecord, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE).addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(saveCancelPanel, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblRecordWindow).addGap(18)
						.addComponent(lblRecord, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(saveCancelPanel, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
						.addContainerGap()));

		JLabel lblSave = new JLabel("Save:");
		lblSave.setFont(new Font("Tahoma", Font.PLAIN, 16));

		nameText = new JTextField();
		nameText.setToolTipText("Name the file");
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameText.setColumns(10);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout gl_saveCancelPanel = new GroupLayout(saveCancelPanel);
		gl_saveCancelPanel.setHorizontalGroup(gl_saveCancelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_saveCancelPanel.createSequentialGroup().addContainerGap().addComponent(lblSave)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_saveCancelPanel.setVerticalGroup(gl_saveCancelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_saveCancelPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_saveCancelPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
								.addGroup(gl_saveCancelPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSave, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
										.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		saveCancelPanel.setLayout(gl_saveCancelPanel);
		contentPane.setLayout(gl_contentPane);

	}
}
