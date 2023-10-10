package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyToolBar extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_toolbar);
        View v = findViewById(R.id.detail_button); //需要设透明的控件的id
        v.getBackground().setAlpha(0); //0~255透明度值
    }
}
