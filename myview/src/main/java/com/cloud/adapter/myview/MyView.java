package com.cloud.adapter.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhang on 2017/10/9.
 */

public class MyView extends View implements View.OnClickListener{
    private Paint mPaint;
    private Rect mRect;
    private int mCount=1;
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect=new Rect();
        setOnClickListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        String text=String.valueOf(mCount);
        mPaint.getTextBounds(text,0,text.length(),mRect);
        float textwidth=mRect.width();
        float textheight=mRect.height();
        canvas.drawText(text,getWidth()/2-textwidth/2,getHeight()/2+textheight/2,mPaint);

    }

    @Override
    public void onClick(View view) {
        mCount++;
        invalidate();
    }
}
