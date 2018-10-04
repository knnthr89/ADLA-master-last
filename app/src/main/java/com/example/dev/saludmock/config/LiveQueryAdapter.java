package com.example.dev.saludmock.config;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.QueryEnumerator;
import com.example.dev.saludmock.activities.ContentPanelActivity;

public abstract class LiveQueryAdapter  extends BaseAdapter {

    private LiveQuery query;
    private QueryEnumerator enumerator;
    private Context context;

    public LiveQueryAdapter(LiveQuery query, Context context) {
        this.query = query;
        this.context = context;
        query.addChangeListener(new LiveQuery.ChangeListener() {
            @Override
            public void changed(final LiveQuery.ChangeEvent changeEvent) {
                ((Activity) LiveQueryAdapter.this.context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enumerator = changeEvent.getRows();
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }



    @Override
    public int getCount() {
        return (enumerator == null) ? 0 : enumerator.getCount();
    }

    @Override
    public Object getItem(int i) {
        return enumerator  != null ? enumerator.getRow(i).getDocument() : null;
    }

    @Override
    public long getItemId(int i) {
        return enumerator.getRow(i).getSequenceNumber();
    }


    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public void invalidate() {
        if (query != null) {
            query.stop();
        }
    }
}
