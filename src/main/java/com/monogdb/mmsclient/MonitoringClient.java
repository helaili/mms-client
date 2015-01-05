package com.monogdb.mmsclient;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

public abstract class MonitoringClient {
	private String mmsUsername = null;
	private String mmsApiKey = null;
	private String mmsGroupId = null;
	private String mmsServerAddress = null;
	private String mmsServerPort = null;
	private int threadCount = -1;
	private int retryCount = -1;
	
	private ExecutorService executorService = null;
	private MMSAPIWrapper apiWrapper = null;
	
	public MonitoringClient() {
		super();
		try {
			Properties mmsClientProps = new Properties();
			FileInputStream in = new FileInputStream("mmsclient.properties");
			mmsClientProps.load(in);
			
			
			mmsUsername = mmsClientProps.getProperty("mmsUsername");
			mmsApiKey = mmsClientProps.getProperty("mmsApiKey");
			mmsGroupId = mmsClientProps.getProperty("mmsGroupId");
			mmsServerAddress = mmsClientProps.getProperty("mmsServerAddress", "127.0.0.1");
			mmsServerPort = mmsClientProps.getProperty("mmsServerPort", "8080");
			threadCount = Integer.parseInt(mmsClientProps.getProperty("threadCount", "15"));
			retryCount = Integer.parseInt(mmsClientProps.getProperty("retryCount", "3"));
			
			executorService = java.util.concurrent.Executors.newFixedThreadPool(threadCount);
			apiWrapper = new MMSAPIWrapper(mmsUsername, mmsApiKey, mmsServerAddress, mmsServerPort);
			apiWrapper.setMaxRetryCount(retryCount);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} 
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
