package bc2019.zmj2.client;
import java.util.ArrayList;

public class Major {
	private String name;
	private ArrayList<Requirable> reqs;
	
	public Major(String name) {
		this.name = name;
		this.reqs = new ArrayList<Requirable>();
	}
	
	public Major(String name, ArrayList<Requirable> reqs) {
		this.name = name;
		this.reqs = reqs;
	}
	
	//Gets an array of all unmet requirements for a student in this major
	public ArrayList<Requirable> getUncompletedReqs(User u){
		ArrayList<Requirable> output = new ArrayList<Requirable>();
		for(Requirable r : this.reqs) {
			if(!r.reqMet(u)) {
				output.add(r);
			}
		}
		return output;
	}
	
	//Checks if a users previously taken/currently planned meets the requirements for this major
	public boolean meetsReqs(User u) {
		ArrayList<StoredCourse> allCourses = u.getPlanned();
		allCourses.addAll(u.getTaken());
		
		//Fix later to make this less hacky
		//Right now, it creates a new user with the old users taken+planned courses listed as taken
		User u2 = new User(u.getName(), u.getMajor());
		u2.setTaken(allCourses);
		
		for(Requirable r: this.reqs) {
			if(!r.reqMet(u2)) {
				return false;
			}
		}
		return true;
	}
}
