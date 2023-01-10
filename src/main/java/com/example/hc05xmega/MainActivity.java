package com.example.hc05xmega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private Button btn_start;
  private Button btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start=(Button)findViewById(R.id.button_start);
        btn_setting=(Button)findViewById(R.id.button_settings);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartActvity();
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingActivity();

            }
        });

    }
    public void openStartActvity()
    {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
    public void openSettingActivity()
    {
        Intent intent = new Intent (this, SettingActivity.class);
        startActivity(intent);
    }
}