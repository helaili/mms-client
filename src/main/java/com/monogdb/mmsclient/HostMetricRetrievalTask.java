package com.monogdb.mmsclient;

import java.util.concurrent.Callable;

import javax.json.JsonObject;

public class HostMetricRetrievalTask implements Callable<JsonObject> {
	private MonitoringClient mc;
	private String host = null;
	private String metric = null;
	
	public HostMetricRetrievalTask(MonitoringClient mc, String host, String metric) {
		super();
		this.mc = mc;
		this.host = host;
		this.metric = metric;
	}


	@Override
	public JsonObject call() throws Exception {
		return mc.getApiWrapper().getHostMetric(mc.getMmsGroupId(), host, metric);
	}

}
