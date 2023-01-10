package com.example.hc05xmega;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import android.content.IntentFilter;


public class bluetooth_class   {

    public BluetoothDevice BlDevice = null;
    public BluetoothAdapter BluAdapter = BluetoothAdapter.getDefaultAdapter();
    public static BluetoothSocket BlSocket;
    public Set<BluetoothDevice> BlPairedDevices = null;
    public ArrayList<String> alPairedDevices=new ArrayList<>();
    public ArrayAdapter<String> arrayAdapter;
    public boolean Bluetooth_Connected_check=false;

   static final UUID MY_UUID =UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //public Spinner bt_spinner_bluetooth_class;


    bluetooth_class() {
    }



    int getBLluPairedDevices() {
        int flag_paired = 0;

        if (BluAdapter == null) {
            //nieznaleziono urzadzen

            flag_paired = 2;
        }  if (!BluAdapter.isEnabled()) {
            flag_paired = 3;
            // trzeba wlaczyc bluetooth


        }  if (BluAdapter.enable()) {
            flag_paired = 1;
            BluAdapter.enable(); //??
            //BlPairedDevices = BluAdapter.getBondedDevices();




        }
        if(flag_paired==1) {

            alPairedDevices.add("Wybierz");
            if (BlPairedDevices.size() > 0) {
                for (BluetoothDevice device : BlPairedDevices) {
                    String deviceName = device.getName();
                    String deviceHWAdress = device.getAddress();
                    alPairedDevices.add(device.getName());
                    //String test = SeAc.bt_spinner.getSelectedItem().toString();
                }
            }


        }
        return flag_paired;
    }


    void GetConnected()
    {

        try {
            BlSocket=BlDevice.createRfcommSocketToServiceRecord(MY_UUID);
            BlSocket.connect();
            Bluetooth_Connected_check=true;



        }
        catch(IOException e)
        {
            Bluetooth_Connected_check=false;
            e.printStackTrace();
        }


    }




}




