package com.test.demo.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Comment implements Serializable {
    public static final String CLASS_NAME = "comment";
    private String className;
    private String idComment;
    private String idUser;
    private String idGalEnt;
    private String text;
    private Date date;
    private ArrayList<String> idAnsCommentId;

    public Comment() {
        this.idComment = String.valueOf(UUID.randomUUID());
        this.className = CLASS_NAME;
    }

    public Comment(String idComment, String idUser, String idGalEnt, String text, Date date) {
        this.className = CLASS_NAME;
        this.idComment = idComment;
        this.idUser = idUser;
        this.idGalEnt = idGalEnt;
        this.text = text;
        this.date = date;
    }

    public String getClassName() {
        return className;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdGalEnt() {
        return idGalEnt;
    }

    public void setIdGalEnt(String idGalEnt) {
        this.idGalEnt = idGalEnt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        Date dateTmp;
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
        try {
            dateTmp = dateForm.parse(date);
            this.date = dateTmp;
        } catch (ParseException e) {
            this.date = new Date();
        }
    }

    public ArrayList<String> getIdAnsCommentId() {
        return idAnsCommentId;
    }

    public void setIdAnsCommentId(String idAnsCommentId) {

        if (this.idAnsCommentId == null) {
            this.idAnsCommentId = new ArrayList<>();
            this.idAnsCommentId.add(idAnsCommentId);
        } else {
            this.idAnsCommentId.add(idAnsCommentId);
        }
    }

    @Override
    public String toString() {
        return "Comment{" +
                "className='" + className + '\'' +
                ", idComment='" + idComment + '\'' +
                ", idUser='" + idUser + '\'' +
                ", idGalEnt='" + idGalEnt + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", idAnsCommentId=" + idAnsCommentId +
                '}';
    }
}
