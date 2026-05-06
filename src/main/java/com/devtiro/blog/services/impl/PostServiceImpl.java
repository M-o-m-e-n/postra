package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.PostStatus;
import com.devtiro.blog.domain.dtos.CreatePostRequest;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.domain.entities.User;
import com.devtiro.blog.repositories.CategoryRepository;
import com.devtiro.blog.repositories.PostRepository;
import com.devtiro.blog.repositories.TagRepository;
import com.devtiro.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public List<Post> getPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            return postRepository.findByCategoryIdAndTagsIdAndStatus(categoryId, tagId, PostStatus.PUBLISHED);
        }
        if (categoryId != null) {
            return postRepository.findByCategoryIdAndStatus(categoryId, PostStatus.PUBLISHED);
        }
        if (tagId != null) {
            return postRepository.findByTagsIdAndStatus(tagId, PostStatus.PUBLISHED);
        }
        return postRepository.findByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public Post getPostById(UUID id) {
        return postRepository.findByIdAndStatus(id, PostStatus.PUBLISHED)
                .orElseThrow(() -> new NoSuchElementException("Post not found: " + id));
    }

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request, User author) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + request.getCategoryId()));
        Set<Tag> tags = resolveTags(request.getTagIds());

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .tags(tags)
                .author(author)
                .readingTime(request.getReadingTime())
                .status(resolveStatus(request.getStatus()))
                .build();

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, CreatePostRequest request) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found: " + id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + request.getCategoryId()));
        Set<Tag> tags = resolveTags(request.getTagIds());

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());
        existingPost.setReadingTime(request.getReadingTime());
        existingPost.setStatus(resolveStatus(request.getStatus()));
        existingPost.setCategory(category);
        existingPost.setTags(tags);

        return postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new NoSuchElementException("Post not found: " + id);
        }
        postRepository.deleteById(id);
    }

    private Set<Tag> resolveTags(Set<UUID> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return new HashSet<>();
        }
        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new NoSuchElementException("One or more tags were not found");
        }
        return new HashSet<>(tags);
    }

    private PostStatus resolveStatus(PostStatus status) {
        return status == null ? PostStatus.DRAFT : status;
    }
}
