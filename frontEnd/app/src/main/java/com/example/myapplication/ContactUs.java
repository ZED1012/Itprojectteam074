package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUs extends AppCompatActivity{

    private EditText contactName, contactEmail, contactSubject, contactMessage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        contactName = findViewById(R.id.name);
        contactEmail = findViewById(R.id.email);
        contactSubject = findViewById(R.id.subject);
        contactMessage = findViewById(R.id.message);

        Button send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                sendMsg(v);

            }

        });

    }

    void sendMsg (View v){


    }




}
