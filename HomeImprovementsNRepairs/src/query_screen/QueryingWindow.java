package query_screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import main_screen.HomeData;
import main_screen.HomeMainGui;
import main_screen.HomeMainGui.SortHomeDataInDescendingOrderByDate;
import mydatabase.MySQLConnect;
import tables.QueryResultsTable;

public class QueryingWindow {

	//private final static String inputFile = "cost.csv";
	private JFrame qFrame;
	private JDateChooser dateChooserB, dateChooserE;
	private JComboBox<Object> comboBoxArea, comboBoxItem1, comboBoxItem2, comboBoxItem3;
	private JCheckBox chckbxHomeImprovementDates,chckBoxHomeImprovementItems;
	private JTextField textFieldCost;
	private String username;
	private MySQLConnect myDatabase;
	private final String HOMEIMPROVEMENT_DATABASE = "houseexpenses";//database that stores past locker information

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try {
		 * QueryingWindow window = new QueryingWindow("rbrewer");
		 * window.qFrame.setVisible(true); } catch (Exception e) { e.printStackTrace();
		 * } } });
		 */
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public QueryingWindow(String username, MySQLConnect mySQLDatabase) {
		this.username = username;
		this.myDatabase = mySQLDatabase;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	
		String[] areas = {"ATTIC", "BACKYARD","BATH1", "BATH2","BDRM1","BDRM2","BDRM3","BDRM4",
        		   "FRONTYARD", "GARAGE", "HALLWAY", "KITCHEN","LIVING ROOM", "ROOF"};
		
		String[] items = {"N/A", "ANTENNA", "CHIMNEY", "CLOSET","DISHWASHER","DOOR","DRYER", "FAN",
		           "FENCE", "FLOOR", "FRIG", "GUTTERS", "INTERNET", "LIGHTS","MIRROR", 
		           "MISC",  "OUTLETS","OVEN", "PLANTS", "SEWAGE LINE", "SHOWER/TUB", "SINK","TILE",  
		           "TOILET","TV", "WALLS","WASHER",  "WINDOWS"};
		
		qFrame = new JFrame();
		qFrame.setBounds(100, 100, 600, 500);
		qFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		qFrame.getContentPane().setBackground(Color.YELLOW);
		qFrame.getContentPane().setLayout(null);
		qFrame.setTitle("QUERIES FOR REPAIRS/HOME IMPROVEMENTS");
		qFrame.setVisible(true);
		
		JLabel lblQueryPanel = new JLabel("QUERY  PANEL");
		lblQueryPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueryPanel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblQueryPanel.setBounds(180, 0, 219, 32);
		qFrame.getContentPane().add(lblQueryPanel);
		
		JLabel lblArea = new JLabel("AREA");
		lblArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblArea.setBounds(10, 225, 105, 19);
		qFrame.getContentPane().add(lblArea);
		
	    comboBoxArea = new JComboBox<Object>();
		comboBoxArea.setModel(new DefaultComboBoxModel<Object>( areas));
		comboBoxArea.setBounds(10, 245, 130, 31);
		qFrame.getContentPane().add(comboBoxArea);
		
		JLabel lblItem1 = new JLabel("ITEM1");
		lblItem1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblItem1.setBounds(241, 225, 105, 19);
		qFrame.getContentPane().add(lblItem1);
		
		
		comboBoxItem1 = new JComboBox<Object>();
		comboBoxItem1.setModel(new DefaultComboBoxModel(new String[] {"N/A", "ANTENNA", "CHIMNEY", "CLOSET", "DISHWASHER", "DOOR", "DRYER", "FAN", "FENCE", "FLOOR", "FRIG", "GUTTERS", "INTERNET", "LIGHTS", "MIRROR", "MISC", "OUTLETS", "OVEN", "PLANTS", "SEWAGE LINE", "SHOWER/TUB", "SINK", "TILE", "TOILET", "TV", "WALLS", "WASHER", "WINDOWS"}));
		comboBoxItem1.setBounds(241, 245, 130, 31);
		qFrame.getContentPane().add(comboBoxItem1);
		comboBoxItem1.setSelectedItem("N/A");
		
		JLabel lblItem = new JLabel("ITEM2");
		lblItem.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblItem.setBounds(241, 287, 105, 19);
		qFrame.getContentPane().add(lblItem);
		
		comboBoxItem2 = new JComboBox<Object>();
		comboBoxItem2.setModel(new DefaultComboBoxModel<Object>( items));
		comboBoxItem2.setBounds(241, 307, 130, 31);
		qFrame.getContentPane().add(comboBoxItem2);
		comboBoxItem2.setSelectedItem("N/A");
		
		JLabel lblItem_1 = new JLabel("ITEM3");
		lblItem_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblItem_1.setBounds(241, 349, 105, 19);
		qFrame.getContentPane().add(lblItem_1);
		
		comboBoxItem3 = new JComboBox<Object>();
		comboBoxItem3.setModel(new DefaultComboBoxModel<Object>( items));
		comboBoxItem3.setBounds(241, 369, 130, 31);
		qFrame.getContentPane().add(comboBoxItem3);
		comboBoxItem3.setSelectedItem("N/A");
		
		JButton btnResultsDateRange = new JButton("RESULTS");
		btnResultsDateRange.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnResultsDateRange.setBounds(400, 81, 125, 29);
		qFrame.getContentPane().add(btnResultsDateRange);
		
		JLabel lblDateRange = new JLabel("DATE QUERIES");
		lblDateRange.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDateRange.setBounds(154, 40, 181, 14);
		qFrame.getContentPane().add(lblDateRange);
		
		JLabel lblBeginningDate = new JLabel("BEGINNING DATE");
		lblBeginningDate.setBounds(10, 65, 143, 14);
		qFrame.getContentPane().add(lblBeginningDate);
		
		JLabel lblEndingDate = new JLabel("ENDING DATE");
		lblEndingDate.setBounds(241, 65, 125, 14);
		qFrame.getContentPane().add(lblEndingDate);
		
		dateChooserB = new JDateChooser();
		dateChooserB.setDateFormatString("yyyy-MM-dd");
		dateChooserB.setBounds(10, 81, 130, 31);
		qFrame.getContentPane().add(dateChooserB);
		
		dateChooserE = new JDateChooser();
		dateChooserE.setDateFormatString("yyyy-MM-dd");
		dateChooserE.setBounds(241, 81, 130, 31);
		qFrame.getContentPane().add(dateChooserE);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(10, 183, 500, 1);
		qFrame.getContentPane().add(horizontalStrut);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 182, 414, 2);
		qFrame.getContentPane().add(separator);
		
		chckbxHomeImprovementDates = new JCheckBox("HOME IMPROVEMENTS");
		chckbxHomeImprovementDates.setBounds(10, 130, 212, 46);
		chckbxHomeImprovementDates.setSelected(false);
		qFrame.getContentPane().add(chckbxHomeImprovementDates);
		
		chckBoxHomeImprovementItems = new JCheckBox("HOME IMPROVEMENTS");
		chckBoxHomeImprovementItems.setBounds(10, 361, 212, 46);
		chckBoxHomeImprovementItems.setSelected(false);
		qFrame.getContentPane().add(chckBoxHomeImprovementItems);
		
		JButton btnResultsItems = new JButton("RESULTS");
		btnResultsItems.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnResultsItems.setBounds(400, 307, 125, 29);
		qFrame.getContentPane().add(btnResultsItems);
			
		JLabel lblItemQueries = new JLabel("ITEM QUERIES");
		lblItemQueries.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblItemQueries.setBounds(154, 195, 149, 14);
		qFrame.getContentPane().add(lblItemQueries);
		
		JLabel lblCost = new JLabel("COST");
		lblCost.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCost.setBounds(10, 291, 105, 14);
		qFrame.getContentPane().add(lblCost);
		
		textFieldCost = new JTextField();
		textFieldCost.setText("100");
		textFieldCost.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCost.setBounds(10, 307, 130, 30);
		qFrame.getContentPane().add(textFieldCost);
		textFieldCost.setColumns(10);
		
		
		//limits textfield to numeric characters Only
		textFieldCost.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        e.consume();
		      }
		    }
		  });
		
		
		JLabel lblClickForQuery = new JLabel("Click For Query Results");
		lblClickForQuery.setBounds(400, 340, 150, 14);
		qFrame.getContentPane().add(lblClickForQuery);
		
		JLabel label = new JLabel("Click For Query Results");
		label.setBounds(400, 109, 150, 14);
		qFrame.getContentPane().add(label);
		
		JLabel lblMaximumCostOf = new JLabel("Maximum Cost");
		lblMaximumCostOf.setBounds(10, 340, 105, 14);
		qFrame.getContentPane().add(lblMaximumCostOf);
		

		btnResultsDateRange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	 
					getDateRangeFromDatabase();// pulls data from a MYSQL database
				}
			});
		
		btnResultsItems.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doItemsQueryFromDatabase();
				}
			});	
	}//end initialize


	
///////////////////////////METHODS USED TO RETRIEVE DATA FROM DATABASE---->BEGIN///////////////////////////////////////////		
	
	
/*********************************************************************************
	 * getDateRangeFromDatabase displays a table of HomeData objects within a specific date range 
	 * @pre none
	 * @parameter none
	 * @post       table representation of query result with a specific date range 
**********************************************************************************/
	private void getDateRangeFromDatabase(){

		String pattern = "yyyy-MM-dd";
		DateFormat formatter = new SimpleDateFormat(pattern);

		Date bDate = dateChooserB.getDate();
		String dateStringB = (formatter.format(bDate));
		Date eDate = dateChooserE.getDate();
		String dateStringE = (formatter.format(eDate));

		String result = "SELECT * FROM " + HOMEIMPROVEMENT_DATABASE + " WHERE  DATE >= '" + dateStringB + "' AND DATE <= '" + dateStringE + "'" ;
		myDatabase.getDateRangeResults(result);

		List<HomeData> dateRangeList = myDatabase.getList();
		Collections.sort(dateRangeList, new SortHomeDataInDescendingOrderByDate());

		String title = "Date Range Queries. Total Cost = $" + Double.toString(computeTotalCost(dateRangeList));
		new QueryResultsTable(dateRangeList, title, username);

		myDatabase.clearList();

	}	
	
/*********************************************************************************
	 * doItemsQueryFromDatabase() displays a table of HomeData objects using AREA and ITEMS selections 
	 * @pre none
	 * @parameter none
	 * @post       table representation of query result with AREA and ITEMS parameters 
**********************************************************************************/	
	private void doItemsQueryFromDatabase() {
	
		String result= "SELECT * FROM " + HOMEIMPROVEMENT_DATABASE + " WHERE ITEMS IN ( '" 
				+ comboBoxItem1.getSelectedItem().toString() + "' ,'"
				+ comboBoxItem2.getSelectedItem().toString() + "' ,'"
				+ comboBoxItem3.getSelectedItem().toString() + "' ) AND AREA = '"
				+ comboBoxArea.getSelectedItem().toString() + "';";
        System.out.println(result);
		myDatabase.getQuery(result); 
		List<HomeData> myList = myDatabase.getList();
		if(myList.size() == 0) {
			JOptionPane.showMessageDialog(null,"Your query returned 0 results"); 
		}else {
			String title = "Item Queries. Total Cost = $" +  Double.toString(computeTotalCost(myList)); 
			new QueryResultsTable(myList,title, username); 
		}

		myDatabase.clearList();

	}
	
	

///////////////////METHODS USED TO RETRIEVE DATA FROM DATABASE--------->END///////////////////////////////////////////		
	

	
	
	
	
	
	
///////////////////////////////////////////// OLD CODE NOT USED	
	
	
	/////////////////////////////////////////////////////////METHODS USED TO RETRIEVE DATA FROM FILE///////////////////////////////////////////	
	/*********************************************************************************
	 * doDateQuerying() creates a list of the HomeData objects within a specific date range using data from a file
	 * @pre none
	 * @parameter int: bDate Integer representation of the beginning date parameter
	 * 			  int: eDate Integer representation of the ending date parameter
	 * 			  String[] items: the query  parameters of items in the room
	 *            Boolean isSelected: parameter that determines if all items or only items that add value to the house
	 *                                will be included in the query result
	 * @post       table representation of query result with a specific date range 
**********************************************************************************/
	private void doDateQuerying() {
	   int dateSelect = 1;
	   String pattern = "yyyy-MM-dd";
	   DateFormat formatter = new SimpleDateFormat(pattern);
	   
	   Date bDate = dateChooserB.getDate();
	   String dateStringB = (formatter.format(bDate));
	   Date eDate = dateChooserE.getDate();
	   String dateStringE = (formatter.format(eDate));
	   
	 
	   List<HomeData> dateRangeList = getDateRange(HomeMainGui.convertDateStringToInt(dateStringB, dateSelect), 
                HomeMainGui.convertDateStringToInt(dateStringE, dateSelect), chckbxHomeImprovementDates.isSelected());
	   String title = "Date Range Queries. Total Cost = $" + Double.toString(computeTotalCost(dateRangeList));
	   new QueryResultsTable(dateRangeList, title, username);
	
	}
	
	

	/*********************************************************************************
	 * getDateRange creates a list of the HomeData objects within a specific date range using data from a file
	 * @pre none
	 * @parameter int: bDate Integer representation of the beginning date parameter
	 * 			  int: eDate Integer representation of the ending date parameter
	 * 			  String[] items: the query  parameters of items in the room
	 *            Boolean isSelected: parameter that determines if all items or only items that add value to the house
	 *                                will be included in the query result
	 * @post      List of HomeData objects containing query results
**********************************************************************************/	
	public static  List<HomeData> getDateRange(int bDate, int eDate, Boolean isSelected){
		
		int dateSelect = 1;// integer value of date
		List<HomeData> myList =  HomeMainGui.getDataFromFile();

		Iterator<HomeData> itr = myList.iterator();//filters all class objects within
		//specified date range 
		while(itr.hasNext()) { int date =HomeMainGui.convertDateStringToInt(itr.next().getDate(), dateSelect); 
		if(date < bDate || date > eDate ){ 
			itr.remove(); }
		}

		itr = myList.iterator(); 
		if(isSelected) {//filters all class objects for
			//repairs that increases home value 
			while(itr.hasNext()) {
				boolean homeImprovementSelected = itr.next().getIsValue();

				if(!homeImprovementSelected ) { 
					itr.remove(); 
				} 
			} 
		}

		return myList;
	}
	

	 /*********************************************************************************
	 * doItemsQuery() displays a table of the HomeData objects using several items as query parameters
	 * @pre none
	 * @parameter none
	 * @post     Table representation of query results
**********************************************************************************/
	private void doItemsQuery() {
		List<HomeData> myList =  new ArrayList<>();
		String area = comboBoxArea.getSelectedItem().toString();
		String [] items = {comboBoxItem1.getSelectedItem().toString(), comboBoxItem2.getSelectedItem().toString(), comboBoxItem3.getSelectedItem().toString()};
		
		myList = getQueryItemsList(area, items, chckBoxHomeImprovementItems.isSelected());
		if(myList.size() == 0) {
	  		 JOptionPane.showMessageDialog(null,"Your query returned 0 results");
		}else {
			String title = "Item Queries. Total Cost = $" + Double.toString(computeTotalCost(myList));
			new QueryResultsTable(myList, title, username);	
		}
	}
	
	
	/*********************************************************************************
	 * getQueryItemsList creates a list of the HomeData objects from the query parameters
	 * @pre none
	 * @parameter String: house's room parameter
	 * 			  String[] items: the query  parameters of items in the room
	 *            Boolean isSelected: parameter that determines if all items or only items that add value to the house
	 *                                will be included in the query result
	 * @post  	  List of HomeData objects containing query results
**********************************************************************************/
	private List<HomeData> getQueryItemsList(String area, String[] items, Boolean isSelected){
		//String inputFile = "cost.csv";
		List<HomeData> myList = HomeMainGui.getDataFromFile();
		String notApplicable = "N/A";
		Iterator<HomeData> itr = myList.iterator();//filters for home area
		
		while(itr.hasNext()) { //retrieves the area item data
			String areaString = itr.next().getArea();
			if(!(areaString.equals(area))) {
				itr.remove();
			}	
		}
		
		if(items[0].equals(notApplicable) && items[1].equals(notApplicable) && items[2].equals(notApplicable)) {			
			getQueryMaxCostLimit(myList, isSelected);
			return myList;
		}
		
		
		itr = myList.iterator();//filters all class objects for items selected
		
		if(items[0].equals(notApplicable)) {
			while(itr.hasNext()) {
				String item1String = itr.next().getItem();
				if( !(item1String.equals(items[1])) && 	!(item1String.equals(items[2])) ) {	
					itr.remove();
				}
			}			
		}else if(items[1].equals(notApplicable)) {			
			while(itr.hasNext()) {
				String item1String = itr.next().getItem();
				if( !(item1String.equals(items[0])) && 	!(item1String.equals(items[2])) ) {	
					itr.remove();
				}
			}
		}else if (items[2].equals(notApplicable)) {
			while(itr.hasNext()) {
				String item1String = itr.next().getItem();
				if( !(item1String.equals(items[0])) && 	!(item1String.equals(items[1])) ) {	
					itr.remove();
				}
			}	
		} 
		else {
			
			while(itr.hasNext()) {
				String item1String = itr.next().getItem();
				if( !(item1String.equals(items[0])) && 	!(item1String.equals(items[1]))	&&!(item1String.equals(items[2])) ) {	
					itr.remove();
				}
			}
		}
		
		getQueryMaxCostLimit(myList, isSelected);
	    return myList;
	}
	
	
	
	public void getQueryMaxCostLimit(List<HomeData> myList, Boolean isSelected) {
		
		
		int cost =Integer.parseInt(textFieldCost.getText());
		
		Iterator<HomeData> itr = myList.iterator();//filters for home area
		itr = myList.iterator();//filters all class objects for repairs that are below the cost value in the cost field
		while(itr.hasNext()) {
			int value = (int) Math.round(itr.next().getCost());
			if(value > cost ) {
				itr.remove();
				}
		}
		

		itr = myList.iterator();//filters all class objects for repairs that increases home value
		if(isSelected) {
			while(itr.hasNext()) {
				boolean homeImprovementSelectedItems  = itr.next().getIsValue();
				if(!homeImprovementSelectedItems ) {
					itr.remove();
				}
			}
		}
	}
	
	
	/*********************************************************************************
	 * computeTotalCost computes the total expenditures of the query result 
	 * @pre none
	 * @parameter List<HomeData>: list
	 * @post  double: total cost off query list
**********************************************************************************/
    public static double computeTotalCost(List<HomeData> list) {
    	
    	double count = 0.;
    	for (HomeData myList : list) {
    		count+= myList.getCost();
    	}
    	
    	return Math.round(count * 100)/100.;//rounds to 2 decimal places
    }
	
	
	

    
    
    
    
    
    
    
    
    
/*********************************************************************************
	 *  placeDataInTable takes list of HomeData objects from the query result and places the data in a table
	 * @pre none
	 * @parameter List<HomeData> list
	 * @post  table containing HomeData query results
**********************************************************************************/
/*
    private void placeDataInTable(List<HomeData> list, String title) {
		
		new QueryResultsTable(list, title, username);
		
		//DefaultTableModel model = new DefaultTableModel();
		myQueryTable = new MyQueryTable(list);//create Table
	    JScrollPane mScrollPane= new JScrollPane(myQueryTable.getTable());//place table in scroll pane
	    JPanel panel = new JPanel();//panel object
	    panel.setLayout(new BorderLayout());
	    panel.add(mScrollPane, BorderLayout.CENTER);
	    
	  
	      
	      //place table in frame
	    JFrame myFrame = new JFrame();
	    myFrame.setResizable(true);
	    myFrame.setBounds(100, 400, 800, 400);
		myFrame.setTitle(title);
		myFrame.getContentPane().setLayout(new BorderLayout());
			//myFrame.setSize(800,400);
		myFrame.getContentPane().add(panel);
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setVisible(true);

	}
*/

	
}
