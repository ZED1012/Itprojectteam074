package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        View v = findViewById(R.id.page1_menu_button); //需要设透明的控件的id
        v.getBackground().setAlpha(0);       //0~255透明度值


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




    }

    //弹出按钮框
    private void showPopupMenu(final View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, 5);//这里可以添加gravity在最后,right的int为5
        popupMenu.inflate(R.menu.menu_popup);//menu 布局 getMenuInflater(). , popupMenu.getMenu()
        popupMenu.show();//显示菜单

        // 点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.volunteer) {
                    volunteerPage(view);
                    return true;//return true
                }
                if (item.getItemId() == R.id.leader) {
                    leaderPage();
                    return true;//return true
                }
                if (item.getItemId() == R.id.contact_us) {
                    contactUsPage();
                    return true;//return true
                }
                return false;
            }
        });
        //关闭事件 popupMenu.dismiss();放入case中
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(view.getContext(), "close", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //跳转volunteer的page
    private void volunteerPage(final View view) {
        Toast.makeText(view.getContext(), "Volunteer Page Open", Toast.LENGTH_SHORT).show();
    }

    //跳转leader的page
    private void leaderPage() {

    }

    //跳转contact us page
    private void contactUsPage() {

    }
}