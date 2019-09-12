package com.test.demo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.Comment;
import com.test.demo.utils.mongo.MongoFactory;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@Service("CommentService")
@Transactional
public class CommentService {
    Logger log = Logger.getLogger(CommentService.class.getName());
    static String db_collection = "mycollection";

    // Fetch all comments from the mongo database.
    public List getAll() {
        List<Comment> comment_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);
        // Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Comment comment = new Comment();
            log.warning("!!!!!Document to String: " + doc.toString());
            String classNameValue;
            try {
                classNameValue = (String) doc.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }
            if (!doc.isEmpty() && Comment.CLASS_NAME.equalsIgnoreCase(classNameValue)) {
                comment.setIdComment(doc.get(("idComment")).toString());
                comment.setIdUser(null == doc.get("idUser") ? "" : doc.get("idUser").toString());
                comment.setIdGalEnt(null == doc.get("idGalEnt") ? "" : doc.get("idGalEnt").toString());
                comment.setText(doc.get("text").toString());
                log.warning("Data form mongo db value: " + ( doc.get("date")).toString());
                comment.setDate(doc.get("date").toString());
                if (doc.get("idAnsCommentId") == null) {
                    comment.setIdAnsCommentId("");
                } else {
                    comment.setIdAnsCommentId(doc.get("idAnsCommentId").toString());
                }
                comment_list.add(comment);
            }
        }
        return comment_list;
    }

    // Add a new comment to the mongo database.
    public Boolean addComment(Comment comment) {
        boolean output;
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);
            log.warning("add Comment to mongo DB");
            // Create a new object and add the new comment details to this object.
            Document doc = new Document();
            doc.put("className", comment.getClassName());
            doc.put("idComment", comment.getIdComment());
            doc.put("idUser", comment.getIdUser());
            doc.put("idGalEnt", comment.getIdGalEnt());
            doc.put("text", comment.getText());
            doc.put("date", new Date().toString());
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
        boolean output;
        try {
            // Fetching the comment details.
            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);
            // Create a new object and assign the updated details.
            Document edited = getDocument(comment.getIdComment());
            edited.put("text", comment.getText());
            // Update the existing comment to the mongo database.
            coll.replaceOne(eq("idComment", comment.getIdComment()), edited);
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
        Comment comment = new Comment();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        // Fetching the record object from the mongo database.
        Document doc = (Document) coll.find(eq("idComment", idComment)).first();
        comment.setIdComment(doc.get("idComment").toString());
        comment.setIdUser(doc.get("idUser").toString());
        comment.setIdGalEnt(doc.get("idGalEnt").toString());
        comment.setText(doc.get("text").toString());
        comment.setDate(doc.get("date").toString());
        if (doc.get("IdAnsCommentId") != null)
            comment.setIdAnsCommentId((doc.get("IdAnsCommentId").toString()));
        return comment;
    }

    // Fetching a particular record from the mongo database.
    private Document getDocument(String idComment) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        return (Document) coll.find(eq("idComment", idComment)).first();
    }
}

