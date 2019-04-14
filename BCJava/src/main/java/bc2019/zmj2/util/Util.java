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
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import bc2019.zmj2.client.AuthUser;
import bc2019.zmj2.client.Database;
import bc2019.zmj2.client.Grade;
import bc2019.zmj2.client.PlannedCourse;
import bc2019.zmj2.client.StoredCourse;
import bc2019.zmj2.client.User;

public class Util {
	
	private static final String logName = "BC2019ZMJ2";
	private static final String authBaseUrl = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/";
	private static final String dbBaseUrl = "https://firestore.googleapis.com/v1beta1/projects/bitcamp2019-5b031/databases/(default)/documents/";
	private static final String apiKey = "AIzaSyDBQF0pEnxAK7-npYhU8tNSHziAkwd2U38";
	
	public static void init() {
		getLogger().info("Util init");
	}
	
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
			System.out.println(response);
			return (AuthUser)Database.getUser(user, (JsonObject)response);
		} else {
			//failed
			if(response.isJsonObject()) {
				JsonObject o = (JsonObject)response;
				String errorType = o.get("error").getAsJsonObject().get("message").getAsString();
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
	
	public static JsonObject signup(String email, String pass) throws SignupException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email",email));
		params.add(new BasicNameValuePair("password",pass));
		params.add(new BasicNameValuePair("returnSecureToken","true"));
		JsonElement response = request("signup",params,"");
		if(!isError(response)) {
			//signup good
			return (JsonObject)response;
		} else {
			//failed
			if(response.isJsonObject()) {
				JsonObject o = (JsonObject)response;
				String errorType = o.get("error").getAsJsonObject().get("message").getAsString();
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
	
	//blocking
	public static DocumentSnapshot retrieve(String documentPath, JsonObject token) {
		String[] collections = documentPath.split("/");
		CollectionReference rootRef = WebUtil.getDB().collection(collections[0]);
		ApiFuture<DocumentSnapshot> future = rootRef.document(collections[1]).get();
		try {
			DocumentSnapshot doc = future.get();
			return doc;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//must be POJO
	
	public static Timestamp write(String documentPath, Object value) {
		String[] collections = documentPath.split("/");
		CollectionReference rootRef = WebUtil.getDB().collection(collections[0]);
		ApiFuture<WriteResult> future = rootRef.document(collections[1]).set(value);
		try {
			return future.get().getUpdateTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<QueryDocumentSnapshot> retrieveAll(String parentPath) {
		ApiFuture<QuerySnapshot> docs = WebUtil.getDB().collection(parentPath).get();
		try {
			return docs.get().getDocuments();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<StoredCourse> toStoredCourse(Grade g, PlannedCourse... c) {
		List<StoredCourse> planned = new ArrayList<StoredCourse>();
		for(PlannedCourse x : c) {
			StoredCourse newC = new StoredCourse(x.getName(), g, x.getYear(), x.getSeason());
			planned.add(newC);
		}
		return planned;
	}
	
	//to do: to and from reference object
	
	public void updateUser(User u) {
		if(u instanceof AuthUser) {
			AuthUser au = (AuthUser)u;
			au.updateUser();
		}
	}
}
