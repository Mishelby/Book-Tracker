package org.example.booktracker.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.booktracker.domain.post.PostCommentDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LastNCommentsDto(
        String authorName,
        List<PostCommentDto> postComments
) {
}