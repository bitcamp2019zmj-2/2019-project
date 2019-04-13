package bc2019.zmj2.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonObject;

public class Util {
	
	private static final String logName = "BC2019ZMJ2";
	private static final String baseUrl = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/";
	private static final String apiKey = "AIzaSyDBQF0pEnxAK7-npYhU8tNSHziAkwd2U38";

	public static String getUrl(String reqType) {
		String ret = baseUrl;
		reqType = reqType.toLowerCase();
		switch(reqType) {
		case "login":
			ret+="verifyPassword?";
			break;
		case "signup":
			ret+="signupNewUser?";
			break;
		}
		ret+="key=" + apiKey;
		return ret;
	}
	
	public static Logger getLogger() {
		return Logger.getLogger(logName);
	}
	
	public static AuthUser login(String user, String pass) throws LoginException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email",user));
		params.add(new BasicNameValuePair("password",pass));
		params.add(new BasicNameValuePair("returnSecureToken","true"));
		JsonObject response = request("login",params);
		if(!isError(response)) {
			//login good!
		} else {
			//failed
		}
		return null;
	}
	
	public static AuthUser signUp(String email, String pass) throws SignupException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email",email));
		params.add(new BasicNameValuePair("password",pass));
		params.add(new BasicNameValuePair("returnSecureToken","true"));
		JsonObject response = request("signup",params);
		if(!isError(response)) {
			//signup good
		} else {
			//failed
		}
		return null;
	}
	
	private static boolean isError(JsonObject response) {
		if(response == null) return true;
		return response.get("error").getAsBoolean();
		
	}
	
	public static JsonObject request(String reqType, List<NameValuePair> params) {
		try {
			HttpResponse response = WebUtil.postRequest(getUrl(reqType), params);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				try  {
					InputStream is = entity.getContent();
					System.out.println(new String(is.readAllBytes()));
					is.close();
					//fix later
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		} catch (IOException | HttpClientException e) {
			e.printStackTrace();
			return null;
		}
	}
}
