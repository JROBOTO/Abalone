package com.jcroberts.abalone.multidevice;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

/**
 * Connect to and communicate with another device using peer to peer communication
 * Author: Joshua Roberts
 */
public class MultiDevice {
    private WifiP2pManager connectionManager;
    private WifiBroadcastReceiver wifiBroadcastReceiver;
    private WifiP2pManager.Channel channel;
    private WifiP2pDevice peerDevice;
    private WifiP2pDeviceList deviceList;
    private IntentFilter intentFilter;
    private Context context;

    private boolean isConnectedToPeer;

    /**
     * @param cont The app context
     * @param wP2pM An instance of WifiP2pManager
     * @param c The WifiP2pManager Channel
     * @param wBR A WifiBroadcastReceiver class which responds to wifi events
     */
    public MultiDevice(Context cont, WifiP2pManager wP2pM, WifiP2pManager.Channel c, WifiBroadcastReceiver wBR){
        connectionManager = wP2pM;
        channel = c;
        wifiBroadcastReceiver = wBR;

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    /**
     * Discover all the  peers connecting on the wifi through the same channel
     */
    public void discoverPeers(){
        connectionManager.discoverPeers(channel, new WifiP2pManager.ActionListener(){

            @Override
            public void onSuccess() {
                connectToPeer();
            }

            @Override
            public void onFailure(int reason) {
                switch(reason){
                    case(WifiP2pManager.BUSY):
                        Toast.makeText(context, "BUSY", Toast.LENGTH_LONG).show();

                        break;
                    case(WifiP2pManager.ERROR):
                        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();

                        break;
                }
            }
        });
    }

    /**
     * Change the logic of whether or not the device is connected to its peer
     * @param isConnected
     */
    private void changePeerConnectionStatus(boolean isConnected){
        isConnectedToPeer = isConnected;
    }

    /**
     * Connect to another device
     */
    private void connectToPeer(){
        //TODO Connect
    }
}
