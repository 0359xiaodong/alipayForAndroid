package com.alipay;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Logins extends Activity {
	private static final String TAG = "Alipay";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				switch(v.getId()){
				case R.id.alipay_login:
					Intent intent = new Intent();
					intent.setClass(Logins.this, AlipayLogin.class);
					startActivity(intent);
					break;
				case R.id.taobao_login:
					Intent intent2 = new Intent();
					intent2.setClass(Logins.this, TaobaoLogin.class);
					startActivity(intent2);
					break;
				}
			}};
		LinearLayout alipay_login = (LinearLayout) findViewById(R.id.alipay_login);
		LinearLayout taobao_login = (LinearLayout) findViewById(R.id.taobao_login);
		alipay_login.setOnClickListener(listener);
		taobao_login.setOnClickListener(listener);
	}
}