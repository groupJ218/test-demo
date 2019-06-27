package com.test.demo.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private long idComment;
    private long idUser;
    private long idGalEnt;
    private String text;
    private Date date;
    private ArrayList<Long> idAnsCommentId;

    public Comment() {
        super();
    }

    public Comment(long idComment, long idUser, long idGalEnt, String text, Date date) {
        this.idComment = idComment;
        this.idUser = idUser;
        this.idGalEnt = idGalEnt;
        this.text = text;
        this.date = date;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getIdComment() {
        return idComment;
    }

    public void setIdComment(long idComment) {
        this.idComment = idComment;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdGalEnt() {
        return idGalEnt;
    }

    public void setIdGalEnt(long idGalEnt) {
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
        Date dateTmp = null;
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
        try {
            dateTmp = dateForm.parse(date);
            this.date = dateTmp;
        } catch (ParseException e) {
            this.date = new Date();
        }
    }

    public ArrayList<Long> getIdAnsCommentId() {
        return idAnsCommentId;
    }

    public void setIdAnsCommentId(long idAnsCommentId) {

        if (this.idAnsCommentId == null) {
            this.idAnsCommentId = new ArrayList<>();
            this.idAnsCommentId.add(idAnsCommentId);
        } else {
            this.idAnsCommentId.add(idAnsCommentId);
        }
    }

    @Override
    public String toString() {
        Date date = new Date();
        return "Comment{" +
                "idComment=" + idComment +
                ", idUser=" + idUser +
                ", idGalEnt=" + idGalEnt +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
