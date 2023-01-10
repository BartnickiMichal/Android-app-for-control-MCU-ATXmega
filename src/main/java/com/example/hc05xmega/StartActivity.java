package com.example.hc05xmega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button btn_LED, btn_SW, btn_UART, btn_Received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btn_LED=(Button) findViewById(R.id.button_led);
        btn_SW=(Button)findViewById(R.id.button_switch);
        btn_UART=(Button)findViewById(R.id.button_uart);
        btn_Received=(Button)findViewById(R.id.button_received_data);
        btn_LED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLedActivity();
            }
        });
        btn_SW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSwitchActivity();
            }
        });
        btn_UART.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUartActivity();
            }
        });
        btn_Received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openReceivedActivity();

            }
        });
    }
    public void openLedActivity()
    {
        Intent intent = new Intent(this, LedActivity.class);
        startActivity(intent);
    }
    public void openSwitchActivity()
    {
        Intent intent = new Intent(this, SwitchActivity.class);
        startActivity(intent);
    }
    public void openUartActivity()
    {

        Intent intent = new Intent(this, UartActivity.class);
        startActivity(intent);

    }
    public void openReceivedActivity()
    {
        Intent intent = new Intent(this, ReceivedActivity.class);
        startActivity(intent);

    }
}