package com.monogdb.mmsclient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Future;

import javax.json.JsonArray;
import javax.json.JsonObject;
 


public class BasicMonitoringClient extends MonitoringClient {
	
	private static int callCounter = 0;
	
	public static void main(String[] args) {
		BasicMonitoringClient client = new BasicMonitoringClient();
		Long before = Calendar.getInstance().getTimeInMillis();
		int repeat = 1;
		
		if(args[0] != null) {
			repeat = Integer.parseInt(args[0]);
		}
		
		int returnCode = client.runManyParallel(repeat);
		
		Long after = Calendar.getInstance().getTimeInMillis();
		Long duration = after-before;
		System.out.println("Completed " + callCounter + " API calls in " + duration + " ms, so it's " + (duration/callCounter) + " ms per call");
		System.exit(returnCode);
	}
	
	public BasicMonitoringClient() {
		super();
	}

	
	
	public int runManyParallel(int iterationCount) {
		int returnCode = 0;
		
		for(int counter=0; counter < iterationCount; counter++) {
			returnCode = runParallel();
			if(returnCode != 0) {
				return returnCode;
			}
		}
		
		return 0;
	}
	

	
	public int runParallel() {
		JsonObject hostList = getApiWrapper().getHostList(getMmsGroupId());
		callCounter++;
		
		if(hostList == null) {
			return -1;
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
		
		
		/*
		 getExecutorService().shutdown();
        
		try {
			getExecutorService().awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Time out !");
			e.printStackTrace();
		}
		
		 */

		return 0;
	}
	
}





