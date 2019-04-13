package bc2019.zmj2.client;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	private static ArrayList<Major> majors;
	private static HashMap<String, Course> courses;
	
	public static void getFromFirebase() {
		//TODO: Retrieve and parse data on FireBase
	}

	public static void getFromCSV() {
		//TODO: Parse data pulled from CSV file, necessary only if web-scraping script has to be run manually
	}

	public static Course getCourse(String name) {
		return courses.get(name);
	}
}





