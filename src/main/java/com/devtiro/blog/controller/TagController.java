package com.devtiro.blog.controller;

import com.devtiro.blog.domain.dtos.CreateTagsRequest;
import com.devtiro.blog.domain.dtos.TagDto;
import com.devtiro.blog.domain.dtos.UpdateTagRequest;
import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.mappers.TagMapper;
import com.devtiro.blog.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagMapper tagMapper;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getTags() {
        List<Tag> tags = tagService.getTags();
        List<TagDto> tagRespons = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagRespons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable UUID id) {
        Tag tag = tagService.getTagById(id);
        return ResponseEntity.ok(tagMapper.toTagResponse(tag));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<TagDto>> createTags(@Valid @RequestBody CreateTagsRequest createTagsRequest) {
        List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
        List<TagDto> createdTagRespons = savedTags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(
                createdTagRespons,
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<TagDto> updateTag(@PathVariable UUID id, @Valid @RequestBody UpdateTagRequest updateTagRequest) {
        Tag updatedTag = tagService.updateTag(id, updateTagRequest.getName());
        return ResponseEntity.ok(tagMapper.toTagResponse(updatedTag));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
