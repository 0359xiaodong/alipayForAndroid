package com.alipay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class TransferSelectContacter extends Activity {
	private static final String TAG = "Alipay";
	private BaseAdapter ba;
	private ListView lv;
	private LetterListView letterLV;
	private ContacterAsyncQueryHandler asyncQueryHandler;
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	
	private static final String NAME = "name", NUMBER = "number", SORT_KEY = "sort_key";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transfer_select_contacter);
		
		lv = (ListView) findViewById(R.id.dataList);
		
		asyncQueryHandler = new ContacterAsyncQueryHandler(this.getContentResolver());
		letterLV = (LetterListView) findViewById(R.id.letter_list);
		letterLV.setOnTouchingLetterChangedListener(new LetterListViewListener());
	}
	@Override
	protected void onResume(){
		super.onResume();
		Uri uri = Uri.parse("content://com.android.contacts/data/phones");
		String[] projection = {"display_name", "data1", "sort_key"};
		asyncQueryHandler.startQuery(0, null, uri, projection, null, null, "sort_key COLLATE LOCALIZED asc");
	}
	
	private class ContacterAsyncQueryHandler extends AsyncQueryHandler{
		public ContacterAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}
		
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor){
			if(cursor != null && cursor.getCount() > 0){
				List<ContentValues> list = new ArrayList<ContentValues>();
				cursor.moveToFirst();
				int cursorLen = cursor.getCount();
				for(int i = 0; i < cursorLen; i++){
					ContentValues cv = new ContentValues();
					cursor.moveToPosition(i);
					String name = cursor.getString(0);
					String number = cursor.getString(1);
					String sortKey = cursor.getString(2);
					if(number.startsWith("+86")){
						number = number.substring(3);
					}
					cv.put(NAME, name);
					cv.put(NUMBER, number);
					cv.put(SORT_KEY, sortKey);
					list.add(cv);
				}
				if(list.size() > 0){
					setAdatper(list);
				}
			}
		}
	}
	
	private void setAdatper(List<ContentValues> list){
		ba = new ListAdapter(this, list);
		lv.setAdapter(ba);
	}
	
	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<ContentValues> list;
		
		public ListAdapter(Context c, List<ContentValues> list){
			inflater = LayoutInflater.from(c);
			this.list = list;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];
			for(int i = 0; i < list.size(); i++){
				String currentStr = getAlpha(list.get(i).getAsString(SORT_KEY));
				String prevStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getAsString(SORT_KEY)) : " ";
				if(!prevStr.equals(currentStr)){
					String name = getAlpha(list.get(i).getAsString(SORT_KEY));
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.contacter_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.number = (TextView) convertView.findViewById(R.id.number);
				holder.type = (TextView) convertView.findViewById(R.id.type);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			ContentValues cv = list.get(position);
			holder.name.setText(cv.getAsString(NAME));
			holder.number.setText(cv.getAsString(NUMBER));
			String currentStr = getAlpha(list.get(position).getAsString(SORT_KEY));
			String prevStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getAsString(SORT_KEY)) : " ";
			if(!prevStr.equals(currentStr)){
				holder.type.setVisibility(View.VISIBLE);
				holder.type.setText(currentStr);
			}else{
				holder.type.setVisibility(View.GONE);
			}
			return convertView;
		}
		
		private class ViewHolder{
			TextView name;
			TextView number;
			TextView type;
		}
	}
	
	private String getAlpha(String str){
		if(str == null){
			return "#";
		}
		
		if(str.trim().length() == 0){
			return "#";
		}
		
		char c = str.trim().substring(0, 1).charAt(0);
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if(pattern.matcher(c + "").matches()){
			return (c + "").toUpperCase();
		}else{
			return "#";
		} 
	}
	
	private class LetterListViewListener implements OnTouchingLetterChangedListener{
		@Override
		public void onTouchingLetterChanged(String s) {
			Integer map = alphaIndexer.get(s);
			if(map != null){
				int position = alphaIndexer.get(s);
				lv.setSelection(position);
				Log.d(TAG, s);
			}
		}
	}
}
