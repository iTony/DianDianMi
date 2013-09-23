package com.diandianmi.activity;

import com.diandianmi.service.DataBaseService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	
	//SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final EditText user = (EditText)findViewById(R.id.user);//获取activity_login的用户输入框
		final EditText password = (EditText)findViewById(R.id.password);//获取activity_login的密码输入框
		final CheckBox remPsd = (CheckBox)findViewById(R.id.rememberpassword);
		CheckBox autoLogin = (CheckBox)findViewById(R.id.autologin);
		
		final DataBaseService dbSer = new DataBaseService(this);//数据库操作
		SharedPreferences preferences = getSharedPreferences("diandianmi",MODE_PRIVATE);//记录一些信息，使用SharePreferences更方便。
		final SharedPreferences.Editor editor = preferences.edit();//对SharePreferences进行操作。
		
		String userName = preferences.getString("userName", null);//获取保存过的用户名。
		Boolean isRemPsd = preferences.getBoolean("rememberpassword", false);//获取是否记住密码
		Boolean isAutoLogin = preferences.getBoolean("autologin", false);//获取是否自动登录
		if(userName!=null&&isRemPsd&&isAutoLogin){
			//如果自动登录，并且记住用户名，就直接进入到MainActivity
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			
			startActivity(intent);
		}else if(isRemPsd&&userName!=null){
			//如果只是记住密码，则在用户框中输入用户名，密码框中输入密码。
			user.setText(userName);
			String psd = preferences.getString("password", null);
			password.setText(psd);
		}
		Button button  = (Button)findViewById(R.id.login);
		button.setOnClickListener(new OnClickListener(){ //点击登录按钮产生点击事件

			@Override
			public void onClick(View arg0) {
				if("".equals(password.getText().toString())||"".equals(user.getText().toString())){
					 Toast.makeText(LoginActivity.this, "please input username or password" , Toast.LENGTH_LONG).show();
					 return;
				}
				Boolean isHaveUser = dbSer.haveUser(user.getText().toString(), password.getText().toString());//查询数据库中用户 和密码是否正确
				if(isHaveUser){
					editor.putString("userName", user.getText().toString());
					editor.putString("password", password.getText().toString());
					
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					intent.putExtra("username", user.getText().toString());
					startActivity(intent);
					
					finish();
				}else{
					 Toast.makeText(LoginActivity.this, "user name or password error" , Toast.LENGTH_LONG).show();
				}
				
				
			}
			
		});
		Button registerBtn = (Button)findViewById(R.id.register);
		registerBtn.setOnClickListener(new OnClickListener(){//点击注册按钮，进行注册。

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				
				finish();
			}
			
		});
		
		remPsd.setOnCheckedChangeListener(new OnCheckedChangeListener(){//点击记住密码复选框

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				editor.putBoolean("rememberpassword", isChecked);
			}
			
		});
		autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener(){//点击自动登录复选框

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				editor.putBoolean("autologin", isChecked);
				if(isChecked){
					//如果自动登录，记住密码也要选上。
					remPsd.setChecked(isChecked);
					editor.putBoolean("rememberpassword", isChecked);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
