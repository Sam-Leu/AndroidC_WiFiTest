package com.example.one.wifitest;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private WifiInfo wifiInfo = null;           // 获得的wifi信息
    private WifiManager wifiManager = null;     // wifi管理器
    private Handler handler;
    private ImageView wifi_img;                 // 信号图片显示
    private int level;                          // 信号强度值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi_img = (ImageView)findViewById(R.id.wifi_img);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        // 使用定时器,每隔5秒获得一次信号强度值
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                wifiInfo = wifiManager.getConnectionInfo();
                // 获得信号强度值
                level = wifiInfo.getRssi();
                if (level<=0 && level>=-50){
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
                else if(level<=-50 && level>=-70){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
                else if(level<=-70 && level>=-100){
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                }
                else{
                    Message message = new Message();
                    message.what = 4;
                    handler.sendMessage(message);
                }
            }
        },1000,5000);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){

                    case 1:
                        wifi_img.setImageResource(R.drawable.level4);
                        Toast.makeText(MainActivity.this,"信号非常好！",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        wifi_img.setImageResource(R.drawable.level3);
                        Toast.makeText(MainActivity.this,"信号一般般！",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        wifi_img.setImageResource(R.drawable.level2);
                        Toast.makeText(MainActivity.this,"信号比较差！",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        wifi_img.setImageResource(R.drawable.level1);
                        Toast.makeText(MainActivity.this,"信号非常差！",Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        wifi_img.setImageResource(R.drawable.level0);
                        Toast.makeText(MainActivity.this,"没有信号啦！",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

    }
}
