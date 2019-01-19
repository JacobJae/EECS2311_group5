package talkAppV1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TalkBoxGui extends JFrame {

	
	private JLabel disp;
	private JButton b1,b2,b3,b4;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkBoxGui frame = new TalkBoxGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TalkBoxGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel Display = new JLabel("BUTTON PRESSED!");
		contentPane.add(Display);
		
		
		JButton btnImage1 = new JButton("IMAGE 1");
		btnImage1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText("B1 Pressed!");
			}
		});
		contentPane.add(btnImage1);
		
		JButton btnImage2 = new JButton("IMAGE 2");
		btnImage2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText("B2 Pressed!");
			}
		});
		btnImage2.setActionCommand("IMAGE 1");
		contentPane.add(btnImage2);
		
		
		JButton btnImage3 = new JButton("IMAGE 3");
		btnImage3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText("B3 Pressed!");
			}
		});
		contentPane.add(btnImage3);
		
		JButton btnImage4 = new JButton("IMAGE 4");
		btnImage4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText("B4 Pressed!");
			}
		});
		contentPane.add(btnImage4);
	}

	

}
