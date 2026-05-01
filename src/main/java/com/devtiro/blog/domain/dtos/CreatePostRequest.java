package com.devtiro.blog.domain.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter

public class CreatePostRequest {

    private String title;
    private String content;
    private String authorId;
    private String category;
    private int readingTime;
}
