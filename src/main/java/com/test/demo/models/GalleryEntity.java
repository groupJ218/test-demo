package com.test.demo.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;


public class GalleryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idGalEnt;
    private byte[] file;
    private String name;
    private String description;
    private String idUser;

    public GalleryEntity () {
        this.idGalEnt =String.valueOf(UUID.randomUUID());
    }

    public GalleryEntity (String idGalEnt, byte[] file , String name , String description , String idUser) {
        super ( );
        this.idGalEnt = idGalEnt;
        this.file = file;
        this.name = name;
        this.description = description;
        this.idUser = idUser;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getIdGalEnt() {
        return idGalEnt;
    }

    public void setIdGalEnt(String idGalEnt) {
        this.idGalEnt = idGalEnt;
    }

    public byte[] getFile () {
        return file;
    }

    public void setFile ( byte[] file ) {
        this.file = file;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString () {
        return "GalleryEntity{" +
                "idGalEnt='" + idGalEnt + '\'' +
                ", file=" + Arrays.toString (file) +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}