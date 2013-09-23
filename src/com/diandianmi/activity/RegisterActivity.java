package com.diandianmi.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.diandianmi.model.UserInfo;
import com.diandianmi.service.DataBaseService;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText name =null;
	
	private ImageView imgShow;
	//private Bitmap mBitmap;
	private String imgPath = null;
	//private String username;
	/**
	 * ��ʱBitmap�ļ�
	 * */
	private Bitmap mTmpBmp;
	
	private Boolean isHeader = false;
	
	private static final int IMAGE_CUT = 1;
	public static final int IMAGE_SELECT = 2;
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	//private DataBaseService dbSer = null;
	private UserInfo user = null;
	@SuppressLint({ "CommitPrefEdits", "SimpleDateFormat" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//dbSer = new DataBaseService(RegisterActivity.this);
		imgShow = (ImageView) this.findViewById(R.id.img_show);
		imgShow.setAdjustViewBounds(true);  //����ͼƬ�Ĵ�С
		
		preferences = getSharedPreferences("diandianmi",MODE_PRIVATE);
		editor = preferences.edit();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		editor.putString("time", sdf.format(new Date()));
		name = (EditText)findViewById(R.id.user);
		final EditText password = (EditText)findViewById(R.id.password);
		final EditText confirmpassword = (EditText)findViewById(R.id.confirmpassword);
		RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup);

		Button uploadBtn = (Button)findViewById(R.id.uploadIcon);
		Button ok = (Button)findViewById(R.id.ok);
		
		user = new UserInfo();
		
		
		confirmpassword.setOnEditorActionListener(new OnEditorActionListener(){   //ȷ������

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if(!password.getText().toString().equals(confirmpassword.getText().toString())){
					Toast.makeText(RegisterActivity.this, "the password is wrong", Toast.LENGTH_SHORT).show();
				}
				
				return false;
			}
			
		});
		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener(){   //ѡ���Ա�

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if(arg1==R.id.male){
					user.setSex("male");
					editor.putString("sex", "male");
				}else if(arg1==R.id.female){
					user.setSex("female");
					editor.putString("sex", "female");
				}
			}
			
		});
		
		uploadBtn.setOnClickListener(new OnClickListener(){  //�ϴ�ͼƬ��ť

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,SelectFileActivity.class); //������һ��Activity��ѡ��SD���е�ͼƬ
				startActivityForResult(intent, IMAGE_SELECT);
			}
			
		});
		
		ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				user.setName(name.getText().toString());
				editor.putString("username", name.getText().toString());
				user.setPassword(password.getText().toString());
				if(!password.getText().toString().equals(confirmpassword.getText().toString())){
					Toast.makeText(RegisterActivity.this, "the password is wrong", Toast.LENGTH_SHORT).show();
					return ;
				}
				DataBaseService dbSer = new DataBaseService(RegisterActivity.this);
				
				if(!isHeader){
					Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.header);  //��Ĭ��ͼƬ�������ݿ�
					user.setIcon(rawBitmap);
				}
				
				dbSer.addUser(user);    //���û���Ϣ�������ݿ�
				
				dbSer.close();
				
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);  //���ص���¼����
				startActivity(intent);
			}
			
		});
	}

	/**
	 * ����android�ṩ�ļ���ͼƬ�Ĺ���
	 */
	private void runIntent(Uri uri) {

		System.out.println("ͼƬ·��Uri" + uri);

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, IMAGE_CUT);
	}

	/**
	 *  ����ͼƬ��sd����
	 * @throws IOException 
	 */
	/*
	private void saveImage() throws IOException {

		
		//dbSer.updateUserIcon(mTmpBmp,name.getText().toString() );
		
		FileService fileSer = new FileService();
		String imgSavePath = fileSer.getSDcard() ;

		File dirFile = new File(imgSavePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imgSavePath + System.currentTimeMillis()
					+ ".jpg");
			mTmpBmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);// ��λͼѹ����ΪJPEG��ʽ
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IMAGE_CUT && data != null) {   //�������֮�󷵻�
			mTmpBmp = data.getParcelableExtra("data");  //��ȡͼƬ����
			//saveImage();
			user.setIcon(mTmpBmp);    
			isHeader = true;
			
			imgShow.setImageBitmap(mTmpBmp);

		} else if (requestCode == IMAGE_SELECT && data != null) {   //��SelectFileActivity����
			imgPath = data.getStringExtra(SelectFileActivity.FILEPATH); //��ȡ�ļ�·��
			File file = new File(imgPath);  
			Uri uri = Uri.fromFile(file);
			runIntent(uri);     //�������й���
			
		} else {
			imgShow.setImageBitmap(mTmpBmp);
		}
			//btnCut.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
