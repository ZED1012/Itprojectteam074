package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SurveyPopupPage extends AllPageToolBar {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_popup_page);
        //获得数据
        ClientInfo client = (ClientInfo) this.getApplication();

        //在edittext中写入历史数据
        EditText firstName = (EditText) findViewById(R.id.FirstName);
        EditText lastName = (EditText) findViewById(R.id.LastName);
        EditText email = (EditText) findViewById(R.id.Email);
        EditText groupName = (EditText) findViewById(R.id.Group);
        EditText postCode = (EditText) findViewById(R.id.PostCode);
        firstName.setText(client.getFirstName().toCharArray(), 0, client.getFirstName().length());
        lastName.setText(client.getLastName().toCharArray(), 0, client.getLastName().length());
        email.setText(client.getEmail().toCharArray(), 0, client.getEmail().length());
        groupName.setText(client.getGroup().toCharArray(), 0, client.getGroup().length());
        postCode.setText(client.getPostCode().toCharArray(), 0, client.getPostCode().length());


        Button startButton = findViewById(R.id.upload_changes);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetail(client.getFirstName(), client.getLastName(), client.getEmail(),
                        client.getGroup(), client.getPostCode(), Role(), client.isLeader(), client.getRoleId());

            }
        });


    }
    public String Role(){
        ClientInfo client = (ClientInfo) this.getApplication();
        if (client.isGroup()) {
            return "group";
        }
        if (client.isClub()) {
            return "club";
        }
        if (client.isOrganisation()) {
            return "organisation";
        }
        return "unknown";
    }




    public void personalDetail(String firstName, String lastName, String email,
                               String groupName, String postCode, String group, boolean isLeader, int roleId) {
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
                        "\"role_id\": %d" +
                        "}",
                groupName, email, firstName, group, lastName, isLeader ? "leader" : "member", postCode,roleId
        );

        postToBackend("http://hf2019.natapp1.cc/auth/signup", jsonPayload);
    }

    private void postToBackend(String url, String jsonPayload) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create
                (MediaType.parse("application/json; charset=utf-8"), jsonPayload);
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
                        Toast.makeText(SurveyPopupPage.this, "Network error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SurveyPopupPage.this, "Data sent successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
