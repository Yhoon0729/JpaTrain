package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment1;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    private Long articleId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment1 comment1) {
        return new CommentDto(
                comment1.getId(),
                comment1.getArticle().getId(),
                comment1.getNickname(),
                comment1.getBody()
        );
    }
}
