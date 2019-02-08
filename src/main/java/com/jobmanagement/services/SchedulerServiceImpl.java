package com.jobmanagement.services;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jobmanagement.common.JobManagementConstants;
import com.jobmanagement.interfaces.Job;
import com.jobmanagement.interfaces.SchedulerService;
import com.jobmanagement.models.JobStatus;
import com.jobmanagement.temp.jobs.JobFirst;
import com.jobmanagement.wrapper.JobManagementServiceWrapper;

/**
 * @author sachinkatarnaware
 * 
 * This is the class which implements the scheduler service.
 *
 */
public class SchedulerServiceImpl implements SchedulerService {

	final static Log logger = LogFactory.getLog(SchedulerServiceImpl.class);
	private final Object lock = new Object();
	private volatile boolean running = true;

	private static final BlockingQueue<JobTimer> queueOfJobs = new PriorityBlockingQueue<>(JobManagementConstants.jCapacity,
			new Comparator<JobTimer>() {
				@Override
				public int compare(JobTimer s, JobTimer t) {
					return s.getScheduledTime().compareTo(t.getScheduledTime());
				}
			});

	private void start() throws InterruptedException {
		logger.info(JobManagementConstants.jStartOFMessages + " SchedulerServiceImpl.start()");
		try {
			while (running) {
				JobTimer task = queueOfJobs.take();
				JobManagementServiceWrapper.jListOfRunningJobs.put(task.getJob().getJobName(), task.getJob());
				new Thread(new Runnable() {
					@Override
					public void run() {
						JobStatus jobstatus = task.run();
						if (jobstatus.getJobStatusCode() == JobManagementConstants.jSuccessCode) {
							JobManagementServiceWrapper.jListOfSuccessJobs.put(task.getJob().getJobName(),
									task.getJob());
						} else {
							JobManagementServiceWrapper.jListOfFailedJobs.put(task.getJob().getJobName(), task.getJob());
						}
					}
				}).start();

				Runnable startQueueExecution = () -> {
					JobStatus jobstatus = task.run();
					if (jobstatus.getJobStatusCode() == JobManagementConstants.jSuccessCode) {
						JobManagementServiceWrapper.jListOfSuccessJobs.put(task.getJob().getJobName(), task.getJob());
					} else {
						JobManagementServiceWrapper.jListOfFailedJobs.put(task.getJob().getJobName(), task.getJob());
					}

				};
				new Thread(startQueueExecution).start();
				JobManagementServiceWrapper.jListOfRunningJobs.remove(task.getJob().getJobName());
				waitForNextTask();
			}
		} catch (Exception ex) {
			logger.info("Exception occured in method SchedulerServiceImpl.start()" + ex);
		}

		logger.info(JobManagementConstants.jEndOfMethodMessage + "  SchedulerServiceImpl.start()");
	}

	private void waitForNextTask() throws InterruptedException {
		logger.info("Start of the method SchedulerServiceImpl.waitForNextTask()");

		try {
			synchronized (lock) {
				JobTimer nextTask = queueOfJobs.peek();
				while (nextTask == null || !nextTask.shouldRunNow()) {
					if (nextTask == null) {
						lock.wait();
					} else {
						lock.wait(nextTask.runFromNow());
					}
					nextTask = queueOfJobs.peek();
				}

			}
		} catch (Exception ex) {
			logger.error("Exception occured in method SchedulerServiceImpl.waitForNextTask()" + ex);
		}

		logger.info(JobManagementConstants.jEndOfMethodMessage + " SchedulerServiceImpl.waitForNextTask()");
	}

	private void add(Job job) {

		try {
			add(job, 0);

		} catch (Exception ex) {
			logger.error("Exception occured in method SchedulerServiceImpl.add()" + ex);
		}

	}

	@Override
	public void jobsInQueue() {
		JobManagementServiceWrapper.jListOfQueuedJobs = new HashMap<String, Object>();
		queueOfJobs.forEach((Object) -> addToList((JobTimer) Object));
	}

	private static void addToList(JobTimer job) {

		JobManagementServiceWrapper.jListOfQueuedJobs.put(job.getJob().getJobName(), job);

	}

	private JobStatus add(Job job, long delayMs) {
		logger.info("Start of the method SchedulerServiceImpl.waitForNextTask()");

		try {
			synchronized (lock) {
				queueOfJobs.offer(JobTimer.fromTask(job, delayMs));
				lock.notify();
			}
			return (new JobStatus(JobManagementConstants.jSuccessCode, JobManagementConstants.jSuccessMessage,
					job.getJobName()));
		} catch (Exception ex) {
			logger.info("Exception occured in method SchedulerServiceImpl.add()" + ex);
		}
		logger.info(JobManagementConstants.jEndOfMethodMessage + "  SchedulerServiceImpl.waitForNextTask()");
		return (new JobStatus(JobManagementConstants.jFailureCode, JobManagementConstants.jFailureMessage,
				job.getJobName()));
	}

	public void stop() {
		this.running = false;
	}

	private static class JobTimer {
		private Job job;
		private Calendar scheduledTime;

		public JobTimer(Job job, Calendar scheduledTime) {
			this.job = job;
			this.scheduledTime = scheduledTime;
		}

		public Job getJob() {
			return job;
		}

		public static JobTimer fromTask(Job job, long delayMs) {
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(now.getTimeInMillis() + delayMs);
			return new JobTimer(job, now);
		}

		public Calendar getScheduledTime() {
			return scheduledTime;
		}

		public long runFromNow() {
			return scheduledTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		}

		public boolean shouldRunNow() {
			return runFromNow() <= 0;
		}

		public JobStatus run() {

			return job.executeJob();

		}
	}

	@Override
	public JobStatus addJobToScheduler(Job job, long timeinMilli) {

		logger.info("Start of the method SchedulerServiceImpl.addJobToScheduler()");
		try {
			SchedulerServiceImpl scheduler = new SchedulerServiceImpl();
			scheduler.add(new JobFirst("Listener Job"));

			Runnable startTheQueueOperation = () -> {
				try {
					scheduler.start();
				} catch (InterruptedException e) {
					logger.error("Exception in SchedulerServiceImpl.addJobToScheduler()");
				}

			};
			new Thread(startTheQueueOperation).start();

			Runnable scheduleTheJob = () -> {
				scheduler.add(job, timeinMilli);

			};
			new Thread(scheduleTheJob).start();

			return (new JobStatus(JobManagementConstants.jSuccessCode, JobManagementConstants.jSuccessMessage,
					job.getJobName()));

		} catch (Exception ex) {
			logger.info("Exception occured SchedulerServiceImpl.addJobToScheduler()" + ex);
		}
		logger.info(JobManagementConstants.jEndOfMethodMessage + " SchedulerServiceImpl.addJobToScheduler()");
		return (new JobStatus(JobManagementConstants.jFailureCode, JobManagementConstants.jFailureMessage,
				job.getJobName()));

	}

}
