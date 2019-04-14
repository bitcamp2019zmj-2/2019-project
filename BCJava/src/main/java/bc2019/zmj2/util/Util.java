package bc2019.zmj2.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import bc2019.zmj2.client.AuthUser;
import bc2019.zmj2.client.Course;
import bc2019.zmj2.client.Database;
import bc2019.zmj2.client.User;

public class Util {
	
	private static final String logName = "BC2019ZMJ2";
	private static final String authBaseUrl = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/";
	private static final String dbBaseUrl = "https://firestore.googleapis.com/v1beta1/projects/bitcamp2019-5b031/databases/(default)/documents/";
	private static final String apiKey = "AIzaSyDBQF0pEnxAK7-npYhU8tNSHziAkwd2U38";
	

	public static String getUrl(String reqType) {
		String ret = "";
		reqType = reqType.toLowerCase();
		boolean requiresKey = false;
		switch(reqType) {
		case "login":
			ret+=authBaseUrl+"verifyPassword?";
			requiresKey = true;
			break;
		case "signup":
			ret+=authBaseUrl+"signupNewUser?";
			requiresKey = true;
			break;
		case "get":
			ret+=dbBaseUrl;
		}
		if(requiresKey) {
			ret+="key=" + apiKey;
		}
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
		JsonElement response = request("login",params,"");
		if(!isError(response)) {
			//login good
			return (AuthUser)Database.getUser(user, (JsonObject)response);
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
		JsonElement response = request("signup",params,"");
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
	
	public static JsonElement request(String reqType, List<NameValuePair> params, String urlAppend) {
		try {
			if(params == null) params = new ArrayList<NameValuePair>();
			HttpResponse response = WebUtil.postRequest(getUrl(reqType)+urlAppend, params);
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
	
	public static JsonElement retrieve(String documentPath, JsonObject token) {
		String[] collections = documentPath.split("/");
		CollectionReference rootRef = WebUtil.getDB().collection(collections[0]);
		ApiFuture<DocumentSnapshot> future = rootRef.document(collections[1]).get();
		try {
			DocumentSnapshot doc = future.get();
			Course courseObj = doc.toObject(Course.class);
			System.out.println("epic");
			System.out.println(courseObj.getCredits());
			return null;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JsonElement write(String documentPath) {
		return null;
	}
	
	public static JsonElement retrieveAll(String parentPath) {
		return null;
	}
	
	//to do: to and from reference object
}
