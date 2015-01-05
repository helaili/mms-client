package com.monogdb.mmsclient;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class HostMetricListRetrievalTask implements Callable<Integer> {
	private MonitoringClient mc;
	private String host = null;
	
	
	
	public HostMetricListRetrievalTask(MonitoringClient mc, String host) {
		super();
		this.mc = mc;
		this.host = host;
	}


	@Override
	public Integer call() throws Exception {
		JsonObject metrics = mc.getApiWrapper().getHostMetricList(mc.getMmsGroupId(), host);
		
		int callCount = 1;
		
		JsonArray metricListResult = metrics.getJsonArray("results");
		
		for(int metricCounter = 0; metricCounter < metricListResult.size(); metricCounter++) {
			@SuppressWarnings("unused")
			Future<JsonObject> future = mc.getExecutorService().submit(new HostMetricRetrievalTask(mc, host, metricListResult.getJsonObject(metricCounter).getString("metricName")));
			callCount++;
		}
		
		return callCount;
	}

}
