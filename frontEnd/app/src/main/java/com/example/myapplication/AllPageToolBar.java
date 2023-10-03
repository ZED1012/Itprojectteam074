package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

public class AllPageToolBar extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.tool_bar);


    }
    @Override
    protected void onResume()
    {
        super.onResume();
        onPostOnCreate();
    }

    public final void onPostOnCreate()//find同一个控件
    {
        InitSetting();
        InitEvent();
    }

    /**
     * 初始化参数
     */

    protected void InitSetting(){

        //绑定两个groupRadio
        final ImageButton button = (ImageButton) findViewById(R.id.all_page_menu_button);

        //停止按钮
        final Toolbar toolbar = (Toolbar) findViewById(R.id.all_page_toolbar);
    }

    /**
     * 初始化事件
     */
    protected void InitEvent(){
        final ImageButton button = (ImageButton) findViewById(R.id.all_page_menu_button);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.all_page_toolbar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(button);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View w){
                finish();
            }
        });
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
    protected void volunteerPage(final View view) {
        Intent intent = new Intent();
        intent.setClass(AllPageToolBar.this, ForVolunteer.class);
        startActivity(intent);

    }

    //跳转leader的page
    protected void leaderPage() {
        Intent intent = new Intent();
        intent.setClass(AllPageToolBar.this, ForLeader.class);
        startActivity(intent);
    }

    //跳转contact us page
    protected void contactUsPage() {
        Intent intent = new Intent();
        intent.setClass(AllPageToolBar.this, ContactUs.class);
        startActivity(intent);
    }

}



