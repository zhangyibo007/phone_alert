package com.cloud.adapter.myview;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by zhang on 2017/10/10.
 */

public class PhoneReceiver extends BroadcastReceiver {

private Context mcontext;
    private WindowManager wm;

    @Override
    public void onReceive(Context context, Intent intent){
        mcontext=context;
        System.out.println("action"+intent.getAction());
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            //如果是去电（拨出）
            Log.e("TAG","拨出");
        }else{
            //查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电
            Log.e("TAG","来电");
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
            //设置一个监听器
        }
    }

    private TextView tv;
    private LayoutInflater inflate;
    private View phoneView;
    private PhoneStateListener listener=new PhoneStateListener(){

        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            // TODO Auto-generated method stub
            //state 当前状态 incomingNumber,貌似没有去电的API
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.e("TAG","挂断");
                    wm.removeView(tv);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.e("TAG","接听");
                    wm.removeView(tv);
                    break;
                case TelephonyManager.CALL_STATE_RINGING:

                    inflate= LayoutInflater.from(mcontext);
                    wm = (WindowManager)mcontext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                    params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    params.gravity= Gravity.CENTER;
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = 600;
                    params.format = PixelFormat.RGBA_8888;
                    phoneView=inflate.inflate(R.layout.phone_alert,null);
                    wm.addView(phoneView, params);
                    Log.e("TAG","响铃:来电号码"+incomingNumber);
                    Log.e("TAG","响铃:======"+Thread.currentThread().getName());
                    //输出来电号码
                    break;
            }
        }
    };
};

