package main_screen;

/**
 * Created by Robert Brewer on 2/15/2018.
 *
 * HomeMainGui() allows the user to input information associated with home improvement items . 
 * using the MyBackgroundPanel.A menu is provided to allow the user to query the cost by dates
 * rooms in the house and and the current month.
 * is add
 * 

 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import mycharts.MyBarChart;
import mydatabase.MySQLConnect;
import query_screen.QueryingWindow;
import tables.QueryResultsTable;

public class HomeMainGui {

	/**
	 * 
	 */

	private static final long serialVersionUID = -8044048618173986424L;
	private static String username;
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu myMenu;
	private JMenuItem queryWork, logout, download, upload, about, databaseToFile,reformat;
	private JMenuItem currentMonth;
	private MySQLConnect mySQLDatabase;
	private final String HOMEIMPROVEMENT_DATABASE = "houseexpenses";
	private final static String inputFile = "cost.csv";
	// public final static String credentialsFilename = "mysignonstuff.txt" ;
	public static final int ROWS = 6;// number of years between 2020-2025
	public static final int COLS = 12;// each month of the year
	private static boolean maxLimitFlag = false;// When the max monthly limit is exceeded, the flag asserts high and
	// displays a notification to the user. The flag remain high
	// and wont display any notifications for future overlimits. The flag
	// resets to
	// false when the app is relaunch.

	// public static MyNetworkConnection networkConnection;
	private MyBackgroundPanel myBackgroundPanel;

	/**


	/**
	 * Create the application.
	 */
	public HomeMainGui(String username, char[] password) {
		HomeMainGui.username = username;
		signonCredentCorrect(); 
		initialize();
	}

	
	
	//Inner class to make "Reformat CSV File" menu item Active/Inactive in Menu
	class MyMouseListener extends MouseAdapter {
		  public void mouseClicked(MouseEvent evt) {
		    if (evt.getClickCount() == 3) {
		    	reformat.setVisible(false);
		    } else if (evt.getClickCount() == 2) {
		    	reformat.setVisible(true);
		      System.out.println("double-click");
		    }
		  }
		}
	
	private void initialize() {

		frame = new JFrame();
		myBackgroundPanel = new MyBackgroundPanel(mySQLDatabase);
		frame.setContentPane(myBackgroundPanel);

		/// Set up menu
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuBar.setBackground(Color.WHITE);// sets color of menubar

		// Build the menu items
		myMenu = new JMenu("MENU");// MENU: adds a Menu to the MENU BAR
		myMenu.setMnemonic(0);
		myMenu.getAccessibleContext().setAccessibleDescription("Get My Shit");
		myMenu.setBackground(new Color(50, 205, 50));
		menuBar.add(myMenu); // add to menu bar

		
		
		
		//// create menu items
		download = new JMenuItem("Download From Server");
		queryWork = new JMenuItem("Perform Queries");// MENU ITEM : adds a Menu Item to the MENU
		currentMonth = new JMenuItem("Current Month's Expenses");
		upload = new JMenuItem("Copy File Data To Database");//uploads cost.csv file to MYSQL database
		logout = new JMenuItem("Logout");
		about = new JMenuItem("About");
		reformat = new JMenuItem("Reformat CSV File"); // Menu item is auxiliary item used when the csv file data needs to be reformatted
		reformat.setVisible(false);
		//databaseToFile = new JMenuItem("Copy Database Date To File");
		

		//// add menu items to menu
		myMenu.add(reformat);
		myMenu.add(currentMonth);
		//myMenu.add(databaseToFile);
		//myMenu.add(download);
		myMenu.add(queryWork);
		myMenu.add(upload);
		myMenu.add(about);
		myMenu.add(logout);
		
		
		myMenu.addMouseListener(new MyMouseListener());
		/*
		 * //Special code to make "Reformat CSV File" active/Inactive in Menu
		 * myMenu.addMouseListener(new MouseAdapter() { public void
		 * mouseClicked(MouseEvent e) { if(reformat.isVisible()) {
		 * reformat.setVisible(false); System.out.println("Reformat is not visible");
		 * }else { reformat.setVisible(true); System.out.println("Reformat is visible");
		 * } } });
		 */
		
		
		
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new About();
			}

		});

		currentMonth.addActionListener(new ActionListener() {//retrieves current months entries

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getMonthlyExpenses(true);
			}
		});
		
		download.addActionListener(new ActionListener() {//Downloads Server data to cost.csv file
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				downloadFromServerToFile();
			}
		});
		
		/*
		 * databaseToFile.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { // TODO Auto-generated
		 * method stub //refreshDatabase(); }
		 * 
		 * });
		 */
		
		logout.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JFrame frame1 = new JFrame();
						String theMessage = " Do You Want To Quit The Application?";
						int result = JOptionPane.showConfirmDialog(frame1, theMessage, "alert", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							downloadFromServerToFile();
							System.exit(0);// closes all open windows
						}
					}
				});

		queryWork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new QueryingWindow("rbrewer",mySQLDatabase );
			}
		});

		

		upload.addActionListener(new ActionListener() {//Uploads cost.csv file to database
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//uploadFileToGoogleDrive();
				if(refreshDatabase()) {
					JOptionPane.showMessageDialog(null,"MYSQL database updated");
				}else {
					JOptionPane.showMessageDialog(null,"ERROR:::MYSQL database was not updated");
				} 
			}
		});

		
		
		reformat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List<HomeData> list = getDataFromFile();
				replaceDataInFile(list, "cost.csv");
				JOptionPane.showMessageDialog(null,"Data reformatted in  cost.csv file"); 
			}
			
		});
		
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

		getMonthlyExpenses(false);
		displayMarlinExpensesBarChart();

	}

	protected boolean refreshDatabase() {
		// TODO Auto-generated method stub
		if(mySQLDatabase.refreshDatabase(getDataFromFile())) {
			return true;
		}else {
			return false;
		}
	}

	public void uploadFileToGoogleDrive() {

		JFrame frame = new JFrame();
		String theMessage = " UpLoading Cost.csv File to Google Drive. Continue with upload?";
		int result = JOptionPane.showConfirmDialog(frame, theMessage, "alert", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			// File file = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int option = fileChooser.showOpenDialog(frame);
			if (option == JFileChooser.APPROVE_OPTION) {
				// file = fileChooser.getSelectedFile();
				// networkConnection.uploadToServerAndUpdateFile(
				// file.getAbsolutePath().toString());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JOptionPane.showMessageDialog(null, "SUCCESS: File Uploaded to Google Drive ");
			}
		}

	}

	public void downloadFromServerToFile() {

		JFrame frame = new JFrame();
		String theMessage = " Download From Server? File Data Will Be Overwritten. Continue?";
		int result = JOptionPane.showConfirmDialog(frame, theMessage, "alert", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.YES_OPTION) {

			new Thread(new Runnable() {
				@Override
				public void run() {

					String query = "SELECT * FROM "+ HOMEIMPROVEMENT_DATABASE;
					mySQLDatabase.getQuery(query);
					List<HomeData> myList = mySQLDatabase.getList();
					Collections.sort(myList, new SortHomeDataInDescendingOrderByDate());
					if(replaceDataInFile(myList, "cost.csv")) {
						JOptionPane.showMessageDialog(null,"SUCCESS: Data Downloaded to File " );
					}else {
						JOptionPane.showMessageDialog(null,"ERROR: Downloading Data " );
					}
				}
			}).start();
			mySQLDatabase.clearList();
		}
	}

	/*********************************************************************************
	 * getMonthlyExpenses() retrieves the current month's data from the cost.csv
	 * file. Depending on the boolean parameter, it creates a table displaying the
	 * current month's expenses
	 * 
	 * @pre none
	 * @parameter Boolean showGraph:determines whether the table is displayed.
	 * @post none: Displays a table with the current months expenses
	 **********************************************************************************/
	public void getMonthlyExpenses(Boolean showTable) {
		// TODO Auto-generated method stub
		Date date = new Date();
		try {
			final double MONTHLY_MAX = 250.00;//


			String result = "SELECT * FROM " + HOMEIMPROVEMENT_DATABASE + " WHERE  DATE >= '" + getFirstDayOfMonth(date) 
			+ "' AND DATE <= '" + 	getLastDayOfMonth(date) + "'" ;	
			mySQLDatabase.getDateRangeResults(result);

			List<HomeData> dateRangeList = mySQLDatabase.getList();
			Collections.sort(dateRangeList, new SortHomeDataInDescendingOrderByDate());
			double currentBalance = QueryingWindow.computeTotalCost(dateRangeList);

			if (showTable) { 
				String stitle = "Monthly Query for " + getMonthOfDate() + ". Total Cost = $"
						+ Double.toString(currentBalance); 
				new  QueryResultsTable(dateRangeList, stitle, username);
			}
			if (currentBalance > MONTHLY_MAX && !maxLimitFlag) {
				//System.out.println( "current balance = " + currentBalance + ", maxLimit = " + MONTHLY_MAX);
				maxLimitFlag = true;
				playMaxLimitSound(currentBalance, MONTHLY_MAX);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "ERROR: Unable To obtain current months total expense."
					+ " Check database connection ");
		}

		mySQLDatabase.clearList();

	}



	/*********************************************************************************
	 * playMaxLimitSound() displays a message and plays a sound when the current
	 * months expenditures have reached the maximum monthly threshold
	 * 
	 * @pre none
	 * @parameter double maxLimit Boolean showGraph
	 * @post none: Displays a table with the current months expenses
	 **********************************************************************************/
	public static void playMaxLimitSound(double currentBalance, double maxLimit) {

		/*
		 * String.format("%.2f", input)); sets double value to 2 decimal places
		 */

		try {
			File musicpath = new File("cash_register.wav");
			if (musicpath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicpath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				// double overLimit = currentBalance - maxLimit;
				String theMessage = "Your monthly expenditures have exceed your set $" + String.format("%.2f", maxLimit)
				+ "\n by $" + String.format("%.2f", (currentBalance - maxLimit));
				JOptionPane.showMessageDialog(null, theMessage, "ALERT", JOptionPane.ERROR_MESSAGE);

			} else {
				String theMessage1 = "Audio File Not Found";
				JOptionPane.showMessageDialog(null, theMessage1, "alert", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {

		}
	}

	/*********************************************************************************
	 * getFirstDayOfMonth() Uses the current date and returns the first day of that
	 * month
	 * 
	 * @pre none
	 * @parameter Date: current Date object
	 * @post String: returns a formatted string representation of the first day of
	 *       the current month(1/1/2022) format
	 **********************************************************************************/
	public static String getFirstDayOfMonth(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date dddd = calendar.getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		return sdf1.format(dddd);
	}

	/*********************************************************************************
	 * getLastDayOfMonth() Uses the current date and returns the last day of that
	 * month
	 * 
	 * @pre none
	 * @parameter Date: current Date object
	 * @post String: returns a formatted string representation of the first day of
	 *       the current month(1/31/2022) format
	 **********************************************************************************/
	public static String getLastDayOfMonth(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dddd = calendar.getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		return sdf1.format(dddd);
	}

	/*********************************************************************************
	 * getMonthofDate() retrieves the string representation of the current month
	 * 
	 * @pre none
	 * @parameter
	 * @post String: return the string representation of the month(i.e. Jan, feb,
	 *       Mar, etc)
	 **********************************************************************************/
	public static String getMonthOfDate() {

		LocalDate date = LocalDate.now();
		return date.getMonth().toString();
	}

	/*********************************************************************************
	 * getFirstDateOfMonth() retrieves the first day of the current month
	 * 
	 * @pre none
	 * @parameter
	 * @post Date: returns the Date object of the current first day of the month.
	 **********************************************************************************/
	/*
	 * 
	 * public static Date getFirstDateOfMonth(Date date){ Calendar cal =
	 * Calendar.getInstance(); cal.setTime(date); cal.set(Calendar.DAY_OF_MONTH,
	 * cal.getActualMinimum(Calendar.DAY_OF_MONTH)); return cal.getTime(); }
	 * 
	 * 
	 */

	/*********************************************************************************
	 * displayMarlinExpensesBarChart() retrieves all expense data from the cost.csv
	 * file and displays the monthly cost in a bar graph
	 * 
	 * @pre none
	 * @parameter
	 * @post Displays a bar graph depicting the total monthly expenses for each
	 *       month
	 **********************************************************************************/
	private void displayMarlinExpensesBarChart() {
		// TODO Auto-generated method stub

		//List<HomeData> myList = getDataFromFile();

		String query = "SELECT * FROM  "+ HOMEIMPROVEMENT_DATABASE;
		mySQLDatabase.getQuery(query);
		List<HomeData> myList = mySQLDatabase.getList();


		double[][] monthlyTotals = new double[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {// initialize array contents to 0
			for (int col = 0; col < COLS; col++) {
				monthlyTotals[row][col] = 0.; // Whatever value you want to set them to
			}
		}

		List<HomeData> list2020 = getListForYear(myList, 2020); // Cost data for 2020
		getMonthlyTotalForTheYear(monthlyTotals, list2020, 0);

		List<HomeData> list2021 = getListForYear(myList, 2021);// Cost data for 2020
		getMonthlyTotalForTheYear(monthlyTotals, list2021, 1);

		List<HomeData> list2022 = getListForYear(myList, 2022);// Cost data for 2020
		getMonthlyTotalForTheYear(monthlyTotals, list2022, 2);

		List<HomeData> list2023 = getListForYear(myList, 2023); // Cost data for 2020
		getMonthlyTotalForTheYear(monthlyTotals, list2023, 3);

		List<HomeData> list2024 = getListForYear(myList, 2024);// Cost data for 2020
		getMonthlyTotalForTheYear(monthlyTotals, list2024, 4);

		List<HomeData> list2025 = getListForYear(myList, 2025);// Cost data for 2020
		getMonthlyTotalForTheYear(monthlyTotals, list2025, 5);

		new MyBarChart("Marlin's Monthly Expenses", monthlyTotals);

		mySQLDatabase.clearList();

	}

	/*********************************************************************************
	 * getListForYear() iterates through an arraylist for the required year
	 * 
	 * @pre none
	 * @parameter HomeData item, int year
	 * @post ArrayList: returns an arrayList of HomeData items for the respective
	 *       query year
	 **********************************************************************************/
	private List<HomeData> getListForYear(List<HomeData> myList, int year) {
		int dateSelect = 3; // selects the date year
		List<HomeData> list = new ArrayList<>();
		Iterator<HomeData> it = myList.iterator();
		while (it.hasNext()) {
			HomeData data = it.next();
			int listYear = convertDateStringToInt(data.getDate(), dateSelect);
			if (listYear == year) {
				list.add(data);
			}
		}
		return list;
	}

	/*********************************************************************************
	 * getMonthlyTotalForThe Year() computes the monthly total for each month within
	 * a calendar year
	 * 
	 * @pre none
	 * @parameter double[][] monthlyTotals: a 6X12 array used to hold the total of
	 *            each month's total expenses within a calendar year List<HomeData>
	 *            list: A list containing a calendar year of house expense costs int
	 *            row: the row in the 6X12 array that represents the calendar
	 *            yera(i.e. 0 = 2020, 1 = 2021, etc
	 * @post updates monthlyTotals array
	 **********************************************************************************/
	public static void getMonthlyTotalForTheYear(double[][] monthlyTotals, List<HomeData> list, int row) {

		int dateSelect = 2;

		for (int i = 0; i < list.size(); i++) {
			switch (convertDateStringToInt((list.get(i).getDate()), dateSelect)) {
			case 1:
				monthlyTotals[row][0] += list.get(i).getCost();
				break;
			case 2:
				monthlyTotals[row][1] += list.get(i).getCost();
				break;
			case 3:
				monthlyTotals[row][2] += list.get(i).getCost();
				break;
			case 4:
				monthlyTotals[row][3] += list.get(i).getCost();
				break;
			case 5:
				monthlyTotals[row][4] += list.get(i).getCost();
				break;
			case 6:
				monthlyTotals[row][5] += list.get(i).getCost();
				break;
			case 7:
				monthlyTotals[row][6] += list.get(i).getCost();
				break;
			case 8:
				monthlyTotals[row][7] += list.get(i).getCost();
				break;
			case 9:
				monthlyTotals[row][8] += list.get(i).getCost();
				break;
			case 10:
				monthlyTotals[row][9] += list.get(i).getCost();
				break;
			case 11:
				monthlyTotals[row][10] += list.get(i).getCost();
				break;
			case 12:
				monthlyTotals[row][11] += list.get(i).getCost();
				break;
			default:
			}// end switch

		} // endfor(int i = 0; i < list.size(); i++)

	}



	/**************************************************************************************
	 * convertDateStringToInt() converts the string date to its date, month or year
	 * integer value
	 * 
	 * 
	 * @pre none
	 * @parameter String date, int dateSelect
	 * @post returns the integer value of the string date, month or year
	 **********************************************************************************/
	public static int convertDateStringToInt(String date, int dateSelect) {

		String delimStr = "-"; // date format is yyyy-MM-dd

		String[] words = date.split(delimStr);
		int intDate = 0;
		switch (dateSelect) {

		case 1:
			intDate = (Integer.parseInt(words[1]) * 100) + (Integer.parseInt(words[2]))
			+ Integer.parseInt(words[0]) * 10000; // date
			break;
		case 2:
			intDate = (Integer.parseInt(words[1]));// month
			break;
		case 3:
			intDate = Integer.parseInt(words[0]);// year
			break;
		default:
		}

		return intDate;
	} // end method


	private void signonCredentCorrect() {

		final String credentialsFilename = "mysqlsignonstuff.txt" ; 
		final String DELIMITER= "%";
		String myDatastuff[] = getCredentialsFromFile(credentialsFilename).split(DELIMITER);


		mySQLDatabase = new MySQLConnect(myDatastuff[0],myDatastuff[1],myDatastuff[2]);


		if(mySQLDatabase.isConnected()) { 
			JOptionPane.showMessageDialog(null,"Database Connected"); //isConnected = true; 
		}else {
			JOptionPane.showMessageDialog(null, "Trouble Connecting to Database"); 
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

		final int myMagicNumber = 36; 
		String allData = null; 
		int count = 1;
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




	/*
	 * getDateRangeFromDatabase displays a table of HomeData objects within a specific date range 
	 * @pre none
	 * @parameter none
	 * @post       table representation of query result with a specific date range 
	 **********************************************************************************/
	private void getDateRangeFromDatabase(String bDate, String eDate){
		//int dateSelect = 1;

		String result = "SELECT * FROM " + HOMEIMPROVEMENT_DATABASE + " WHERE  DATE >= '" + bDate + "' AND DATE <= '" + eDate + "'" ;
		mySQLDatabase.getDateRangeResults(result);

		List<HomeData> dateRangeList = mySQLDatabase.getList();

		String title = "Date Range Queries. Total Cost = $" + Double.toString(QueryingWindow.computeTotalCost(dateRangeList));
		new QueryResultsTable(dateRangeList, title, username);

		mySQLDatabase.clearList();

	}	
	/***********************************************************************************************
	 * SortGateDataInAscendingOrderByDate() sorts GateData objects by their dates,
	 * in ascending order
	 * 
	 * @pre none
	 * @parameter List<> : a list of GateData objects
	 * @post sorted list of GateData
	 ********************************************************************************************/
	public static class SortHomeDataInAscendingOrderByDate implements Comparator<HomeData> {

		public int compare(HomeData a, HomeData b) {
			int dateSelect = 1;// date's year

			return convertDateStringToInt(a.getDate(), dateSelect) - convertDateStringToInt(b.getDate(), dateSelect);

		}
	}

	/***********************************************************************************************
	 * SortGateDataInDescendingOrderByDate() sorts GateData objects by their dates,
	 * in descending order
	 * 
	 * @pre none
	 * @parameter List<> : a list of GateData objects
	 * @post none sorted list of GateData
	 ********************************************************************************************/
	public static class SortHomeDataInDescendingOrderByDate implements Comparator<HomeData> {

		public int compare(HomeData a, HomeData b) {
			int dateSelect = 1;// date's year
			return convertDateStringToInt(b.getDate(), dateSelect) - convertDateStringToInt(a.getDate(), dateSelect);
		}
	}



	/*********************************************READING/WRITING TO FILE///////////////////////////////////////////////////////////
	 **********************************************METHODS NOT USED*******************************************************************	
	 *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/*********************************************************************************
	 * getDataFromFile() retrieves the cost data from the cost.csv file and returns
	 * a List of HomeData objects
	 * 
	 * @pre none
	 * @parameter
	 * @post returns a List of HomeData objects
	 **********************************************************************************/
	public static List<HomeData> getDataFromFile() {

		BufferedReader fileReader = null;
		String str = "";
		List<HomeData> myList = new ArrayList<>();

		try {
			// Read the file line by line
			fileReader = new BufferedReader(new FileReader(inputFile));

			// reads the first line, which contains the header names
			str = fileReader.readLine();
			while ((str = fileReader.readLine()) != null) {
				HomeData data = new HomeData(str);
				myList.add(data);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Cannot find cost.csv file");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error Closing The File" + e);
				// e.printStackTrace();
			}
		}
		if (myList.size() > 0) {

			Collections.sort(myList, new SortHomeDataInDescendingOrderByDate());
			// replaceDataInFile(myList,"cost.csv");
			// Arrays.toString(myList.toArray());
			return myList;
		} else {
			return null;
		}

	} // end method

	/*
	 * placeFileInData() inserts a HomeData object in the cost.csv file
	 *
	 * @pre none
	 * @parameter HomeData item
	 * @post Boolean: returns a boolean if the data was written
	 *       successfully/unsuccessfully the file
	 **********************************************************************************/
	public static Boolean placeInFile(HomeData item) {// make function return a boolean

		// System.out.println(item.toString());
		Boolean isWrittenToFile = true;
		BufferedWriter bw = null;
		Boolean createFileHeaders = true;
		final String COMMA_DELIMITER = ",";
		final String NEW_LINE_SEPARATOR = "\n";
		try {
			File file = new File(inputFile);

			/*
			 * This logic will make sure that the file gets created if it is not present at
			 * the specified location
			 */
			if (!file.exists()) {
				file.createNewFile();
				createFileHeaders = false;
			}
			FileWriter fw = new FileWriter(file, true);// appends
			bw = new BufferedWriter(fw);

			if (!createFileHeaders) {
				bw.write("DATE");
				bw.write(COMMA_DELIMITER);
				bw.write("AREA");
				bw.write(COMMA_DELIMITER);
				bw.write("ITEMS");
				bw.write(COMMA_DELIMITER);
				bw.write("COST");
				bw.write(COMMA_DELIMITER);
				bw.write("RECEIPT FILE NAME");
				bw.write(COMMA_DELIMITER);
				bw.write("INFO");
				bw.write(COMMA_DELIMITER);
				bw.write("VALUE ADDED");
				bw.write(NEW_LINE_SEPARATOR);
			}

			bw.write(item.getDate());
			bw.write(COMMA_DELIMITER);
			bw.write(item.getArea());
			bw.write(COMMA_DELIMITER);
			bw.write(item.getItem());
			bw.write(COMMA_DELIMITER);
			bw.write(item.getCost().toString());
			bw.write(COMMA_DELIMITER);
			bw.write(item.getReceiptFilename());
			bw.write(COMMA_DELIMITER);
			bw.write(item.getInfo());
			bw.write(COMMA_DELIMITER);
			bw.write(item.getIsValue().toString());
			bw.write(NEW_LINE_SEPARATOR);

			// JOptionPane.showMessageDialog(null,"Data Written To File Successfully" );
		} catch (IOException ioe) {
			isWrittenToFile = false;
			JOptionPane.showMessageDialog(null, "Error Opening The File ");
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error Closing The File ");
			}
		}
		return isWrittenToFile;
	}// end method

	/*********************************************************************************
	 * replaceDataFileInData() replaces a HomeData object in the cost.csv file upon
	 * opdating the Table fields
	 * 
	 * @pre none
	 * @parameter List<HomeData> item
	 * @post none: Updates the repair info in the cost.csv file
	 **********************************************************************************/
	public static Boolean replaceDataInFile(List<HomeData> item, String filename) {
		Boolean isWriteSuccess = false;
		BufferedWriter bw = null;
		FileWriter fw;
		final String COMMA_DELIMITER = ",";
		final String NEW_LINE_SEPARATOR = "\n";
		try {
			File file = new File(filename);

			/*
			 * This logic will make sure that the file gets created if it is not present at
			 * the specified location
			 */
			if (!file.exists()) {
				file.createNewFile();
			}

			if (filename.equalsIgnoreCase(inputFile)) {
				fw = new FileWriter(file, false);// empty all data and creates new file and its column names
				bw = new BufferedWriter(fw);
				bw.write("DATE");
				bw.write(COMMA_DELIMITER);
				bw.write("AREA");
				bw.write(COMMA_DELIMITER);
				bw.write("ITEMS");
				bw.write(COMMA_DELIMITER);
				bw.write("COST");
				bw.write(COMMA_DELIMITER);
				bw.write("RECEIPT FILE NAME");
				bw.write(COMMA_DELIMITER);
				bw.write("INFO");
				bw.write(COMMA_DELIMITER);
				bw.write("VALUE ADDED");
				bw.write(NEW_LINE_SEPARATOR);

			} else {

				fw = new FileWriter(file, true); // appends data to file
				bw = new BufferedWriter(fw);
			}

			for (int i = 0; i < item.size(); i++) {
				bw.write(item.get(i).getDate());
				bw.write(COMMA_DELIMITER);
				bw.write(item.get(i).getArea());
				bw.write(COMMA_DELIMITER);
				bw.write(item.get(i).getItem());
				bw.write(COMMA_DELIMITER);
				bw.write(item.get(i).getCost().toString());
				bw.write(COMMA_DELIMITER);
				bw.write(item.get(i).getReceiptFilename());
				bw.write(COMMA_DELIMITER);
				bw.write(item.get(i).getInfo());
				bw.write(COMMA_DELIMITER);
				bw.write(item.get(i).getIsValue().toString());
				bw.write(NEW_LINE_SEPARATOR);
			}
			isWriteSuccess = true;
			// JOptionPane.showMessageDialog(null,"Data written to file. " );
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Data was not written to file. \nVerify the cost.csv file is closed");
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error Closing The File " + e);
			}
		}
		return isWriteSuccess;
	}// end method




	public static String reformatDateString(String date) {

		String newDate = null; String DELIMITER = "/"; //date format = mm/dd/YYYY
		String oldDate[] = date.split(DELIMITER); 
		String month = null; 
		String day = null;

		month = oldDate[0]; day = oldDate[1];

		if(Integer.parseInt(oldDate[0]) < 10) { 
			month = "0" + oldDate[0]; 
		}else {
			month = oldDate[0]; }

		if(Integer.parseInt(oldDate[1]) < 10) { 
			day = "0" + oldDate[1]; 
		}else { day =
				oldDate[1]; }


		newDate = oldDate[2] + "-" +oldDate[0] + "-" + oldDate[1];
		return newDate;

	}





	/* Launch the application.
	 */
	public static void main(String[] args) {


		char[] p = { 'l', 'i', 'i', 's', 't', '9', 'm', '1', '6', '5', '5' };

		new HomeMainGui("getMoney", p);


	}

}
