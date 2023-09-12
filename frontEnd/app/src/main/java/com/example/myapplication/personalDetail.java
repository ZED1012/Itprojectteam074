package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class personalDetail extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPostCode, editTextGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_detail);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPostCode = findViewById(R.id.editTextPostCode);
        editTextGroup = findViewById(R.id.editTextGroup);

        Button startButton = findViewById(R.id.startSurvey);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalDetail(v);

                // to survey page


            }
        });
    }

    //personal detail
    public void personalDetail(View view){
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String postCode = editTextPostCode.getText().toString();
        String groupName = editTextGroup.getText().toString();

        // wait to add group/club/organization leader or not choices


        SharedPreferences preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.putString("email", email);
        editor.putString("post_code", postCode);
        editor.putString("group_name", groupName);
        editor.apply();

    }

}