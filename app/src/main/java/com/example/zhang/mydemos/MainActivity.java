package com.example.zhang.mydemos;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.policy.PhoneWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends Activity implements View.OnTouchListener {

    private String DIR="zhangyibo";
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnTouchListener(this);


    }




    private void showDir() {

        // 获得缓存文件路径，磁盘空间不足或清除缓存时数据会被删掉，一般存放一些临时文件
        // /data/data/<application package>/cache目录
        File cacheDir = getCacheDir();
        Log.d("TAG", "getCacheDir() : " + cacheDir.getAbsolutePath());

        // 获得文件存放路径，一般存放一些需要长期保留的文件
        // /data/data/<application package>/files目录
        File fileDir = getFilesDir();
        Log.d("TAG", "getFilesDir() : " + fileDir.getAbsolutePath());

        // 这是一个可以存放你自己应用程序自定义的文件，你可以通过该方法返回的File实例来创建或者访问这个目录
        // /data/data/<application package>/
        File dir = getDir("fileName", MODE_PRIVATE);
        Log.d("TAG", "getDir() : " + dir.getAbsolutePath());

        // 获取应用程序外部存储的缓存目录路径
        // SDCard/Android/data/<application package>/cache目录
        File externalCacheDir = getExternalCacheDir();
        Log.d("TAG", "getExternalCacheDir() : " + externalCacheDir.getAbsolutePath());

        // 获取应用程序外部存储的某一类型的文件目录，
        // SDCard/Android/data/<application package>/files目录
        // 这里的类型有
        // Environment.DIRECTORY_MUSIC音乐
        // Environment.DIRECTORY_PODCASTS 音频
        // Environment.DIRECTORY_RINGTONES 铃声
        // Environment.DIRECTORY_ALARMS 闹铃
        // Environment.DIRECTORY_NOTIFICATIONS 通知铃声
        // Environment.DIRECTORY_PICTURES 图片
        // Environment.DIRECTORY_MOVIES 视频
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        Log.d("TAG", "getExternalFilesDir() : " + externalFilesDir.getAbsolutePath());

        // 获取应用的外部存储的缓存目录
        File[] externalCacheDirs = new File[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            externalCacheDirs = getExternalCacheDirs();
        }
        for (int i = 0; i < externalCacheDirs.length; i++) {
            Log.d("TAG", "getExternalCacheDirs() " + i + " : " + externalCacheDirs[i].getAbsolutePath());
        }

        // 获取应用的外部存储的某一类型的文件目录
        File[] externalFilesDirs = new File[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            externalFilesDirs = getExternalFilesDirs(Environment.DIRECTORY_MUSIC);
        }
        for (int i = 0; i < externalFilesDirs.length; i++) {
            Log.d("TAG", "getExternalFilesDirs() " + i + " : " + externalFilesDirs[i].getAbsolutePath());
        }

        // 获取应用的外部媒体文件目录
        File[] externalMediaDirs = new File[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            externalMediaDirs = getExternalMediaDirs();
        }
        for (int i = 0; i < externalMediaDirs.length; i++) {
            Log.d("TAG", "getExternalMediaDirs() " + i + " : " + externalMediaDirs[i].getAbsolutePath());
        }

        // 获得应用程序指定数据库的绝对路径
        // /data/data/<application package>/database/database.db目录
        File databasePath = getDatabasePath("database.db");
        Log.d("TAG", "getDatabasePath() : " + databasePath.getAbsolutePath());


        // -------------分界线-----------------------
        // 以下是一些共有的目录，与APP包名无关，不会随APP卸载被删除
        // /data目录
        File dataDirectory = Environment.getDataDirectory();
        Log.d("TAG", "Environment.getDataDirectory() : " + dataDirectory.getAbsolutePath());
        // /cache目录
        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
        Log.d("TAG", "Environment.getDownloadCacheDirectory() : " + downloadCacheDirectory.getAbsolutePath());
        // /sdcard目录
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        Log.d("TAG", "Environment.getExternalStorageDirectory() : " + externalStorageDirectory.getAbsolutePath());
        // /sdcard/Pictures目录
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("TAG", "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) : " + externalStoragePublicDirectory.getAbsolutePath());
        // /system目录
        File rootDirectory = Environment.getRootDirectory();
        Log.d("TAG", "Environment.getRootDirectory()() : " + rootDirectory.getAbsolutePath());
    }









    private void writeFiles(String content) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 如果sdcard存在
            File file = new File(getExternalFilesDir(null)
                    .toString()
                    + File.separator
                    + DIR
                    + File.separator
                    + "zhang.txt"); // 定义File类对象
            if (!file.getParentFile().exists()) { // 父文件夹不存在
                file.getParentFile().mkdirs(); // 创建文件夹
            }
            PrintStream out = null; // 打印流对象用于输出
            try {
                out = new PrintStream(new FileOutputStream(file, true)); // 追加文件
                out.println(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close(); // 关闭打印流
                }
            }
        } else { // SDCard不存在，使用Toast提示用户
            Toast.makeText(this, "保存失败，SD卡不存在！", Toast.LENGTH_LONG).show();
        }

    }


    public boolean getSDKState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    int x = 0;
    int y = 0;
    int rawx = 0;
    int rawy = 0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) event.getX();
                y = (int) event.getY();
                rawx = (int) event.getRawX();
                rawy = (int) event.getRawY();
                Log.e("TAG", "getX=" + x + "getY=" + y + "\n" + "getRawX=" + rawx
                        + "getRawY=" + rawy + "\n");
                    break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
