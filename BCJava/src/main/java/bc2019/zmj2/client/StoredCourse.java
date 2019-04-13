package bc2019.zmj2.client;

public class StoredCourse {
	private String name;
	private Grade grade;
	private int year;
	private Season season;
	private int credits;
	
	public StoredCourse(String name, Grade grade, int year, Season season) {
		this.name = name;
		this.grade = grade;
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
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
