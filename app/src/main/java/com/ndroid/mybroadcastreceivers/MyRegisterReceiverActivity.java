package com.ndroid.mybroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MyRegisterReceiverActivity extends AppCompatActivity {
    /**
    Steps :
    1. Declare BR object at instance level
    2. Create IntentFilter object in onCreate
    3. Initialize BR object
    4. Register BR by calling registerReceiver method
    5. Unregister BR in destroy method by calling unregisterReceiver
    */

    BroadcastReceiver receiver;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");

        receiver = new MyRegisterReceiver();
        registerReceiver(receiver, filter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null)
            unregisterReceiver(receiver);
        receiver = null;
    }
}