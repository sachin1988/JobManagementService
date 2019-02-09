package com.jobmanagement.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jobmanagement.common.JobManagementConstants;
import com.jobmanagement.interfaces.Job;
import com.jobmanagement.interfaces.JobManagementService;
import com.jobmanagement.interfaces.SchedulerService;
import com.jobmanagement.main.StartDemo;
import com.jobmanagement.models.JobInformation;
import com.jobmanagement.models.JobStatus;
import com.jobmanagement.services.SchedulerServiceImpl;


/**
 * @author sachinkatarnaware
 * 
 * This class implements the jobManagmentService, this will be responsible for 
 * handling complete system. In current prototype we have just implemented one job
 * scheduling method. 
 *
 */
public class JobManagementServiceImpl implements JobManagementService {
	SchedulerService scheduler = new SchedulerServiceImpl();

	final static Log logger = LogFactory.getLog(JobManagementServiceImpl.class);

	@Override
	public JobStatus scheduleJob(JobInformation jobInfo) {
		logger.info("Start of the method JobManagementServiceImpl.scheduleJob()");

		long scheduledTime;
		JobStatus jobstatus = null;
		Calendar now = Calendar.getInstance();
		URLClassLoader child = null;
		try {
			scheduledTime = ((jobInfo.getExecutionDateTime().getTimeInMillis() - now.getTimeInMillis()));
			child = new URLClassLoader(
					new URL[] { new URL(
							StartDemo.jobManagementSettings.getProperty("job.load.runtime") + jobInfo.getJobName()) },
					JobManagementServiceImpl.class.getClassLoader());
			Class<?> clazz = Class.forName(jobInfo.getExecutorClass(), true, child);
			Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class });
			Job job = (Job) constructor.newInstance(jobInfo.getJobName());
			jobstatus = scheduler.addJobToScheduler(job, scheduledTime);
			logger.info("End of the method JobManagementServiceImpl.scheduleJob()");
			return jobstatus;

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			logger.error("Exception occured in method JobManagementServiceImpl.scheduleJob()" + e);
			return (new JobStatus(JobManagementConstants.jFailureCode,
					JobManagementConstants.jRuntimeClassLoaderError, jobInfo.getJobName()));

		} catch (Exception e) {
			logger.error("Exception occured in method JobManagementServiceImpl.scheduleJob()" + e);
			return (new JobStatus(JobManagementConstants.jFailureCode,
					JobManagementConstants.jRuntimeClassLoaderError, jobInfo.getJobName()));
		} finally {
			if (child != null) {
				try {
					child.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(" " + e);
				}
			}

		}

	}

	public void listOfQueuedJobs() {
		scheduler.jobsInQueue();

	}

}
