package com.example.dev.saludmock.config;

import android.app.Notification;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.couchbase.lite.Context;
import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.View;
import com.example.dev.saludmock.R;
import com.example.dev.saludmock.activities.ContentPanelActivity;

import javax.jmdns.impl.DNSOutgoing;

public class MascotaAdapter extends LiveQueryAdapter {


    public MascotaAdapter(LiveQuery query, android.content.Context context) {
        super(query, context);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
        return null;
    }
}
