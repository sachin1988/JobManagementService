package com.jobmanagement.main;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jobmanagement.common.PropsFileUtil;
import com.jobmanagement.gui.impl.CheckJobStatusFormImpl;
import com.jobmanagement.gui.impl.ScheduleJobFormImpl;
import com.jobmanagement.interfaces.ScheduleJobWidget;
import com.jobmanagement.models.FormDimensions;

/**
 * StartDemo,
 *
 * This class demonstrates the most basic usage of the job management system.
 * More specifically, this class only executes the GUI of the system and loads the configuration
 * needed in the system.
 */
public class StartDemo {

	/**
	 * main, This is the entry point for the basic demo.
	 */

	final static Log logger = LogFactory.getLog(StartDemo.class);
	public static Properties jobManagementSettings = null;
	public static String path_toJobManagementSettingsFolder = null;

	static {
		String sep = System.getProperty("file.separator");
		String usrhome = System.getProperty("user.dir");
		if (usrhome == null) {
			logger.info("Unable to find the current directory.");
		}
		usrhome += sep + "jobManagement-settings";
		path_toJobManagementSettingsFolder = usrhome + sep;
		String filepath = usrhome + sep + "job.management.conf";
		PropsFileUtil propertyFile = new PropsFileUtil();

		if (propertyFile.loadFile(filepath) == true) {
			jobManagementSettings = propertyFile.properties;
			logger.info("The configuration file loaded.");
		} else {
			Date d = new Date();
			logger.info(d + ">>" + "Configuration file failed to load >> " + filepath);
		}

	}

	public static void main(String[] args) {

		initializeRegistrationForm();
	}

	/**
	 * initializeComponents, This creates the user interface for the basic demo.
	 */
	private static void initializeRegistrationForm() {
		FormDimensions classPathParameters = new FormDimensions();
		classPathParameters.setxBound(100);
		classPathParameters.setyBound(20);
		classPathParameters.setWidthBound(250);
		classPathParameters.setHeightBound(30);
		classPathParameters.setSpaceing(50);

		ScheduleJobWidget jRegistrationForm = new CheckJobStatusFormImpl(
				new ScheduleJobFormImpl(2000, 2000, classPathParameters));

		jRegistrationForm.drawScheduleJobForm();
	}
}
