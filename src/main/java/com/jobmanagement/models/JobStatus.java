package com.jobmanagement.models;

import java.io.Serializable;



/**
 * @author sachinkatarnaware
 * 
 * This is the object which will flow from GUI to back end. It contains the status of
 * the job after executing the job.
 *
 */
public class JobStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8967786867984515113L;
	private int jobStatusCode;
	private String jobStatusMessage;
	private String  jobName;
	public JobStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobStatus(int jobStatusCode, String jobStatusMessage, String jobName) {
		super();
		this.jobStatusCode = jobStatusCode;
		this.jobStatusMessage = jobStatusMessage;
		this.jobName = jobName;
	}
	
	
	public int getJobStatusCode() {
		return jobStatusCode;
	}
	public void setJobStatusCode(int jobStatusCode) {
		this.jobStatusCode = jobStatusCode;
	}
	public String getJobStatusMessage() {
		return jobStatusMessage;
	}
	public void setJobStatusMessage(String jobStatusMessage) {
		this.jobStatusMessage = jobStatusMessage;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobStatus [jobStatusCode=");
		builder.append(jobStatusCode);
		builder.append(", jobStatusMessage=");
		builder.append(jobStatusMessage);
		builder.append(", jobName=");
		builder.append(jobName);
		builder.append("]");
		return builder.toString();
	}

	

}
