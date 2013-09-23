package com.diandianmi.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

@SuppressLint("HandlerLeak")
public class AsyncImageLoader {
	private HashMap<String,SoftReference<Drawable>> imageCache;
	
	public AsyncImageLoader(){
		imageCache = new HashMap<String,SoftReference<Drawable>>();
	}
	
	public Drawable loadDrawable(final String imageUrl,final ImageView imageView,final ImageCallback imageCallback){
		if(imageCache.containsKey(imageUrl)){
			SoftReference<Drawable> softRefenerce = imageCache.get(imageUrl);
			Drawable drawable = softRefenerce.get();
			if(drawable!=null)
				return drawable;
		}
		final Handler handler = new Handler(){
			public void handleMessage(Message message){
				imageCallback.imageLoaded((Drawable)message.obj,imageView,imageUrl);
			}
		};
		
		new Thread(){
			@Override
			public void run(){
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl,new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0,drawable);
				handler.sendMessage(message);
			}
		}.start();
		
		return null;
	}
	
	public static Drawable loadImageFromUrl(String url){
		URL m;
		InputStream i = null;
		try{
			m = new URL(url);
			i = (InputStream)m.getContent();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Drawable d = Drawable.createFromStream(i,"src");
		return d;
	}
	
	public interface ImageCallback{
		public void imageLoaded(Drawable imageDrawable,ImageView imageView,String imageUrl);
	}
}
