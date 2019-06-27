package com.test.demo.controllers;


import com.test.demo.models.Comment;
import com.test.demo.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource(name = "CommentService")
    private CommentService commentService;

    // Displaying the initial comment list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getComment (Model model){
        System.out.println("list comment url ");
        List comment_list = commentService.getAll();
        model.addAttribute("comment", comment_list);
        System.out.println(comment_list.toString());
        return "index";
    }

    // Opening the add new comment form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addComment(Model model) {
        System.out.println("add comment url");
        model.addAttribute("commentAttr", new Comment());
        return "index";
    }

    // Opening the edit comment form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
public  String editComment (@RequestParam(value= "id", required = true)String id, Model model){

        System.out.println("edit comment url");
        model.addAttribute("commentAtttr", String.valueOf(commentService.findCommentId(id)));
        return "index";
    }

    // Deleting the specified comment.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id, Model model) {
        System.out.println("delete comment URL");
        commentService.deleteComment(id);
        return "redirect:list";
    }

    // Adding a new user or updating an existing user.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveComment (@ModelAttribute("commentAttr") Comment comment){
        if ( (!(String.valueOf(comment.getIdComment()).trim().equals(""))) && (String.valueOf(comment.getIdComment()) !=null)){
            commentService.editComment(comment);
        } else {
            commentService.addComment(comment);
        }
        return "redirect:list";
    }








}
