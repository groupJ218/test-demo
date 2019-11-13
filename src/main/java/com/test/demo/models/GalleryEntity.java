package com.test.demo.models;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.Arrays;
import java.io.Serializable;

public class GalleryEntity implements Serializable {

    public static final String CLASS_NAME= "gallery";
    private String file;
    private String state;
    private String idUser;
    private String idGalEnt;
    private String className ;
    private String galleryName;
    private String description;

    public GalleryEntity() {
        this.className = CLASS_NAME;
        this.idGalEnt = String.valueOf(UUID.randomUUID());
    }

    public GalleryEntity(String idGalEnt, String  file, String state, String galleryName, String description, String idUser) {
        super();
        this.className = CLASS_NAME;
        this.file = file;
        this.state = state;
        this.idUser = idUser;
        this.idGalEnt = idGalEnt;
        this.className = CLASS_NAME;
        this.galleryName = galleryName;
        this.description = description;

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClassName() {
        return className;
    }

    public String getIdGalEnt() {
        return idGalEnt;
    }

    public void setIdGalEnt(String idGalEnt) {
        this.idGalEnt = idGalEnt;
    }

    public String  getFile() {
        return file;
    }

    public void setFile(String  file) {
        this.file = file;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "GalleryEntity{" +
                ", idGalEnt='" + idGalEnt + '\'' +
                ", galleryName='" + galleryName + '\'' +
                ", description='" + description + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}