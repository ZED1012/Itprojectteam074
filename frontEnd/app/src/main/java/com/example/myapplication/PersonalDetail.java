package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class Person {
    String firstName;
    String lastName;
    String email;
    String postCode;
    String group;
    boolean isGroup;
    boolean isClub;
    boolean isOrganisation;
    boolean isLeader;

    public Person(String FirstName, String LastName, String Email, String PostCode, String Group,
                  boolean isGroup, boolean isClub, boolean isOrganisation, boolean isLeader) {
        this.firstName = FirstName;
        this.lastName = LastName;
        this.email = Email;
        this.postCode = PostCode;
        this.group = Group;
        this.isGroup = isGroup;
        this.isClub = isClub;
        this.isOrganisation = isOrganisation;
        this.isLeader = isLeader;
    }
}

public class PersonalDetail extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPostCode, editTextGroup;
    private boolean isGroup, isClub, isOrganisation, isLeader;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_detail);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPostCode = findViewById(R.id.editTextPostCode);
        editTextGroup = findViewById(R.id.editTextGroup);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.groupRadio) {
                    isGroup = true;
                    isClub = false;
                    isOrganisation = false;
                } else if (checkedId == R.id.clubRadio) {
                    isGroup = false;
                    isClub = true;
                    isOrganisation = false;
                } else if (checkedId == R.id.organisationRadio) {
                    isGroup = false;
                    isClub = false;
                    isOrganisation = true;
                }
            }
        });

        RadioGroup leader = findViewById(R.id.leader);
        leader.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.lead) {
                    isLeader = true;
                } else {
                    isLeader = false;
                }
            }
        });

        Button startButton = findViewById(R.id.startSurvey);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetail(v);
                Intent intent = new Intent();
                intent.setClass(PersonalDetail.this, SurveyToolbar.class);
                startActivity(intent);
            }
        });
    }

    public void personalDetail(View view) {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String postCode = editTextPostCode.getText().toString();
        String groupName = editTextGroup.getText().toString();

        // Save the details in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.putString("email", email);
        editor.putString("post_code", postCode);
        editor.putString("group_name", groupName);
        editor.putBoolean("group", isGroup);
        editor.putBoolean("club", isClub);
        editor.putBoolean("organisation", isOrganisation);
        editor.putBoolean("leader", isLeader);
        editor.apply();
        //Person client = new Person(firstName,lastName,email,postCode,groupName,isGroup,isClub,isOrganisation,isLeader);
        String[] data = new String[5];
        data[0] = firstName;
        data[1] = lastName;
        data[2] = email;
        data[3] = groupName;
        data[4] = postCode;
        String group = getGroupType();
        String role = new String();
        if(isLeader){
            role = "leader";
        }else{
            role = "member";
        }
        //global var
        //ClientInfo client = (ClientInfo) getApplication();
        //client.setFirstName(firstName);



        // Preparing the JSON payload
        String jsonPayload = String.format(
                "{" +
                        "\"_group\": \"%s\"," +
                        "\"agree\": true," +
                        "\"email\": \"%s\"," +
                        "\"first_name\": \"%s\"," +
                        "\"group_type\": \"%s\"," +
                        "\"last_name\": \"%s\"," +
                        "\"peak\": true," +
                        "\"peak_details\": \"\"," +
                        "\"position\": \"%s\"," +
                        "\"postcode\": \"%s\"," +
                        "\"role_id\": 0" +
                        "}",
                groupName, email, firstName, getGroupType(), lastName, isLeader ? "leader" : "member", postCode
        );

        postToBackend("http://hf2019.natapp1.cc/auth/signup", jsonPayload);

        //transform data
        Intent intent=new Intent(PersonalDetail.this, SurveyPopupPage.class);
        intent.putExtra("basicDetail", data);
        intent.putExtra("group", group);
        intent.putExtra("isLeader", role);
        startActivity(intent);
    }

    private String getGroupType() {
        if (isGroup) {
            return "group";
        }
        if (isClub) {
            return "club";
        }
        if (isOrganisation) {
            return "organisation";
        }
        return "unknown";
    }

    private void postToBackend(String url, String jsonPayload) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonPayload);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PersonalDetail.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PersonalDetail.this, "Data sent successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}