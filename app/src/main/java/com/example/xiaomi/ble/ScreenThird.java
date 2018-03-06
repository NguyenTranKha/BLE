package com.example.xiaomi.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaomi.ble.R;
import com.example.xiaomi.ble.ScreenMain;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes;

public class ScreenThird extends AppCompatActivity {

    TextView r;
    TextView b;
    TextView g;
    Switch red;
    Switch green;
    Switch blue;
    SeekBar ared;
    SeekBar ablue;
    SeekBar agreen;
    String deviceName;
    String deviceAddress;
    private OutputStream outputStream;
    BluetoothAdapter BLT_Adapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket BLT_Socket;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//hc-05

    //confirm and connect Bluetooth
    void connectToDevice() throws IOException {

        BluetoothDevice device = BLT_Adapter.getRemoteDevice(deviceAddress);
        BLT_Socket = device.createRfcommSocketToServiceRecord(MY_UUID);
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        BLT_Socket.connect();
        outputStream = BLT_Socket.getOutputStream();
    }

    //sendData
    public void sendData(String message) throws IOException {
        outputStream.write(message.getBytes());
    }

    public void sendDataHex(int message) throws IOException {
        outputStream.write(message);
    }

    public void Red() {
        String json = "R";

        try {
            sendData(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Green() {
        String json = "G";

        try {
            sendData(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Blue() {
        String json = "B";

        try {
            sendData(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OffR() {
        String json = "1";

        try {
            sendData(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OffB() {
        String json = "2";

        try {
            sendData(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OffG() {
        String json = "3";

        try {
            sendData(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendsbvl(int a)
    {
        try{
            sendDataHex(a);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendsbst(String a)
    {
        try{
            sendData(a);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenthird);
        r = (TextView) findViewById(R.id.tvr);
        b = (TextView) findViewById(R.id.tvb);
        g = (TextView) findViewById(R.id.tvg);
        red =(Switch) findViewById(R.id.switchr);
        green =(Switch) findViewById(R.id.switchg);
        blue =(Switch) findViewById(R.id.switchb);
        ared = (SeekBar) findViewById(R.id.sbr);
        ablue = (SeekBar) findViewById(R.id.sbb);
        agreen = (SeekBar) findViewById(R.id.sbg);
        Intent intent = getIntent();
        deviceName = intent.getStringExtra(ScreenMain.EXTRA_BLUETOOTH_NAME);
        deviceAddress = intent.getStringExtra(ScreenMain.EXTRA_BLUETOOTH_ADDRESS);
        try {
            connectToDevice();
        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            finish();
        }

        red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Red();
                }
                else
                {
                    sendsbvl(255);
                }
            }
        });

        green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Green();
                }
                else
                {
                    sendsbvl(255);
                }
            }
        });

        blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Blue();
                }
                else
                {
                    sendsbvl(255);
                }
            }
        });

        ared.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sendsbvl(128+(31 - progress));
                r.setText("Red :"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ablue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sendsbvl(32 + (31 - progress));
                b.setText("Blue :"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        agreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sendsbvl(64+(31 - progress));
                g.setText("Green :"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
