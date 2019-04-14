package bc2019.zmj2;

import bc2019.zmj2.client.Database;

public class CSVDriver {

	public static void main(String[] args) {
		Database.getFromCSV("CMSC");
		System.out.println(Database.getCourse("CMSC250").getDescription());
	}

}
