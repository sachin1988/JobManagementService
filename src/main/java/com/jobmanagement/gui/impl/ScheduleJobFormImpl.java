package com.jobmanagement.gui.impl;

import java.awt.Button;

import java.awt.FlowLayout;
import java.awt.TextField;
import java.io.File;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freixas.jcalendar.JCalendar;

import com.jobmanagement.common.FileUtils;
import com.jobmanagement.common.JobManagementConstants;
import com.jobmanagement.interfaces.ScheduleJobWidget;
import com.jobmanagement.main.StartDemo;
import com.jobmanagement.models.FormDimensions;
import com.jobmanagement.models.JobStatus;
import com.jobmanagement.wrapper.JobManagementServiceWrapper;



/**
 * @author sachinkatarnaware
 * 
 * This class is used to developed the frame or base gui of the system.
 * This class basically implements ScheduleJobWidget.
 *
 */
public class ScheduleJobFormImpl implements ScheduleJobWidget {

	final static Log logger = LogFactory.getLog(ScheduleJobFormImpl.class);
	public static String jarNameToExecute;
	JPanel scheduleDateTimePanel;
	int widthOfFrame, heightOfFrame;
	TextField completePathOfClass;
	FormDimensions formDimensions;
	JCalendar scheduleDateTimecalendar;
	Button uploadJobButton, scheduleJobButton, executeJobNowButton;
	JCheckBox repeatScheduleCheckBox;

	JobManagementServiceWrapper serviceWrapper;

	public ScheduleJobFormImpl(int width, int height, FormDimensions dimensions) {
		widthOfFrame = width;
		heightOfFrame = height;
		formDimensions = dimensions;
		completePathOfClass = new TextField(JobManagementConstants.jCompleteClassPath);
		scheduleDateTimePanel = new JPanel(new FlowLayout());
		scheduleJobButton = new Button(JobManagementConstants.jScheduleJobButtonLabel);
		executeJobNowButton = new Button(JobManagementConstants.jExecuteJobButtonLabel);
		uploadJobButton = new Button(JobManagementConstants.jUploadJobButtonLabel);
		serviceWrapper = JobManagementServiceWrapper.getInstance();

	}

	@Override
	public void drawScheduleJobForm() {
		logger.info("Start of the method ScheduleJobFormImpl.drawScheduleJobForm()");
		try {
			frame.setLayout(null);
			frame.setVisible(true);
			frame.add(completePathOfClass);
			frame.add(uploadJobButton);
			frame.add(scheduleJobButton);
			frame.getContentPane().add(scheduleDateTimePanel);
			frame.add(executeJobNowButton);
			frame.setSize(widthOfFrame, heightOfFrame);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			completePathOfClass.setBounds(formDimensions.getxBound(), formDimensions.getyBound(),
					formDimensions.getWidthBound(), formDimensions.getHeightBound());

			uploadJobButton.setBounds(formDimensions.getxBound(),
					(formDimensions.getyBound() + (formDimensions.getSpaceing())), formDimensions.getWidthBound(),
					formDimensions.getHeightBound());

			scheduleDateTimecalendar = new JCalendar(JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME, true);
			Border etchedBorder = BorderFactory.createEtchedBorder();
			Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			Border compoundBorder = BorderFactory.createCompoundBorder(etchedBorder, emptyBorder);

			scheduleDateTimecalendar.setBorder(compoundBorder);
			scheduleDateTimePanel.add(scheduleDateTimecalendar);
			scheduleDateTimePanel.setBounds((30), (formDimensions.getyBound() + (formDimensions.getSpaceing() * 2)),
					(formDimensions.getWidthBound() * 2), (formDimensions.getHeightBound() * 9));

			repeatScheduleCheckBox = new JCheckBox("Repeat?");
			repeatScheduleCheckBox.setBounds(formDimensions.getxBound(),
					(formDimensions.getyBound() + (formDimensions.getSpaceing() * 8)), 100,
					formDimensions.getHeightBound());
			frame.add(repeatScheduleCheckBox);

			scheduleJobButton.setBounds(formDimensions.getxBound(),
					(formDimensions.getyBound() + (formDimensions.getSpaceing() * 9)),
					(formDimensions.getWidthBound() - 20), formDimensions.getHeightBound());
			executeJobNowButton.setBounds(formDimensions.getxBound(),
					(formDimensions.getyBound() + (formDimensions.getSpaceing() * 10)), formDimensions.getWidthBound(),
					formDimensions.getHeightBound());

			uploadJobButton.addActionListener(e -> {
				JFileChooser selectJob = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = selectJob.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					File selectedFile = selectJob.getSelectedFile();
					File soucrcefile = new File(selectedFile.getAbsolutePath());

					File destinationfile = new File(
							StartDemo.jobManagementSettings.getProperty("job.upload.dir") + selectedFile.getName());
					FileUtils.copyFile(soucrcefile, destinationfile);
				}

			});

			scheduleJobButton.addActionListener(e -> {
				JobStatus jobstatus = readFormData(false);
				if (null != jobstatus) {
					JOptionPane.showMessageDialog(null, jobstatus.getJobStatusMessage(),
							JobManagementConstants.jResponsePanelName, JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Enter class path or upload the file.",
							JobManagementConstants.jResponsePanelName, JOptionPane.PLAIN_MESSAGE);
				}

			});
			
			executeJobNowButton.addActionListener(e -> {
				JobStatus jobstatus = readFormData(true);
				if (null != jobstatus) {
					JOptionPane.showMessageDialog(null, jobstatus.getJobStatusMessage(),
							JobManagementConstants.jResponsePanelName + "Service", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Enter class path",
							JobManagementConstants.jResponsePanelName, JOptionPane.PLAIN_MESSAGE);
				}

			});


		} catch (Exception ex) {
			logger.error("Exception occured in ScheduleJobFormImpl.drawScheduleJobForm()");
		}

		logger.info("End of the method ScheduleJobFormImpl.drawScheduleJobForm()");
	}

	private JobStatus readFormData(boolean executeNow) {
		Calendar scheduleDateTime = null;
		String executionClass = completePathOfClass.getText();
		boolean isinterval = repeatScheduleCheckBox.isSelected();
		JobStatus jobStatus;
		if (StringUtils.isNotEmpty(executionClass) != StringUtils.equals(executionClass,
				JobManagementConstants.jCompleteClassPath)) {
			if (executeNow) {
				scheduleDateTime = Calendar.getInstance();
				jobStatus = serviceWrapper.scheduleFormData(executionClass, jarNameToExecute, scheduleDateTime,
						isinterval);
			} else {
				scheduleDateTime = (Calendar) scheduleDateTimecalendar.getCalendar();
				jobStatus = serviceWrapper.scheduleFormData(executionClass, jarNameToExecute, scheduleDateTime,
						isinterval);
			}
			return jobStatus;
		} else {
			return null;
		}

	}

}
