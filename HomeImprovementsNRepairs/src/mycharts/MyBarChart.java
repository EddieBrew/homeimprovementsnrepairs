package mycharts;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import main_screen.HomeMainGui;

public class MyBarChart extends JFrame{
	
	private static final long serialVersionUID = 1L;  
	private double[][] mydata;
	//final int ROWS = 6;
	//final int COLS = 12;
	//String title;
	public MyBarChart(String appTitle, double[][] data) {  
		super(appTitle); 
		mydata = new double[HomeMainGui.ROWS][HomeMainGui.COLS];
		mydata= data;


		// Create Dataset  
		CategoryDataset dataset = createDataset();  

		//Create chart  
		JFreeChart chart=ChartFactory.createBarChart(  
				"5917 Marlin Monthly Expenses", //Chart Title  
				"Month", // Category axis  
				"Cost($) ", // Value axis  
				dataset,  
				PlotOrientation.VERTICAL,  
				true,true,false  
				);  


		//JFrame myFrame = new JFrame();
		setResizable(true);
		setBounds(10, 500, 500, 500);
		setTitle(appTitle);
		ChartPanel panel=new ChartPanel(chart);  
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

	}


	private CategoryDataset createDataset() {
		// TODO Auto-generated method stub

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
		String year = ""; 
		for(int i = 0; i < HomeMainGui.ROWS; i++) {
			if(i == 0) year = "2020";
			if(i == 1) year = "2021";
			if(i == 2) year = "2022";
			if(i == 3) year = "2023";
			if(i == 4) year = "2024";
			if(i == 5) year = "2025";
			for(int j = 0; j < HomeMainGui.COLS; j++) {
				switch(j) {
				case 0: dataset.addValue(mydata[i][j], year, "Jan");
				break;
				case 1: dataset.addValue(mydata[i][j], year, "Feb");
				break;
				case 2: dataset.addValue(mydata[i][j], year, "Mar");
				break;
				case 3: dataset.addValue(mydata[i][j], year, "Apr");
				break;
				case 4: dataset.addValue(mydata[i][j], year, "May");
				break;
				case 5: dataset.addValue(mydata[i][j], year, "Jun");
				break;
				case 6: dataset.addValue(mydata[i][j], year, "Jul");
				break;
				case 7: dataset.addValue(mydata[i][j], year, "Aug");
				break;
				case 8: dataset.addValue(mydata[i][j], year, "Sep");
				break;
				case 9: dataset.addValue(mydata[i][j], year, "Oct");
				break;
				case 10: dataset.addValue(mydata[i][j], year, "Nov");
				break;
				case 11: dataset.addValue(mydata[i][j], year, "Dec");
				break;
				default:
				}

			}
		}


		return dataset;  	

	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub


	}

}
