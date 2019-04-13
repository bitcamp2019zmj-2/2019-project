package bc2019.zmj2.client;

public interface Requirable {
	//Checks if the requirement has been met for this required group/course for a specific user
	public boolean reqMet(User u);
	public boolean reqMet(User u, Grade g);
}
