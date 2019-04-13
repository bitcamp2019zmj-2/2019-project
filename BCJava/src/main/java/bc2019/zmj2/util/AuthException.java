package bc2019.zmj2.util;

import com.google.gson.JsonElement;

public class AuthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8433715407786766969L;
	private final JsonElement el;
	
	public AuthException(String message) {
		super(message);
		el = null;
	}
	
	public AuthException(String message, JsonElement el) {
		super(message);
		this.el = el;
	}
	
	public String getMessage() {
		return this.getMessage() + " -- " + (el != null ? el.toString():"element is null");
	}
	
	public JsonElement getElement() {
		return el;
	}

}
