package com.test.demo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.test.demo.models.User;
import com.test.demo.utils.mongo.MongoFactory;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
            Document doc = cursor.next();
            User user = new User();
            String classNameValue;
            try {
                classNameValue = (String) doc.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }

            if (!doc.isEmpty() && User.CLASS_NAME.equalsIgnoreCase(classNameValue)) {
                setDocToUser(doc, user);
                user_list.add(user);
            }
        }
        return user_list;
    }

    // Fetching a single user details from the mongo database.
    public User findUserById(String idUser) {
        User user = new User();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        // Fetching the record object from the mongo database.
        Document doc = (Document) coll.find(eq("idUser", idUser)).first();
        setDocToUser(doc, user);
        return user;
    }

    public User findUserByEmail(String email) {
        User user = new User();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        // Fetching the record object from the mongo database.
        Document doc = (Document) coll.find(eq("email", email)).first();
        return null != doc ? setDocToUser(doc, user) : null;
    }
    public User findUserByLogin(String login) {
        User user = new User();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        // Fetching the record object from the mongo database.
        Document doc = (Document) coll.find(eq("login", login)).first();
        return null != doc ? setDocToUser(doc, user) : null;
    }


    // Add a new user to the mongo database.
    public Boolean addUser(User user) {
        boolean output;
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);
            log.warning("add User to mongo DB");
            // Create a new object and add the new user details to this object.
            Document doc = new Document();
            doc.put("className", user.getClassName());
            doc.put("idUser", user.getIdUser());
            editUserToDoc(user, doc);
            // Save a new user to the mongo collection.
            coll.insertOne(doc);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    private User setDocToUser(Document doc, User user) {
        user.setIdUser(doc.get(("idUser")).toString());
        user.setName(doc.get(("name")).toString());
        user.setPhone(doc.get(("phone")).toString());
        user.setEmail(doc.get(("email")).toString());
        user.setStatus(doc.get(("status")).toString());
        user.setCountry(doc.get(("country")).toString());
        user.setLogin(doc.get(("login")).toString());
        user.setPass(doc.get(("pass")).toString());
        return user;
    }

    private void editUserToDoc(User user, Document doc) {
        doc.put("name", user.getName());
        doc.put("phone", user.getPhone());
        doc.put("email", user.getEmail());
        doc.put("status", user.getStatus());
        doc.put("country", user.getCountry());
        doc.put("login", user.getLogin());
        doc.put("pass", user.getPass());
    }

    // Update the selected user in the mongo database.
    public Boolean editUser(User user) {
        boolean output;
        try {
            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);
            // Create a new object and assign the updated details.
            Document edited = getDocument(user.getIdUser());
            editUserToDoc(user, edited);
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
        return (Document) coll.find(eq("idUser", idUser)).first();
    }
}
