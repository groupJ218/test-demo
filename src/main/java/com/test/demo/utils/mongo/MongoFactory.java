package com.test.demo.utils.mongo;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("deprecation")
public class MongoFactory {
    private static Mongo mongo;

    public MongoFactory() {
    }

    public static MongoDatabase getMongo() {

        MongoDatabase db = null;
        try {

            MongoClientURI uri = new MongoClientURI("mongodb://127.0.0.1:27017/mydb");
            MongoClient client = new MongoClient(uri);
            db = client.getDatabase(uri.getDatabase());

        } catch (MongoException ex) {
            System.err.println(ex);
        }
        return db;
    }

    // Fetches the collection from the mongo database.
    public static MongoCollection getCollection(String db_collection) {
        return getMongo().getCollection(db_collection) ;
    }
}
