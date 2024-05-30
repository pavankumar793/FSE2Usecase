package com.fse2.usecase.blogservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.service.BlogService;

@RestController
@RequestMapping("/blogsite/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/add")
    public ResponseEntity<String> createOrUpdateBlog(@RequestBody BlogRequest blogRequest) {
        String message = blogService.createOrUpdateBlog(blogRequest);
        if (message.equals("Blog created successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{blogName}")
    public ResponseEntity<String> deleteBlog(@PathVariable("blogName") String blogName) {
        String message = blogService.deleteBlog(blogName);
        if (message.equals("Blog deleted successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> allBlogs = blogService.getAllBlogs();
        if (!allBlogs.isEmpty()) {
            return ResponseEntity.ok(allBlogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
