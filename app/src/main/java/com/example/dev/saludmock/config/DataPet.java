package com.example.dev.saludmock.config;

import com.couchbase.lite.Database;

import org.w3c.dom.Document;

import java.util.Date;

public class DataPet {

    public static String VIEW_NAME = "messages-vie";
    // se cambio de valor de variable
    public static String TYPE = "data";

    private Document sourceDocument;
    private Database database;
    private Date createdAt;
    private String message;


    public DataPet(Database database) {
        this.sourceDocument = sourceDocument;
        this.createdAt = createdAt;
        this.database = database;
    }

    public Document getSourceDocument() {
        return sourceDocument;
    }

    public void setSourceDocument(Document sourceDocument) {
        this.sourceDocument = sourceDocument;
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
}
