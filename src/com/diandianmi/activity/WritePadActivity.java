package com.diandianmi.activity;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.diandianmi.service.FileService;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WritePadActivity extends Activity {

	final String FILE_NAME = "/smile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_pad);
		
		final EditText subEdit = (EditText)findViewById(R.id.smileSubject);
		final EditText conEdit = (EditText)findViewById(R.id.smileText);
		
		Button btn = (Button)findViewById(R.id.ok);
		
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				System.out.println("write click");
				// TODO Auto-generated method stub
				FileService fileSer = new FileService();
				///if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//	System.out.println("sd card");
				String sdCardDir;
				try {
					sdCardDir = fileSer.getSDcard();
					if(sdCardDir!=null){
						String subject = subEdit.getText().toString();
						String content = conEdit.getText().toString();
						String text = "["+subject+"|";
						
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						String date = format.format(new Date());
						text += date + "]\n"+content+"\n";
						System.out.println("dir:"+sdCardDir);
						System.out.println("<--------------->\n"+text);
						
						fileSer.write(sdCardDir+FILE_NAME, text);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_pad, menu);
		return true;
	}

}
