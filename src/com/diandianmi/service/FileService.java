package com.diandianmi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.os.Environment;

public class FileService {
	
	public String getSDcard() throws IOException{
		String sdCardDir = Environment.getExternalStorageDirectory()+"";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			
			System.out.println("sdcard is exist");
			return sdCardDir;
		}else{
			System.out.println("sdcard is not exist");
		}

		return null;
	}

	public String read(String filePath){
		try{
			File file = new File(filePath);
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(file);
			byte[] buff = new byte[1024];
			int hasRead = 0;
			StringBuilder sb = new StringBuilder("");
			while((hasRead=fis.read(buff))>0){
				sb.append(new String(buff,0,hasRead));
			}
			
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void write(String filePath,String content){
		File file = new File(filePath);
		
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			RandomAccessFile raf = new RandomAccessFile(file,"rw");
			raf.seek(file.length());
			raf.write(content.getBytes());
			raf.close();
			/*
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			ps.println(content);
			ps.close();*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
