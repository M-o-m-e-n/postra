package com.devtiro.blog.repositories;

import com.devtiro.blog.domain.PostStatus;
import com.devtiro.blog.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByStatus(PostStatus status);

    List<Post> findByCategoryIdAndStatus(UUID categoryId, PostStatus status);

    List<Post> findByTagsIdAndStatus(UUID tagId, PostStatus status);

    List<Post> findByCategoryIdAndTagsIdAndStatus(UUID categoryId, UUID tagId, PostStatus status);

    Optional<Post> findByIdAndStatus(UUID id, PostStatus status);
}
