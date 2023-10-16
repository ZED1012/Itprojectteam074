package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;



public class EndSurvey extends AllPageToolBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_survey);

        ImageView imageView = findViewById(R.id.completed_gif);
        Glide.with(this).load(R.drawable.completed).into(imageView);//gif

        TextView textView = findViewById(R.id.textView);
        ImageButton menuButton = findViewById(R.id.all_page_menu_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(EndSurvey.this, LearnMore.class);
                startActivity(intent);
            }
        });

    }
}