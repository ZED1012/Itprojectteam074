package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import android.widget.Button;

import android.graphics.Color;

public class EndSurvey extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_survey);

        ImageView imageView = findViewById(R.id.completed_gif);
        Glide.with(this).load(R.drawable.completed).into(imageView);//gif
    }
}