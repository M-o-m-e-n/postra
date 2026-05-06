package com.devtiro.blog.domain.dtos;

import com.devtiro.blog.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 120, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 20, max = 10000, message = "Content must be between {min} and {max} characters")
    private String content;

    @NotNull(message = "Category is required")
    private UUID categoryId;

    @Size(max = 10, message = "Maximum {max} tags are allowed")
    private Set<UUID> tagIds;

    @NotNull(message = "Reading time is required")
    @Positive(message = "Reading time must be greater than zero")
    private Integer readingTime;

    private PostStatus status;
}
