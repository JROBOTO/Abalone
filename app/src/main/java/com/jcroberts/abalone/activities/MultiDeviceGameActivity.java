package com.jcroberts.abalone.activities;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import com.jcroberts.abalone.multidevice.MultiDevice;
import com.jcroberts.abalone.multidevice.WifiBroadcastReceiver;
/**
 * Author: Joshua Roberts
 */

public class MultiDeviceGameActivity extends GameActivity {
    private MultiDevice multiDevice;
    private WifiBroadcastReceiver wifiBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setupMultiDevice();
    }

    /**
     * Initialize the multi device functionality
     */
    private void setupMultiDevice(){
        WifiP2pManager someP2pManager = (WifiP2pManager)getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel channel = someP2pManager.initialize(this, getMainLooper(), null);
        wifiBroadcastReceiver = new WifiBroadcastReceiver(someP2pManager, channel);
        multiDevice = new MultiDevice(this, someP2pManager, channel, wifiBroadcastReceiver);
    }
}
