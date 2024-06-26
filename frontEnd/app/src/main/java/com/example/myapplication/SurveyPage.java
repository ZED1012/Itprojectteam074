package com.example.myapplication;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;


public class SurveyPage extends AppCompatActivity {

    private TextView fieldGroup, question, description;
    private RadioButton button1, button2, button3, button4, button5;

    private JSONArray dataArray;

    private int currentQuestionIndex = 0;

    private Button nextButton;
    private RadioGroup radioGroup;

    private ImageView loadingGif;

    private int[] answers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_pages);

        question = findViewById(R.id.question);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        fieldGroup = findViewById(R.id.fieldGroup);
        description = findViewById(R.id.description);

        nextButton = findViewById(R.id.nextButton);
        radioGroup = findViewById(R.id.group);
        loadingGif = findViewById(R.id.loading_gif);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i != -1){
                    nextButton.setText("Next");
                }
            }
        });

        //toolbar
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



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                int answer = -1;
                if (selectedRadioButtonId == R.id.button1) {
                    answer = 1;
                } else if (selectedRadioButtonId == R.id.button2) {
                    answer = 2;
                }else if (selectedRadioButtonId == R.id.button3) {
                    answer = 3;
                }else if (selectedRadioButtonId == R.id.button4) {
                    answer = 4;
                }else if (selectedRadioButtonId == R.id.button5) {
                    answer = 5;
                }
                answers[currentQuestionIndex] = answer;


                try {
                    int field_id = dataArray.getJSONObject(currentQuestionIndex).getInt("id");

                    String token = getToken();

                    postAnswerData(answer, field_id, token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (currentQuestionIndex < dataArray.length() - 1) {
                    currentQuestionIndex++;
                    updateUI();
                } else {
                    // Navigate to the EndSurvey page
                    Intent intent = new Intent(SurveyPage.this, EndSurvey.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button PreviouseButton = findViewById(R.id.previousButton);
        PreviouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    updateUI();
                }
            }
        });

        int role_id = getIntent().getIntExtra("role_id", 0);
        fetchQuestionData(role_id);
    }
    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
    private void postAnswerData(int answer, int field_id, String token) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        try {
            if (answer != -1) {
                json.put("answer", answer);
                json.put("skipped", JSONObject.NULL);
            } else {
                json.put("skipped", true);
            }
            json.put("field_id", field_id);
            json.put("textAnswer", JSONObject.NULL);
            json.put("token", token);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

            Request request = new Request.Builder()
                    .url("http://hf2019.natapp1.cc/answers")
                    .post(requestBody)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Handle the error here
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(SurveyPage.this, "Data fetched successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        //Toast.makeText(SurveyPage.this, "Failed to fetch data. Please check your connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateUI() {
        try {
            JSONObject currentQuestion = dataArray.getJSONObject(currentQuestionIndex);

            final String apiName = currentQuestion.getJSONObject("fieldGroups").getString("name");
            final String apiQuestion = currentQuestion.getString("question");
            final String apiDescription = currentQuestion.optString("description");
            final String apiType = currentQuestion.getString("type");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fieldGroup.setText(apiName);
                    question.setText(apiQuestion);

                    // Check if description is not "null"
                    if (!"null".equals(apiDescription)) {
                        description.setText(apiDescription);
                    } else {
                        description.setText("");
                    }

                    // Handle the visibility of the Previous button
                    Button previousButton = findViewById(R.id.previousButton);
                    if (currentQuestionIndex == 0) {
                        previousButton.setVisibility(View.GONE);
                    } else {
                        previousButton.setVisibility(View.VISIBLE);
                    }

                    // Clear radio group selection and set default button text
                    radioGroup.clearCheck();
                    nextButton.setText("Skip");
                    int storedAnswer = answers[currentQuestionIndex];
                    if(storedAnswer != 0) {
                        RadioButton selectedButton = (RadioButton) radioGroup.getChildAt(storedAnswer - 1);
                        selectedButton.setChecked(true);
                    }

                    // Hide loading gif now that we are updating the UI
                    loadingGif.setVisibility(View.GONE);

                    // Check the type of the question to adjust visibility
                    if ("Intro".equals(apiType)) {
                        radioGroup.setVisibility(View.GONE);
                        nextButton.setText("Next");
                    } else {
                        radioGroup.setVisibility(View.VISIBLE);
                    }

                    // Always make sure the Next button is visible after data is loaded
                    nextButton.setVisibility(View.VISIBLE);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void fetchQuestionData(int role_id) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://hf2019.natapp1.cc/fields?role_id=" + role_id;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();


        Glide.with(SurveyPage.this)
                .asGif()
                .load(R.drawable.loading)
                .centerCrop()
                .into(loadingGif);

        loadingGif.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.GONE);          // Hide the RadioGroup
        nextButton.setVisibility(View.GONE);          // Hide the Next button
        Button previousButton = findViewById(R.id.previousButton);
        previousButton.setVisibility(View.GONE);      // Hide the Previous button
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingGif.setVisibility(View.GONE);
                    }
                });
                // Handle the error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        dataArray = jsonObject.getJSONArray("data");
                        answers = new int[dataArray.length()];
                        updateUI();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingGif.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
    //toolbar
    public void showDetailPage(final View view) {
        Intent intent = new Intent();
        intent.setClass(SurveyPage.this, SurveyPopupPage.class);
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
        intent.setClass(SurveyPage.this, ForVolunteer.class);
        startActivity(intent);

    }

    //跳转leader的page
    protected void leaderPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyPage.this, ForLeader.class);
        startActivity(intent);
    }

    //跳转contact us page
    protected void contactUsPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyPage.this, ContactUs.class);
        startActivity(intent);
    }

    protected void learnMorePage(){
        Intent intent = new Intent();
        intent.setClass(SurveyPage.this, LearnMore.class);
        startActivity(intent);
    }
}