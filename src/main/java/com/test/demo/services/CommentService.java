package com.test.demo.services;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.Comment;
import com.test.demo.utils.mongo.MongoFactory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

@Service("CommentService")
@Transactional
public class CommentService {

    static String db_collection = "mycollection";

    // Fetch all comments from the mongo database.
    public List getAll() {
        List comment_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        // Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            Comment comment = new Comment();
            comment.setIdComment(Long.parseLong((document.getLong(("idComment")).toString())));
            comment.setIdUser(Long.parseLong((document.getLong(("idUser")).toString())));
            comment.setIdGalEnt(Long.parseLong((document.getLong(("idGalEnt")).toString())));
            comment.setText(document.get("text").toString());
            comment.setDate(document.get("date").toString());
            comment.setIdAnsCommentId(Long.parseLong((document.getLong(("idAnsCommentId")).toString())));
        }
        return comment_list;
    }

    // Add a new comment to the mongo database.

    public Boolean addComment(Comment comment) {
        boolean output = false;
        Random ran = new Random();
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);

            // Create a new object and add the new comment details to this object.
            Document doc = new Document();
            doc.put("idComment", comment.getIdComment());
            doc.put("idUser", comment.getIdUser());
            doc.put("idGalEnt", comment.getIdGalEnt());
            doc.put("text", comment.getText());
            doc.put("date", comment.getDate().toString());
            doc.put("idAnsCommentId", comment.getIdAnsCommentId());

            // Save a new comment to the mongo collection.
            coll.insertOne(doc);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    // Update the selected comment in the mongo database.
    public Boolean editComment(Comment comment) {
        boolean output = false;
        Bson filter;
        Bson query;
        try {
            // Fetching the user details.
            Document existing = getDocument(String.valueOf(comment.getIdComment()));
            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

            // Create a new object and assign the updated details.

            Document edited = new Document();
            edited.put("id", comment.getIdComment());
            edited.put("idUser", comment.getIdUser());
            edited.put("idGalEnt", comment.getIdGalEnt());
            edited.put("text", comment.getText());
            edited.put("date", comment.getDate());
            edited.put("idAnsCommentId", comment.getIdAnsCommentId());


            // Update the existing comment to the mongo database.
            coll.replaceOne(eq("id", comment.getIdComment()), edited);
            coll.replaceOne(eq("idUser", comment.getIdUser()), edited);
            coll.replaceOne(eq("idGalEnt", comment.getIdGalEnt()), edited);
            coll.replaceOne(eq("text", comment.getText()), edited);
            coll.replaceOne(eq("date", comment.getDate()), edited);
            coll.replaceOne(eq("idAnsCommentId", comment.getIdAnsCommentId()), edited);

            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public Boolean deleteComment(String idComment) {
        boolean output = false;
        try {
            // Fetching the required comment from the mongo database.
            Document item = (Document) getDocument(String.valueOf(idComment));
            MongoCollection coll = MongoFactory.getCollection(db_collection);

            // Deleting the selected comment from the mongo database.
            coll.deleteOne(eq("idComment", idComment));
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    // Fetching a single user details from the mongo database.
    public Comment findCommentId(String idComment) {
        Comment c = new Comment();
        MongoCollection coll = MongoFactory.getCollection(db_collection);

        // Fetching the record object from the mongo database.
        Document where_query = new Document();
        //        where_query.put("idComment", idComment);

        Document dbo = (Document) coll.find(eq("idComment", idComment)).first();
        System.out.println(dbo.toString());
        c.setIdComment(Long.parseLong(dbo.get("idComment").toString()));
        c.setIdUser(Long.parseLong(dbo.get("idUser").toString()));
        c.setIdGalEnt(Long.parseLong(dbo.get("idGalEnt").toString()));
        c.setText(dbo.get("text").toString());
        c.setDate(dbo.get("date").toString());
        if (dbo.get("IdAnsCommentId") != null)
            c.setIdAnsCommentId(Long.parseLong((dbo.get("IdAnsCommentId").toString())));
        // Return comment object.
        return c;
    }


    // Fetching a particular record from the mongo database.
    private Document getDocument(String idComment) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);

        // Fetching the record object from the mongo database.
        Document where_query = new Document();
        // Put the selected user_id to search.
        // where_query.put("idComment", idComment);

        return (Document) coll.find(eq("idComment", idComment)).first();

    }


}

