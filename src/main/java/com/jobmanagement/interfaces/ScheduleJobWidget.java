package com.jobmanagement.interfaces;

import javax.swing.JFrame;

import com.jobmanagement.common.JobManagementConstants;

/**
 * @author sachinkatarnaware
 * This is the interface used for the GUI development. In prototype designed it contains
 * only 1 method and the static jframe variable.
 *
 */
public interface ScheduleJobWidget {

	static JFrame frame = new JFrame(JobManagementConstants.jFrameName);
	
	public void drawScheduleJobForm();
}
