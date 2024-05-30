package com.fse2.usecase.blogservice.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.repo.BlogRepository;
import com.fse2.usecase.blogservice.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public String createOrUpdateBlog(BlogRequest blogRequest) {
        try {
            List<Blog> existingBlogs = blogRepository.findByBlogNameAndBlogCategoryAndBlogArticleAndBlogAuthor(
                    blogRequest.getBlogName(), blogRequest.getBlogCategory(), blogRequest.getBlogArticle(),
                    blogRequest.getBlogAuthor());

            if (!existingBlogs.isEmpty()) {
                Blog existingBlog = existingBlogs.get(0);
                existingBlog.setBlogName(blogRequest.getBlogName());
                existingBlog.setBlogArticle(blogRequest.getBlogArticle());
                existingBlog.setBlogAuthor(blogRequest.getBlogAuthor());
                existingBlog.setBlogCategory(blogRequest.getBlogCategory());
                blogRepository.save(existingBlog);
                return "Blog already exists hence updated successfully";
            } else {
                Blog blog = new Blog();
                blog.setBlogName(blogRequest.getBlogName());
                blog.setBlogArticle(blogRequest.getBlogArticle());
                blog.setBlogAuthor(blogRequest.getBlogAuthor());
                blog.setBlogCategory(blogRequest.getBlogCategory());
                blogRepository.save(blog);
                return "Blog created successfully";
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while processing the blog data", e);
        }
    }

    @Override
    public String deleteBlog(String blogName) {
        try {
            List<Blog> existingBlogs = blogRepository.findByBlogName(blogName);
            if (existingBlogs.isEmpty()) {
                return "Blog not found";
            } else {
                blogRepository.delete(existingBlogs.get(0));
                return "Blog deleted successfully";
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while deleting the blog", e);
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
