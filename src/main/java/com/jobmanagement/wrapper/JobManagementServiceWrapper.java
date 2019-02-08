package com.jobmanagement.wrapper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jobmanagement.impl.JobManagementServiceImpl;
import com.jobmanagement.interfaces.JobManagementService;
import com.jobmanagement.models.JobInformation;
import com.jobmanagement.models.JobStatus;

public class JobManagementServiceWrapper {

	final static Log logger = LogFactory.getLog(JobManagementServiceWrapper.class);

	public static Map<String, Object> jListOfSuccessJobs = new HashMap<String, Object>();;
	public static Map<String, Object> jListOfQueuedJobs = new HashMap<String, Object>();
	public static Map<String, Object> jListOfFailedJobs = new HashMap<String, Object>();
	public static Map<String, Object> jListOfRunningJobs = new HashMap<String, Object>();

	public static JobManagementServiceWrapper _INSTANCE = new JobManagementServiceWrapper();

	JobManagementService jobManagementService = new JobManagementServiceImpl();

	public JobManagementServiceWrapper() {
		super();

	}

	public JobStatus scheduleFormData(String executorClass, String jobName, Calendar executionDate,
			boolean isinterval) {
		logger.info("Start of the method JobManagementServiceWrapper.registrationFormData()");
		try {
			JobInformation jobConfig = new JobInformation();
			jobConfig.setExecutorClass(executorClass);
			jobConfig.setJobName(jobName);
			jobConfig.setExecutionDateTime(executionDate);
			jobConfig.setScheduleInterval(isinterval);
			logger.info("End of the method JobManagementServiceWrapper.registrationFormData()");
			return jobManagementService.scheduleJob(jobConfig);
		} catch (Exception ex) {
			logger.error("Exception occured in the method JobManagementServiceWrapper.registrationFormData()" + ex);
		}
		return null;

	}

	public static void checkStatusOfJob(String jobName) {

		logger.info("Start of the method JobManagementServiceWrapper.checkStatusOfJob()");

		try {

			if (null != jListOfSuccessJobs.get(jobName)) {
				logger.info("The job have been executed successfully");
			} else if (null != jListOfFailedJobs.get(jobName)) {
				logger.info("The job have failed,please check logs executed successfully.");
			} else if (null != jListOfRunningJobs.get(jobName)) {
				logger.info("The Job is in running state.");
			} else if (null != jListOfQueuedJobs.get(jobName)) {
				logger.info("The Job is in queued and will execute it on schedule time.");
			}
		} catch (Exception ex) {
			logger.error("Exception occured in method JobManagementServiceWrapper.checkStatusOfJob()" + ex);
		}

		logger.info("End of the method JobManagementServiceWrapper.checkStatusOfJob()");

	}

	public static void listOfRunningJobs() {
		try {
			JobManagementServiceWrapper.jListOfRunningJobs
					.forEach((k, v) -> logger.info("List of running jobs Item : " + k + "   " + v));
		} catch (Exception ex) {
			logger.error("Exception occured in method JobManagementServiceWrapper.listOfRunningJobs()" + ex);
		}

	}

	private void printQueueData() {

		try {
			jobManagementService.listOfQueuedJobs();
			JobManagementServiceWrapper.jListOfQueuedJobs
					.forEach((k, v) -> logger.info("List of queued jobs Item : " + k + "  " + v));

		} catch (Exception ex) {
			logger.error("Exception occured in method JobManagementServiceWrapper.printQueueData()" + ex);
		}

	}

	public static void listOfQueueJobs() {
		try {
			JobManagementServiceWrapper.getInstance().printQueueData();
		} catch (Exception ex) {
			logger.error("Exception occured in method JobManagementServiceWrapper.listOfQueueJobs()");
		}

	}

	public static void listOfSuccessJobs() {

		try {
			JobManagementServiceWrapper.jListOfSuccessJobs
					.forEach((k, v) -> logger.info("List of success jobs" + k + "   " + v));
		} catch (Exception ex) {
			logger.error("Exception occured in method JobManagementServiceWrapper.listOfSuccessJobs()" + ex);
		}

	}

	public static void listofFailedJobs() {

		try {
			JobManagementServiceWrapper.jListOfFailedJobs
					.forEach((k, v) -> logger.info("List of falied jobs : " + k + "  " + v));
		} catch (Exception ex) {
			logger.error("Exception occured in method JobManagementServiceWrapper.listofFailedJobs()" + ex);
		}

	}

	public static JobManagementServiceWrapper getInstance() {
		return _INSTANCE;
	}

}
