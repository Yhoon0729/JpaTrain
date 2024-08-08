package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment1, Long> {
    // 특정 게시글의 모든 댓글 조회
    @Query(value = "select * from COMMENT1 where ARTICLE_ID  = :articleId", nativeQuery = true)
    List<Comment1> findByArticleId(@Param("articleId") Long articleId);

    // 특정 닉네임의 모든 댓글 조회
    @Query(value = "select * from comment1 where nickname = :nickname", nativeQuery = true)
    List<Comment1> findByNickname(@Param("nickname") String nickname);
}
