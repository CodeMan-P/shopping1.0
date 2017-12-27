package com.mod.bean;

import java.io.IOException;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionInfo {
    private int value;
    private String city;
    @JsonSerialize(using  = CustomDoubleSerialize.class)
    private double lng;
    @JsonSerialize(using  = CustomDoubleSerialize.class)
    private double lat;
    public static class CustomDoubleSerialize extends JsonSerializer<Double> {  
		  
	    private DecimalFormat df = new DecimalFormat("##.00");  
	  
	    @Override  
	    public void serialize(Double value, JsonGenerator jgen,  
	            SerializerProvider provider) throws IOException,  
	            JsonProcessingException {  
	    	jgen.writeNumber(df.format(value));  
	    }  
	} 
	public RegionInfo() {
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public RegionInfo(int value, String city, double lng, double lat) {
		super();
		this.value = value;
		this.city = city;
		this.lng = lng;
		this.lat = lat;
	}
	
	
    
}
