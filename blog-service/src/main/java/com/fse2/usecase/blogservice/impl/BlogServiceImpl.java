package com.fse2.usecase.blogservice.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.repo.BlogRepository;
import com.fse2.usecase.blogservice.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    public BlogRepository blogRepository;

    @Override
    public String createOrUpdateBlog(BlogRequest blogRequest) {
        try {
            Blog existingBlog = new Blog();

            if (blogRequest.getBlogId() != null) {
                existingBlog = blogRepository.findById(blogRequest.getBlogId()).orElse(null);
                if (existingBlog != null) {
                    existingBlog.setBlogId(blogRequest.getBlogId());
                    existingBlog.setBlogName(blogRequest.getBlogName());
                    existingBlog.setBlogArticle(blogRequest.getBlogArticle());
                    existingBlog.setBlogAuthor(blogRequest.getBlogAuthor());
                    existingBlog.setBlogCategory(blogRequest.getBlogCategory());
                    existingBlog.setUserId(blogRequest.getUserId());
                    blogRepository.save(existingBlog);
                    return "Blog already exists so updated successfully";
                }
            }
            Blog blog = new Blog();
            blog.setBlogName(blogRequest.getBlogName());
            blog.setBlogArticle(blogRequest.getBlogArticle());
            blog.setBlogAuthor(blogRequest.getBlogAuthor());
            blog.setBlogCategory(blogRequest.getBlogCategory());
            blog.setUserId(blogRequest.getUserId());
            blogRepository.save(blog);
            return "Blog created successfully";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while processing the blog data", e);
        }
    }

    @Override
    public String deleteBlog(String blogId) {
        try {
            Blog existingBlog = blogRepository.findById(blogId).orElse(null);
            if (ObjectUtils.isEmpty(existingBlog)) {
                return "Blog not found";
            } else {
                blogRepository.delete(existingBlog);
                return "Blog deleted successfully";
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while deleting the blog", e);
        }
    }

    @Override
    public List<Blog> getAllBlogsOfUser(String userId) {
        try {
            return blogRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while fetching the blogs", e);
        }
    }

    @Override
    public List<Blog> getAllBlogs() {
        try {
            return blogRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while fetching the blogs", e);
        }
    }

    @Override
    public List<Blog> getBlogsByCategory(String blogCategory) {
        try {
            return blogRepository.findByBlogCategory(blogCategory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while fetching the blogs", e);
        }
    }

    @Override
    public List<Blog> getBlogsByCategoryAndDuration(String blogCategory, String durationFrom, String durationTo) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = formatter.parse(durationFrom);
            Date toDate = formatter.parse(durationTo);
            return blogRepository.findByBlogCategoryAndBlogCreatedOnBetween(blogCategory, fromDate, toDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while fetching the blogs", e);
        }
    }
}
