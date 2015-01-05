package com.monogdb.mmsclient;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public abstract class MonitoringClient {
	private String mmsUsername = null;
	private String mmsApiKey = null;
	private String mmsGroupId = null;
	private String mmsServerAddress = null;
	private String mmsServerPort = null;
	private int threadCount = -1; 
	
	private ExecutorService executorService = null;
	private MMSAPIWrapper apiWrapper = null;
	
	public MonitoringClient() {
		super();
		ResourceBundle bundle = ResourceBundle.getBundle("mmsclient");
		mmsUsername = bundle.getString("mmsUsername");
		mmsApiKey = bundle.getString("mmsApiKey");
		mmsGroupId = bundle.getString("mmsGroupId");
		mmsServerAddress = bundle.getString("mmsServerAddress");
		mmsServerPort = bundle.getString("mmsServerPort");
		threadCount = Integer.parseInt(bundle.getString("threadCount"));
		
		executorService = java.util.concurrent.Executors.newFixedThreadPool(threadCount);
		apiWrapper = new MMSAPIWrapper(mmsUsername, mmsApiKey, mmsServerAddress, mmsServerPort);
	}
	
	
	public String getMmsUsername() {
		return mmsUsername;
	}
	public void setMmsUsername(String mmsUsername) {
		this.mmsUsername = mmsUsername;
	}
	public String getMmsApiKey() {
		return mmsApiKey;
	}
	public void setMmsApiKey(String mmsApiKey) {
		this.mmsApiKey = mmsApiKey;
	}
	public String getMmsGroupId() {
		return mmsGroupId;
	}
	public void setMmsGroupId(String mmsGroupId) {
		this.mmsGroupId = mmsGroupId;
	}
	public String getMmsServerAddress() {
		return mmsServerAddress;
	}
	public void setMmsServerAddress(String mmsServerAddress) {
		this.mmsServerAddress = mmsServerAddress;
	}
	public String getMmsServerPort() {
		return mmsServerPort;
	}
	public void setMmsServerPort(String mmsServerPort) {
		this.mmsServerPort = mmsServerPort;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public ExecutorService getExecutorService() {
		return executorService;
	}
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	public MMSAPIWrapper getApiWrapper() {
		return apiWrapper;
	}
	public void setApiWrapper(MMSAPIWrapper apiWrapper) {
		this.apiWrapper = apiWrapper;
	}
	
}
