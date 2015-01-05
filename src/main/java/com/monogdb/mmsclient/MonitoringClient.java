package com.monogdb.mmsclient;

import java.util.concurrent.ExecutorService;

public abstract class MonitoringClient {
	private String mmsUsername = "alain.helaili@mongodb.com";
	private String mmsApiKey = "4543da68-4369-40fa-8e7f-67ebc74f7534";
	private String mmsGroupId = "54a119826ba2c33b30d75066";
	private String mmsServerAddress = "127.0.0.1";
	private String mmsServerPort = "8180";
	private int threadCount = 15; 
	
	private ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(threadCount);
	//private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
	 
	private MMSAPIWrapper apiWrapper = new MMSAPIWrapper(mmsUsername, mmsApiKey, mmsServerAddress, mmsServerPort);
	
	
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
