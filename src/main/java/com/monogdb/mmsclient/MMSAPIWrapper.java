package com.monogdb.mmsclient;

import java.net.URI;

import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
 


public class MMSAPIWrapper {
	//private String mms_username = null;
	//private String mms_api_key = null;
	private String mms_server_address = null;
	private String mms_server_port = null;
	
	private static String MMS_API_URI = "api/public/v1.0";
	private static String MMS_METRIC_GRANULARITY = "MINUTE";
	private static String MMS_METRIC_PERIOD = "PT1M";
			
	private Client restClient = null;
	
	
	public MMSAPIWrapper(String mms_username, String mms_api_key, String mms_server_address, String mms_server_port) {
		super();
		//this.mms_username = mms_username;
		//this.mms_api_key = mms_api_key;
		this.mms_server_address = mms_server_address;
		this.mms_server_port = mms_server_port;
		
		restClient = ClientBuilder.newClient(new ClientConfig()
												.register(JsonProcessingFeature.class)
												.property(JsonGenerator.PRETTY_PRINTING, true));
		restClient.register(HttpAuthenticationFeature.digest(mms_username, mms_api_key));
	}

	public JsonObject getHostList(String group) {
		String restURLStr = String.format("http://%s:%s/%s/groups/%s/hosts", mms_server_address, mms_server_port, MMS_API_URI, group);
		
		try {
			WebTarget target = restClient.target(new URI(restURLStr));
			return target.request().get(JsonObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public JsonObject getHostMetricList(String group, String host) {
		String restURLStr = String.format("http://%s:%s/%s/groups/%s/hosts/%s/metrics", mms_server_address, mms_server_port, MMS_API_URI, group, host);
		
		try {
			WebTarget target = restClient.target(new URI(restURLStr));
			return target.request().get(JsonObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public JsonObject getHostMetric(String group, String host, String metric) {
		return getHostMetric(group, host, metric, MMS_METRIC_GRANULARITY, MMS_METRIC_PERIOD);
	} 
	
	
	public JsonObject getHostMetric(String group, String host, String metric, String granularity, String period) {
		String restURLStr = String.format("http://%s:%s/%s/groups/%s/hosts/%s/metrics/%s?granularity=%s&period=%s", mms_server_address, mms_server_port, MMS_API_URI, group, host, metric, granularity, period);
	
		try {
			WebTarget target = restClient.target(new URI(restURLStr));
			return target.request().get(JsonObject.class);
		} catch (Exception e) {
			System.out.println(restURLStr);
			e.printStackTrace();
		}
		
		return null;
	}
}





