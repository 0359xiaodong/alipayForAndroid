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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;

public class TradeList extends Activity implements OnScrollListener{
	private static final String TAG = "Alipay";
	private ListView listView;
	private int lastIndex = 0;
	private int dataSize = 22;
	private int perCount = 10;
	private int itemCount;
	private PaginationAdapter adapter;
	private View loadMoreView;
	private Button loadMoreButton;
	private Handler handler = new Handler();
	private PopupWindow popupWindow = null;

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
		
		LinearLayout topBar = (LinearLayout) findViewById(R.id.left);
		topBar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				showPopupWindow(v);
			}
		});
	}
	
	private void initializeAdapter(){
		List<Trades> trades = new ArrayList<Trades>();
		for(int i=0; i<perCount; i++){
			Trades trade = new Trades();
			trade.setName("商品名称"+i);
			trade.setDate("交易日期"+i);
			trade.setMoney(i+"元");
			trade.setStatus("交易状态"+i);
			trades.add(trade);
		}
		adapter = new PaginationAdapter(trades);
	}
	
	private void noData(){
		loadMoreButton.setOnClickListener(null);
		Toast.makeText(TradeList.this, "数据全部加载", Toast.LENGTH_SHORT).show();
	}
	
	private void loadMoreData(){
		int count = adapter.getCount();
		if(count == dataSize){
			noData();
		}else{
			if(count+perCount <= dataSize){
				for(int i=count; i<count+perCount; i++){
					Trades trade = new Trades();
					trade.setName("商品名称"+i);
					trade.setDate("交易日期"+i);
					trade.setMoney(i+"元");
					trade.setStatus("交易状态"+i);
					adapter.addNewItem(trade);
				}
			}else{
				for(int i=count; i<dataSize; i++){
					Trades trade = new Trades();
					trade.setName("商品名称"+i);
					trade.setDate("交易日期"+i);
					trade.setMoney(i+"元");
					trade.setStatus("交易状态"+i);
					adapter.addNewItem(trade);
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		Log.d("========================= ","========================"); 
		Log.d(TAG,"第一条显示的数据是: "+firstVisibleItem);
		Log.d(TAG,"共显示 "+visibleItemCount+" 条数据");
		Log.d(TAG,"总共有 "+totalItemCount+" 条数据");
		
		if(totalItemCount == dataSize+1){
			noData();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE 
				&& adapter.getCount()-1 != dataSize
				){
			Log.d(TAG,""+scrollState);
			//loadMoreData();
		}
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
		
		private void addNewItem(Trades trade){
			trades.add(trade);
		}
	}
	
	private void showPopupWindow(View v){
		ListView menus = null;
		int windowWidth;
		int popupWindowWidth;
		int popupWindowHeight;
		int xPos = 0;
		final List<String> groups = new ArrayList<String>();
		
		WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		windowWidth = window.getDefaultDisplay().getWidth();
		popupWindowWidth = windowWidth/2;
		popupWindowHeight = 208;
		xPos = (windowWidth-popupWindowWidth)/2;
		
		if(popupWindow == null){
			View view = getLayoutInflater().inflate(R.layout.box_arrow_top, null);
			menus = (ListView) view.findViewById(R.id.menus);
			groups.add("全部收支");
			groups.add("近一周");
			GroupAdapter adapter = new GroupAdapter(this, groups);
			menus.setAdapter(adapter);
			popupWindow = new PopupWindow(view, popupWindowWidth,popupWindowHeight);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(v, xPos, 0);
		if(menus != null){
		  menus.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Toast.makeText(TradeList.this, groups.get(position), Toast.LENGTH_SHORT).show();
				if(popupWindow != null){
					popupWindow.dismiss();
				}
			}});
		}
	}
	
	public class GroupAdapter extends BaseAdapter{
		private Context context;
		private List<String> list;
		
		public GroupAdapter(Context c, List<String> list){
			this.context = c;
			this.list = list;
		}

		@Override
		public int getCount() {
			return this.list.size();
		}

		@Override
		public Object getItem(int position) {
			return this.list.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ViewHolder holder;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.popup_window_item, null);
				holder = new ViewHolder();
				view.setTag(holder);
				holder.groupItem = (TextView) view.findViewById(R.id.item);
			}else{
				holder = (ViewHolder) view.getTag();
			}
			holder.groupItem.setText(this.list.get(position));
			return view;
		}
	}
	
	static class ViewHolder{
		TextView groupItem;
	}
}