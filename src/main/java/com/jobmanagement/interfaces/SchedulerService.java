package com.jobmanagement.interfaces;

import com.jobmanagement.models.JobStatus;

/**
 * @author sachinkatarnaware
 * 
 * This interface is responsible for the scheduling the jobs. This contains two methods schedule time and the job 
 * to be scheduled.
 *
 */
public interface SchedulerService {

	public JobStatus addJobToScheduler(Job job, long timeinMilli);

	public void jobsInQueue();
}
