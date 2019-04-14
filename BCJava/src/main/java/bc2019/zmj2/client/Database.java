package bc2019.zmj2.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonElement;

import bc2019.zmj2.util.Util;

public class Database {
	private static ArrayList<Major> majors;
	private static HashMap<String, Course> courses;
	
	public static void getFromFirebase() {
		//TODO: Retrieve and parse data on FireBase
		
	}

	public static void getFromCSV(String dept) {
		//TODO: Parse data pulled from CSV file, necessary only if web-scraping script has to be run manually
		File f = new File("classes-"+dept+".csv");
		//Scanner s = null;
		try (Scanner s = new Scanner(f)) {
			while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] rawData = line.split(",");
			for (String q : rawData) {
				q = q.replace('§', ','); //Patchy way to deal with CSV delimiter
			}
			}
		} catch (Exception e) {
			
		}
	}

	public static Course getCourse(String name) {
		return courses.get(name);
	}
	
	private static void retrieveMajorFromDB(String name) {
		JsonElement majs = Util.retrieveAll("majors");
		//majs is map
		//majs.get(key) = x is map
		//x has field string name
		//x has field array requirements (reference)
	}
}





