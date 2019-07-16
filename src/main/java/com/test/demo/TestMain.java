package com.test.demo;

import com.test.demo.models.GalleryEntity;
import com.test.demo.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TestMain {

    public static void main(String[] args) {

        GalleryService galleryService = new GalleryService();
        GalleryEntity ge = new GalleryEntity();
        byte[] tmpImg;
        tmpImg = getByteArrFromFile("static/img.jpg");

        ge.setDescription("description");
//        ge.setFile(tmpImg);
        ge.setId("gal16072019");
        ge.setName("img");
        ge.setUserId("usr160720192151");
        System.out.println("===========start add Gallery ============");
        galleryService.addGall(ge);
        System.out.println("===========start get Gallery ============");

        System.out.println(galleryService.findGallId(ge.getId()).toString());

        System.out.println("==============END===================");

    }

    private static byte[] getByteArrFromFile(String path) {
        byte[] tmpImg = null;
        File file = new File(TestMain.class.getClassLoader().getResource(path).getFile());
        InputStream inputStream = TestMain.class.getClassLoader().getResourceAsStream(path);
        tmpImg = new byte[(int) file.length()];
        try {
            inputStream.read(tmpImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpImg;
    }
}
