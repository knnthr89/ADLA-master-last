package com.example.dev.saludmock.config;

import com.couchbase.lite.Database;
import com.couchbase.lite.auth.AuthenticatorFactory;
import com.couchbase.lite.listener.Credentials;
import com.couchbase.lite.replicator.Replication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class DiscoveryListener implements ServiceListener {

    private Database database;
    private JmDNS jmdns;
    private String serviceName;
    private Replication pullReplication;
    private Replication pushReplication;

    public DiscoveryListener(Database database, JmDNS jmdns, String serviceName) {
        this.database = database;
        this.jmdns = jmdns;
        this.serviceName = serviceName;
    }

    @Override
    public void serviceAdded(ServiceEvent event) {
       if(! serviceName.equals(event.getName())){
           jmdns.requestServiceInfo(event.getType(), event.getName(), 10);
       }
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
       System.out.println(event.getName() + " removed");
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
     System.out.println("RESOLVED");
     String [] serviceUrls = event.getInfo().getURLs();
     for (String url : serviceUrls) {
         System.out.println(url);
         setupSync(database, url + "/registers");
     }
    }

    public void setupSync(Database database, String syncUrl){
        /*try{

            com.couchbase.lite.auth.Authenticator auth = AuthenticatorFactory.createBasicAuthenticator("hello", "pw123");
            URL url = new URL(syncUrl);
            pullReplication = database.createPullReplication(url);


             pullReplication.setAuthenticator(auth);
            pullReplication.setContinuous(true);
            pullReplication.start();


            pushReplication = database.createPushReplication(url);


            pushReplication.setAuthenticator(auth);
            pushReplication.setContinuous(true);
            pushReplication.start();

        }catch (IOException e){
            throw  new RuntimeException(e);
        }*/

        com.couchbase.lite.auth.Authenticator auth = AuthenticatorFactory.createBasicAuthenticator("hello", "pw123");

        URL url = null;

        try {
            url = new URL(syncUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (pullReplication == null) {
            pullReplication = database.createPullReplication(url);
            pullReplication.setContinuous(true);
            pullReplication.setAuthenticator(auth);
            pullReplication.addChangeListener((Replication.ChangeListener) this);
        }

        if (pushReplication == null) {
            pushReplication = database.createPushReplication(url);
            pushReplication.setContinuous(true);
            pushReplication.setAuthenticator(auth);
            pushReplication.addChangeListener((Replication.ChangeListener) this);
        }

        pullReplication.stop();
        pullReplication.start();

        pushReplication.stop();
        pushReplication.start();
    }

    public boolean isSyncOn(){
        return pullReplication.isRunning();
    }


    public void stopReplication() {
        if (pullReplication != null) {
            pullReplication.removeChangeListener((Replication.ChangeListener) this);
            pullReplication.stop();
            pullReplication = null;
        }

        if (pushReplication != null) {
            pushReplication.removeChangeListener((Replication.ChangeListener) this);
            pushReplication.stop();
            pushReplication = null;
        }
    }
}
