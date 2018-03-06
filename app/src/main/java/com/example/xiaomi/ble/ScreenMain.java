package com.example.xiaomi.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DELL on 04/02/2018.
 */

public class ScreenMain extends AppCompatActivity {
    Button IB;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ListView LView;
    List ListBLEscan = new ArrayList();
    int REQUEST_ENABLE_BT = 1;
    public static final String EXTRA_BLUETOOTH_NAME = "EXTRA_BLUETOOTH_NAME";
    public static final String EXTRA_BLUETOOTH_ADDRESS = "EXTRA_BLUETOOTH_ADDRESS";
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.screenmain);
        IB = (Button) findViewById(R.id.BT);
        LView = (ListView) findViewById(R.id.LV);

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } //enable bluetooth

        //reset list bluetooth
        IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBLEscan.clear();
                update();
            }
        });
    }

    public void update() {
        Set<BluetoothDevice> pairedDevices;
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                ListBLEscan.add(device.getName() + "\n" +device.getAddress());
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListBLEscan);
        LView.setAdapter(adapter);
        LView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String info = ((TextView) view).getText().toString();
                String name = info.substring(0, info.length() - 18);
                String address = info.substring(info.length() - 17);

                Intent newActivity = new Intent(ScreenMain.this, ScreenThird.class);
                newActivity.putExtra(EXTRA_BLUETOOTH_NAME, name);
                newActivity.putExtra(EXTRA_BLUETOOTH_ADDRESS, address);
                startActivity(newActivity);
            }
        });
    }
}
