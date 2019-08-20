package com.test.demo.models;

import java.util.UUID;
import java.util.Arrays;
import java.io.Serializable;

public class GalleryEntity implements Serializable {

    public static final String CLASS_NAME= "gallery";
    private byte[] file;
    private String name;
    private String idUser;
    private String idGalEnt;
    private String className ;
    private String description;

    public GalleryEntity() {
        this.className = CLASS_NAME;
        this.idGalEnt = String.valueOf(UUID.randomUUID());
    }

    public GalleryEntity(String idGalEnt, byte[] file, String name, String description, String idUser) {
        super();
        this.file = file;
        this.name = name;
        this.idUser = idUser;
        this.idGalEnt = idGalEnt;
        this.className = CLASS_NAME;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "className='" + className + '\'' +
                ", idGalEnt='" + idGalEnt + '\'' +
                ", file=" + Arrays.toString(file) +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}