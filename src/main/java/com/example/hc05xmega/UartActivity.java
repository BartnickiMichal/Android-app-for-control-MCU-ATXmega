package com.example.hc05xmega;

import static java.lang.Thread.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
//package com.journaldev.threads;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.concurrent.TimeUnit;

public class UartActivity extends SettingActivity{
    private TextInputEditText textInput;
    private Button bt_send;
    private DataCommunication DaCo = new DataCommunication(NewDevice_BluetoothSocket);
    private byte[] uart_mode= new byte[]{0x01,0x09,0x00,0x00};
    private String send_uart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(NewDevice_BluetoothSocket!=null)
            DaCo.write(uart_mode);

        setContentView(R.layout.activity_uart);
        bt_send=(Button)findViewById(R.id.button_send);
        textInput=findViewById(R.id.text_input);
        Editable text = textInput.getText();
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send_uart=text.toString();
                if(NewDevice_BluetoothSocket!=null)
                    DaCo.write(send_uart.getBytes());
                for(int i=0; i<3; i++) {
                    try {
                        sleep(1000); // delay zeby nie wyslal informacji o trybie po wyswietleniu tekstu
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(NewDevice_BluetoothSocket!=null)
                {
                    DaCo.write(uart_mode);

                }

                text.clear();
                finish();
                startActivity(getIntent());
            }
        });

    }

    private void openUartActivity() {
        Intent intent = new Intent(this, UartActivity.class);
        startActivity(intent);

    }


}
