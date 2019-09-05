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
            Document document = cursor.next();
            User u = new User();
//            log.warning("!!!!!Document to String: " + document.toString());

//            // this part of method for delete users
//            if (!document.isEmpty() && document.get("idUser") != null && document.get("email") != null && null == document.get("name")) {
//                log.warning("!!!!!with email and name = null " + document.toString());
////                deleteUser(document.getString("idUser"));
//            }
            String classNameValue;
            try {
                classNameValue = (String) document.get("className");
            } catch (NullPointerException e) {
                classNameValue = null;
            }

            if (!document.isEmpty() && User.CLASS_NAME.equalsIgnoreCase(classNameValue)) {
                log.warning("!!!!!with className" + document.toString());
                setDocToUser(document, u);
                user_list.add(u);
            }
        }
        return user_list;
    }

    // Fetching a single user details from the mongo database.
    public User findUserById(String idUser) {
        User u = new User();
        MongoCollection coll = MongoFactory.getCollection(db_collection);
        // Fetching the record object from the mongo database.
        Document doc = (Document) coll.find(eq("idUser", idUser)).first();
        setDocToUser(doc, u);
        return u;
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

    private void setDocToUser(Document document, User u) {
        u.setIdUser(document.get(("idUser")).toString());
        u.setName(document.get(("name")).toString());
        u.setPhone(document.get(("phone")).toString());
        u.setEmail(document.get(("email")).toString());
        u.setStatus(document.get(("status")).toString());
        u.setCountry(document.get(("country")).toString());
        u.setLogin(document.get(("login")).toString());
        u.setPass(document.get(("pass")).toString());
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
