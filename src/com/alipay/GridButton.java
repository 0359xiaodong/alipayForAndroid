package com.alipay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class GridButton extends BaseAdapter{
	private Context context;
	private String[] appTitles;
	private int[] appIcons;
	
	public GridButton(Context context, int[] appIcons, String[] appTitles){
		this.context = context;
		this.appTitles = appTitles;
		this.appIcons = appIcons;
	}
	
	@Override
	public int getCount() {
		return appIcons.length;
	}

	@Override
	public Object getItem(int position) {
		return appIcons[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		View gridView;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null){
			gridView = new View(this.context);
			gridView = inflater.inflate(R.layout.grid_item, null);
			TextView textView = (TextView) gridView.findViewById(R.id.gridText);
			textView.setText(this.appTitles[position]);
			
			ImageButton appButton = (ImageButton) gridView.findViewById(R.id.gridBtn);
			appButton.setImageResource(appIcons[position]);
		}else{
			gridView = convertView;
		}
		
		return gridView;
	}
	
}