package com.jobmanagement.interfaces;

import com.jobmanagement.models.JobStatus;


/**
 * @author sachinkatarnaware
 * This is the interface which client will implement. Prototype designed contains
 * only 2 methods. The JobName and executeJob method.
 *
 */
public interface Job {
	
	public String getJobName();

	
	public JobStatus executeJob();

	

}
