package com.cloud.adapter.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zhang on 2017/10/11.
 */

public class MyLinearLayout extends LinearLayout {
    private final int width;
    private final int height;
    private float mLastTouchX;
    private float mLastTouchY;

    private int INVALID_POINTER_ID = -1000;
    private int mActivePointerId = INVALID_POINTER_ID;
    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
            float tranX=0; //悬浮窗移动位置的相对值
            float tranY = 0;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                    mLastTouchX = ev.getX();
                    mLastTouchY= ev.getY();
                Log.e("YTAG","====="+mLastTouchX);
                Log.e("YTAG","--------"+mLastTouchY);

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x =ev.getX();
                final float y = ev.getY();

                tranX = mLastTouchX-x;
                tranY = mLastTouchY-y;
                scrollTo((int)( tranX),(int)(tranY));
                break;
            }
            case MotionEvent.ACTION_UP: {
                mLastTouchX=ev.getX();
                mLastTouchY=ev.getY();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                break;
            }
        }
        return true;
    }
}
