package com.test.demo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.User;
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

@Service("UserService")
@Transactional
public class UserService {
    Logger log = Logger.getLogger(UserService.class.getName());
    static String db_collection = "mycollection";

    // Fetch all users from the mongo database.
    public List getAll() {
        List user_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        // Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            User user = new User();
            log.warning("!!!!!Document to String: " + document.toString());

            if (!document.isEmpty() && document.get("idUser") != null && document.get("email") != null && null == document.get("name")) {
                log.warning("!!!!!with email and name = null " + document.toString());
//                deleteUser(document.getString("idUser"));
            }
            if (!document.isEmpty() && document.get("idUser") != null && document.get("email") != null) {
                log.warning("!!!!!with email" + document.toString());
                user.setIdUser(document.get(("idUser")).toString());
                user.setName(document.get(("name")).toString());
                user.setPhone(document.get(("phone")).toString());
                user.setEmail(document.get(("email")).toString());
                user.setStatus(document.get(("status")).toString());
                user_list.add(user);
            }
        }

        return user_list;
    }

    // Add a new user to the mongo database.
    public Boolean addUser(User user) {
        boolean output = false;
        Random ran = new Random();
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);
            log.warning("add User to mongo DB");
            // Create a new object and add the new user details to this object.
            Document doc = new Document();
            doc.put("idUser", String.valueOf(ran.nextInt(100)));
            doc.put("name", user.getName());
            doc.put("phone", user.getPhone());
            doc.put("email", user.getEmail());
            doc.put("status", user.getStatus());

            // Save a new user to the mongo collection.
            coll.insertOne(doc);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    // Update the selected user in the mongo database.
    public Boolean editUser(User user) {
        boolean output = false;
        Bson filter;
        Bson query;
        try {
            Document existing = getDocument(user.getIdUser());
            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

            // Create a new object and assign the updated details.

            Document edited = new Document();
            edited.put("idUser", user.getIdUser());
            edited.put("name", user.getName());
            edited.put("phone", user.getPhone());
            edited.put("email", user.getEmail());
            edited.put("status", user.getStatus());

            // Update the existing user to the mongo database.
            coll.replaceOne(eq("email", user.getEmail()), edited);

            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    // Delete a user from the mongo database.
    public Boolean deleteUser(String idUser) {
        boolean output = false;
        try {
            // Fetching the required user from the mongo database.
            Document item = (Document) getDocument(idUser);
            MongoCollection coll = MongoFactory.getCollection(db_collection);

            // Deleting the selected user from the mongo database.
            coll.deleteOne(eq("idUser", idUser));
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    // Fetching a particular record from the mongo database.
    private Document getDocument(String idUser) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        Document where_query = new Document();
        return (Document) coll.find(eq("idUser", idUser)).first();
    }

    // Fetching a single user details from the mongo database.
    public User findUserById(String idUser) {
        User u = new User();
        MongoCollection coll = MongoFactory.getCollection(db_collection);

        // Fetching the record object from the mongo database.
        Document where_query = new Document();
//        where_query.put("idUser", idUser);
        Document dbo = (Document) coll.find(eq("idUser", idUser)).first();
        System.out.println(dbo.toString());
        u.setIdUser(dbo.get("idUser").toString());
        u.setName(dbo.get("name").toString());
        u.setPhone(dbo.get("phone").toString());
        u.setEmail(dbo.get("email").toString());
        u.setStatus(dbo.get("status").toString());
        // Return user object.
        return u;
    }
}
