package com.jobmanagement.interfaces;

import com.jobmanagement.models.JobInformation;
import com.jobmanagement.models.JobStatus;

/**
 * @author sachinkatarnaware
 * 
 * This interface is reponsible for the complete job management. It contains only 
 * two methods in the prototype design.
 *
 */
public interface JobManagementService {

	public JobStatus scheduleJob(JobInformation jobinformation);

	public void listOfQueuedJobs();

}
