package bc2019.zmj2.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
		List<String[]> fullData = new ArrayList<String[]>();
		try (Scanner s = new Scanner(f)) {
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] rawData = line.split(",");
				for (String q : rawData) {
					q = q.replace('�', ','); //Patchy way to deal with CSV delimiter
				}
				courses.put(rawData[0]+rawData[1]+rawData[2],
						new Course(rawData[0],new Integer(rawData[1]),rawData[2]));
			}
		} catch (NumberFormatException e) {
			System.err.println("CSV Datasheet "+dept+" corrupted, course number not valid");
		} catch (FileNotFoundException e1) {
			System.err.println("CSV Datasheet "+dept+" missing or misnamed");
		}
		//All courses are in system, but dependencies and other data not resolved
		for (String[] data : fullData) {
			Course cCourse = courses.get(data[0]+data[1]+data[2]);
			cCourse.setName(data[3]);
			cCourse.setCredits(new Integer(data[4]));
			
			//Grade Methods
			String[] gMeth = data[5].split(",");
			List<String> gMethConvert = new ArrayList<>();
			for (String g : gMeth)
				gMethConvert.add(g);
			cCourse.setGradeMethods(gMethConvert);
			
			cCourse.setDescription(data[6]);
			
			//PREQ, CORQ, REST, ALTS
			
			cCourse.setGened(data[11]);
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

	public static AuthUser getUser(String user, JsonObject response) {
		// TODO Auto-generated method stub
		return null;
	}
}





