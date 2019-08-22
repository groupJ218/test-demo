package com.test.demo.models;

import java.util.UUID;
import java.util.Arrays;
import java.io.Serializable;

public class GalleryEntity implements Serializable {

    public static final String CLASS_NAME= "gallery";
    private byte[] file;
    private String idUser;
    private String idGalEnt;
    private String className ;
    private String galleryName;
    private String description;

    public GalleryEntity() {
        this.className = CLASS_NAME;
        this.idGalEnt = String.valueOf(UUID.randomUUID());
    }

    public GalleryEntity(String idGalEnt, byte[] file, String galleryName, String description, String idUser) {
        super();
        this.className = CLASS_NAME;
        this.file = file;
        this.idUser = idUser;
        this.idGalEnt = idGalEnt;
        this.className = CLASS_NAME;
        this.galleryName = galleryName;
        this.description = description;
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
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
                ", file=" + Arrays.toString(file) +
                ", galleryName='" + galleryName + '\'' +
                ", description='" + description + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}