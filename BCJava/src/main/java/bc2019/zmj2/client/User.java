package bc2019.zmj2.client;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

public class User {
	private String name, major;
	private List<StoredCourse> taken, planned;
	
	public User() {
		name = null;
		major = null;
		taken = null;
		planned = null;
	}
	
	public User(String name, String major) {
		this.name = name;
		this.major = major;
		this.taken = new ArrayList<StoredCourse>();
		this.planned = new ArrayList<StoredCourse>();
	}
	
	public User(String name, String major, List<StoredCourse> taken, List<StoredCourse> planned) {
		this.name = name;
		this.major = major;
		this.taken = taken;
		this.planned = planned;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public List<StoredCourse> getTaken() {
		return taken;
	}

	public void setTaken(List<StoredCourse> taken) {
		this.taken = taken;
	}

	public List<StoredCourse> getPlanned() {
		return planned;
	}

	public void setPlanned(List<StoredCourse> planned) {
		this.planned = planned;
	}

	//Just for checking if they've taken a course at any point in time
	public boolean hasTaken(String name) {
		for(StoredCourse tc: this.taken) {
			if(tc.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	//Just for checking if they've taken a course at any point in time and achieved a specific minimum grade
		public boolean hasTaken(String name, Grade g) {
			for(StoredCourse tc: this.taken) {
				if(tc.getName().equals(name) && tc.getGrade().ordinal() >= g.ordinal()) {
					return true;
				}
			}
			return false;
		}
	
	//For checking if they've taken a course during a specific semester
	public boolean hasTaken(String name, int year, Season season) {
		for(StoredCourse tc: this.taken) {
			if(tc.getName().equals(name) && tc.getYear() == year && tc.getSeason().equals(season)) {
				return true;
			}
		}
		return false;
	}
	
	//Adds a course to the User's taken list if it does not already exist
	public void addTakenCourse(String name, Grade grade, int year, Season season) {
		if(!hasTaken(name, year, season)) {
			this.taken.add(new StoredCourse(name, grade, year, season));
		}
	}

	//Returns combined credits of taken and planned courses, used for checking if the student meets the 120 credit minimum
	public int getTotalCredits() {
		int total = 0;
		for(StoredCourse sc: this.planned) {
			total += sc.getCredits();
		}
		for(StoredCourse sc: this.taken) {
			total += sc.getCredits();
		}
		return total;
	}

	public static AuthUser getUser(String email, JsonObject response) {
		// TODO Auto-generated method stub
		return null;
	}

}
