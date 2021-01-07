package com.wy.letsgo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wy.letsgo.R;
import com.wy.letsgo.util.ExceptionUtil;

import org.androidannotations.annotations.AfterViews;

public class SplashActivity extends Activity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            handler.postDelayed(new Runnable() {

                @AfterViews
                public void run() {
                    try {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 1000);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }
}
