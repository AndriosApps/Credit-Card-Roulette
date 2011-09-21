package com.andrios.creditcardroulette;

import java.io.Serializable;
import java.text.DecimalFormat;



public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7137400157619683658L;
	String name;
	String id;
	double totalLoss;
	double totalSaved;
	
	public Person(String name, String id){
		this.name = name;
		this.id = id;
		this.totalLoss = 0.0;
		this.totalSaved = 0.0;
		
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name + " $" + Double.toString(getTotal());
	}
	
	public void setLoss(double loss){
		this.totalLoss -= loss;
	}
	
	public void setSaved(double saved){
		this.totalSaved += saved;
	}
	
	public double getTotal(){
		double returnValue = roundTwoDecimals(totalSaved + totalLoss);
		return returnValue;
	}
	
	public String getId(){
		return id;
	}
	
	private double roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}
}
