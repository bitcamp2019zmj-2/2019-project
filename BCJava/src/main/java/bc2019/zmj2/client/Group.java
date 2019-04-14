package bc2019.zmj2.client;
import java.util.ArrayList;
import java.util.List;

public class Group implements Requirable {
	private String groupName;
	private List<CourseRef> requiredCourses;
	private List<Group> requiredGroup;
	private int min, max;
	
	public Group() {
		groupName = null;
		min = 0;
		max = 0;
		this.requiredCourses = null;
		this.requiredGroup = null;
	}
	
	public Group(String name) {
		groupName = name;
		min = 0;
		max = 0;
		this.requiredCourses = new ArrayList<CourseRef>();
		this.requiredGroup = new ArrayList<Group>();
	}
	
	public Group(String name, List<CourseRef> courses, List<Group> groups, int min, int max) {
		this.groupName = name;
		this.requiredCourses = courses;
		this.requiredGroup = groups;
		this.min = min;
		this.max = max;
	}
	
	public String getName() {
		return this.groupName;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public void addReq(Requirable req) {
		if(req instanceof Group) {
			this.requiredGroup.add((Group)req);
		} else if(req instanceof CourseRef) {
			this.requiredCourses.add((CourseRef)req);
		}
	}
	
	private List<Requirable> getReqs() {
		List<Requirable> output = new ArrayList<Requirable>();
		for(Group r : this.requiredGroup) {
			output.add(r);
		}
		for(CourseRef r : this.requiredCourses) {
			output.add(r);
		}
		return output;
	}

	@Override
	public boolean reqMet(User u) {
		int met = 0;
		for (Requirable r: this.getReqs()) {
			if(r.reqMet(u)) {
				met++;
			}
		}
		return (met>=min && met<=max);
	}

	@Override
	public boolean reqMet(User u, Grade g) {
		int met = 0;
		for (Requirable r: this.getReqs()) {
			if(r.reqMet(u, g)) {
				met++;
			}
		}
		return (met>=min && met<=max);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<CourseRef> getRequiredCourses() {
		return requiredCourses;
	}

	public void setRequiredCourses(List<CourseRef> requiredCourses) {
		this.requiredCourses = requiredCourses;
	}

	public List<Group> getRequiredGroup() {
		return requiredGroup;
	}

	public void setRequiredGroup(List<Group> requiredGroup) {
		this.requiredGroup = requiredGroup;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
	
	
	
}
