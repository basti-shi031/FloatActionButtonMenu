package com.basti.floatingactionbutton;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.basti.floatingactionbuttonmenu.FabMenu;
import com.basti.floatingactionbuttonmenu.OnItemClick;

public class MainActivity extends AppCompatActivity {

    private FabMenu mFabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFabMenu = (FabMenu) findViewById(R.id.fabMenu);

        mFabMenu.addFab(ColorStateList.valueOf(Color.RED),getResources().getDrawable(R.mipmap.ic_create_white_36dp));
        mFabMenu.addFab(ColorStateList.valueOf(Color.GREEN),getResources().getDrawable(R.mipmap.ic_drafts_white_36dp));
        mFabMenu.addFab(ColorStateList.valueOf(Color.BLUE),getResources().getDrawable(R.mipmap.ic_send_white_36dp));
        mFabMenu.setOnItemClick(new OnItemClick() {
            @Override
            public void onRootFabClick() {
                Toast.makeText(getApplicationContext(),"点击了root按钮", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildFabChild(int index) {
                Toast.makeText(getApplicationContext(), "点击了第" + index + "个子按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
