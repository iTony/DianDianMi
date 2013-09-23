package com.diandianmi.activity;

import java.io.File;
import java.io.IOException;

import com.diandianmi.model.SmileHolder;
import com.diandianmi.model.SmileInfo;
import com.diandianmi.model.SmileInfoList;
import com.diandianmi.service.AsyncImageLoader;
import com.diandianmi.service.FileService;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class SmileActivity extends TabActivity {

	final String FILE_NAME = "/smile";
	private SmileInfoList msgInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		msgInfo = new SmileInfoList();
		TabHost tabHost = getTabHost();
		//setContentView(R.layout.activity_smile);
		LayoutInflater.from(this).inflate(R.layout.activity_smile,tabHost.getTabContentView() ,true);
		
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(this.getString(R.string.hall)).setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(this.getString(R.string.self)).setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(this.getString(R.string.frienddynamic)).setContent(R.id.tab3));
	
		setContentView(tabHost);
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.equals("tab2")){
					FileService fileSer = new FileService();
					msgInfo.clear();
					//if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					//	File sdCardDir = Environment.getExternalStorageDirectory();
					try {
						String sdCardDir = fileSer.getSDcard();
						//System.out.println("dir:"+sdCardDir.getCanonicalPath());
						File file = new File(sdCardDir+FILE_NAME);
						if(file.exists()){
							System.out.println("file:"+file.getCanonicalPath());
							String message = fileSer.read(sdCardDir+FILE_NAME);
							System.out.println("message:"+message);
							String[] msg = message.split("\\[");
							System.out.println("length:"+msg.length);
							for(int i=0;i<msg.length;i++){
								String m = msg[i];
								if("".equals(m))
									continue;
								SmileInfo info = new SmileInfo();
								int index = m.indexOf("]");
								String temp = m.substring(0,index);
								System.out.println("temp"+temp);
								String[] tmp = temp.split("\\|");
								String title = tmp[0];
								String time = tmp[1];
								String text = m.substring(index+1);
								
								System.out.println("title:"+title);
								System.out.println("time:"+title);
								System.out.println("text:"+text);
								
								//info.setId("123");
								//info.setUserId("123456");
								info.setHaveImage(false);
								//info.setUserName(title);
								info.setTitle(title);
								info.setTime(time);
								info.setText(text);
								//info.setUserIcon(userIcon)
								msgInfo.add(info);
								//info.set
								
							}
							
							SmileAdapater adapter = new SmileAdapater();
							ListView msgList = (ListView)findViewById(R.id.MsgList);
							msgList.setAdapter(adapter);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
				//	}
					ImageButton writeBtn = (ImageButton)findViewById(R.id.writeBtn);
					writeBtn.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(SmileActivity.this,WritePadActivity.class);
							
							startActivity(intent);
						}
						
					});
				}
			}
			
		});
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.smile, menu);
		return true;
	}

	public class SmileAdapater extends BaseAdapter{

		private AsyncImageLoader asyncImageLoader;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return msgInfo.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return msgInfo.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			asyncImageLoader = new AsyncImageLoader();
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.self_smile,null);
			SmileHolder holder = new SmileHolder();
			//holder.Icon = (ImageView)convertView.findViewById(R.id.icon);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.text = (TextView)convertView.findViewById(R.id.content);
			holder.time = (TextView)convertView.findViewById(R.id.time);
			//holder.user = (TextView)convertView.findViewById(R.id.user);
			//holder.image = (ImageView)convertView.findViewById(R.id.image);
			
			SmileInfo si = msgInfo.get(position);
			if(si!=null){
				convertView.setTag(si.getId());
				holder.title.setText(si.getTitle());
				holder.time.setText(si.getTime());
				holder.text.setText(si.getText(),TextView.BufferType.SPANNABLE);
				//textHighlight(holder.text,new char[]{'#'},new char[]{'#'});
				if(si.getHaveImage()){
					holder.image.setImageResource(R.drawable.images);
				}
				/*
				Drawable cachedImage = asyncImageLoader.loadDrawable(si.getUserIcon(),holder.Icon,new AsyncImageLoader.ImageCallback(){

					@Override
					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						// TODO Auto-generated method stub
						imageView.setImageDrawable(imageDrawable);
					}
					
				});
				if(cachedImage == null){
					holder.Icon.setImageResource(R.drawable.usericon);
				}else {
					holder.Icon.setImageDrawable(cachedImage);
				}*/
			}
			return convertView;
		}
		
	}
}
