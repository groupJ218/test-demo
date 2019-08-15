package com.test.demo.services;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.GalleryEntity;
import com.test.demo.utils.mongo.MongoFactory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@Service("GalleryService")
@Transactional
public class GalleryService {
    Logger log = Logger.getLogger(CommentService.class.getName());
    static String db_collection = "mycollection";

    //Fetch all galleryEntity from the mongo database.
    public List getAll() {
        List gallery_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        //Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            GalleryEntity galleryEntity = new GalleryEntity();

            if (!document.isEmpty() && document.get("idGalEnt") != null && null != document.get("description") ) {
                log.info( "!!GALLERY_SERVICE " + document.toString());
                galleryEntity.setIdGalEnt(document.get("idGalEnt").toString());
                galleryEntity.setName(document.get("name").toString());
                galleryEntity.setDescription(document.get("description").toString());
                galleryEntity.setIdUser(document.get("idUser").toString());
                if (document.get("file") == null) {
                    galleryEntity.setFile(null);
                } else {
                    galleryEntity.setFile(document.get("file").toString().getBytes());
                }
                gallery_list.add(galleryEntity);
            }
        }
        return gallery_list;
    }


    public Boolean addGall(GalleryEntity galleryEntity) {
        boolean output = false;
        Random ran = new Random();
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);
            log.warning("add Gallery to mongo DB");
            Document doc = new Document();
            doc.put("idGalEnt", galleryEntity.getIdGalEnt());
            doc.put("file", galleryEntity.getFile());
            doc.put("name", galleryEntity.getName());
            doc.put("description", galleryEntity.getDescription());
            doc.put("idUser", galleryEntity.getIdUser());

            // Save a new gallery to the mongo collection.
            coll.insertOne(doc);
            output = true;

        } catch (Exception e) {
            output = false;

        }
        return output;

    }

    public Boolean editGall(GalleryEntity galleryEntity) {
        boolean output = false;
        Bson filter;
        Bson query;
        try {
            Document existing = getDocument(galleryEntity.getIdGalEnt());
            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

            Document edited = new Document();
            edited.put("idGalEnt", galleryEntity.getIdGalEnt());
            edited.put("file", galleryEntity.getFile());
            edited.put("name", galleryEntity.getName());
            edited.put("description", galleryEntity.getDescription());
            edited.put("idUser", galleryEntity.getDescription());

            coll.replaceOne(eq("idGalEnt", galleryEntity.getIdGalEnt()), edited);
//            coll.replaceOne (eq ("file" ,galleryEntity.getFile()), edited);
//            coll.replaceOne (eq ("name" ,galleryEntity.getName()), edited);
//            coll.replaceOne (eq ("description" ,galleryEntity.getDescription()), edited);
//            coll.replaceOne (eq ("idUser" ,galleryEntity.getIdUser()), edited);

            output = true;

        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    private Document getDocument(String idGalEnt) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        Document where_query = new Document();
        return (Document) coll.find(eq("idGalEnt", idGalEnt)).first();
    }

    public Boolean deleteGall(String idGalEnt) {
        boolean output = false;
        try {
            Document item = (Document) getDocument(idGalEnt);
            MongoCollection coll = MongoFactory.getCollection(db_collection);

            coll.deleteOne(eq("idGalEnt", idGalEnt));
            output = true;
        } catch (Exception e) {
            output = false;

        }
        return output;

    }

    public GalleryEntity findGallById(String idGalEnt) {
        GalleryEntity g = new GalleryEntity();
        MongoCollection coll = MongoFactory.getCollection(db_collection);

        Document where_query = new Document();
        Document dbo = (Document) coll.find(eq("idGalEnt", idGalEnt)).first();
        System.out.println(dbo.toString());
        g.setIdGalEnt(dbo.get("idGalEnt").toString());
//        g.setFile (dbo.get ("file").toString ().getBytes ());
        g.setName(dbo.get("name").toString());
        g.setDescription(dbo.get("description").toString());
        g.setIdUser(dbo.get("idUser").toString());

        return g;
    }
}













