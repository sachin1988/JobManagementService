package com.jobmanagement.gui.impl;

import java.awt.Button;
import java.awt.TextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jobmanagement.common.JobManagementConstants;
import com.jobmanagement.interfaces.ScheduleJobWidget;
import com.jobmanagement.wrapper.JobManagementServiceWrapper;


/**
 * @author sachinkatarnaware
 * This class extends registration decorator which is 
 * designed using decorator designed pattern.
 *
 */
public class CheckJobStatusFormImpl extends RegistrationDecorator {

	final static Log logger = LogFactory.getLog(CheckJobStatusFormImpl.class);

	TextField nameOfJob;
	Button successListButton, failureListButton, queuedListButton, runningListButton, checkStatusOfJob;
	

	public CheckJobStatusFormImpl(ScheduleJobWidget inner) {
		super(inner);
		// TODO Auto-generated constructor stub
		nameOfJob = new TextField(JobManagementConstants.jCheckStatusJobNameText);
		successListButton = new Button(JobManagementConstants.jListOfSuccessButtonLabel);
		failureListButton = new Button(JobManagementConstants.jListOfFailureButtonLabel);
		queuedListButton = new Button(JobManagementConstants.jListOfQueuedButtonLabel);
		runningListButton = new Button(JobManagementConstants.jListOfRunningButtonLabel);
		checkStatusOfJob = new Button(JobManagementConstants.jCheckStatusOfJob);
		

	}

	@Override
	public void drawScheduleJobForm() {
		logger.info("Start of the method CheckJobStatusFormImpl.draw()");
		super.drawScheduleJobForm();

		try {

			nameOfJob.setBounds(700, 20, 200, 30);
			checkStatusOfJob.setBounds(700, 70, 200, 30);
			successListButton.setBounds(700, 120, 200, 30);
			failureListButton.setBounds(700, 170, 200, 30);
			queuedListButton.setBounds(700, 230, 200, 30);
			runningListButton.setBounds(700, 270, 200, 30);

			frame.add(successListButton);
			frame.add(failureListButton);
			frame.add(queuedListButton);
			frame.add(runningListButton);
			frame.add(checkStatusOfJob);
			frame.add(nameOfJob);

			
			checkStatusOfJob.addActionListener(e ->JobManagementServiceWrapper.checkStatusOfJob(nameOfJob.getText().toString()));
			successListButton.addActionListener(e -> JobManagementServiceWrapper.listOfSuccessJobs());
			failureListButton.addActionListener(e -> JobManagementServiceWrapper.listofFailedJobs());
			runningListButton.addActionListener(e -> JobManagementServiceWrapper.listOfRunningJobs());
			queuedListButton.addActionListener(e -> JobManagementServiceWrapper.listOfQueueJobs());
	
		} catch (Exception ex) {
			logger.error("Exception occured in CheckJobStatusFormImpl.draw()"+ex);
		}

		logger.info("Start of the method CheckJobStatusFormImpl.draw()");
	}

	

}
