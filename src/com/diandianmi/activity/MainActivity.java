package com.diandianmi.activity;

import com.diandianmi.service.DataBaseService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageButton imgShow;
	
	String username = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgShow = (ImageButton) this.findViewById(R.id.ImageButton);
		imgShow.setAdjustViewBounds(true);  //调整图片的大小
		username = getIntent().getStringExtra("username");
		if(username!=null){
			DataBaseService dbSer = new DataBaseService(this);
			Bitmap bit = dbSer.getIconByName(username);
			if(bit!=null){
				imgShow.setImageBitmap(bit);
			}
		}
		
		Button smileButton = (Button)findViewById(R.id.SmileButton);
		Button thinkButton = (Button)findViewById(R.id.ThinkButton);
		Button discussButton = (Button)findViewById(R.id.DiscussButton);
		Button musicButton = (Button)findViewById(R.id.MusicButton);
		Button noteButton = (Button)findViewById(R.id.NoteButton);
		Button friendButton = (Button)findViewById(R.id.FriendButton);
		
		
		
		OnClickListener clickListener = new OnClickListener(){
			Intent intent ;
			
			@Override
			public void onClick(View view) {
				switch(view.getId()){
				case R.id.SmileButton:
					intent = new Intent(MainActivity.this,SmileActivity.class);
					startActivity(intent);
					break;
				case R.id.ThinkButton:
					intent = new Intent(MainActivity.this,ThinkActivity.class);
					startActivity(intent);
					break;
				case R.id.DiscussButton:
					intent = new Intent(MainActivity.this,DiscussActivity.class);
					startActivity(intent);
					break;
				case R.id.MusicButton:
					intent = new Intent(MainActivity.this,MusicActivity.class);
					startActivity(intent);
					break;
				case R.id.NoteButton:
					intent = new Intent(MainActivity.this,NoteActivity.class);
					startActivity(intent);
					break;
				case R.id.FriendButton:
					intent = new Intent(MainActivity.this,FriendActivity.class);
					startActivity(intent);
					break;
				case R.id.ImageButton:
					intent = new Intent(MainActivity.this,UserActivity.class);
					startActivity(intent);
					break;
				}
			}
			
		};
		
		smileButton.setOnClickListener(clickListener);
		thinkButton.setOnClickListener(clickListener);
		discussButton.setOnClickListener(clickListener);
		musicButton.setOnClickListener(clickListener);
		noteButton.setOnClickListener(clickListener);
		friendButton.setOnClickListener(clickListener);
		imgShow.setOnClickListener(clickListener);
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
