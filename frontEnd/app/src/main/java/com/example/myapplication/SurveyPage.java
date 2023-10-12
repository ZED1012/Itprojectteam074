package com.example.myapplication;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SurveyPage extends AppCompatActivity {

    private TextView fieldGroup, question, description;
    private RadioButton button1, button2, button3, button4, button5;
    private int currentQuestionIndex = 0;
    private JSONArray dataArray;

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
        Button NextButton = findViewById(R.id.nextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < dataArray.length() - 1) {
                    currentQuestionIndex++;
                    updateUI();
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
                    if (!apiDescription.isEmpty()) {
                        description.setText(apiDescription);
                    } else {
                        description.setText("");
                    }

                    RadioGroup group = findViewById(R.id.group);
                    if ("Intro".equals(apiType)) {
                        group.setVisibility(View.GONE);
                    } else {
                        group.setVisibility(View.VISIBLE);
                    }
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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle the error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        dataArray = jsonObject.getJSONArray("data");
                        updateUI(); // This function will be defined in the next step

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}