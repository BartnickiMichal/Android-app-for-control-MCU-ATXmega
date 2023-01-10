package com.example.hc05xmega;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import	android.os.Message;

public class DataCommunication extends Thread {
    private final BluetoothSocket mmSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
    public byte[] mmBuffer; // mmBuffer store for the stream
    public int numBytes;
    public byte[] RecevedDataBytes;
    public int test_value;
    public final static int MESSAGE_READ = 2;
    private byte[] mmBuffer1;

    public DataCommunication(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
if(mmSocket!=null) {
    // Get the input and output streams; using temp objects because
    // member streams are final.
    try {
        tmpIn = socket.getInputStream();
    } catch (IOException e) {
        Log.e(TAG, "Error occurred when creating input stream", e);
    }
    try {
        tmpOut = socket.getOutputStream();
    } catch (IOException e) {
        Log.e(TAG, "Error occurred when creating output stream", e);
    }

}
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    Handler handler =new Handler(new Handler.Callback() {
       public byte[] readBuff;

       @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch(msg.what)
            {
                case MESSAGE_READ:
                    readBuff=(byte[]) msg.obj;
                    readBufferFromBd(readBuff);
                   
                    break;

            }
            return false;
        }
    });

    public void readBufferFromBd(byte[] rB)
    {
        RecevedDataBytes=rB;


    }


    public void run() {
        mmBuffer = new byte[1024];
        byte[] RetArray = new byte[]{};
        int bytes;
       // int numBytes; // bytes returned from read()
        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                if(mmInStream.available()>0)
                {
                   
                    bytes=mmInStream.available();
                    numBytes = mmInStream.read(mmBuffer);
                    //RecevedDataBytes[0]=mmBuffer[0];

                  //  RetArray=new byte [numBytes];





                }

                else break;
                // Send the obtained bytes to the UI activity.

                Message readMsg = handler.obtainMessage(MESSAGE_READ, numBytes, -1,
                         mmBuffer);
                 readMsg.sendToTarget();

            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                break;
            }
        }
    }

    // Call this from the main activity to send data to the remote device.
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);

            // Share the sent message with the UI activity.
            //Message writtenMsg = handler.obtainMessage(
                   // MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
           //writtenMsg.sendToTarget();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when sending data", e);

            // Send a failure message back to the activity.
           // Message writeErrorMsg =
                    //handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
           // Bundle bundle = new Bundle();

           // writeErrorMsg.setData(bundle);
            //handler.sendMessage(writeErrorMsg);
        }
    }

    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}

