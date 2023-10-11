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

public class SurveyPopupPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_popup_page);
        //获得数据
        Intent intent = getIntent();
        String[] basicDetail = intent.getStringArrayExtra("basicDetail");
        String group = intent.getStringExtra("group");
        String role = intent.getStringExtra("isLeader");
        //修改部分



        //ClientInfo client = (ClientInfo) this.getApplication();

        //在edittext中写入历史数据
        /*
        EditText firstName = (EditText) findViewById(R.id.FirstName);
        EditText lastName = (EditText) findViewById(R.id.LastName);
        EditText email = (EditText) findViewById(R.id.Email);
        EditText groupName = (EditText) findViewById(R.id.Group);
        EditText postCode = (EditText) findViewById(R.id.PostCode);
        firstName.setText(basicDetail[0].toCharArray(), 0, basicDetail[0].length());//client.getFirstName()
        lastName.setText(basicDetail[1].toCharArray(), 0, basicDetail[1].length());
        email.setText(basicDetail[2].toCharArray(), 0, basicDetail[2].length());
        groupName.setText(basicDetail[3].toCharArray(), 0, basicDetail[3].length());
        postCode.setText(basicDetail[4].toCharArray(), 0, basicDetail[4].length());*/




        Button startButton = findViewById(R.id.upload_changes);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetail(basicDetail[0], basicDetail[1], basicDetail[2], basicDetail[3], basicDetail[4], group, role);
            }
        });


        //修改部分
        final ImageButton button = (ImageButton) findViewById(R.id.survey_page_button);
        final ImageButton detailButton = findViewById(R.id.detail_button);
        final Toolbar toolbar = findViewById(R.id.survey_page_toolbar);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showPopupMenu(button);
            }
        });
        detailButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDetailPage(detailButton);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View w) {
                finish();
            }
        });
    }

    public void showDetailPage(final View view) {
        Intent intent = new Intent();
        intent.setClass(SurveyPopupPage.this, SurveyPopupPage.class);
        startActivity(intent);
    }


    protected void showPopupMenu(final View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, 5);//这里可以添加gravity在最后,right的int为5
        popupMenu.inflate(R.menu.menu_popup);//显示menu
        popupMenu.show();//显示菜单

        // menu点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.volunteer) {
                    volunteerPage();
                    return true;
                }
                if (item.getItemId() == R.id.leader) {
                    leaderPage();
                    return true;
                }
                if (item.getItemId() == R.id.contact_us) {
                    contactUsPage();
                    return true;
                }
                if (item.getItemId() == R.id.learn_more) {
                    learnMorePage();
                    return true;
                }
                return false;
            }
        });
        //关闭事件 popupMenu.dismiss();放入case中
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });

    }

    protected void volunteerPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyPopupPage.this, ForVolunteer.class);
        startActivity(intent);

    }

    //跳转leader的page
    protected void leaderPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyPopupPage.this, ForLeader.class);
        startActivity(intent);
    }

    //跳转contact us page
    protected void contactUsPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyPopupPage.this, ContactUs.class);
        startActivity(intent);
    }

    protected void learnMorePage(){
        Intent intent = new Intent();
        intent.setClass(SurveyPopupPage.this, LearnMore.class);
        startActivity(intent);
    }




    public void personalDetail(String firstName, String lastName, String email,
                               String groupName, String postCode, String group, String role) {
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
                groupName, email, firstName, group, lastName, role, postCode
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
