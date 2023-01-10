package com.example.hc05xmega;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Byte;
import java.util.ArrayList;

public class ReceivedActivity extends SettingActivity {

    private DataCommunication DaCo = new DataCommunication(NewDevice_BluetoothSocket);
    private TextView TV;
    private byte[] message_from_bl;
    private ArrayList<Integer> led_buff=new ArrayList<Integer>();
    Button BtReceivedData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        TV=(TextView) findViewById(R.id.textView_show);
        BtReceivedData=(Button) findViewById(R.id.received_data_button);
        if(NewDevice_BluetoothSocket!=null) {

            if(DaCo.numBytes!=0)
            {
                message_from_bl= DaCo.RecevedDataBytes;

             //   Toast.makeText(getBaseContext(), "XD0"+DaCo.mmBuffer[0] , Toast.LENGTH_SHORT).show();


            }

        }
        BtReceivedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaCo.run();
                int received_buff=Byte.toUnsignedInt(DaCo.mmBuffer[0]);
               // Toast.makeText(getBaseContext(), "Nacisnieto SW"+received_buff, Toast.LENGTH_SHORT).show();
                if(received_buff<<4==0x2E0 || received_buff<<4==0x1E0 || received_buff<<4==0x3E0 || received_buff<<4==0x4E0)
                { //SW0-4 //ok
                    Toast.makeText(getBaseContext(), "Nacisnieto SW"+((received_buff>>4)-1), Toast.LENGTH_LONG).show();
                }
                else
                {
                    for(int i=0; i<8; i++)
                    {
                        if((received_buff&(1<<i))==0)
                        {
                            led_buff.add(i);

                        }

                    }

                    Toast.makeText(getBaseContext(), "ZAPALONE LED TO "+led_buff, Toast.LENGTH_LONG).show();
                   led_buff.clear();
                    /*
                    switch(received_buff)
                    {


                        case 1:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 0", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 1", Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 2", Toast.LENGTH_SHORT).show();
                            break;
                        case 9:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 3", Toast.LENGTH_SHORT).show();
                            break;
                        case 17:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 4", Toast.LENGTH_SHORT).show();
                            break;
                        case 33:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 5", Toast.LENGTH_SHORT).show();
                            break;
                        case 65:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 6", Toast.LENGTH_SHORT).show();
                            break;
                        case 127:
                            Toast.makeText(getBaseContext(), "Wlaczono LED 7", Toast.LENGTH_SHORT).show();
                            break;



                    }

                     */



                }



            //notify();

            }
        });

    }




}