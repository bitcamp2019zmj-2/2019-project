package bc2019.zmj2.client;
import java.util.ArrayList;

public class Group implements Requirable {
	private String groupName;
	private ArrayList<Requirable> required;
	private int min, max;
	
	public Group(String name) {
		this.groupName = name;
		this.required = new ArrayList<Requirable>();
		this.min = 0;
		this.max = 0;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public void addReq(Requirable req) {
		this.required.add(req);
	}

	@Override
	public boolean reqMet(User u) {
		int met = 0;
		for (Requirable r: this.required) {
			if(r.reqMet(u)) {
				met++;
			}
		}
		return (met>=min && met<=max);
	}

	@Override
	public boolean reqMet(User u, Grade g) {
		int met = 0;
		for (Requirable r: this.required) {
			if(r.reqMet(u, g)) {
				met++;
			}
		}
		return (met>=min && met<=max);
	}
	
	
	
}
