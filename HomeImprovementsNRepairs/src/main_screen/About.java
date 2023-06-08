package main_screen;


/*
Created by Robert Brewer on 3/5/2022.
About Java contains Version Information of HomeMainGui info

*/

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class About {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About window = new About();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public About() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 10));
		frame.getContentPane().setBackground(Color.PINK);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 517, 367);//100,100 ( setting on screen) 450, 300 (width, height)
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("About");
		frame.getContentPane().setLayout(null);


		JLabel lblPageName = new JLabel("About");
		lblPageName.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblPageName.setBounds(10, 11, 157, 30);
		frame.getContentPane().add(lblPageName);

		JLabel lblTitle = new JLabel("<html>HomeImprovementsNRepairs Version 3.2<br>\r\n@ Dec 2022 Created with Eclipse <br>\r\n<br>\r\n\r\nVersion 3.2 updates: <br>\r\n1)Format HomeData Object date from mm/dd/YYYY to YYYY-mm-dd<br>\r\n        to meet MYSQL database date forma<br>\r\n2)Updated methods that parsed HomeData date string to include new format<br>\r\n3) Created and also stored data into a MYSQL database\r\n\r\n\r\n");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setBounds(10, 52, 481, 124);
		frame.getContentPane().add(lblTitle);

		JLabel lblDescription = new JLabel("<html>HomeImprovementsNRepairs is an application that keep tracks of<br> maintenance  and  home improvement repairs at 5917 Marlin Cir. The app <br>\r\nstores all information in a file and a MYSQL database. The user can query any <br>\r\nroom, using different input parameter and  display the results in a table<br>\r\n format");
		lblDescription.setVerticalAlignment(SwingConstants.TOP);
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescription.setBackground(new Color(240, 240, 240));
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescription.setBounds(10, 198, 459, 93);
		frame.getContentPane().add(lblDescription);

		JLabel lblVerionDate = new JLabel("Version Date : Dec 2022");
		lblVerionDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVerionDate.setBounds(20, 288, 189, 24);
		frame.getContentPane().add(lblVerionDate);

		frame.setVisible(true);
	}

}
