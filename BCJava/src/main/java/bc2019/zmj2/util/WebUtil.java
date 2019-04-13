package bc2019.zmj2.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

public class WebUtil {

	private static HttpClient currentClient;
	
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
	
	//init
	static {
		if(createClient(false)) {
			Util.getLogger().info("WebUtil Client created");
		} else {
			Util.getLogger().info("WebUtil Client creation failed");
		}
	}
	
	public static HttpResponse postRequest(String url, List<NameValuePair> param) throws ClientProtocolException, IOException, HttpClientException {
		if(currentClient == null) throw new HttpClientException("No Client");
		HttpPost postReq = new HttpPost(url);
		postReq.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));
		HttpResponse response = currentClient.execute(postReq);
		return response;
	}
	
	
	
}
