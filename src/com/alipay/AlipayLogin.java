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

public class AlipayLogin extends Activity {
	private static final String TAG = "Alipay";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alipay_login);
		
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AlipayLogin.this, TaobaoLogin.class);
				startActivity(intent);
			}};
		Button taobao_login = (Button) findViewById(R.id.useTaobaoLogin);
		taobao_login.setOnClickListener(listener);
		
		Button submitLogin = (Button) findViewById(R.id.submitLogin);
		View.OnClickListener loginListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AlipayLogin.this, Logining.class);
				Bundle bl = new Bundle();
				bl.putBoolean("remind", true);
				intent.putExtras(bl);
				startActivity(intent);
			}
		};
		submitLogin.setOnClickListener(loginListener);
	}
}