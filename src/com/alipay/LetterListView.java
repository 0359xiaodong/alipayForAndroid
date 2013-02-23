package com.alipay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class LetterListView extends View{
	OnTouchingLetterChangedListener OnTouchingLetterChangedListener;
	String[] b = {"#","A","B","C","D","E","F","G","H","I","J","K","L"
			,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;
	
	private LinearLayout top;
	private int topHeight;
	
	public LetterListView(Context c, AttributeSet attrs, int defStyle){
		super(c, attrs, defStyle);
	}
	
	public LetterListView(Context c, AttributeSet attrs){
		super(c, attrs);
	}
	
	public LetterListView(Context context) {
		super(context);
	}
	
	@Override 
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//canvas.
		if(showBkg){
			canvas.drawColor(Color.parseColor("#40000000"));
		}
		int height = this.getHeight();
		int width = this.getWidth();
		int singleHeight = (height-43) / b.length;
		top = (LinearLayout) findViewById(R.id.top);
		for(int i = 0; i < b.length; i++){
			paint.setColor(Color.parseColor("#cccccc"));
			paint.setTextSize(10);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			if(i == choose){
				paint.setColor(Color.parseColor("#000000"));
				paint.setFakeBoldText(true);
			}
			float x = width - (paint.measureText(b[i])+11);
			float y = singleHeight*i + singleHeight;
			if(top != null){topHeight = top.getHeight();}
			else{topHeight = 43;};
			y += topHeight;
			canvas.drawText(b[i], x, y, paint);
			paint.reset();
		}
	}
	
	@Override 
	public boolean dispatchTouchEvent(MotionEvent ev){
		final int action = ev.getAction();
		final float y = ev.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = OnTouchingLetterChangedListener;
		final int c = (int) (y/getHeight()*b.length);
		switch(action){
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if(oldChoose != c && listener != null){
				if(c > 0 && c < b.length){
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(oldChoose != c && listener != null){
				if(c > 0 && c < b.length){
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev){
		return super.onTouchEvent(ev);
	}
	
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener OnTouchingLetterChangedListener){
		this.OnTouchingLetterChangedListener = OnTouchingLetterChangedListener;
	}
}