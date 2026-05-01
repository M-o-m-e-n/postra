package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.repositories.PostRepository;
import com.devtiro.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<Post> getPosts(UUID categoryId, UUID tagId) {

        return List.of();
    }
}
