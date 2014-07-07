import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllDone extends JFrame {

	private static final long serialVersionUID = -5223255908630283967L;

	public AllDone() {

		// Window Properties
		setSize(1024, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("All Done");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// This panel contains a label and a button
		JPanel panel = new JPanel();
		panel.setBounds(28, 51, 951, 66);
		getContentPane().add(panel);

		JLabel lblTheResultsWere = new JLabel(
				"The results were compared and the logs have been mailed to you. ");
		lblTheResultsWere.setFont(new Font("Tahoma", Font.PLAIN, 32));
		panel.add(lblTheResultsWere);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnExit.setBounds(411, 463, 159, 73);
		getContentPane().add(btnExit);

		getRootPane().setDefaultButton(btnExit);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

		// This Panel just contains a label
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(119, 184, 791, 53);
		getContentPane().add(panel_1);

		JLabel lblPleaseConfirmAnd = new JLabel(
				"Please confirm and then click on Exit ");
		lblPleaseConfirmAnd.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_1.add(lblPleaseConfirmAnd);

	}
}
