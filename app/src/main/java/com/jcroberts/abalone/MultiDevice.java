package com.jcroberts.abalone;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Connect to and communicate with another device using peer to peer communication
 * Author: Joshua Roberts
 */
//TODO This is still shit
public class MultiDevice {
    private WifiP2pManager connectionManager;
    private WifiBroadcastReceiver wifiBroadcastReceiver;
    private WifiP2pManager.Channel channel;

    private boolean isConnectedToPeer;

    public MultiDevice(WifiP2pManager wP2pM, WifiP2pManager.Channel c, WifiBroadcastReceiver wBR){
        connectionManager = wP2pM;
        channel = c;
        wifiBroadcastReceiver = wBR;
    }

    public void discoverPeers(){
        connectionManager.discoverPeers(channel, new WifiP2pManager.ActionListener(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    public void changePeerConnectionStatus(boolean isConnected){
        isConnectedToPeer = isConnected;
    }
}
