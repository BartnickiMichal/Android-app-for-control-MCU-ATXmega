package com.example.hc05xmega;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.example.hc05xmega.bluetooth_class.MY_UUID;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.lang.Object;
import java.util.Timer;

public class SettingActivity extends AppCompatActivity {

    public static BluetoothSocket NewDevice_BluetoothSocket;
    private Button bt_connect_bluetooth, bt_search;
    private bluetooth_class bl_class = new bluetooth_class();
    private Spinner bt_spinner;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<BluetoothDevice> arrayList_adress = new ArrayList<BluetoothDevice>();
    private boolean IsSearchBluetooth=false;
    private String SelectedDevice;
    private BluetoothAdapter ba;
    private BluetoothDevice bd;

    public SettingActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        bt_search = (Button) findViewById(R.id.button_search);
        bt_connect_bluetooth = (Button) findViewById(R.id.button_connected_blue);
        bt_spinner = findViewById(R.id.spinner_connected_blue);
      //  arrayList.add("Wybierz");


        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsSearchBluetooth)
                {
                    ba.cancelDiscovery(); // po ponownym nacisnieciu w wyszukiwanie reset

                }
                arrayList.clear();
                arrayList_adress.clear();
                search();

                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
                arrayAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
                bt_spinner.setAdapter(arrayAdapter);
            }

            private void search() { //wyszukiwanie urzadzen
                ba = BluetoothAdapter.getDefaultAdapter();
                if (!ba.isEnabled()) {
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(i);
                    ba.startDiscovery();
                } else {
                    Toast.makeText(getBaseContext(), "START DISCOVERY...", Toast.LENGTH_SHORT).show();
                    ba.startDiscovery();
                    IsSearchBluetooth=true;
                }
                final BroadcastReceiver br = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                            BluetoothDevice bd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            if (bd.getName() != null &&!arrayList.contains(bd.getName())) {
                                arrayList.add(bd.getName());
                                arrayList_adress.add(bd);

                            }

                            arrayAdapter.notifyDataSetChanged(); // aktualizuj zmiane
                        }
                    }
                };
                IntentFilter ifr = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(br, ifr);
            }
        });

        bt_connect_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsSearchBluetooth) {
                    ba.cancelDiscovery(); //przestan wyszukiwac
                    Connect();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Nie wyszukales urzadzenia!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    void WaitForPairing() { // poczekanie na parowanie, wchodzi kiedy zmieni sie stan parowania
        BroadcastReceiver BrRe = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                    if (bd.getBondState() == BluetoothDevice.BOND_BONDED) {
                        ConnectedWithPairedDevice(); // sprawono, polacz
                    }

                }


            }

        };
        IntentFilter ifr = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(BrRe, ifr);

    }

    private void Connect() {

            SelectedDevice = bt_spinner.getSelectedItem().toString(); //wybrane urzadzenie
            for (BluetoothDevice device : arrayList_adress) {
                if (SelectedDevice.equals(device.getName())) {
                    bd = ba.getRemoteDevice(device.getAddress());
                    ConnectedWithPairedDevice();
                    if (bd.getBondState() != BluetoothDevice.BOND_BONDED) {
                        bd.createBond();
                        WaitForPairing();
                    }
                }
            }

    }

    private void ConnectedWithPairedDevice() {
        try {
            NewDevice_BluetoothSocket = bd.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            Toast.makeText(getBaseContext(), "Polaczono Socket!", Toast.LENGTH_SHORT).show();
            NewDevice_BluetoothSocket.connect();
            bl_class.BlDevice = bd;
            bl_class.GetConnected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
