package tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import main_screen.HomeData;

public class MyQueryTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static JTable table;
	public JTable getTable() {return table;}



	public MyQueryTable(List<HomeData> myList) {
		fillTable(myList);
	}

	public static void fillTable(List<HomeData> myList) {

		try {
			String[] columns = new String[] {
					"DATE", "AREA", "ITEMS", "COST", "FILENAME", "HOME VALUE+", "INFO"
			};


			int rows = myList.size(); 
			final int COLS = 7;
			Object[][] data = new Object[rows][COLS];
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
					case 5:  data[i][j] = myList.get(i).getIsValue();
					break;
					case 6:  data[i][j] = myList.get(i).getInfo();
					break;
					default:
					}//end switch
				}
			}


			//create table with data
			table = new JTable(data, columns);
			table.setEnabled(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(150);//date
			table.getColumnModel().getColumn(1).setPreferredWidth(200);//area
			table.getColumnModel().getColumn(2).setPreferredWidth(200);//items
			table.getColumnModel().getColumn(3).setPreferredWidth(100);//cost
			table.getColumnModel().getColumn(4).setPreferredWidth(200);//filename
			table.getColumnModel().getColumn(5).setPreferredWidth(200);//isValue
			table.getColumnModel().getColumn(6).setPreferredWidth(500);	//info		
			table.setAutoCreateRowSorter(true);//allows the table  to be sorted by rows
			table.setGridColor(Color.YELLOW);
			table.setBackground(Color.CYAN);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("OUT: " + e.toString());
			table = null;
		}


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyQueryTable myQueryTable;
		List<HomeData> myList = new ArrayList<>();
		HomeData myInfo;
		String title = "Testing Title";

		for( int i = 0; i < 10; i++) {
			myInfo = new HomeData("12/01/2018", "BEDRM", "TUB", 22.77,"cost.pdf", "All is forgiven" , true);

			myList.add(myInfo);
		}


		myQueryTable = new MyQueryTable(myList);//create Table
		JScrollPane mScrollPane= new JScrollPane(myQueryTable.getTable());//place table in scroll pane
		JPanel panel = new JPanel();//panel object
		panel.setLayout(new BorderLayout());
		panel.add(mScrollPane, BorderLayout.CENTER);


		//place table in frame
		JFrame myFrame = new JFrame();
		myFrame.setResizable(true);
		myFrame.setTitle(title);
		myFrame.getContentPane().setLayout(new BorderLayout());
		myFrame.setBounds(100, 400, 800, 400);
		//myFrame.setSize(800,400);
		myFrame.getContentPane().add(panel);
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setVisible(true);

	}

}
