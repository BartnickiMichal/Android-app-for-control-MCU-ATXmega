package com.example.hc05xmega;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.BitSet;
import java.lang.reflect.Array;

public class LedActivity extends SettingActivity {
    private Switch[] sw_led = new Switch[8];
    private DataCommunication DaCo = new DataCommunication(NewDevice_BluetoothSocket);
    private byte[] led_mode = new byte[]{(byte) 0xFF, 0x03, 0x00, 0x00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        for (int i = 0; i < 8; i++) {
            sw_led[i] = (Switch) findViewById(R.id.led0 + i);
        }


        sw_led[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(0, 0);

            }
        });
        sw_led[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_led(1, 1);


            }
        });
        sw_led[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(2, 2);

            }
        });
        sw_led[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(3, 3);
            }
        });
        sw_led[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(4, 4);
            }
        });
        sw_led[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(5, 5);
            }
        });
        sw_led[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(6, 6);
            }
        });
        sw_led[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_led(7, 7);
            }
        });


    }

    private void set_led(int number_led, int bytes_move) {
        if (sw_led[number_led].isChecked()) {
            if (NewDevice_BluetoothSocket != null) {
                led_mode[0] = (byte) (led_mode[0] & ~(1 << bytes_move));
                DaCo.write(led_mode);
            }
        } else {

            if (NewDevice_BluetoothSocket != null) {
                led_mode[0] = (byte) (led_mode[0] | (1 << bytes_move));
                DaCo.write(led_mode);

            }


        }

    }
}