package com.test.demo.models;

import java.io.Serializable;
import java.util.Arrays;


public class GalleryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private byte[] file;
    private String name;
    private String description;
    private String userId;

    public GalleryEntity () {
        super ( );
    }

    public GalleryEntity ( String id , byte[] file , String name , String description , String userId ) {
        super ( );
        this.id = id;
        this.file = file;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
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

    public String getUserId () {
        return userId;
    }

    public void setUserId ( String userId ) {
        this.userId = userId;
    }

    @Override
    public String toString () {
        return "GalleryEntity{" +
                "id='" + id + '\'' +
                ", file=" + Arrays.toString (file) +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}