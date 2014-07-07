import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	// Just to fix warning of serialization error
	private static final long serialVersionUID = -979691148534081065L;
	public static String userString;
	public static String passString;
	public static int loginFlag = 0;

	public MainFrame() {

		// Window properties
		setVisible(true);
		setSize(1024, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("ASU TestSuite");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Login Panel
		JPanel loginPanel = new JPanel();
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(223, 265, 212, 54);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 44));
		usernameLabel.setToolTipText("Your username");
		loginPanel.add(usernameLabel);

		final JTextField username = new JTextField(15);
		username.setBounds(440, 272, 290, 39);
		username.setFont(new Font("Tahoma", Font.PLAIN, 27));
		loginPanel.add(username);
		username.requestFocus();

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(229, 439, 200, 54);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 44));
		passwordLabel.setToolTipText("Your password");
		loginPanel.add(passwordLabel);

		final JPasswordField password = new JPasswordField(15);
		password.setBounds(440, 446, 290, 39);
		password.setFont(new Font("Tahoma", Font.PLAIN, 27));
		loginPanel.add(password);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(440, 594, 169, 59);
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 42));
		loginButton.setToolTipText("Press to log-in");
		loginPanel.add(loginButton);

		getRootPane().setDefaultButton(loginButton);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userString = username.getText();
				passString = new String(password.getPassword());
				loginFlag = 1;
				int localFlag = LaunchSuite.flag;
				while (localFlag == 0) {
					localFlag = LaunchSuite.flag;
					System.out.print(localFlag);
					if (localFlag == 2) {
						JOptionPane.showMessageDialog(null,
								"Wrong UserName or Password! ...Closing");
						dispose();
						System.exit(0);
					} else if (localFlag == 1) {
						dispose();
						MailTo mT = null;
						try {
							mT = new MailTo();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mT.setVisible(true);

					} else {
						continue;
					}
				}
			}
		});

	}
}
