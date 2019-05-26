package com.pilihan.hsd.myangkot.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Generated("com.robohorse.robopojogenerator")
public class ResponseRoute{

	@SerializedName("routes")
	private List<RoutesItem> routes;

	@SerializedName("geocoded_waypoints")
	private List<GeocodedWaypointsItem> geocodedWaypoints;

	@SerializedName("status")
	private String status;

	public void setRoutes(List<RoutesItem> routes){
		this.routes = routes;
	}

	public List<RoutesItem> getRoutes(){
		return routes;
	}

	public void setGeocodedWaypoints(List<GeocodedWaypointsItem> geocodedWaypoints){
		this.geocodedWaypoints = geocodedWaypoints;
	}

	public List<GeocodedWaypointsItem> getGeocodedWaypoints(){
		return geocodedWaypoints;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseRoute{" + 
			"routes = '" + routes + '\'' + 
			",geocoded_waypoints = '" + geocodedWaypoints + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}