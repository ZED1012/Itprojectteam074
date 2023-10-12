package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);


        TextView pageOneText = findViewById(R.id.page1_text);
        Toolbar pageOneToolbar = findViewById(R.id.page1_toolbar);

        //popup menu部分
        final ImageButton button = (ImageButton) findViewById(R.id.page1_menu_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(button);

            }
        });

        //start survey部分
        final Button startSurvey = (Button) findViewById(R.id.page1_start);
        startSurvey.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(WelcomePage.this, PersonalDetail.class);
                startActivity(intent);

            }
        });
    }

    //弹出menu
    private void showPopupMenu(final View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, 5);//这里可以添加gravity在最后,right的int为5
        popupMenu.inflate(R.menu.menu_popup);//显示menu
        popupMenu.show();//显示菜单

        // menu点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.volunteer) {
                    volunteerPage(view);
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

    //跳转volunteer的page
    private void volunteerPage(final View view) {
        Intent intent = new Intent();
        intent.setClass(WelcomePage.this, ForVolunteer.class);
        startActivity(intent);

    }

    //跳转leader的page
    private void leaderPage() {
        Intent intent = new Intent();
        intent.setClass(WelcomePage.this, ForLeader.class);
        startActivity(intent);
    }

    //跳转contact us page
    private void contactUsPage() {
        Intent intent = new Intent();
        intent.setClass(WelcomePage.this, ContactUs.class);
        startActivity(intent);
    }
    protected void learnMorePage(){
        Intent intent = new Intent();
        intent.setClass(WelcomePage.this, LearnMore.class);
        startActivity(intent);
    }

}