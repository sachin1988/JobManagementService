package com.jobmanagement.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jobmanagement.gui.impl.ScheduleJobFormImpl;

/**
 * 
 *
 * This class is used for copying or uploading of the file,basically file
 * related operations the system.
 * 
 * @author sachinkatarnaware
 * 
 *
 */
public class FileUtils {

	final static Log logger = LogFactory.getLog(FileUtils.class);

	public FileUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void copyFile(File sourceFile, File destFile) {

		if (!sourceFile.exists())
			return;

		try {

			if (!destFile.exists()) {
				destFile.createNewFile();
			}
			ScheduleJobFormImpl.jarNameToExecute = sourceFile.getName();
			FileChannel source = new FileInputStream(sourceFile).getChannel();
			FileChannel destination = new FileOutputStream(destFile).getChannel();

			if (destination != null && source != null) {
				destination.transferFrom(source, 0, source.size());
			}
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}

		} catch (IOException e) {
			logger.error("Issue while copying the file");
		}

		logger.info("File " + sourceFile.getName() + " is copied to " + destFile.getName());

	}
}
