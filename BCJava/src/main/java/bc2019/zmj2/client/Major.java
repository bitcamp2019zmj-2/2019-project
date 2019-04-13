package bc2019.zmj2.client;
import java.util.ArrayList;

public class Major {
	private String name;
	private ArrayList<Requirable> reqs;
	
	public Major(String name) {
		this.name = name;
		this.reqs = new ArrayList<Requirable>();
	}
	
	public ArrayList<Requirable> getUncompletedReqs(User u){
		ArrayList<Requirable> output = new ArrayList<Requirable>();
		
		return output;
	}
}
