package com.pilihan.hsd.myangkot.response;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Polyline{

	@SerializedName("points")
	private String points;

	public void setPoints(String points){
		this.points = points;
	}

	public String getPoints(){
		return points;
	}

	@Override
 	public String toString(){
		return 
			"Polyline{" + 
			"points = '" + points + '\'' + 
			"}";
		}
}