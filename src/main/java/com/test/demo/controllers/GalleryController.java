package com.test.demo.controllers;

import com.test.demo.models.GalleryEntity;
import com.test.demo.services.GalleryService;
import com.test.demo.utils.sys.Const;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    Logger log = Logger.getLogger(GalleryController.class.getName());
    @Resource(name = "GalleryService")
    private GalleryService galleryService;

    // Displaying the initial gallery list.

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(HttpSession session,  Model model) {
        log.warning("---------------------START get Gallery LIST method----------------------");
        String id =(String) session.getAttribute("id_user");
//        galleryService.getAll();
        List gall_list = galleryService.getAllById(id);
        model.addAttribute("newGall", new GalleryEntity());
        model.addAttribute("gallery", gall_list);
        log.warning("GalleryEntity" + gall_list.toString());
        log.warning("---------------------END get Gallery LIST method----------------------");
        return "galleryEntity";
    }
//@RequestMapping(value = "/list", method = RequestMethod.GET)
//public String getPersons(Model model) {
//    log.warning("---------------------START get Gallery LIST method----------------------");
//    String img = galleryService.findGallById("bda467e4-5ea7-4b78-b175-8f6972bd2b5e").getFile();
//    log.warning("String img :" + img);
//    List gall_list = galleryService.getAll();
//    model.addAttribute("newGall", new GalleryEntity());
//    model.addAttribute("image", "data:image/jpeg;base64,"+img);
//    model.addAttribute("gallery", gall_list);
//    log.warning("GalleryEntity" + gall_list.toString());
//    log.warning("---------------------END get Gallery LIST method----------------------");
//    return "galleryEntity";
//}

    // Opening the add new gallery form page.
    @PostMapping("/add")
    public String addGall(HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("galleryName") String galleryName,
                          @RequestParam("description") String description) {

        Object idObj = session.getAttribute("id_user");
        if (idObj==null){
            return Const.PAGE_MAIN;
        }
        String id = idObj.toString();
        log.warning("---------------------START ADD method----------------------");
        log.warning("Income Params: " + galleryName + ", " + description + ", " + id);
        GalleryEntity galleryEntity = new GalleryEntity();
        galleryEntity.setDescription(description);
        galleryEntity.setGalleryName(galleryName);
        galleryEntity.setIdUser(id);
        galleryEntity.setState(Const.TRUE);
        try {
            galleryEntity.setFile(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        galleryService.addGall(galleryEntity);
        log.warning("---------------------FINISH ADD method----------------------");
        return Const.SERVICE_REDIRECT + Const.URL_GALLERY_LIST;
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
        return Const.SERVICE_REDIRECT + Const.URL_GALLERY_LIST;
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
        return Const.SERVICE_REDIRECT + Const.URL_GALLERY_LIST;
    }

    @RequestMapping(value = "/one_gallery/{idGalEnt}", method = RequestMethod.GET)
    public String getGalleryById(@PathVariable String idGalEnt, Model model) {
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        model.addAttribute("gallery", gallEnt);
        log.info("Gallery: " + gallEnt.toString());
        return "galleryEntity";
    }

    @RequestMapping(value = "/list_true", method = RequestMethod.GET)
    public String getListStateTrue(HttpSession session,  Model model) {
        log.warning("---------------------START get Gallery TRUE LIST method----------------------");
        String state =(String) session.getAttribute("state");
//        galleryService.getAll();
        List gall_list = galleryService.getAllByState(state);
        model.addAttribute("newGall", new GalleryEntity());
        model.addAttribute("gallery", gall_list);
        log.warning("GalleryEntity" + gall_list.toString());
        log.warning("---------------------END get Gallery TRUE LIST method----------------------");
        return "galleryEntity";
    }
}
