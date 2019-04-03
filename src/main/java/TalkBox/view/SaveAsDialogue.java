package main.java.TalkBox.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;

public class SaveAsDialogue extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameText;
	private boolean valid = false;
	private String name;
	private ArrayList<KeyEvent> inValid = new ArrayList();
	private JLabel xlbl;
	private JButton btnSave;

	/**
	 * Create the dialog.
	 * 
	 * @param configurationGUI
	 */
	public SaveAsDialogue(ConfigurationGUI configurationGUI) {

		setSize(450, 165);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNameTheFile = new JLabel("Name the file:");
			lblNameTheFile.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblNameTheFile);
		}
		{
			nameText = new JTextField();
			nameText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					name = nameText.getText();
					if (name.contains("[!@#$%^&*()]") || name.isEmpty()) {
						valid = false;
						xlbl.setText("I");
						xlbl.setForeground(Color.red);
					} else {
						valid = true;
						xlbl.setText("V");
						xlbl.setForeground(Color.green);
					}

				}
			});
			nameText.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(nameText);
			nameText.setColumns(10);
		}
		{
			xlbl = new JLabel("X");
			xlbl.setBackground(Color.WHITE);
			xlbl.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPanel.add(xlbl);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSave = new JButton("Save");
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						name = nameText.getText();
						if (valid) {
							dispose();
							configurationGUI.setEnabled(true);
							configurationGUI.setCurrentSettings(name);
							configurationGUI.setSetting(name);
						} else {
							xlbl.setText("Enter a valid name");
							xlbl.setForeground(Color.red);
						}
					}
				});
				btnSave.setActionCommand("OK");
				buttonPane.add(btnSave);
				getRootPane().setDefaultButton(btnSave);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						configurationGUI.setEnabled(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
