package com.fse2.usecase.blogservice.service;

import java.util.List;

import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;

public interface BlogService {
    String createOrUpdateBlog(BlogRequest blogRequest);
    String deleteBlog(String blogId);
    List<Blog> getAllBlogs();
    List<Blog> getAllBlogsOfUser(String userId);
    List<Blog> getBlogsByCategory(String blogCategory);
    List<Blog> getBlogsByCategoryAndDuration(String blogCategory, String durationFrom, String durationTo);
}