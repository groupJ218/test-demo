package com.test.demo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.utils.mongo.MongoFactory;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class GalleryService {

    static String db_collection = "mycollection";

    //Fetch all galleryEntity from the mongo database.
    public List getAll () {
        List GalleryEntity = new ArrayList ( );
        MongoCollection<Document> coll = MongoFactory.getCollection (db_collection);

        //Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cusor = coll.find ( ).iterator ( );
        while (cusor.hasNext ( )) {
            Document document = cusor.next ( );

            com.test.demo.models.GalleryEntity galleryEntity = new GallryEntity ( );

        }

    }

}

