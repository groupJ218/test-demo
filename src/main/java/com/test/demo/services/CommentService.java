package com.test.demo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.Comment;
import com.test.demo.utils.mongo.MongoFactory;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            Document document = cursor.next();
            Comment comment = new Comment();
            log.warning("!!!!!Document to String: " + document.toString());
            String classNameValue;
            try {
                classNameValue = (String) document.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }
            if (!document.isEmpty() && Comment.CLASS_NAME.equalsIgnoreCase(classNameValue)) {
                log.warning("!!!!!with className" + document.toString());
                comment.setIdComment(document.get(("idComment")).toString());
                comment.setIdUser(null == document.get("idUser") ? "" : document.get("idUser").toString());
                comment.setIdGalEnt(null == document.get("idGalEnt") ? "" : document.get("idGalEnt").toString());
                comment.setText(document.get("text").toString());
                comment.setDate(document.get("date").toString());
                if (document.get("idAnsCommentId") == null) {
                    comment.setIdAnsCommentId("");
                } else {
                    comment.setIdAnsCommentId(document.get("idAnsCommentId").toString());
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
        Comment c = new Comment();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        // Fetching the record object from the mongo database.
        Document dbo = (Document) coll.find(eq("idComment", idComment)).first();
        c.setIdComment(dbo.get("idComment").toString());
        c.setIdUser(dbo.get("idUser").toString());
        c.setIdGalEnt(dbo.get("idGalEnt").toString());
        c.setText(dbo.get("text").toString());
        c.setDate(dbo.get("date").toString());
        if (dbo.get("IdAnsCommentId") != null)
            c.setIdAnsCommentId((dbo.get("IdAnsCommentId").toString()));
        return c;
    }

    // Fetching a particular record from the mongo database.
    private Document getDocument(String idComment) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        return (Document) coll.find(eq("idComment", idComment)).first();
    }
}

