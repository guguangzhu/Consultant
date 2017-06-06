package com.toda.consultant;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.toda.consultant.util.UserUtils;

public class SplashActivity extends AppCompatActivity {
    public final int TO_MAIN=1;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TO_MAIN:
                    if(UserUtils.isLogin()){
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
//                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            // 透明状态栏
             getWindow().addFlags(
             WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TO_MAIN);
            }
        },2000);
    }
}
