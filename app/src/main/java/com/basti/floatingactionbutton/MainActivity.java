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

    private FloatingActionButton mFab;
    private FabMenu mFabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFabMenu = (FabMenu) findViewById(R.id.fabMenu);

        mFabMenu.addFab(ColorStateList.valueOf(Color.RED),null);
        mFabMenu.addFab(ColorStateList.valueOf(Color.GREEN),null);
        mFabMenu.addFab(ColorStateList.valueOf(Color.BLUE),null);
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
        //mFab.setRippleColor();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
