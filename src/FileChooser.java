import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class FileChooser extends JFrame {

	private static final long serialVersionUID = 6925233179506078873L;

	// File address and names
	public static String oldFile;
	public static String newFile;
	public static String newFileName;

	// FileChooser Object
	JFileChooser fc;

	// Flags
	int openFlag = 0;
	int saveFlag = 0;

	public FileChooser() {

		// Window properties
		setVisible(true);
		setSize(1024, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("File Chooser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// File chooser object initialization and setting the mode
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// This Panel contains a button
		JPanel panel = new JPanel();
		panel.setBounds(322, 82, 291, 55);
		getContentPane().add(panel);

		JButton btnOpenFile = new JButton("Open .xls File");
		btnOpenFile.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(btnOpenFile);

		btnOpenFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int returnVal = fc.showOpenDialog(FileChooser.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					oldFile = file.getPath();
					openFlag = 1;
				} else {
					JOptionPane.showMessageDialog(null,
							"Please select the file to be opened");
				}
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(322, 437, 291, 55);
		getContentPane().add(panel_1);

		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_1.add(btnNext);

		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (openFlag == 1 && saveFlag == 1) {
					dispose();
					DisplayOutput dO = new DisplayOutput();
					dO.setVisible(true);
				} else {
					JOptionPane
							.showMessageDialog(null,
									"You need to define the file to be opened and file to be saved");
				}
			}
		});

		getRootPane().setDefaultButton(btnNext);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(322, 255, 291, 55);
		getContentPane().add(panel_2);

		JButton btnSavexlsFile = new JButton("Save .xls File");
		btnSavexlsFile.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_2.add(btnSavexlsFile);

		btnSavexlsFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Date for Excel sheet
				Calendar cal = Calendar.getInstance();
				cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");
				fc.setSelectedFile(new File("MyASUSuite_"
						+ sdf.format(Calendar.getInstance().getTime()) + ".xls"));
				int returnVal = fc.showSaveDialog(FileChooser.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would save the file.
					newFile = file.getPath();
					newFileName = file.getName();
					saveFlag = 1;
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Please select the name and location of the file to be saved");
				}
			}
		});

		// just two labels
		JLabel lblSelectTheMost = new JLabel(
				"Select the most recent log file for comparing");
		lblSelectTheMost.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectTheMost.setBounds(271, 29, 404, 37);
		getContentPane().add(lblSelectTheMost);

		JLabel lblSelectTheName = new JLabel(
				"Select the name and location for saving the file");
		lblSelectTheName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectTheName.setBounds(271, 191, 430, 37);
		getContentPane().add(lblSelectTheName);

	}
}
