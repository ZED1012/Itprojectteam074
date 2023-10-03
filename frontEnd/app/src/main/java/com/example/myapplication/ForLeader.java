package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ForLeader extends AllPageToolBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_leader);

        //调用toolbar
        LayoutInflater factory = LayoutInflater.from(this);
        View layout = factory.inflate(R.layout.tool_bar, null);
        Toolbar pageOneToolbar = layout.findViewById(R.id.all_page_toolbar);







    }
}