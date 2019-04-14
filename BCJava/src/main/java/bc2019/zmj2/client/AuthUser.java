package bc2019.zmj2.client;

import java.util.List;

import com.google.gson.JsonObject;

import bc2019.zmj2.util.Util;

public class AuthUser extends User {

	private final JsonObject authObject;
	
	public AuthUser() {
		super();
		authObject = null;
	}
	
	public AuthUser(String name, String major, JsonObject authObject) throws IllegalArgumentException {
		super(name, major);
		if(authObject == null) throw new IllegalArgumentException("authObject is null");
		this.authObject = authObject;
		//verify
	}
	
	public AuthUser(String name, String major, List<StoredCourse> taken, List<PlannedCourse> planned, JsonObject authObject) throws IllegalArgumentException {
		super(name, major,taken,planned);
		if(authObject == null) throw new IllegalArgumentException("authObject is null");
		this.authObject = authObject;
		//verify
	}

	public String getEmail() {
		return authObject.get("email").getAsString();
	}
	
	public JsonObject getAuthObject() {
		return authObject;
	}

	@Override
	public void updateUser() {
		String s = "users/"+authObject.get("localId").getAsString();
		Util.write("user/" + s, ((User)this));
		Util.getLogger().info(this.getPlanned().toString());
		Util.getLogger().info("Updated user");
	}
}
