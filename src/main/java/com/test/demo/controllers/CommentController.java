package com.test.demo.controllers;

import com.test.demo.models.Comment;
import com.test.demo.services.CommentService;
import com.test.demo.utils.sys.Const;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/comment")
public class CommentController {

    Logger log = Logger.getLogger(CommentController.class.getName());
    @Resource(name = "CommentService")
    private CommentService commentService;

    // Displaying the initial comment list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getComment(Model model) {
        log.warning("---------------------------LIST-Comment-START-Controller---------------------------------");
        List comment_list = commentService.getAll();
        model.addAttribute("newComment", new Comment());
        model.addAttribute("comments", comment_list);
        log.warning("Comments: " + comment_list.toString());
        log.warning("---------------------------LIST-Comment-END-Controller---------------------------------");
        return "comment";
    }

    @PostMapping("/add")
    public String addComment(@ModelAttribute Comment comment){
        log.warning("Income comment: " + comment.toString());
        commentService.addComment(comment);
        return Const.SERVICE_REDIRECT + Const.URL_COMMENT_LIST;
    }

    // Opening the edit comment form page.
    @RequestMapping(value = "/edit/{idComment}/{text}", method = RequestMethod.GET)
    public String editComment(@PathVariable String idComment, @PathVariable String text) {
        log.warning("edit comment " + idComment + ", text: " + text);
        Comment comment = commentService.findCommentId(idComment);
        if (comment != null) {
            comment.setText(text);
            comment.setDate(new Date().toString());
            commentService.editComment(comment);
            log.info("!!!!!!!!Success update comment with id {" + comment.toString() + "}");
        }
        return Const.SERVICE_REDIRECT + Const.URL_COMMENT_LIST;
    }

    // Deleting the specified comment.
    @RequestMapping(value = "/delete/{idComment}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable String idComment) {
        log.info("delete comment");
        Comment comment = commentService.findCommentId(idComment);
        if (comment != null) {
            commentService.deleteComment(idComment);
            log.info("Success delete comment with id {" + idComment + "}");
        } else {
            log.warning("Unable to delete. Comment with id {" + idComment + "} not found.");
        }
        return Const.SERVICE_REDIRECT + Const.URL_COMMENT_LIST;
    }

    @RequestMapping(value = "/one_comment/{idComment}", method = RequestMethod.GET)
    public String getCommentById(Model model, @PathVariable String idComment) {
        Comment oneComment = commentService.findCommentId(idComment);
        model.addAttribute("oneComment", oneComment);
        log.info("Comment: " + oneComment.toString());
        return "comment";
    }
}
