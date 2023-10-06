package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PersonalDetail extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPostCode, editTextGroup;
    private boolean isGroup, isClub, isOrganisation, isLeader;

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
        //RadioButton lead = findViewById(R.id.lead);
        leader.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.lead){
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

                // to survey page
                goToSurveyActivity(v);
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
    }
    //测试传输数据给后端
     public void goToSurveyActivity(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = "";//直接copy后端格式
                    OkHttpClient client = new OkHttpClient();
                    /*1.后端电脑ip地址（服务器地址)+:+端口号
                    * 2.用post方法发送信息
                    * */
                    Request request = new Request.Builder()
                            .url("http://")
                            .post(RequestBody.create(MediaType.parse("application/json"),json))
                            .build();//创建http请求
                    Response response = client.newCall(request).execute();//执行发送的指令
                    //操作主线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonalDetail.this,"connect success",Toast.LENGTH_SHORT).show();
                        }
                    });



                }catch(Exception e){
                    e.printStackTrace();
                    //因为子线程不能直接操作ui，所以添加下面的runOnUIThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonalDetail.this,"connect fail",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
     }

}