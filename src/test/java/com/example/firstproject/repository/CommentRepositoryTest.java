package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // 해당 클래스를 JPA와 연동해 테스트
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") // 테스트 이름을 붙일 때 사용
    void findByArticleId() {
        // Case 1 : 4번 게시글의 모든 댓글 조회
        { // 테스트를 여러 개 할것이므로 중괄호로 묶음
            // 1. 입력 데이터 준비
            Long articleId = 4L;

            // 2. 실제 데이터
            List<Comment1> comments = commentRepository.findByArticleId(articleId);
            System.out.println("실제 데이터 => " + comments);

            // 3. 예상 데이터
            // 3-2. 부모게시글 객체 생성
            Article article = new Article(4L, "당신의 인생 영화?", "댓글 ㄱ");
            // 3-1. 댓글 객체 생성
            Comment1 a = new Comment1(1L, article, "Park", "굿 윌 헌팅");
            Comment1 b = new Comment1(2L, article, "Kim", "아이 엠 샘");
            Comment1 c = new Comment1(3L, article, "Choi", "쇼생크 탈출");
            // 3-3. 댓글 객체 합치기
            List<Comment1> expected = Arrays.asList(a,b,c);

            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString());
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 대슬 조회")
    void findByNickname() {
        // Case 1 : "Park"의 모든 댓글 조회
        {
            // 1. 입력 데이터 준비
            String nickname = "Park";

            // 2. 실제 데이터
            List<Comment1> comments = commentRepository.findByNickname(nickname);
            System.out.println("실제 데이터" + comments);

            // 3. 예상 데이터
            
            // 4. 비교 및 검증
        }
    }
}