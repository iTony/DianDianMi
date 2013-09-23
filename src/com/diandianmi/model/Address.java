package com.diandianmi.model;

public class Address {
	
	public static final String ID = "_id"; 						//��Id
	public static final String PROVINCE = "province";			//ʡ
	public static final String CITY = "city";					//����
	public static final String COUNTY = "county";				//����
	public static final String TOWN = "town";					//��
	public static final String STREET = "street";				//�ֵ�
	
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
