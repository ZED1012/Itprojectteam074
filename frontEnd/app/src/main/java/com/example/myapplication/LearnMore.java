package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class LearnMore extends AllPageToolBar{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_more);

        Button brochure = findViewById(R.id.brochure);
        brochure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrochurePage();
            }
        });
    }
    private void openBrochurePage(){
        String pdfUrl = "https://volpoll.org.au/wp-content/uploads/2020/05/VolPoll_WEB.pdf";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(pdfUrl));
        startActivity(intent);
    }
}
