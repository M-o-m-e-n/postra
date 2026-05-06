package com.devtiro.blog.services;


import com.devtiro.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getTags();
    Tag getTagById(UUID id);
    List<Tag> createTags(Set<String> tagNames);
    Tag updateTag(UUID id, String name);
    void deleteTag(UUID id);
}
