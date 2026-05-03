//package com.devtiro.blog.mappers;
//
//import com.devtiro.blog.domain.dtos.CreatePostRequest;
//import com.devtiro.blog.domain.dtos.CreatePostRequestDto;
//import com.devtiro.blog.domain.dtos.PostDto;
//import com.devtiro.blog.domain.entities.Post;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
//public interface PostMapper {
//
//    @Mapping(target = "author", source = "author")
//    @Mapping(target = "category", source = "category")
//    @Mapping(target = "tags", source = "tags")
//    @Mapping(target = "status", source = "status")
//    PostDto toDto(Post post);
//
//    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
//
//    Object toPostResponse(Post post);
//}
