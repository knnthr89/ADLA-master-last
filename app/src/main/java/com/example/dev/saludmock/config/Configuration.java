package com.example.dev.saludmock.config;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.listener.LiteListener;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import com.couchbase.lite.Database;

public class Configuration  extends AppCompatActivity {

    public static final String SERVICE_TYPE = "_cblite._http._tcp.local.";

    public static final String SERVICE_DESCRIPTION = "I am a message service demo";

    public final String serviceName;

    private JmDNS jmdns;
    private Database database;

    Manager mManager;

    public Configuration(WifiManager wifi, Database database, String serviceName) throws IOException {
        this.database = database;
        this.serviceName = serviceName;
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = InetAddress.getByName(addr.getHostName()).toString();
        this.jmdns = JmDNS.create(addr, hostname);
    }

    private Manager getManager() {
        if (mManager == null) {
            try {
                AndroidContext context = new AndroidContext(getApplicationContext());
                mManager = new Manager(context, Manager.DEFAULT_OPTIONS);
            } catch (Exception e) {
                Log.e("TAG", "Cannot create Manager object", e);
            }
        }
        return mManager;
    }


    public void exposeService(int port) throws IOException {
        ServiceInfo sInfos = ServiceInfo.create(SERVICE_TYPE, serviceName, port, SERVICE_DESCRIPTION);
        jmdns.registerService(sInfos);
    }

    public void listenForService(){
        jmdns.addServiceListener(SERVICE_TYPE, new DiscoveryListener(database, jmdns, serviceName));
    }

    public int startCBLiteListener(int port) {

        LiteListener ls = new LiteListener(getManager(), port, null);
        Thread thread = new Thread(ls);
        thread.start();
        return ls.getListenPort();
    }
}
