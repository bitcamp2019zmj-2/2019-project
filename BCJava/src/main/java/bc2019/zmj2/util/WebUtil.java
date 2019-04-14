package bc2019.zmj2.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class WebUtil {

	private static HttpClient currentClient;
	private static Firestore db;
	
	/**
	 * Create client
	 * @param force
	 * @return
	 */
	private static boolean createClient(boolean force) {
		if((currentClient != null && force) || currentClient == null) {
			currentClient = HttpClients.createDefault();
			return true;
		}
		return false;
	}
	
	public static void init() {
		//
	}
	
	//init
	static {
		if(createClient(false)) {
			Util.getLogger().info("WebUtil Client created");
		} else {
			Util.getLogger().severe("WebUtil Client creation failed");
		}
		
		FirebaseOptions options;
		try {
			FileInputStream serviceAccount = new FileInputStream("servicekey.json");
			options = new FirebaseOptions.Builder()
			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			  .setDatabaseUrl("https://bitcamp2019-5b031.firebaseio.com")
			  .build();
			FirebaseApp.initializeApp(options);
			db = FirestoreClient.getFirestore();
			Util.getLogger().info("Firebase initialized");
		} catch (IOException e) {
			Util.getLogger().severe("Failed to initialize firebase");
			e.printStackTrace();
			db = null;
		}

	}
	
	public static HttpResponse postRequest(String url, List<NameValuePair> param) throws ClientProtocolException, IOException, HttpClientException {
		if(currentClient == null) throw new HttpClientException("No Client");
		HttpPost postReq = new HttpPost(url);
		postReq.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));
		HttpResponse response = currentClient.execute(postReq);
		return response;
	}
	
	public static Firestore getDB() {
		return db;
	}
	
	
}
