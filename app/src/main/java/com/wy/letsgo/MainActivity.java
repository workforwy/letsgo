package com.wy.letsgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.wy.letsgo.entity.UserEntity;
import com.wy.letsgo.util.ExceptionUtil;
import com.wy.letsgo.util.LogUtil;
import com.wy.letsgo.view.LoginActivity;

import org.androidannotations.annotations.AfterViews;

public class MainActivity extends Activity {
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
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
