package com.alipay;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AlipayDialog{
	Context c;
	AlertDialog ad;
	TextView mes;
	Button ok;
	Button cancel;
	
	public AlipayDialog(Context c){
		this.c = c;
		ad = new AlertDialog.Builder(c).create();
		Window window = ad.getWindow();
		ad.show();
		window.setContentView(R.layout.dialog);
		mes = (TextView) window.findViewById(R.id.data);
		ok = (Button) window.findViewById(R.id.ok);
		cancel = (Button) window.findViewById(R.id.cancel);
	}
	
	public void dismiss(){
		ad.dismiss();
	}
	
	public void show(){
		ad.show();
	}
	
	public void setMessage(String s){
		mes.setText(s);
	}
	
	public void setOkBtn(String text, View.OnClickListener listener){
		ok.setText(text);
		ok.setOnClickListener(listener);
	}
	
	public void setCancelBtn(String text, View.OnClickListener listener){
		cancel.setText(text);
		cancel.setOnClickListener(listener);
	}
}