package bc2019.zmj2.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Course implements Requirable {
	private String dept;
	private int number;
	private String suffix;
	
	private String name;
	private int credits;
	private List<String> gradeMethods;
	private String description;
	
	private Map<Requirable, Grade> prereqs; //AND conditions, OR done by Group
	private List<Course> coreqs;
	private List<String> deptForbidden;
	private List<String> majorForbidden;
	private List<String> requiredMajor;
	private List<Course> alternates;
	
	private String gened;
	
	public Course() {
		this.dept = null;
		this.number = 0;
		this.suffix = null;
		this.name = null;
		this.credits = 0;
		this.gradeMethods = null;
		this.description = null;
		this.prereqs = null;
		this.coreqs = null;
		this.deptForbidden = null;
		this.majorForbidden = null;
		this.requiredMajor = null;
		this.alternates = null;
		this.gened = null;
	}
	
	public Course(String dept, int number, String suffix,
			String name, int credits, List<String> gradeMethods, String description,
			Map<Requirable,Grade> prereqs, List<Course> coreqs,
			List<String> deptForbidden, List<String> majorForbidden,
			List<String> requiredMajor, List<Course> alternates, String gened) {
		this.dept = dept;
		this.number = number;
		this.suffix = suffix;
		this.name = name;
		this.credits = credits;
		this.gradeMethods = gradeMethods;
		this.description = description;
		this.prereqs = prereqs;
		this.coreqs = coreqs;
		this.deptForbidden = deptForbidden;
		this.majorForbidden = majorForbidden;
		this.requiredMajor = requiredMajor;
		this.alternates = alternates;
		this.gened = gened;
	}
	
	public Course(String dept, int number, String suffix) {
		this.dept = dept;
		this.number = number;
		this.suffix = suffix;
		this.name = dept+number+suffix;
		this.credits = 0;
		this.gradeMethods = null;
		this.description = "Blank Description";
		this.prereqs = null;
		this.coreqs = null;
		this.deptForbidden = null;
		this.majorForbidden = null;
		this.requiredMajor = null;
		this.alternates = null;
		this.gened = "";
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getGradeMethods() {
		return gradeMethods;
	}

	public void setGradeMethods(List<String> gradeMethods) {
		this.gradeMethods = gradeMethods;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<Requirable, Grade> getPrereqs() {
		return prereqs;
	}

	public void setPrereqs(Map<Requirable, Grade> prereqs) {
		this.prereqs = prereqs;
	}

	public List<Course> getCoreqs() {
		return coreqs;
	}

	public void setCoreqs(List<Course> coreqs) {
		this.coreqs = coreqs;
	}

	public List<Course> getAlternates() {
		return alternates;
	}

	public void setAlternates(List<Course> altNames) {
		this.alternates = altNames;
	}

	public String getGened() {
		return gened;
	}

	public void setGened(String gened) {
		this.gened = gened;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getCredits() {
		return this.credits;
	}

	public String getDept() {
		return dept;
	}

	public int getNumber() {
		return number;
	}

	public String getSuffix() {
		return suffix;
	}

	public List<String> getDeptForbidden() {
		return deptForbidden;
	}

	public void setDeptForbidden(List<String> deptForbidden) {
		this.deptForbidden = deptForbidden;
	}

	public List<String> getMajorForbidden() {
		return majorForbidden;
	}

	public void setMajorForbidden(List<String> majorForbidden) {
		this.majorForbidden = majorForbidden;
	}

	public List<String> getRequiredMajor() {
		return requiredMajor;
	}

	public void setRequiredMajor(List<String> requiredMajor) {
		this.requiredMajor = requiredMajor;
	}
	
}
