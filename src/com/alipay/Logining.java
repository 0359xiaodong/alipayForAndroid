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
import android.view.View.OnTouchListener;
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

public class Logining extends Activity {
	private static final String TAG = "Alipay";
	private LinearLayout remind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logining);
		
		GridView gridView = (GridView) findViewById(R.id.grid);
		int[] appIcons = {
				R.drawable.sjcz, R.drawable.bl, R.drawable.sjzz, R.drawable.xykhk,
				R.drawable.cp, R.drawable.sf, R.drawable.df, R.drawable.rqf,
				R.drawable.ghkd, R.drawable.wysk, R.drawable.wyfk, R.drawable.yxp,
				R.drawable.tmzf, R.drawable.tmsy, R.drawable.axjz, R.drawable.yxdk
				};
		String[] appTitles = {
				getString(R.string.sjcz), getString(R.string.bl), getString(R.string.sjzz), getString(R.string.xykhk),
				getString(R.string.cp), getString(R.string.sf), getString(R.string.df), getString(R.string.rqf), 
				getString(R.string.ghkd), getString(R.string.wysk), getString(R.string.wyfk), getString(R.string.yxp), 
				getString(R.string.tmzf), getString(R.string.tmsy), getString(R.string.axjz), getString(R.string.yxdk), 
				};
		GridButton adapter = new GridButton(Logining.this, appIcons, appTitles);
		gridView.setAdapter(adapter);
		
		bindFooterTabUI();
		
	    remind = (LinearLayout) findViewById(R.id.remind);
		Intent intent = this.getIntent();
		Bundle bl = intent.getExtras();
		if(bl !=null && bl.getBoolean("remind")){
			remind.setVisibility(View.VISIBLE);
		}else if(bl==null || !bl.getBoolean("remind")){
			remind.setVisibility(View.GONE);
		}
	}
	
	private void bindFooterTabUI(){
		final LinearLayout homeIconLayer = (LinearLayout) findViewById(R.id.homeIconLayer);
		final Button homeIcon = (Button) findViewById(R.id.homeIcon);
		final LinearLayout tradeIconLayer = (LinearLayout) findViewById(R.id.tradeIconLayer);
		final Button tradeIcon = (Button) findViewById(R.id.tradeIcon);
		final LinearLayout moneyIconLayer = (LinearLayout) findViewById(R.id.moneyIconLayer);
		final Button moneyIcon = (Button) findViewById(R.id.moneyIcon);
		final LinearLayout moreIconLayer = (LinearLayout) findViewById(R.id.moreIconLayer);
		final Button moreIcon = (Button) findViewById(R.id.moreIcon);
		
		OnTouchListener homeIconLayerListener = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					homeIcon.setBackgroundResource(R.drawable.icon_home_selected);
					break;
				case MotionEvent.ACTION_UP:
					homeIcon.setBackgroundResource(R.drawable.icon_home_default);
					break;
					
				}
				return false;
			}
		}; 
		OnTouchListener tradeIconLayerListener = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					tradeIcon.setBackgroundResource(R.drawable.icon_trade_selected);
					break;
				case MotionEvent.ACTION_UP:
					tradeIcon.setBackgroundResource(R.drawable.icon_trade_default);
					break;
					
				}
				return false;
			}
		};
		OnTouchListener moneyIconLayerListener = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					moneyIcon.setBackgroundResource(R.drawable.icon_money_selected);
					break;
				case MotionEvent.ACTION_UP:
					moneyIcon.setBackgroundResource(R.drawable.icon_money_default);
					break;
					
				}
				return false;
			}
		};
		OnTouchListener moreIconLayerListener = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					moreIcon.setBackgroundResource(R.drawable.icon_more_selected);
					break;
				case MotionEvent.ACTION_UP:
					moreIcon.setBackgroundResource(R.drawable.icon_more_default);
					break;
					
				}
				return false;
			}
		};
		
		homeIconLayer.setPressed(true);
		homeIcon.setDuplicateParentStateEnabled(true);
		homeIconLayer.setOnTouchListener(homeIconLayerListener);
		homeIcon.setOnTouchListener(homeIconLayerListener);
		tradeIcon.setDuplicateParentStateEnabled(true);
		tradeIconLayer.setOnTouchListener(tradeIconLayerListener);
		tradeIcon.setOnTouchListener(tradeIconLayerListener);
		moneyIcon.setDuplicateParentStateEnabled(true);
		moneyIconLayer.setOnTouchListener(moneyIconLayerListener);
		moneyIcon.setOnTouchListener(moneyIconLayerListener);
		moreIcon.setDuplicateParentStateEnabled(true);
		moreIconLayer.setOnTouchListener(moreIconLayerListener);
		moreIcon.setOnTouchListener(moreIconLayerListener);
	}
}