package bc2019.zmj2.util;

import com.google.gson.JsonElement;

public class SignupException extends AuthException {

	public SignupException(String message) {
		super(message);
	}

	public SignupException(String message, JsonElement el) {
		super(message, el);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1029085765088941997L;

	
}
