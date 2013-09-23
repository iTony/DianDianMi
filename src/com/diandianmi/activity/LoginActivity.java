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
		
		final EditText user = (EditText)findViewById(R.id.user);//��ȡactivity_login���û������
		final EditText password = (EditText)findViewById(R.id.password);//��ȡactivity_login�����������
		final CheckBox remPsd = (CheckBox)findViewById(R.id.rememberpassword);
		CheckBox autoLogin = (CheckBox)findViewById(R.id.autologin);
		
		final DataBaseService dbSer = new DataBaseService(this);//���ݿ����
		SharedPreferences preferences = getSharedPreferences("diandianmi",MODE_PRIVATE);//��¼һЩ��Ϣ��ʹ��SharePreferences�����㡣
		final SharedPreferences.Editor editor = preferences.edit();//��SharePreferences���в�����
		
		String userName = preferences.getString("userName", null);//��ȡ��������û�����
		Boolean isRemPsd = preferences.getBoolean("rememberpassword", false);//��ȡ�Ƿ��ס����
		Boolean isAutoLogin = preferences.getBoolean("autologin", false);//��ȡ�Ƿ��Զ���¼
		if(userName!=null&&isRemPsd&&isAutoLogin){
			//����Զ���¼�����Ҽ�ס�û�������ֱ�ӽ��뵽MainActivity
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			
			startActivity(intent);
		}else if(isRemPsd&&userName!=null){
			//���ֻ�Ǽ�ס���룬�����û����������û�������������������롣
			user.setText(userName);
			String psd = preferences.getString("password", null);
			password.setText(psd);
		}
		Button button  = (Button)findViewById(R.id.login);
		button.setOnClickListener(new OnClickListener(){ //�����¼��ť��������¼�

			@Override
			public void onClick(View arg0) {
				if("".equals(password.getText().toString())||"".equals(user.getText().toString())){
					 Toast.makeText(LoginActivity.this, "please input username or password" , Toast.LENGTH_LONG).show();
					 return;
				}
				Boolean isHaveUser = dbSer.haveUser(user.getText().toString(), password.getText().toString());//��ѯ���ݿ����û� �������Ƿ���ȷ
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
		registerBtn.setOnClickListener(new OnClickListener(){//���ע�ᰴť������ע�ᡣ

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				
				finish();
			}
			
		});
		
		remPsd.setOnCheckedChangeListener(new OnCheckedChangeListener(){//�����ס���븴ѡ��

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				editor.putBoolean("rememberpassword", isChecked);
			}
			
		});
		autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener(){//����Զ���¼��ѡ��

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				editor.putBoolean("autologin", isChecked);
				if(isChecked){
					//����Զ���¼����ס����ҲҪѡ�ϡ�
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
