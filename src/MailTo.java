import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MailTo extends JFrame {

	private static final long serialVersionUID = 5786194792320158467L;

	// For mailing the results
	public static String Mail1;
	public static String Mail2;
	public static String Mail3;

	// For taking in the Email-Id's
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;

	// For writing recently used Email-Id's
	private static PrintWriter writer3;

	// just a flag
	public int flag0 = 0;

	public MailTo() throws IOException {

		// Create a new text file, if already present append to it
		writer3 = new PrintWriter(new FileWriter("RecentEmailId.txt", true));

		// Function to remove duplicate Email-Id's from the test file
		stripDuplicatesFromFile("RecentEmailId.txt");

		// Window properties
		setVisible(true);
		setSize(1024, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Fill out the email address to send mail to...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// This panel includes a label
		JPanel panel = new JPanel();
		panel.setBounds(15, 27, 988, 61);
		getContentPane().add(panel);

		JLabel lblPleaseFillOut = new JLabel(
				"Please fill out the email-ID's, so that we can send the testing logs...");
		lblPleaseFillOut.setFont(new Font("Tahoma", Font.PLAIN, 32));
		panel.add(lblPleaseFillOut);

		// This panel contains few labels, text-fields and a button
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(15, 408, 988, 352);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblMailId1 = new JLabel("Mail ID 1:");
		lblMailId1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblMailId1.setBounds(164, 46, 106, 44);
		panel_1.add(lblMailId1);

		JLabel lblMailId2 = new JLabel("Mail ID 2:");
		lblMailId2.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblMailId2.setBounds(164, 106, 106, 44);
		panel_1.add(lblMailId2);

		JLabel lblMailId3 = new JLabel("Mail ID 3:");
		lblMailId3.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblMailId3.setBounds(164, 166, 106, 44);
		panel_1.add(lblMailId3);

		textField1 = new JTextField();
		textField1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField1.setBounds(305, 56, 472, 26);
		panel_1.add(textField1);
		textField1.setColumns(10);

		textField2 = new JTextField();
		textField2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField2.setColumns(10);
		textField2.setBounds(305, 116, 472, 26);
		panel_1.add(textField2);

		textField3 = new JTextField();
		textField3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField3.setColumns(10);
		textField3.setBounds(305, 176, 472, 26);
		panel_1.add(textField3);

		JButton btnContinue = new JButton("Continue");
		btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnContinue.setBounds(403, 256, 217, 62);
		panel_1.add(btnContinue);

		btnContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Mail1 = textField1.getText();
				Mail2 = textField2.getText();
				Mail3 = textField3.getText();
				if (Mail1.isEmpty() && Mail2.isEmpty() && Mail3.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"At least enter one Email Id!");
				} else {
					Mail1 = textField1.getText();
					Mail2 = textField2.getText();
					Mail3 = textField3.getText();

					if (Mail1.isEmpty()) {

					} else {
						writer3.println(Mail1);
					}
					if (Mail2.isEmpty()) {

					} else {
						writer3.println(Mail2);
					}
					if (Mail3.isEmpty()) {

					} else {
						writer3.println(Mail3);
					}
					writer3.close();
					dispose();
					FileChooser fC = new FileChooser();
					fC.setVisible(true);
				}
			}
		});

		// Default the continue button
		getRootPane().setDefaultButton(btnContinue);

		// This panel contains a text area to display recently used Email-Id's,
		// a clear button
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(15, 104, 988, 288);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		final JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		textArea.setBounds(161, 100, 611, 172);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		BufferedReader in = new BufferedReader(new FileReader(
				"RecentEmailId.txt"));
		String line = in.readLine();
		while (line != null) {
			textArea.append(line + "\n");
			line = in.readLine();
		}
		in.close();

		// Scroll Pane for the text area
		JScrollPane scr = new JScrollPane(textArea);

		// Pop-up menu for copying the selected text and pasting it in the text
		// fields
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);
		scr.setSize(611, 172);
		scr.setLocation(161, 100);
		panel_2.add(scr);

		JMenuItem copyMenuItem = new JMenuItem("Use this Email-Id");
		popupMenu.add(copyMenuItem);
		copyMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String copiedText = textArea.getSelectedText();
				if (flag0 == 0) {
					textField1.setText(copiedText);
					flag0 = 1;
				} else if (flag0 == 1) {
					textField2.setText(copiedText);
					flag0 = 2;
				} else if (flag0 == 2) {
					textField3.setText(copiedText);
				} else {
					textField3.setText(copiedText);
				}
			}
		});

		JLabel lblRecentEmailIds = new JLabel("Recently used Email Id's");
		lblRecentEmailIds.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRecentEmailIds.setBounds(354, 35, 226, 36);
		panel_2.add(lblRecentEmailIds);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(624, 41, 115, 29);
		panel_2.add(btnClear);

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				try {
					writer3 = new PrintWriter("RecentEmailId.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	// Function to remove duplicates from the text file
	public void stripDuplicatesFromFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		Set<String> lines = new HashSet<String>(10000);
		String line;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		reader.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for (String unique : lines) {
			writer.write(unique);
			writer.newLine();
		}
		writer.close();
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
