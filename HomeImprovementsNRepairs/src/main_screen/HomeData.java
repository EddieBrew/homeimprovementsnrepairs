package main_screen;

import java.util.Objects;

/*
Created by Robert Brewer on 3/5/2022

HomeData is a custom class that holds all input from the HomeMainGui home improvement items


*/
public class HomeData implements Comparable<HomeData>{

	private String date;
	private String area;
	private String item;
	private Double cost;
	private String receiptFilename;
	private String info;
	private Boolean isValue;

	public HomeData(String date, String area, String item, Double cost, String receiptFilename, String info, Boolean isValue) {
		this.date = date;
		this.area = area;
		this.item = item;
		this.cost = cost;
		this.receiptFilename = receiptFilename;
		this.info = info;
		this.isValue = isValue;

	}

	public HomeData(String input) {

		parseIntoVariable(input);
	}


	private void parseIntoVariable(String input){
		final String COMMA_DELIMITER = ",";
		String[] databaseInput = input.split(COMMA_DELIMITER);

		for (int i = 0; i < databaseInput.length; i++) {

			switch (i) {
			case 0:  
				this.date = databaseInput[i];
				break;
			case 1:
				this.area =databaseInput[i].trim();
				break;
			case 2:
				this.item = databaseInput[i].trim();
				break;
			case 3:
				this.cost = Double.parseDouble( databaseInput[i]);
				break;
			case 4:
				this.receiptFilename = databaseInput[i].trim();
				break;
			case 5:
				this.info = databaseInput[i].trim();
				break;
			case 6:
				this.isValue = Boolean.parseBoolean(databaseInput[i]);
				break;
			default:

			}
		}
	}

	public static int convertDateStringToInt(String date) {

		String delimStr = "-"; // date format is yyyy-MM-dd

		String[] words = date.split(delimStr);


		return (Integer.parseInt(words[1]) * 100) + (Integer.parseInt(words[2]))
				+ Integer.parseInt(words[0]) * 10000; 



	} // end method

	@Override
	public String toString() {
		return "HomeData [date=" + date + " \n area=" + area + " \n item=" + item + " \n cost=" + cost + "\n receiptFilename="
				+ receiptFilename + "\n info=" + info + "]";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}


	public String getReceiptFilename() {
		return receiptFilename;
	}

	public void setReceiptFilename(String receiptFilename) {
		this.receiptFilename = receiptFilename;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Boolean getIsValue() {
		return isValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, cost, date, info, isValue, item, receiptFilename);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HomeData other = (HomeData) obj;
		return Objects.equals(area, other.area) && Objects.equals(cost, other.cost) && Objects.equals(date, other.date)
				&& Objects.equals(info, other.info) && Objects.equals(isValue, other.isValue)
				&& Objects.equals(item, other.item) && Objects.equals(receiptFilename, other.receiptFilename);
	}



	@Override
	public int compareTo(HomeData obj) {
		// TODO Auto-generated method stub
		return (convertDateStringToInt(this.date) - convertDateStringToInt(obj.date));
	}



}
