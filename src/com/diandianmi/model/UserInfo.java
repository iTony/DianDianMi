package com.diandianmi.model;

import java.sql.Timestamp;

import android.graphics.Bitmap;

public class UserInfo {
	public static final String ID = "_id"; 						//表Id
	public static final String USERID = "userid";				//用户Id
	public static final String NAME = "name";					//用户名
	public static final String NICKNAME = "nickname";			//用户昵称
	public static final String PASSWORD = "password";			//密码
	public static final String SEX = "sex";						//性别
	public static final String ICON = "icon";					//用户图标
	public static final String EMAIL = "email";					//用户邮箱
	public static final String CELLPHONE = "cellphone";			//电话号码
	public static final String TELEPHONE = "telephone";			//手机号码
	public static final String CONSTELLATION = "constellation";	//星座
	public static final String BLOODTYPE = "bloodtype";			//血型
	public static final String HONOR = "honor";					//荣誉值
	public static final String HONESTY = "honesty";				//诚信值
	public static final String MONEY = "money";					//金钱值
	public static final String HOBBY = "hobby";					//爱好
	public static final String ORIGINALADDRESS = "originaladdress";//祖籍地址
	public static final String CURRENTADDRESS = "currentAddress";//当前居住地
	public static final String REGISTERTIME = "registertime";	//注册时间
	
	private int id;
	private String userId;
	private String name;
	private String nickname;
	private String password;
	private String sex;
	private Bitmap icon;
	private String email;
	private String cellphone;
	private String telephone;
	private String constellation;
	private String bloodType;
	private int honor;
	private int honesty;
	private int money;
	private String hobby;
	private Address originalAddress;
	private Address currentAddress;
	private Timestamp registerTime;
	
	public UserInfo(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		//this.icon = Bitmap.createBitmap(icon);
		this.icon = icon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public int getHonor() {
		return honor;
	}
	public void setHonor(int honor) {
		this.honor = honor;
	}
	public int getHonesty() {
		return honesty;
	}
	public void setHonesty(int honesty) {
		this.honesty = honesty;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public Address getOriginalAddress() {
		return originalAddress;
	}
	public void setOriginalAddress(Address originalAddress) {
		this.originalAddress = originalAddress;
	}
	public Address getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
