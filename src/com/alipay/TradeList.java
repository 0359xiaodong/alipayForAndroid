package com.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class TradeList extends Activity implements OnScrollListener{
	private static final String TAG = "Alipay";
	private ListView listView;
	private int lastIndex = 0;
	private int dataSize = 64;
	private int itemCount;
	private PaginationAdapter adapter;
	private View loadMoreView;
	private Button loadMoreButton;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_list);
		
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
		loadMoreButton = (Button) loadMoreView.findViewById(R.id.load_more_btn);
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMoreButton.setText("正在加载...");
				handler.postDelayed(new Runnable(){
					@Override
					public void run() {
						loadMoreData();
						adapter.notifyDataSetChanged();
						loadMoreButton.setText("加载更多");
					}}, 2000);
			}
		};
		listView = (ListView) findViewById(R.id.dataList);
		listView.addFooterView(loadMoreView);
		initializeAdapter();
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
		loadMoreButton.setOnClickListener(listener);
	}
	
	private void initializeAdapter(){
		List<Trades> trades = new ArrayList<Trades>();
		for(int i=0; i<100; i++){
			Trades trade = new Trades();
			trade.setName("商品名称"+i);
			trade.setDate("交易日期"+i);
			trade.setMoney(i+"元");
			trade.setStatus("交易状态"+i);
			trades.add(trade);
		}
		adapter = new PaginationAdapter(trades);
	}
	
	private void loadMoreData(){
		int count = adapter.getCount();
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public class PaginationAdapter extends BaseAdapter{
		List<Trades> trades;
		
		public PaginationAdapter(List<Trades> trades){
			this.trades = trades;
		}

		@Override
		public int getCount() {
			return trades.size();
		}

		@Override
		public Object getItem(int position) {
			return trades.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.trade_list_item, null);
			}
			
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView status = (TextView) view.findViewById(R.id.status);
			TextView date = (TextView) view.findViewById(R.id.date);
			TextView money = (TextView) view.findViewById(R.id.money);
			
			name.setText(trades.get(position).getName());
			status.setText(trades.get(position).getStatus());
			date.setText(trades.get(position).getDate());
			money.setText(trades.get(position).getMoney());
			
			return view;
		}
		
	}
}