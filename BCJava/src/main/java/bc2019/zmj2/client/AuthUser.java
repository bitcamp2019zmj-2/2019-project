package bc2019.zmj2.client;

import java.util.List;

import com.google.gson.JsonObject;

public class AuthUser extends User {

	private final JsonObject authObject;
	
	public AuthUser(String name, String major, JsonObject authObject) throws IllegalArgumentException {
		super(name, major);
		if(authObject == null) throw new IllegalArgumentException("authObject is null");
		this.authObject = authObject;
		//verify
	}
	
	public AuthUser(String name, String major, List<StoredCourse> taken, List<StoredCourse> planned, JsonObject authObject) throws IllegalArgumentException {
		super(name, major,taken,planned);
		if(authObject == null) throw new IllegalArgumentException("authObject is null");
		this.authObject = authObject;
		//verify
	}

}
