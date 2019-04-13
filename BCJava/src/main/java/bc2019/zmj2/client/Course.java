package bc2019.zmj2.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Course implements Requirable {
	private String name;
	private HashMap<Requirable, Grade> prereqs;
	private ArrayList<Requirable> coreqs;
	private int credits;
	
	
	public Course(String name, int credits) {
		this.name = name;
		this.prereqs = new HashMap<Requirable, Grade>();
		this.credits = credits;
	}
	
	
	@Override
	public boolean reqMet(User u) {
		return u.hasTaken(this.name);
	}

	//Returns bool based on if all prereqs have been met
	public boolean metAllPrereqs(User u){
		Iterator<Requirable> iter = this.prereqs.keySet().iterator();
		while(iter.hasNext()) {
			Requirable r = iter.next();
			if(!r.reqMet(u, this.prereqs.get(r))) {
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean reqMet(User u, Grade g) {
		return u.hasTaken(this.name, g);
	}


	public int getCredits() {
		return this.credits;
	}
	
}
