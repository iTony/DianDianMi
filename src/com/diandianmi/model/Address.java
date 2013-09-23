package com.diandianmi.model;

public class Address {
	
	public static final String ID = "_id"; 						//表Id
	public static final String PROVINCE = "province";			//省
	public static final String CITY = "city";					//城市
	public static final String COUNTY = "county";				//乡镇
	public static final String TOWN = "town";					//村
	public static final String STREET = "street";				//街道
	
	private String province; 
	private String city;
	private String county;
	private String town;
	private String street;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
}
