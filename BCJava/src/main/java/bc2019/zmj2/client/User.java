package bc2019.zmj2.client;
import java.util.ArrayList;

public class User {
	private String name, major;
	private ArrayList<TakenCourse> taken;
	
	public User(String name, String major) {
		this.name = name;
		this.major = major;
		this.taken = new ArrayList<TakenCourse>();
		
	}
	
	//Just for checking if they've taken a course at any point in time
	public boolean hasTaken(String name) {
		for(TakenCourse tc: this.taken) {
			if(tc.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	//For checking if they've taken a course during a specific semester
	public boolean hasTaken(String name, int year, Season season) {
		for(TakenCourse tc: this.taken) {
			if(tc.getName().equals(name) && tc.getYear() == year && tc.getSeason() == season) {
				return true;
			}
		}
		return false;
	}
	
	//Adds a course to the User's taken list if it does not already exist
	public void addTakenCourse(String name, String grade, int year, Season season) {
		if(!hasTaken(name, year, season)) {
			this.taken.add(new TakenCourse(name, grade, year, season));
		}
	}
}
