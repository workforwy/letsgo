package com.wy.letsgo.view;

import com.wy.letsgo.TApplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.ComponentActivity;

public class BaseActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TApplication.listActivity.add(this);
    }

}
