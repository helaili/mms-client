package com.monogdb.mmsclient;

import java.util.ArrayList;
import java.util.List;
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

		JsonArray metricListResult = metrics.getJsonArray("results");

		List<Future<JsonObject>> list = new ArrayList<Future<JsonObject>>();

		for (int metricCounter = 0; metricCounter < metricListResult.size(); metricCounter++) {
			Future<JsonObject> future = mc.getExecutorService().submit(
					new HostMetricRetrievalTask(mc, host, metricListResult.getJsonObject(metricCounter).getString(
							"metricName")));
			list.add(future);
		}

		return processFutureList(list) + 1;
	}

	private int processFutureList(List<Future<JsonObject>> list) {
		int callCount = 0;

		try {
			for (Future<JsonObject> future : list) {
				future.get();
				callCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return callCount;
	}

}
