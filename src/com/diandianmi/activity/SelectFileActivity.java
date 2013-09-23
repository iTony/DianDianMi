package com.diandianmi.activity;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectFileActivity extends Activity {

	private ListView lvShow;

	private ArrayList<File> fileList = new ArrayList<File>();// Ŀ¼�µ������ļ�list
	private ArrayList<String> fileNames = new ArrayList<String>();;// Ŀ¼�µ������ļ�����
	private String currentFilePath = null;

	public static String FILEPATH = "filepath";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_file);
		lvShow = (ListView) this.findViewById(R.id.lv_select);

		lvShow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int postion, long id) {

				if (fileList.get(postion).isDirectory()) {
					loadFile(fileList.get(postion));
					return;
				}

				Intent intent = new Intent();
				intent.putExtra(FILEPATH, fileList.get(postion).getAbsolutePath());    ///��ͼƬ�Ĵ��·��������ע����档
				setResult(RegisterActivity.IMAGE_SELECT, intent);
				finish();
			}
		});

		loadFile(Environment.getExternalStorageDirectory());
	}

	private ArrayAdapter<String> setAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SelectFileActivity.this, android.R.layout.simple_list_item_1,fileNames);

		return adapter;
	}
	/**
	 * �����ļ�
	 * 
	 * @param file
	 */
	private void loadFile(File file) {

		// ���list
		if (fileList.size() > 0) {
			fileList.clear();
		}
		if (fileNames.size() > 0) {
			fileNames.clear();
		}
		// load�ļ��������е��ļ�
		File[] files = file.listFiles();
		// �ж��Ƿ�Ϊ�գ���˵����һ���ļ�������Ŀ¼
		if (files == null) {
			return;
		}

		for (int i = 0; i < files.length; i++) {
			fileList.add(files[i]);
			fileNames.add(files[i].getName());
		}

		currentFilePath = file.getAbsolutePath();
		lvShow.setAdapter(setAdapter());
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {   //��������ذ�ťʱ
			if (currentFilePath.equals("/")) {
				finish();
			} else {
				loadFile(new File(currentFilePath).getParentFile());
			}
		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_file, menu);
		return true;
	}

}
