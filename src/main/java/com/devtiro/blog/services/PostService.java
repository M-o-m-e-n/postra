package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.dtos.CreatePostRequest;
import com.devtiro.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getPosts(UUID categoryId, UUID tagId);

    Post getPostById(UUID id);

    Post createPost(CreatePostRequest request, User author);

    Post updatePost(UUID id, CreatePostRequest request);

    void deletePost(UUID id);
}
