package bc2019.zmj2.client;

public class Course implements Requirable {
	private String name;
	
	public Course(String name) {
		this.name = name;
	}
	
	
	@Override
	public boolean reqMet(User u) {
		return u.hasTaken(this.name);
	}

}
