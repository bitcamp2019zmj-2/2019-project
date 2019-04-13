package bc2019.zmj2.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import bc2019.zmj2.client.AuthUser;
import bc2019.zmj2.client.User;

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
		JsonElement response = request("login",params);
		if(!isError(response)) {
			//login good
			return (AuthUser)User.getUser(user, (JsonObject)response);
		} else {
			//failed
			if(response.isJsonObject()) {
				JsonObject o = (JsonObject)response;
				String errorType = o.get("error").getAsString();
				switch(errorType) {
				case "EMAIL_NOT_FOUND":
					throw new LoginException("Email not found");
				case "INVALID_PASSWORD":
					throw new LoginException("Invalid password");
				case "USER_DISABLED":
					throw new LoginException("This user account has been disabled");
				default:
					throw new LoginException("The classic: An unknown error has occured.", o);
				}
			} else {
				throw new LoginException("The classic: An unknown error has occured.", response);
			}
		}
	}
	
	public static AuthUser signup(String email, String pass) throws SignupException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email",email));
		params.add(new BasicNameValuePair("password",pass));
		params.add(new BasicNameValuePair("returnSecureToken","true"));
		JsonElement response = request("signup",params);
		if(!isError(response)) {
			//signup good
			return (AuthUser)User.getUser(email, (JsonObject)response);
		} else {
			//failed
			if(response.isJsonObject()) {
				JsonObject o = (JsonObject)response;
				String errorType = o.get("error").getAsString();
				switch(errorType) {
				case "EMAIL_EXISTS":
					throw new SignupException("Email Already Exists");
				case "OPERATION_NOT_ALLOWED":
					throw new SignupException("Password Sign-up disabled");
				case "TOO_MANY_ATTEMPTS_TRY_AGAIN_LATER":
					throw new SignupException("Unusual activity detected. Requests temporarily blocked.");
				default:
					throw new SignupException("The classic: An unknown error has occured.", o);
				}
			} else {
				throw new SignupException("The classic: An unknown error has occured.", response);
			}
		}
	}
	
	private static boolean isError(JsonElement response) {
		if(response == null) return true;
		if(response.isJsonObject()) {
			return ((JsonObject)response).get("error") != null;
		} else {
			return true;
		}
		
	}
	
	public static JsonElement request(String reqType, List<NameValuePair> params) {
		try {
			HttpResponse response = WebUtil.postRequest(getUrl(reqType), params);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				try  {
					InputStream is = entity.getContent();
					JsonParser parser = new JsonParser();
					JsonElement obj = parser.parse(new JsonReader(new InputStreamReader(is)));
					is.close();
					return obj;
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
