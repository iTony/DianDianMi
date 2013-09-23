package com.diandianmi.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

import com.diandianmi.model.UserInfo;
import com.diandianmi.model.UserInfoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DataBaseService {
	private static String DB_NAME = "diandianmi.db";
	private static int DB_VERSION = 1;
	private SQLiteDatabase db;
	private SqliteHelper dbHelper;
	
	public DataBaseService(Context context){ //初始化数据库，在第一次运行软件时将创建数据库。
		dbHelper = new SqliteHelper(context,DB_NAME,null,DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public void close(){ //关闭数据库
		db.close();
		dbHelper.close();
	}
	
	public UserInfoList getUserInfo(){  //获取用户列表
		UserInfoList userList = new UserInfoList();
		
		Cursor cursor = db.query(SqliteHelper.TB_NAME, null, null, null, null, null, UserInfo.ID+" DESC");
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()&&(cursor.getString(2)!=null)){
			UserInfo user = new UserInfo();
			user.setId(cursor.getInt(0));
			user.setUserId(cursor.getString(1));
			user.setName(cursor.getString(2));
			user.setNickname(cursor.getString(3));
			user.setPassword(cursor.getString(4));
			user.setSex(cursor.getString(5));
			user.setEmail(cursor.getString(6));
			user.setTelephone(cursor.getString(7));
			user.setCellphone(cursor.getString(8));
			user.setConstellation(cursor.getString(9));
			user.setBloodType(cursor.getString(10));
			user.setHonor(cursor.getInt(11));
			user.setHonesty(cursor.getInt(12));
			user.setMoney(cursor.getInt(13));
			user.setHobby(cursor.getString(14));
			user.setRegisterTime(Timestamp.valueOf(cursor.getString(15)));
			ByteArrayInputStream stream = new ByteArrayInputStream(cursor.getBlob(16));
			//user.setIcon(Drawable.createFromStream(stream,"image"));
			user.setIcon(BitmapFactory.decodeStream(stream));
			
			userList.add(user);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return userList;
	}
	
	public Bitmap getIconByName(String name){
		
		Cursor cursor = db.query(SqliteHelper.TB_NAME,new String[]{UserInfo.ICON},UserInfo.NAME+" = "+name,null,null,null,null);
		cursor.moveToFirst();
		if(cursor.getBlob(0)!=null){
			System.out.println("bitmap");
			ByteArrayInputStream stream = new ByteArrayInputStream(cursor.getBlob(0));
			return BitmapFactory.decodeStream(stream);
		}
		return null;
	}
	
	public Boolean haveUserInfo(String userid){ //根据Id判断用户是否存在。
		Boolean flag = false;
		Cursor cursor = db.query(SqliteHelper.TB_NAME,null,UserInfo.USERID+" = "+userid,null,null,null,null);
		flag = cursor.moveToFirst();
		cursor.close();
		return flag;
	}
	
	public Boolean haveUser(String userName,String password){ //根据用户名或密码判断用户是否存在
		Boolean flag = false;
		Cursor cursor = db.query(SqliteHelper.TB_NAME,null,UserInfo.NAME+"='"+userName+"' and "+UserInfo.PASSWORD+"='"+password+"'",null,null,null,null);
		flag = cursor.moveToFirst();
		cursor.close();
		return flag;
	}
	
	public int updateUserIcon(Bitmap userIcon,String userid){ //更新用户图标
		ContentValues values = new ContentValues();
		//values.put(UserInfo.NAME, name);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);
		values.put(UserInfo.ICON,os.toByteArray());
		int id = db.update(SqliteHelper.TB_NAME, values, UserInfo.NAME+"="+userid,null);
		return id;
	}
	/*
	public int updateUserIcon(Bitmap userIcon,String userid){ //更新用户图标
		ContentValues values = new ContentValues();
		//values.put(UserInfo.NAME, name);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);
		values.put(UserInfo.ICON,os.toByteArray());
		int id = db.update(SqliteHelper.TB_NAME, values, UserInfo.ID+"="+userid,null);
		return id;
	}*/
	
	public int updateUserInfo(UserInfo user){ //更新用户信息
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, user.getUserId());
		values.put(UserInfo.NAME, user.getName());
		values.put(UserInfo.NICKNAME, user.getNickname());
		values.put(UserInfo.PASSWORD, user.getPassword());
		values.put(UserInfo.SEX, user.getSex());
		values.put(UserInfo.EMAIL, user.getEmail());
		values.put(UserInfo.TELEPHONE, user.getTelephone());
		values.put(UserInfo.CELLPHONE, user.getCellphone());
		values.put(UserInfo.CONSTELLATION, user.getConstellation());
		values.put(UserInfo.BLOODTYPE, user.getBloodType());
		values.put(UserInfo.HONOR, user.getHonor());
		values.put(UserInfo.HONESTY, user.getHonesty());
		values.put(UserInfo.MONEY,user.getMoney());
		values.put(UserInfo.HOBBY, user.getHobby());
		values.put(UserInfo.REGISTERTIME, user.getRegisterTime()+"");
		
		int id = db.update(SqliteHelper.TB_NAME, values, UserInfo.ID+"="+user.getId(),null);
		
		return id;
	}
	
	public Long addUser(UserInfo user){  //添加用户
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, user.getUserId());
		values.put(UserInfo.NAME, user.getName());
		values.put(UserInfo.NICKNAME, user.getNickname());
		values.put(UserInfo.PASSWORD, user.getPassword());
		values.put(UserInfo.SEX, user.getSex());
		values.put(UserInfo.EMAIL, user.getEmail());
		values.put(UserInfo.TELEPHONE, user.getTelephone());
		values.put(UserInfo.CELLPHONE, user.getCellphone());
		values.put(UserInfo.CONSTELLATION, user.getConstellation());
		values.put(UserInfo.BLOODTYPE, user.getBloodType());
		values.put(UserInfo.HONOR, user.getHonor());
		values.put(UserInfo.HONESTY, user.getHonesty());
		values.put(UserInfo.MONEY,user.getMoney());
		values.put(UserInfo.HOBBY, user.getHobby());
		values.put(UserInfo.REGISTERTIME, user.getRegisterTime()+"");
		if(user.getIcon()!=null){
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			user.getIcon().compress(Bitmap.CompressFormat.PNG, 100, os);
			values.put(UserInfo.ICON,os.toByteArray());
		}
		
		//user.getIcon()
		Long id = db.insert(SqliteHelper.TB_NAME, UserInfo.ID, values);
		return id;
	}
	
	public int delUser(String userId){  //删除用户
		int id = db.delete(SqliteHelper.TB_NAME, UserInfo.USERID+"="+userId, null);
		return id;
	}
}
