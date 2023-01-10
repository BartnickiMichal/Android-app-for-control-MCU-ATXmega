package com.example.hc05xmega;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SwitchActivity extends SettingActivity {
private Button bt_sw1, bt_sw2, bt_sw3, bt_sw4;
private DataCommunication DaCo = new DataCommunication(NewDevice_BluetoothSocket);
private String SwAc_send="2";
    private byte[] sw_mode= new byte[]{0x00,0x05,0x00,0x00};
    private byte[] TEST_1 = new byte[]{0x51,0x55,0x42,0x11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        bt_sw1=(Button) findViewById(R.id.button_sw1);
        bt_sw2=(Button) findViewById(R.id.button_sw2);
        bt_sw3=(Button) findViewById(R.id.button_sw3);
        bt_sw4=(Button) findViewById(R.id.button_sw4);

        bt_sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw_mode[0]=0x01;
                if(bluetooth_class.BlSocket !=null)
                {
                    DaCo.write(sw_mode);
                }

                //DaCo.write(SwAc_send.getBytes());


            }
        });
        bt_sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetooth_class.BlSocket !=null)
                {
                    sw_mode[0]=0x02;
                    DaCo.write(sw_mode);
                }


            }
        });
        bt_sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetooth_class.BlSocket !=null)
                {
                    sw_mode[0]=0x03;
                    DaCo.write(sw_mode);
                }
            }
        });
        bt_sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetooth_class.BlSocket !=null)
                {
                    sw_mode[0]=0x04;
                    DaCo.write(sw_mode);
                }

            }
        });




    }
}