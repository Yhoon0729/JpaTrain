package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test; // Test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // 앞으로 사용할 수 있는 패키지 임포트

@SpringBootTest // 해당 클래스를 스프링부트와 연동해 테스트
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void showAll() {
        // 1. 예상 데이터
        Article a = new Article(1102L, "45", "4545645");
        Article b = new Article(1053L, "45", "4545");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b));

        // 2. 실제 데이터
        List<Article> articles = articleService.showAll();

        // 3. 예상 데이터와 실제 데이터 비교 및 검증
        // assertEquals(x, y) => x:예상 데이터, y:실제 데이터
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        // 1. 예상 데이터
        Long id = 1102L;
        Article expected = new Article(id, "45", "4545645");

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        // assertEquals(expected, article); 다른 객체 임으로 주소가 다름
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터
        Long id = 7777L;
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.show(id); // null을 반환

        // 3. 비교 및 검증
        assertEquals(expected, article); // null과 null을 비교함으로 시험 통과
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(1802L, title, content);

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
        
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터
        Long id = 1402L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.create(dto); // id가 포함된 게시글 생성요청이 올 경우 null 반환

        // 3. 비교 및 검증
        assertEquals(expected, article); // null과 null 비교 => 시험 통과
    }
}