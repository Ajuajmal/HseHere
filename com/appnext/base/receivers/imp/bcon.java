package com.appnext.base.receivers.imp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0206e;
import com.appnext.base.p005b.C0207f;
import com.appnext.base.p005b.C0213k;
import com.appnext.base.receivers.C1208b;

public class bcon extends C1208b {
    protected static final String TAG = bcon.class.getSimpleName();

    public IntentFilter getBRFilter() {
        if (!C0207f.m65b(C0205d.getContext(), "android.permission.BLUETOOTH")) {
            return null;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        return intentFilter;
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            C0934c aj = C0206e.bB().aj(TAG);
            if (aj == null || C0204c.fO.equalsIgnoreCase(aj.aR())) {
                C0213k.m108h(TAG, "config is off , Not executing anything, unregisterReceiver");
                C0205d.getContext().unregisterReceiver(this);
                return;
            }
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED".equals(action) || "android.bluetooth.device.action.ACL_CONNECTED".equals(action) || "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null && extras.size() > 0) {
                    String name;
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice != null) {
                        name = bluetoothDevice.getName();
                    } else {
                        name = null;
                    }
                    Boolean valueOf = "android.bluetooth.device.action.FOUND".equals(action) ? null : "android.bluetooth.device.action.ACL_CONNECTED".equals(action) ? Boolean.valueOf(true) : "android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action) ? Boolean.valueOf(false) : "android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED".equals(action) ? Boolean.valueOf(false) : "android.bluetooth.device.action.ACL_DISCONNECTED".equals(action) ? Boolean.valueOf(false) : null;
                    if (name != null && valueOf != null) {
                        m466b(TAG, m494a(valueOf, name), C0203a.JSONArray);
                    }
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
