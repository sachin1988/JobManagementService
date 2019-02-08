package com.jobmanagement.models;

import java.io.Serializable;


/**
 * @author sachinkatarnaware
 * This class is used for setting the parameters of the frame.
 *
 */
public class FormDimensions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6979606764270659010L;
	private int xBound;
	private int yBound;
	private int widthBound;
	private int heightBound;
	private int spaceing;
	
	
	public FormDimensions() {
		super();
	}
	
	
	
	public int getyBound() {
		return yBound;
	}



	public void setyBound(int yBound) {
		this.yBound = yBound;
	}



	public int getSpaceing() {
		return spaceing;
	}



	public void setSpaceing(int spaceing) {
		this.spaceing = spaceing;
	}



	public int getxBound() {
		return xBound;
	}
	public void setxBound(int xBound) {
		this.xBound = xBound;
	}
	
	public int getWidthBound() {
		return widthBound;
	}
	public void setWidthBound(int widthBound) {
		this.widthBound = widthBound;
	}
	public int getHeightBound() {
		return heightBound;
	}
	public void setHeightBound(int heightBound) {
		this.heightBound = heightBound;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormParameters [xBound=");
		builder.append(xBound);
		builder.append(", yBound=");
		builder.append(yBound);
		builder.append(", widthBound=");
		builder.append(widthBound);
		builder.append(", heightBound=");
		builder.append(heightBound);
		builder.append(", spaceing=");
		builder.append(spaceing);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
