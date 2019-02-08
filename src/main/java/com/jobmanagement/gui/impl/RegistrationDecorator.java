package com.jobmanagement.gui.impl;

import com.jobmanagement.interfaces.ScheduleJobWidget;

/**
 * @author sachinkatarnaware
 * 
 * This class is basically abstract class in decorator designed pattern.
 * In future we want extend the gui we just need to extend this class.
 *
 */
abstract class RegistrationDecorator implements ScheduleJobWidget {

	private ScheduleJobWidget widget;

	public RegistrationDecorator(ScheduleJobWidget inner) {
		widget = inner;
	}

	public void drawScheduleJobForm() {
		widget.drawScheduleJobForm();
	}
}
