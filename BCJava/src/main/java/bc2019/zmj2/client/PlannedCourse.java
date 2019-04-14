package bc2019.zmj2.client;

public class PlannedCourse {
	private String name;
	private int year;
	private Season season;
	private int credits;
	
	public PlannedCourse(String name, int year, Season season) {
		this.name = name;
		this.year = year;
		this.season = season;
		this.credits = Database.getCourse(name).getCredits();
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}
}
