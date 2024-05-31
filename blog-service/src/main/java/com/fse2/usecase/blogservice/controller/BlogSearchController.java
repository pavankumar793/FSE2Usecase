package com.fse2.usecase.blogservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.service.BlogService;

@RestController
@RequestMapping("/blogsite/search")
public class BlogSearchController {
    
    @Autowired
    public BlogService blogService;

    @GetMapping("/{category}")
    public ResponseEntity<List<Blog>> getAllBlogsByCategory(@PathVariable("category") String category) {
        List<Blog> allBlogs = blogService.getBlogsByCategory(category);
        if (!allBlogs.isEmpty()) {
            return ResponseEntity.ok(allBlogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{category}/{durationFrom}/{durationTo}")
    public ResponseEntity<List<Blog>> getAllBlogsByCategory(@PathVariable("category") String category, @PathVariable("durationFrom") String durationFrom, @PathVariable("durationTo") String durationTo) {
        List<Blog> allBlogs = blogService.getBlogsByCategoryAndDuration(category, durationFrom, durationTo);
        if (!allBlogs.isEmpty()) {
            return ResponseEntity.ok(allBlogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}