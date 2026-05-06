package com.devtiro.blog.controller;

import com.devtiro.blog.domain.dtos.CreatePostRequest;
import com.devtiro.blog.domain.dtos.PostDto;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.User;
import com.devtiro.blog.mappers.PostMapper;
import com.devtiro.blog.repositories.UserRepository;
import com.devtiro.blog.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private static final String DEFAULT_EXTERNAL_PASSWORD = "keycloak";

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<PostDto>> listPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<PostDto> posts = postService.getPosts(categoryId, tagId)
                .stream()
                .map(postMapper::toDto)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(postMapper.toDto(post));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequest createPostRequest,
            @AuthenticationPrincipal Jwt jwt) {
        User author = resolveAuthor(jwt);
        Post createdPost = postService.createPost(createPostRequest, author);
        return new ResponseEntity<>(postMapper.toDto(createdPost), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody CreatePostRequest updatePostRequest) {
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        return ResponseEntity.ok(postMapper.toDto(updatedPost));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    private User resolveAuthor(Jwt jwt) {
        if (jwt == null) {
            throw new IllegalArgumentException("Missing authentication token");
        }
        String email = jwt.getClaimAsString("email");
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Token is missing email claim");
        }
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .name(resolveName(jwt, email))
                        .password(DEFAULT_EXTERNAL_PASSWORD)
                        .build()));
    }

    private String resolveName(Jwt jwt, String fallback) {
        String name = jwt.getClaimAsString("name");
        if (name == null || name.isBlank()) {
            name = jwt.getClaimAsString("preferred_username");
        }
        return (name == null || name.isBlank()) ? fallback : name;
    }
}

