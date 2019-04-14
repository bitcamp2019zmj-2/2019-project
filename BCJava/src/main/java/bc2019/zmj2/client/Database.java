package bc2019.zmj2.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.gson.JsonObject;

import bc2019.zmj2.util.Util;

public class Database {
	private static Map<String,Major> majors;
	private static HashMap<String, Course> courses;
	
	public static void getFromFirebase() {
		//TODO: Retrieve and parse data on FireBase
		retrieveMajorsFromDB();
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
					q = q.replace('*', ','); //Patchy way to deal with CSV delimiter
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
			
			String preqdata = data[7]; //Prerequisites
			preqdata = preqdata.replaceAll("[;\\.]", ""); //Remove random chars
			Map<Requirable, Grade> preqs = new HashMap<>();
			Pattern gPattern = Pattern.compile("\\((.*?)\\)"); //Get groups
			Matcher gMatch = gPattern.matcher(preqdata);
			List<String> groups = new ArrayList<>();
			while (gMatch.find()) { //Grab all of the groups
				groups.add(gMatch.group(1));
			}
			for (String g : groups) {
				Group newGroup = new Group(cCourse.getName()+g);
				newGroup.setMin(1);
				newGroup.setMax(999);
				String[] gReq = g.split(" ");
				for (String grS : gReq)
					newGroup.addReq(courses.get(grS)); //Set up all the groups
				preqs.put(newGroup, Grade.CM);
			}
			
			preqdata = preqdata.replace(" AND", ""); //Get the rest of the prereqs
			preqdata = preqdata.replaceAll("\\(.*?\\)", "");
			String[] andPreq = preqdata.split(" ");
			for (String a : andPreq)
				preqs.put(courses.get(a),Grade.CM);
			cCourse.setPrereqs(preqs);
			
			String[] corq = data[8].split(" "); //Corequisites
			List<Course> corqCourses = new ArrayList<>();
			for (String c : corq) {
				corqCourses.add(courses.get(c));
			}
			cCourse.setCoreqs(corqCourses);
			
			//Restrictions
			//Not even going to try to generalize this now
			String rest = data[9];
			List<Requirable> rList = new ArrayList<>();
			if (rest.equals("NOCLASS[CMSC] AND NOMAJ[CMSC]")) {
				rList.add(new Requirable() {
					@Override
					public boolean reqMet(User u) {
						boolean tookCMSC = false;
						for (StoredCourse c : u.getTaken()) {
							tookCMSC = tookCMSC || c.getName().contains("CMSC");
						}
						return tookCMSC && !u.getMajor().contains("CMSC");
					}
					@Override
					public boolean reqMet(User u, Grade g) {
						return reqMet(u);
					}
				});
			} else if (rest.equals("NOCLASS[CMSC]")) {
				rList.add(new Requirable() {
					@Override
					public boolean reqMet(User u) {
						boolean tookCMSC = false;
						for (StoredCourse c : u.getTaken()) {
							tookCMSC = tookCMSC || c.getName().contains("CMSC");
						}
						return tookCMSC;
					}
					@Override
					public boolean reqMet(User u, Grade g) {
						return reqMet(u);
					}
				});
			} else if (rest.equals("MAJ[CMSC] MAJ[CMPE] MIN[CMSC]")) {
				rList.add(new Requirable() {
					public boolean reqMet(User u) {
						return u.getMajor().contains("CMSC") ||
							   u.getMajor().contains("CMPE");
					}
					public boolean reqMet(User u, Grade g) {
						return reqMet(u);
					}
				});
			}
			
			
			String[] alts = data[10].split(" "); //Alternates
			List<Course> altNames = new ArrayList<>();
			for (String a : alts) {
				altNames.add(courses.get(a));
			}
			cCourse.setAlternates(altNames);
			
			cCourse.setGened(data[11]);
		}
	}
	
	//temp
	public static void addCourse(String name, Course c) {
		if(courses == null) {
			courses = new HashMap<String,Course>();
		}
		courses.put(name, c);
	}
	

	public static Course getCourse(String name) {
		return courses.get(name);
	}
	
	private static void retrieveMajorsFromDB() {
		List<QueryDocumentSnapshot> x = Util.retrieveAll("majors");
		for(QueryDocumentSnapshot sh : x) {
			Major m = sh.toObject(Major.class);
			majors.put(m.getName(), m);
		}
	}

	public static AuthUser getUser(String user, JsonObject response) {
		// TODO Auto-generated method stub
		return null;
	}
}





