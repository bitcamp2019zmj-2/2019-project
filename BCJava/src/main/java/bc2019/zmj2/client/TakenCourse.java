package bc2019.zmj2.client;

public class TakenCourse {
	private String name, grade;
	private int year;
	private Season season;
	
	public TakenCourse(String name, String grade, int year, Season season) {
		this.name = name;
		this.grade = grade;
		this.year = year;
		this.season = season;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
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
