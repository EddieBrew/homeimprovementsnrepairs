package login_systen;

/*

Created by Robert Brewer on 3/2/2022.

Login_Sys Activity--Sign on page to access the HomeImprovement Application.

Updates:
Version 3.1 6/20/22:  Changed login procedures to be more secure by parsing credentials from a file

 */


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main_screen.HomeMainGui;


public class Login_Sys {

	private JFrame frame;
	private JFrame frameLoginSystem;
	private JTextField tFieldUsername;
	private JPasswordField passwordField;
	private HomeMainGui window = null;
	//public final static String credentialsFilename = "mysignonstuff.txt" ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Sys window = new Login_Sys();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login_Sys() {
		initialize();
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 400, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("My Login Page");
		frame.getContentPane().setLayout(null);

		JLabel lblSystemLogonPage = new JLabel("SYSTEM LOGIN");
		lblSystemLogonPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSystemLogonPage.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblSystemLogonPage.setBounds(43, 11, 365, 25);
		frame.getContentPane().add(lblSystemLogonPage);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblUsername.setBounds(10, 69, 183, 45);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblPassword.setBounds(10, 134, 183, 44);
		frame.getContentPane().add(lblPassword);

		tFieldUsername = new JTextField();
		tFieldUsername.setFont(new Font("Dialog", Font.PLAIN, 30));
		tFieldUsername.setBounds(206, 69, 202, 45);
		frame.getContentPane().add(tFieldUsername);
		tFieldUsername.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 30));
		passwordField.setBounds(206, 134, 202, 44);
		frame.getContentPane().add(passwordField);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 414, 11);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 189, 414, 2);
		frame.getContentPane().add(separator_1);


		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnExit.setBounds(306, 202, 102, 48);
		frame.getContentPane().add(btnExit);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnLogin.setBounds(10, 202, 126, 48);
		frame.getContentPane().add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnReset.setBounds(159, 202, 126, 48);
		frame.getContentPane().add(btnReset);

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				frameLoginSystem = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frameLoginSystem, "Confirmif you want to exit", "Login System",
						JOptionPane.YES_NO_OPTION) ==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tFieldUsername.getText();
				char[] password = passwordField.getPassword();

				if(isSignonCredentCorrect(username, password)) {
					frame.setVisible(false); //you can't see me!
					frame.dispose(); //Destroy the JFrame object
					new HomeMainGui(username, password);    
				} else {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Invalid Username and/or Password","Login Error", JOptionPane.ERROR_MESSAGE);
					passwordField.setText(null);
				}	
			}
		});

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tFieldUsername.setText(null);
				passwordField.setText(null);
			}
		});	
	}//end initialize()


	/*********************************************************************************
	 * isSignonCredentCorrect() checks to see if username and password are correct. 
	 * 
	 * @pre String name: username
	 *      char[] pword: password
	 * @parameter 
	 * @post boolean: return boolean value if credential is correct or not
	 **********************************************************************************/
	private boolean isSignonCredentCorrect(String name, char[] pword) {

		final String credentialsFilename = "mysignonstuff.txt" ;
		final String DELIMITER= "#";
		String myDatastuff[] = getCredentialsFromFile(credentialsFilename).split(DELIMITER);

		if(name.equals(myDatastuff[1]) && String.valueOf(pword).equals(myDatastuff[4])) {	
			return  true;
		}else {
			return false;
		}




	}


	/*********************************************************************************
	 * getCredentialFromFile() retrieves the sign-on credentials from a file
	 * 
	 * @pre String inputFile: filename storing credentials
	 * @parameter 
	 * @post String: sign-on credential
	 **********************************************************************************/
	private String getCredentialsFromFile(String inputFile) {

		final int myMagicNumber = 21;
		String allData = null;
		int count = 0;
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(inputFile));
			String data;
			try {
				while ((data = bufferedReader.readLine()) != null) {	
					if(count == myMagicNumber) {
						allData = data;
					}
					count++;
				}
				if(count < myMagicNumber) { //file does not contain sign-on credential info
					JOptionPane.showMessageDialog(null, "Credentials can not be found."); 
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Can not read  from file ");
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Can not find credential file ");
			e.printStackTrace();

		}finally {
			try {
				bufferedReader.close();
				//return allData;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,"Error Closing The File"+e );
				e.printStackTrace();
			}
		}

		return allData;
	}

}
