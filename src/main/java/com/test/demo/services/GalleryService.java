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
    public List getAll () {
        List gallery_list = new ArrayList ( );
        MongoCollection<Document> coll = MongoFactory.getCollection (db_collection);

        //Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find ( ).iterator ( );
        while (cursor.hasNext ( )) {
            Document document = cursor.next ( );
            GalleryEntity galleryEntity = new GalleryEntity ( );

            if (!document.isEmpty() && document.get("id") != null) {
                log.warning(document.toString());

                galleryEntity.setId(document.get("id").toString());
                galleryEntity.setName(document.get("name").toString());
//                galleryEntity.setFile(document.get("file").toString().getBytes());
                galleryEntity.setDescription(document.get("description").toString());
                galleryEntity.setUserId(document.get("userId").toString());

                if (document.get("file")==null){
                    galleryEntity.setFile(null);
                }else {
                    galleryEntity.setFile(document.get("file").toString().getBytes());
                }
                gallery_list.add(galleryEntity);
            }
        }
        return gallery_list;
    }


    public Boolean addGall ( GalleryEntity galleryEntity ) {
        boolean output = false;
        Random ran = new Random ( );
        try {
            MongoCollection coll = MongoFactory.getCollection (db_collection);
            Document doc = new Document ( );
            doc.put ("id" , galleryEntity.getId ( ));
            doc.put ("file" , galleryEntity.getFile ( ));
            doc.put ("name" , galleryEntity.getName ( ));
            doc.put ("description" , galleryEntity.getDescription ( ));
            doc.put ("userId" , galleryEntity.getUserId ( ));

            coll.insertOne (doc);
            output = true;

        } catch (Exception e) {
            output = false;

        }
        return output;

    }

    public Boolean editGall ( GalleryEntity galleryEntity ) {
        boolean output = false;
        Bson filter;
        Bson query;
        try {
            Document existing = getDocument (galleryEntity.getId ( ));
            MongoCollection<Document> coll = MongoFactory.getCollection (db_collection);

            Document edited = new Document ( );
            edited.put ("id" , galleryEntity.getId ( ));
            edited.put ("file" , galleryEntity.getFile ( ));
            edited.put ("name" , galleryEntity.getName ( ));
            edited.put ("description" , galleryEntity.getDescription ( ));
            edited.put ("userId" , galleryEntity.getDescription ( ));

            coll.replaceOne (eq ("id" , galleryEntity.getId ( )) , edited);
            output = true;

        } catch (Exception e) {
            output = false;

        }
        return output;
    }

    private Document getDocument ( String id ) {
        MongoCollection coll = MongoFactory.getCollection (db_collection);
        Document where_query = new Document ( );
        return (Document) coll.find (eq ("id" , id)).first ( );
    }

    public Boolean deleteGall ( String id ) {
        boolean output = false;
        try {
        Document item = (Document)getDocument (id);
        MongoCollection coll = MongoFactory.getCollection (db_collection);

        coll.deleteOne (eq ("id",id));
        output = true;
        }catch (Exception e){
            output = false;

        }
        return  output;

    }

    public GalleryEntity findGallId(String id){
        GalleryEntity g = new GalleryEntity ();
        MongoCollection coll = MongoFactory.getCollection (db_collection);

        Document where_query = new Document ();
        Document dbo = (Document) coll.find (eq("id",id)).first ();
        System.out.println (dbo.toString ());
        g.setId (dbo.get ("id").toString ());
//        g.setFile (dbo.get ("file").toString ().getBytes ());
        g.setName (dbo.get ("name").toString ());
        g.setDescription (dbo.get ("description").toString ());
        g.setUserId (dbo.get ("userId").toString ());

        return g;


    }
}













