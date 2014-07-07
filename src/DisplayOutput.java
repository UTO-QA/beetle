import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class DisplayOutput extends JFrame {

	private static final long serialVersionUID = 4048098140181335272L;

	public DisplayOutput() {

		// Window Properties
		setSize(1024, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Testing...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// This panel contains a label
		JPanel panel1 = new JPanel();
		panel1.setBounds(47, 40, 876, 58);
		getContentPane().add(panel1);

		JLabel lblPleaseBePatient = new JLabel(
				"Please have a cup of coffee while we are testing the website for you...");
		lblPleaseBePatient.setFont(new Font("Tahoma", Font.PLAIN, 28));
		panel1.add(lblPleaseBePatient);

		// This panel contains an animation
		JPanel panel2 = new JPanel();
		panel2.setBounds(318, 273, 337, 304);
		getContentPane().add(panel2);

		ImageIcon image = new ImageIcon(getClass().getResource("loading.gif"));

		JLabel imageLabel = new JLabel(image);
		panel2.add(imageLabel);

		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnNext.setBounds(424, 676, 148, 45);
		getContentPane().add(btnNext);

		// This panel contains just a label
		JPanel panel3 = new JPanel();
		panel3.setBounds(15, 608, 988, 52);
		getContentPane().add(panel3);

		JLabel lblClickOnNext = new JLabel(
				"Click on next when the browser closes.");
		lblClickOnNext.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel3.add(lblClickOnNext);
		getRootPane().setDefaultButton(btnNext);
		
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				AllDone aD = new AllDone();
				aD.setVisible(true);
			}
		});

		// This panel contains just a label
		JPanel panel4 = new JPanel();
		panel4.setBounds(47, 126, 876, 67);
		getContentPane().add(panel4);

		JLabel lblAlsoPleaseDont = new JLabel(
				"Also please don't close the Firefox browser which started with the application");
		lblAlsoPleaseDont.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panel4.add(lblAlsoPleaseDont);
		

	}
}
