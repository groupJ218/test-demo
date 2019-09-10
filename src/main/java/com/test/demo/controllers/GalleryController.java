package com.test.demo.controllers;

import com.test.demo.models.GalleryEntity;
import com.test.demo.services.GalleryService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    Logger log = Logger.getLogger(GalleryController.class.getName());
    @Resource(name = "GalleryService")
    private GalleryService galleryService;

    // Displaying the initial gallery list.

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        log.warning("---------------------START get Gallery LIST method----------------------");
        List gall_list = galleryService.getAll();
        model.addAttribute("newGall", new GalleryEntity());
        model.addAttribute("image", Base64.getEncoder().encodeToString(galleryService.findGallById("bea9349a-69d8-48a1-8980-0b471cc73852").getFile().getData()));
        model.addAttribute("gallery", gall_list);
        log.warning("GalleryEntity" + gall_list.toString());
        log.warning("---------------------END get Gallery LIST method----------------------");
        return "galleryEntity";
    }

    // Opening the add new gallery form page.
    @PostMapping("/add")
    public String addGall(@RequestParam("file") MultipartFile file, @RequestParam("galleryName") String galleryName,
                          @RequestParam("description") String description, @RequestParam("idUser") String idUser) {
        log.warning("---------------------START ADD method----------------------");
        log.warning("Income Params: " + galleryName + ", " + description + ", " + idUser);
        GalleryEntity galleryEntity = new GalleryEntity();
        galleryEntity.setDescription(description);
        galleryEntity.setGalleryName(galleryName);
        galleryEntity.setIdUser(idUser);
        try {
            galleryEntity.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        galleryService.addGall(galleryEntity);
        log.warning("---------------------FINISH ADD method----------------------");
        return "redirect:/gallery/list";
    }

    // Opening the edit gallery form page.
    @RequestMapping(value = "/edit/{idGalEnt}/{galleryName}/{description}", method = RequestMethod.GET)
    public String editGall(@PathVariable String idGalEnt, @PathVariable String galleryName,
                           @PathVariable String description) {
        log.warning("edit gallery " + idGalEnt + ", galleryName " + galleryName + ", description "
                + description);
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        if (gallEnt != null) {
            gallEnt.setGalleryName(galleryName);
            gallEnt.setDescription(description);
            galleryService.editGall(gallEnt);
            log.info("!!!!!!!!Success update comment with idGalEnt {"
                    + gallEnt.toString() + "}");
        }
        return "redirect:/gallery/list";
    }

    // Deleting the specified gallery.
    @RequestMapping(value = "/delete/{idGalEnt}", method = RequestMethod.GET)
    public String deleteGall(@PathVariable String idGalEnt) {
        log.info("delete gallery");
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        if (gallEnt != null) {
            galleryService.deleteGall(idGalEnt);
            log.info("Success delete gallery with idGalEnt {" + idGalEnt + "}");
        } else {
            log.warning("Unable to delete. Gallery with idGalEnt {" + idGalEnt + "} not found.");
        }
        return "redirect:/gallery/list";
    }

//    @RequestMapping(value = "/getPhoto/{id}")
//    public void getStudentPhoto(HttpServletResponse response, @PathVariable("id") String id) throws Exception {
//        log.warning("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        response.setContentType("image/jpeg");
//        byte[] bytes = galleryService.findGallById(id).getFile().getBytes();
//        InputStream inputStream = new ByteArrayInputStream(bytes);
//        IOUtils.copy(inputStream, response.getOutputStream());
//    }

    @RequestMapping(value = "/one_gallery/{idGalEnt}", method = RequestMethod.GET)
    public String getGalleryById(@PathVariable String idGalEnt, Model model) {
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        model.addAttribute("gallery", gallEnt);
        log.info("Gallery: " + gallEnt.toString());
        return "galleryEntity";
    }
}
