package com.jcroberts.abalone.multidevice;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.jcroberts.abalone.activities.MultiDeviceGameActivity;

import java.util.ArrayList;
import java.util.List;

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
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

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
        peerDevice = peers.get(0);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = peerDevice.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        connectionManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //Broadcast receiver will notify of the success
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(context, "Connect failed. Retry.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Class listening for available peer devices
     */
    private class PeersListener implements WifiP2pManager.PeerListListener {

        @Override
        public void onPeersAvailable(WifiP2pDeviceList deviceList){
            List<WifiP2pDevice> refreshedPeers = (List)deviceList.getDeviceList();
            if (!refreshedPeers.equals(peers)) {
                peers.clear();
                peers.addAll(refreshedPeers);

                for(int i = 0; i < refreshedPeers.size(); i++){
                    System.out.println(refreshedPeers.get(i).deviceName);
                }
                // Perform any other updates needed based on the new list of
                // peers connected to the Wi-Fi P2P network.
            }

            if (peers.size() == 0) {
                System.out.println("No devices found");
                return;
            }
        }

    }
}
