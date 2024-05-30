package com.fse2.usecase.blogservice.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fse2.usecase.blogservice.entity.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
    List<Blog> findByBlogNameAndBlogCategoryAndBlogArticleAndBlogAuthor(String blogName, String blogCategory, String blogArticle, String blogAuthor);
    List<Blog> findByBlogName(String blogName);
    List<Blog> findByBlogCategory(String blogCategory);
    List<Blog> findByBlogCategoryAndBlogCreatedOnBetween(String blogCategory, Date durationFrom, Date durationTo);
}