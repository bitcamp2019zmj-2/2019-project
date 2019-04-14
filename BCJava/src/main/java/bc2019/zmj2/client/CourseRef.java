package bc2019.zmj2.client;

public class CourseRef implements Requirable {

	private String courseKey;
	
	public CourseRef() {
		courseKey = null;
	}
	
	public CourseRef(String key) {
		this.courseKey = key;
	}
	
	public String getCourseKey() {
		return this.courseKey;
	}
	
	protected Course getCourse() {
		return Database.getCourse(courseKey);
	}
	
	@Override
	public boolean reqMet(User u) {
		return getCourse().reqMet(u);
	}

	@Override
	public boolean reqMet(User u, Grade g) {
		return getCourse().reqMet(u,g);
	}

}
