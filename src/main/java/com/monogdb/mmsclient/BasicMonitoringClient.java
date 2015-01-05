package com.monogdb.mmsclient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.json.JsonArray;
import javax.json.JsonObject;
 


public class BasicMonitoringClient extends MonitoringClient implements Callable<Integer> {
	
	
	
	public static void main(String[] args) {
		BasicMonitoringClient client = new BasicMonitoringClient();
		Long before = Calendar.getInstance().getTimeInMillis();
		int repeat = 1;
		
		int callCounter = 0;
		
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		
		
		if(args[0] != null) {
			repeat = Integer.parseInt(args[0]);
		}
		
		
		BasicMonitoringClient mc = new BasicMonitoringClient();
		
		System.out.println("Launching " + repeat + " monitoring threads");
		for(int counter=0; counter < repeat; counter++) {
			Future<Integer> future = mc.getExecutorService().submit(mc);
			list.add(future);
		}
		
		try {
			for(Future<Integer> future : list) {
				callCounter += future.get().intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		Long after = Calendar.getInstance().getTimeInMillis();
		Long duration = after-before;
		System.out.println("Completed " + callCounter + " API calls in " + duration + " ms, so it's " + (duration/callCounter) + " ms per call");
		System.exit(0);
	}
	
	public BasicMonitoringClient() {
		super();
	}

	
	
	
	
	@Override
	public Integer call() throws Exception {
		System.out.println("Starting monitoring thread");
		JsonObject hostList = getApiWrapper().getHostList(getMmsGroupId());
		int callCounter = 1; //1 because we just did one call to get the host list
		
		if(hostList == null) {
			return new Integer(callCounter);
		}

		JsonArray hostListResult = hostList.getJsonArray("results");
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		 
		for(int hostCounter = 0; hostCounter < hostListResult.size(); hostCounter++) {
			JsonObject hostObject = hostListResult.getJsonObject(hostCounter);
			Future<Integer> future = getExecutorService().submit(new HostMetricListRetrievalTask(this, hostObject.getString("id")));
			list.add(future);
		}
		
		try {
			for(Future<Integer> future : list) {
				callCounter += future.get().intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		return new Integer(callCounter);
	}
	
}





