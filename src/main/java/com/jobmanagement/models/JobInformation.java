package com.jobmanagement.models;

import java.io.Serializable;
import java.util.Calendar;



/**
 * @author sachinkatarnaware
 * 
 * This is the model class which contains the complete job information.
 *
 */
public class JobInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2416686479933326763L;
	private String executorClass;
	private String jobName;
	private Calendar executionDateTime;
	private boolean scheduleInterval;
	private long jobID;
	
	
	
	public JobInformation() {
		super();
	}

	public JobInformation(String executorClass, String jobName, Calendar executionDateTime,
			boolean scheduleInterval, long jobID) {
		super();
		this.executorClass = executorClass;
		this.jobName = jobName;
		this.executionDateTime = executionDateTime;
		this.scheduleInterval = scheduleInterval;
		this.jobID = jobID;
	}

	public String getExecutorClass() {
		return executorClass;
	}

	public void setExecutorClass(String executorClass) {
		this.executorClass = executorClass;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Calendar getExecutionDateTime() {
		return executionDateTime;
	}

	public void setExecutionDateTime(Calendar executionDateTime) {
		this.executionDateTime = executionDateTime;
	}

	

	public boolean isScheduleInterval() {
		return scheduleInterval;
	}

	public void setScheduleInterval(boolean scheduleInterval) {
		this.scheduleInterval = scheduleInterval;
	}

	public long getJobID() {
		return jobID;
	}

	public void setJobID(long jobID) {
		this.jobID = jobID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobConfiguration [executorClass=");
		builder.append(executorClass);
		builder.append(", jobName=");
		builder.append(jobName);
		builder.append(", executionDateTime=");
		builder.append(executionDateTime);
		builder.append(", timeInterval=");
		builder.append(", scheduleInterval=");
		builder.append(scheduleInterval);
		builder.append(", jobID=");
		builder.append(jobID);
		builder.append("]");
		return builder.toString();
	}
		
	
	
			
}
