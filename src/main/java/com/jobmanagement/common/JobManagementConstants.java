package com.jobmanagement.common;

/**
 * @author sachinkatarnaware
 * This class contains the contant's which is used in the complete system.
 *
 */
public class JobManagementConstants {
	
	
	private JobManagementConstants() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static final int jSuccessCode = 0;
	public static final int jFailureCode = -1;
	
	public static final String jFrameName = "Job Management System";
	
	public static final String jSuccessMessage = "Job schedule successfully.";
	public static final String jFailureMessage = "Job failed to schedule.";
	public static final String jCompleteClassPath = "Enter_class_path";
	public static final int jCapacity = 10;
	
	public static final String jRuntimeClassLoaderError = "Please upload the jar or enter valid classpath";
	
	
	//
	public static final String jCheckStatusJobNameText = "Name of the job.";
	public static final String jCheckStatusOfJob = "Check Status of Job.";
	public static final String jListOfSuccessButtonLabel = "List of success jobs.";
	public static final String jListOfFailureButtonLabel = "List of failed jobs.";
	public static final String jListOfQueuedButtonLabel = "List of queued jobs.";
	public static final String jListOfRunningButtonLabel = "List of running jobs.";
	
	//
	public static final String jUploadJobButtonLabel = "Upload Job";
	public static final String jExecuteJobButtonLabel = "Execute Job.";
	public static final String jScheduleJobButtonLabel = "Schedule Job";
	

	
	public static final String jPropertiesStoreMessage = "DO NOT TAMPER OR DELETE OR EDIT THIS FILE."; 
	public static final String jResponsePanelName = "JobManagement: Service";
	
	public static final String jStartOFMessages="Start of method ";
	public static final String jEndOfMethodMessage="End of method ";
	
	
}
