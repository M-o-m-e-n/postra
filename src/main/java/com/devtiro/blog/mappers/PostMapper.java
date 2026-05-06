package com.devtiro.blog.mappers;

import com.devtiro.blog.domain.dtos.AuthorDto;
import com.devtiro.blog.domain.dtos.PostDto;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TagMapper.class)
public interface PostMapper {

	@Mapping(target = "author", source = "author", qualifiedByName = "toAuthorDto")
	@Mapping(target = "category", source = "category", qualifiedByName = "toCategoryName")
	PostDto toDto(Post post);

	@Named("toAuthorDto")
	default AuthorDto toAuthorDto(User author) {
		if (author == null) {
			return null;
		}
		return AuthorDto.builder()
				.id(author.getId())
				.name(author.getName())
				.build();
	}

	@Named("toCategoryName")
	default String toCategoryName(Category category) {
		return category == null ? null : category.getName();
	}
}
