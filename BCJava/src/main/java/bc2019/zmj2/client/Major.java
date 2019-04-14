package bc2019.zmj2.client;
import java.util.ArrayList;
import java.util.List;

import bc2019.zmj2.util.Util;

public class Major {
	private String name;
//	private List<Requirable> reqs;
	private List<CourseRef> courseReqs;
	private List<Group> groupReqs;
	
	public Major() {
		this.name = null;
		this.courseReqs = new ArrayList<CourseRef>();
		this.groupReqs = new ArrayList<Group>();
	}
	
	public Major(String name) {
		this.name = name;
//		this.reqs = new ArrayList<Requirable>();
		this.courseReqs = new ArrayList<CourseRef>();
		this.groupReqs = new ArrayList<Group>();
	}
	
	public Major(String name, List<CourseRef> courses, List<Group> groups) {
		this.name = name;
		this.courseReqs = courses;
		this.groupReqs = groups;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<CourseRef> getCourses() {
		return courseReqs;
	}
	
	public List<Group> getGroups() {
		return groupReqs;
	}
	
	
	//Gets an array of all unmet requirements for a student in this major
	public List<Requirable> getUncompletedReqs(User u){
		List<Requirable> output = new ArrayList<Requirable>();
		for(Group r : this.groupReqs) {
			if(!r.reqMet(u)) {
				output.add(r);
			}
		}
		for(CourseRef r : this.courseReqs) {
			if(!r.reqMet(u)) {
				output.add(r);
			}
		}
		return output;
	}
	
	private List<Requirable> getReqs() {
		List<Requirable> output = new ArrayList<Requirable>();
		for(Group r : this.groupReqs) {
			output.add(r);
		}
		for(CourseRef r : this.courseReqs) {
			output.add(r);
		}
		return output;
	}
	
	//Checks if a users previously taken/currently planned meets the requirements for this major
	public boolean meetsReqs(User u) {
		PlannedCourse[] pl = new PlannedCourse[u.getPlanned().size()];
		pl = u.getPlanned().toArray(pl);
		List<StoredCourse> allCourses = Util.toStoredCourse(Grade.CM, pl);
		allCourses.addAll(u.getTaken());
		
		//Fix later to make this less hacky
		//Right now, it creates a new user with the old users taken+planned courses listed as taken
		User u2 = new User(u.getName(), u.getMajor());
		u2.setTaken(allCourses);
		
		for(Requirable r: this.getReqs()) {
			if(!r.reqMet(u2)) {
				return false;
			}
		}
		return true;
	}
}
