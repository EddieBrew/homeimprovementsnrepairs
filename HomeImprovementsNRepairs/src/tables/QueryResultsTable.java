package tables;


/*********************************************************************************
 * QueryResultsTable displays the results of the user defined query parameter. The class
 * allows the user to remove incorrect data from  cost.csv file 
 * @Author: Robert E Brewer
 * @Date: 8/14/2020
 * @Version 1
 * @copyright@2020
**********************************************************************************/



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import main_screen.HomeData;
import main_screen.HomeMainGui;

public class QueryResultsTable extends JFrame{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btnRemove, btnModify;
	private DefaultTableModel model;
	private Object[][] data;
	private JFrame frame;
	private String oldValue;//holds the previous cell value
	//private final String MYCLASS = "HomeImprovement";
	//private final String filename = "cost.csv";
	
	public  QueryResultsTable(List<HomeData> myList, String title, String username){
	
		String[] columnNames = new String[] {
		        "DATE", "AREA", "ITEMS", "COST", "FILENAME", "INFO", "VALUE+" 
		    };
		
		 int rows = myList.size(); 
	     final int COLS = 7;
	     data = new Object[rows][COLS];
		
	     for( int i = 0; i < rows; i++ ) {
	    	 for(int j = 0;j < COLS; j++ ) {
	    		 
	    		switch(j) {
	    		  
	    		    case 0:  data[i][j] = myList.get(i).getDate();
		    		break;
		    		case 1:  data[i][j] = myList.get(i).getArea();
	    			break;
		    		case 2:  data[i][j] = myList.get(i).getItem();
	    			break;
		    		case 3:  data[i][j] = myList.get(i).getCost();
	    			break;
		    		case 4:  data[i][j] = myList.get(i).getReceiptFilename();
	    			break;
		    		case 5:  data[i][j] = myList.get(i).getInfo();
					break;
		    		case 6:  data[i][j] = myList.get(i).getIsValue();
					break;
		    		default:
	    		}//end switch
	    	 }
	     }
	     
	     model = new DefaultTableModel(data, columnNames);
	     table = new JTable(model);
	     table.getColumnModel().getColumn(0).setPreferredWidth(150);//date
		    table.getColumnModel().getColumn(1).setPreferredWidth(200);//area
		    table.getColumnModel().getColumn(2).setPreferredWidth(200);//items
		    table.getColumnModel().getColumn(3).setPreferredWidth(80);//cost
		    table.getColumnModel().getColumn(4).setPreferredWidth(200);//filename
		    table.getColumnModel().getColumn(5).setPreferredWidth(500);//info
		    table.getColumnModel().getColumn(6).setPreferredWidth(80);	//isValue	
		    table.setAutoCreateRowSorter(true);//allows the table  to be sorted by rows
		   table.setGridColor(Color.YELLOW);
		   table.setBackground(Color.CYAN);
	     table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	     
	     
	     
	     //MouseListener used to obtain and store previous cell entry before updating
	     table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//String oldValue;
				JTable source = (JTable)e.getSource();
				int row = source.rowAtPoint( e.getPoint() );
		        int column = source.columnAtPoint( e.getPoint() );
		        oldValue = source.getValueAt(row, column).toString();
		        btnRemove.setEnabled(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	     });
	     
	     table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				String fieldNames[] = {"Date", "area", "item", "cost", "filename", "info", "isValue"};
				 String selectedData = null;
				 String queryField = null;
				 int[] selectedRow = table.getSelectedRows();
				    int[] selectedColumns = table.getSelectedColumns();
				  
				   String  data = (String) table.getValueAt(table.getSelectedRow(),table.getSelectedColumn() );
				   System.out.println("Data = " +data);
				    
				    
				    //retrieves data to  be corrected and used to query Parse
				    for (int i = 0; i < selectedRow.length; i++) {
					      for (int j = 0; j < selectedColumns.length; j++) {
					    	  selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);

					    	  queryField = fieldNames[selectedColumns[j]];
					    	  System.out.println(selectedData + ", " + queryField);
					      }
				   }
			}
	    	 
	     });
	     

	     JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	     scrollPane.setPreferredSize(new Dimension(600,300));
	      
	     JPanel tablePanel = new JPanel();
	     tablePanel.setLayout(new BorderLayout());
	     tablePanel.add(scrollPane, BorderLayout.CENTER);
	     tablePanel.setPreferredSize(new Dimension(800, 300));
	     
	     btnModify = new JButton("MODIFY");
	     btnModify.setEnabled(false);
		 btnModify.setBackground(Color.GREEN); 
		 btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final String COMMA = ",";
				final int COLUMN_LENGTH = 7;
				String fieldNames[] = {"Date", "area", "item", "cost", "filename", "info", "isValue"};
				 StringBuilder selectedData = new StringBuilder();
				 String queryField = null;
				 int[] selectedRow = table.getSelectedRows();
				    int[] selectedColumns = table.getSelectedColumns();
				    
				    //retrieves data to  be corrected and used to query Parse
				    
				    
				    for (int i = 0; i < selectedRow.length; i++) {
					      for (int j = 0; j < selectedColumns.length; j++) {
					    	  //Columns in the table matches the indexes in the fieldName array
					    	  
					    	  queryField = fieldNames[selectedColumns[j]];
					    	  System.out.println("queryField = " + queryField);
					      }
					    }
					 
				    
				    //retrieve row data
				    for (int i = 0; i < selectedRow.length; i++) {
				      for (int j = 0; j < COLUMN_LENGTH; j++) {
				     
				    	  if(j == 6) {
				    		  selectedData.append( table.getValueAt(selectedRow[i], j));
				    	  }
				    	  else {
				    		  selectedData.append( table.getValueAt(selectedRow[i], j) + COMMA); 
				    	  }       
				      }
				      
				      //MyNetworkConnection connection =  new MyNetworkConnection(HomeMainGui.credentialsFilename, "rbrewer", "luistam1959");
				      
			    	   if(queryField.equalsIgnoreCase("cost")) {
				    	  //HomeMainGui.networkConnection.changeDataInParseServer(new HomeData(selectedData.toString()), queryField, Double.parseDouble(oldValue));
				      }else if (queryField.equalsIgnoreCase("isValue")) {
				    	  //HomeMainGui.networkConnection.changeDataInParseServer(new HomeData(selectedData.toString()), queryField, Boolean.parseBoolean(oldValue));
			    	  }else {
			    		  //HomeMainGui.networkConnection.changeDataInParseServer(new HomeData(selectedData.toString()), queryField, oldValue);
			    	  }
				    }
			}
			 
		 });
	     
	     
	     btnRemove = new JButton("REMOVE");
	     btnRemove.setBackground(Color.RED); 
	     btnRemove.setEnabled(false);
	     btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final String COMMA = ",";
				System.out.println(table.getSelectedRow());
				if(table.getSelectedRow() != -1) {
		               // remove selected row from the model 
					 JFrame frameLoginSystem = new JFrame("Exit");
					 
					 
						if(JOptionPane.showConfirmDialog(frameLoginSystem, "Confirmif you want to delete the row", "Delete Row?",
								JOptionPane.YES_NO_OPTION) ==JOptionPane.YES_NO_OPTION) {
							   	   
							StringBuilder myString = new StringBuilder();
							for( int i = 0; i < COLS; i++) {
								myString.append(table.getValueAt(table.getSelectedRow(), i) + COMMA);
							}
							model.removeRow(table.getSelectedRow());
							System.out.println(myString.toString());
							//deleteRowItemFromServer(new HomeData(myString.toString()));
							if(deleteRowItemFromFile(new HomeData(myString.toString()))) {
								 JOptionPane.showMessageDialog(null, myString.toString() + "\n Row Info Deleted.");
							}else {
								 JOptionPane.showMessageDialog(null, "Selected row was not deleted");
							}
						} 
		            }else {
		            	System.out.println(ERROR);
		            }
			} 
	     });
	     
	     
	     JPanel buttonPanel = new JPanel();
	        buttonPanel.setBackground(Color.CYAN);
	        BorderLayout buttonBorderlayout = new BorderLayout();
	        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        buttonPanel.setLayout(buttonBorderlayout);
	        buttonPanel.add(btnRemove, BorderLayout.WEST );
	        buttonPanel.add(btnModify, BorderLayout.EAST);
	     
	        frame = new JFrame();
	        frame.setTitle(title);
	        frame.setSize(1000, 400);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLocationRelativeTo(null);
	        frame.getContentPane().add(tablePanel, BorderLayout.NORTH);
	        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);     
	        frame.pack();
	        frame.setResizable(false);
	        frame.setVisible(true);
	     
	        
	        /*
	     add(new JScrollPane(table), BorderLayout.CENTER);
	      add(btnRemove, BorderLayout.SOUTH);
	      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      setSize(800, 300);
	      setLocationRelativeTo(null);
	      setVisible(true);
	*/
	}
	
	private final static String inputFile = "cost.csv";
	
	private Boolean deleteRowItemFromFile(HomeData item){
		System.out.println("Entering deleteRowItemFromFile ");
		Boolean isItemFound = false;
		List<HomeData> list = HomeMainGui.getDataFromFile(); 
		System.out.println(item.toString());
		int count = 0;
		while (!isItemFound) {
			
			for (int i = 0; i < list.size(); i++) {
				if((item.getDate().equals(list.get(i).getDate())) && (item.getArea().equals(list.get(i).getArea())) && (item.getItem().equals(list.get(i).getItem()))) {
					System.out.println(list.get(i).toString());
					list.remove(i);
					isItemFound = true;
					
					break;
				}
				count++;
				//System.out.println(isItemFound);
			}
		}//end while
		
		System.out.println(count);
		if(isItemFound) {
			HomeMainGui.replaceDataInFile(list, inputFile);
		}
		
		
		return isItemFound;
	}
	
	/*
	
	private void deleteRowItemFromServer(HomeData item) {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(MYCLASS);
		query.whereEqualTo("Date", item.getDate());
		query.whereEqualTo("area", item.getArea());
		query.whereEqualTo("item", item.getItem());
		
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> object, ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// iterate over all messages and delete them
					for (ParseObject record : object) {
						record.deleteInBackground();
						//HomeMainGui.retrieveFromParseServer("cost.csv");
						//myArray.remove(index);
					}

				} else {
			
				}
			}
		});	
	}
	
	*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
