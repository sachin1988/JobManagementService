package com.jobmanagement.temp.jobs;

import com.jobmanagement.interfaces.Job;
import com.jobmanagement.models.JobStatus;

/**
 * @author sachinkatarnaware
 *
 */
public class JobFirst implements Job {

	private String name;

	public JobFirst() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobFirst(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getJobName() {
		return this.name;
	}

	
	@Override
	public JobStatus executeJob() {
		JobStatus jobStatus = new JobStatus();
		jobStatus.setJobStatusCode(0);
		jobStatus.setJobStatusMessage("SUCCESS");

		return jobStatus;

	}

	

}
