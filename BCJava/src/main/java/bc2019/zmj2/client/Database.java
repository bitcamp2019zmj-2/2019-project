package bc2019.zmj2.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
		List<String[]> fullData = new ArrayList<String[]>();
		try (Scanner s = new Scanner(f)) {
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] rawData = line.split(",");
				for (String q : rawData) {
					q = q.replace('§', ','); //Patchy way to deal with CSV delimiter
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
			cCourse.setGradeMethods(Arrays.asList(data[5]));
			cCourse.setDescription(data[6]);
			
			//TODO: PREQ
			String preqdata = data[7];
			//preqdata = preqdata.replaceAll("([A-Z].) in ("+dept+"[0-9]{3}[A-Z]?)", "\\[$2\\]$1");
			//preqdata = preqdata.replace("Minimum grade of ", "");
			
			String[] corq = data[8].split(" "); //Corequisites
			List<Course> corqCourses = new ArrayList<>();
			for (String c : corq) {
				corqCourses.add(courses.get(c));
			}
			cCourse.setCoreqs(corqCourses);
			
			//TODO: REST
			String[] alts = data[10].split(" "); //Alternates
			List<Course> altNames = new ArrayList<>();
			for (String a : alts) {
				altNames.add(courses.get(a));
			}
			cCourse.setAlternates(altNames);
			
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
}





