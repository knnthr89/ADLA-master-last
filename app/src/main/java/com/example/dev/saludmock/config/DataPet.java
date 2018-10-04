package com.example.dev.saludmock.config;

import com.couchbase.lite.Database;

import org.w3c.dom.Document;

import java.util.Date;
import com.couchbase.lite.*;
import com.couchbase.lite.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataPet {

    public static String VIEW_NAME = "messages-vie";
    // se cambio de valor de variable
    public static String TYPE = "data";

    private com.couchbase.lite.Document sourceDocument;
    private Database database;
    private Date createdAt;
    private String message;

    //Lee el query en la actividad principal para sincronización, muy importante
    //lo necesitamos
    public static Query findStatsByDate(Database database) {
        View view = database.getView(VIEW_NAME + "_stats");
        if (view.getMap() == null) {
            view.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    if (TYPE.equals(document.get("folio"))) {
                        emitter.emit(document.get("creat_at"),document);
                    }
                }
            }, "1");
        }
        return view.createQuery();
    }
    //que dispositivo lo esta enviando
    public static DataPet from(com.couchbase.lite.Document document) {
        DataPet message = new DataPet(document.getDatabase());
        if (document.getProperty("folio") != null) {
            message.setMessage((String) document.getProperty("folio"));
        }
        if (document.getProperty("creat_at") != null) {
            long createdAtL = 0;
            Object createdAt = document.getProperty("creat_at");
            if (createdAt instanceof Double) {
                createdAtL = ((Double) createdAt).longValue();
            }
            if (createdAt instanceof Long) {
                createdAtL = (Long) createdAt;
            }


            message.setCreatedAt(new Date(createdAtL));
        }
        message.setSourceDocument(document);
        return message;
    }


    public DataPet(Database database) {
        this.sourceDocument = sourceDocument;
        this.createdAt = createdAt;
        this.database = database;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.couchbase.lite.Document getSourceDocument() {
        return sourceDocument;
    }

    public void setSourceDocument(com.couchbase.lite.Document sourceDocument) {
        this.sourceDocument = sourceDocument;
    }
}
