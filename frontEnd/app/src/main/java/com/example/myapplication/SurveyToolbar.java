package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

public class SurveyToolbar extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.survey_toolbar);
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
        intent.setClass(SurveyToolbar.this, SurveyPopupPage.class);
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
        intent.setClass(SurveyToolbar.this, ForVolunteer.class);
        startActivity(intent);

    }

    //跳转leader的page
    protected void leaderPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyToolbar.this, ForLeader.class);
        startActivity(intent);
    }

    //跳转contact us page
    protected void contactUsPage() {
        Intent intent = new Intent();
        intent.setClass(SurveyToolbar.this, ContactUs.class);
        startActivity(intent);
    }

    protected void learnMorePage(){
        Intent intent = new Intent();
        intent.setClass(SurveyToolbar.this, LearnMore.class);
        startActivity(intent);
    }

}

