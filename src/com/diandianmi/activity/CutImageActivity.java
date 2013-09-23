package com.diandianmi.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CutImageActivity extends Activity {

	String username = null;
	private ImageView imgShow;
	private Button btnCut, btnSelect;

	private Bitmap mBitmap;

	private String sdStatue = Environment.getExternalStorageState();
	private String imgPath = null;

	private static final int IMAGE_CUT = 1;
	public static final int IMAGE_SELECT = 2;
	
	/**
	 * 临时Bitmap文件
	 * */
	public Bitmap mTmpBmp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cut_image);
		username = getIntent().getStringExtra("username");
		imgShow = (ImageView) this.findViewById(R.id.img_show);
		btnCut = (Button) this.findViewById(R.id.btn_cut);
		btnCut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File file = new File(imgPath);
				Uri uri = Uri.fromFile(file);
				runIntent(uri);
			}
		});

		btnSelect = (Button) this.findViewById(R.id.btn_select);
		btnSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CutImageActivity.this,
						SelectFileActivity.class);
				startActivityForResult(intent, IMAGE_SELECT);
			}
		});
		
	}
	
	/**
	 * 启动android提供的剪切图片的功能
	 */
	private void runIntent(Uri uri) {

		System.out.println("图片路径Uri" + uri);

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
	 *  保存图片到sd卡中
	 */
	private void saveImage() {

		String imgSavePath = Environment.getExternalStorageDirectory()
				+ "/imgs/";

		if (!sdStatue.endsWith(Environment.MEDIA_MOUNTED)) {

			System.out.println("当前SD卡不能使用---------------");
			return;
		}

		File dirFile = new File(imgSavePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imgSavePath + System.currentTimeMillis()
					+ ".jpg");
			mTmpBmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 将位图压缩成为JPEG格式
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cut_image, menu);
		return true;
	}

}
