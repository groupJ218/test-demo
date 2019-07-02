package com.test.demo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.GalleryEntity;
import com.test.demo.utils.mongo.MongoFactory;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service("GalleryService")
@Transactional
public class GalleryService {

    static String db_collection = "mycollection";

    //Fetch all galleryEntity from the mongo database.
    public List getAll() {
        List gallEntity_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        //Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();

            GalleryEntity galleryEntity = new GalleryEntity();

        }
        return gallEntity_list;
    }

    public Boolean delete(String id) {
        return null;
    }

    public Object findGalleryId(String id) {
        return null;
    }

    public void edit(GalleryEntity entity) {

    }

    public void add(GalleryEntity entity) {

    }
}


