package com.diandianmi.service;

import com.diandianmi.model.UserInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
	
	public static final String TB_NAME = "t_user";			
	public static final String TB_ADDRESS = "t_address";

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE IF NOT EXISTS "+
				TB_NAME+"("+
				UserInfo.ID+" integer primary key," +
				UserInfo.USERID+" varchar(100)," +
				UserInfo.NAME+" varchar(100),"+
				UserInfo.NICKNAME + " varchar(100),"+
				UserInfo.PASSWORD+" varchar(100)," +
				UserInfo.SEX +" varchar(10),"+
				UserInfo.EMAIL+" varchar(255)," +
				UserInfo.TELEPHONE+" varchar(12)," +
				UserInfo.CELLPHONE+" varchar(11)," +
				UserInfo.CONSTELLATION+" varchar(10)," +
				UserInfo.BLOODTYPE+" varchar(5)," +
				UserInfo.HONOR+" integer," +
				UserInfo.HONESTY+" integer," +
				UserInfo.MONEY+" integer," +
				UserInfo.HOBBY+" varchar(255)," +
				UserInfo.REGISTERTIME+" varchar(20)," +
				UserInfo.ICON+" blob" +
				")";
		db.execSQL(sql);
		
		sql = "CREATE TABLE IF NOT EXISTS " +
				TB_ADDRESS+"("+
				"_id integer primary key,"+
				"userid varchar(100),"+
				"province varchar(5)," +
				"city varchar(10)," +
				"country varchar(20)," +
				"town varchar(50)," +
				"street varchar(100)" +
				")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TB_ADDRESS);
		onCreate(db);
	}

}
