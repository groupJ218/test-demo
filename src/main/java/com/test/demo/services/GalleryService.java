package com.test.demo.services;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.GalleryEntity;
import com.test.demo.utils.mongo.MongoFactory;
import com.test.demo.utils.sys.Const;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
            Document doc = cursor.next();
            String classNameValue;
            try {
                classNameValue = (String) doc.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }
            if (!doc.isEmpty() && GalleryEntity.CLASS_NAME.equalsIgnoreCase(classNameValue)) {
                convertDocToGalleryList(gallery_list, doc);
            }
        }
        return gallery_list;
    }

    private void convertDocToGalleryList(List gallery_list, Document doc) {
        GalleryEntity galleryEntity = new GalleryEntity();
        galleryEntity.setIdGalEnt(doc.get("idGalEnt").toString());
        galleryEntity.setGalleryName(doc.get("galleryName").toString());
        galleryEntity.setDescription(doc.get("description").toString());
        galleryEntity.setIdUser(doc.get("idUser").toString());
        galleryEntity.setState(doc.get("state").toString());
        if (doc.get("file") == null) {
            galleryEntity.setFile(null);
            log.warning("No file");
        } else {
            galleryEntity.setFile(doc.get("file").toString());
        }
        log.warning("convertDocToGalleryList: editGall");
//        editGall(galleryEntity);
        gallery_list.add(galleryEntity);
    }

    //Fetch all galleryEntity from the mongo database.
    public List getAllById(String idUser) {
        List gallery_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        //Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String classNameValue;
            try {
                classNameValue = (String) doc.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }
            if (!doc.isEmpty()
                    && GalleryEntity.CLASS_NAME.equalsIgnoreCase(classNameValue)
                    && (doc.get("idUser").toString().equals(idUser))) {
                convertDocToGalleryList(gallery_list, doc);
            }
        }
        return gallery_list;
    }

    public Boolean addGall(GalleryEntity galleryEntity) {
        boolean output;
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);
            log.warning("add Gallery to mongo DB");
            Document doc = getDocument();
            doc.put("className", galleryEntity.getClassName());
            doc.put("idGalEnt", galleryEntity.getIdGalEnt());
            doc.put("file", galleryEntity.getFile());
            doc.put("state", galleryEntity.getState());
            doc.put("galleryName", galleryEntity.getGalleryName());
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

    private Document getDocument() {
        return new Document();
    }

    public Boolean editGall(GalleryEntity galleryEntity) {
        boolean output;
        try {
            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);
            Document edited = getDocument(galleryEntity.getIdGalEnt());
            edited.put("galleryName", galleryEntity.getGalleryName());
            edited.put("description", galleryEntity.getDescription());
            edited.put("state", Const.TRUE);
            coll.replaceOne(eq("idGalEnt", galleryEntity.getIdGalEnt()), edited);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    private Document getDocument(String idGalEnt) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        return (Document) coll.find(eq("idGalEnt", idGalEnt)).first();
    }

    public Boolean deleteGall(String idGalEnt) {
        boolean output;
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);
            coll.deleteOne(eq("idGalEnt", idGalEnt));
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public GalleryEntity findGallById(String idGalEnt) {
        GalleryEntity galleryEntity = new GalleryEntity();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        Document doc = (Document) coll.find(eq("idGalEnt", idGalEnt)).first();
        System.out.println(doc.toString());
        galleryEntity.setIdGalEnt(doc.get("idGalEnt").toString());
        if (doc.get("file") == null) {
            galleryEntity.setFile(null);
            log.warning("No file");
        } else {
            galleryEntity.setFile(doc.get("file").toString());
        }
        galleryEntity.setGalleryName(doc.get("galleryName").toString());
        galleryEntity.setDescription(doc.get("description").toString());
        galleryEntity.setIdUser(doc.get("idUser").toString());
        return galleryEntity;
    }

    public List getAllByState(String state) {
        state="true";
        List gallery_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        //Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String classNameValue;
            try {
                classNameValue = (String) doc.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }
            if (!doc.isEmpty()
                    && GalleryEntity.CLASS_NAME.equalsIgnoreCase(classNameValue)
                    && (doc.get("state").toString().equals(state))) {
                convertDocToGalleryList(gallery_list, doc);
            }
        }
        return gallery_list;
    }

}













