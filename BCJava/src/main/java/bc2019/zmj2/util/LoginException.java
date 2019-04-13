package bc2019.zmj2.util;

import com.google.gson.JsonElement;

public class LoginException extends AuthException {

	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, JsonElement el) {
		super(message, el);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6323568229407291160L;

}
