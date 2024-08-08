package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> showAll() {
        return articleRepository.findAll();
    }

    // 1. GET
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // 2. POST
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    // 3. PATCH
    public Article update(long id, ArticleForm dto) {
        // Long => object 이기 때문에 .equals()
        // long => 숫자형 이기 때문에 ==
        // 1. 수정용 DTO -> 엔티티 변환하기
        Article article = dto.toEntity();
        log.info("입력한 dto 엔티티로 변환한 데이터 = " + article.toString());

        // 2. 타겟 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리하기
        if (target == null || id != article.getId()) {
            // 400 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());

            // 응답은 restcontroller가 함으로 null 반화
            return null;
        }

        // 4. 업데이트 및 정상 응답(200)하기
        // 바로 수정도 가능
        // Article updated = articleRepository.save(article);
        target.patch(article);
        Article updated = articleRepository.save(target);
        
        // 응답은 restcontroller가 함으로 수정 데이터 반환
        return updated;
    }

    // 4. DELETE
    public Article delete(long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null) {
            log.info("뭔가가 잘못됐습니다!! 삭제할 수가 없습니다");
            return null;
        }

        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }

    // 5. 트랜잭션 맛보기
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto묶음을 entity묶음으로 변환하기
        List<Article> articleList = dtos.stream() // 1. dtos를 스트림화, 4. 최종결과 articleList에 저장
                .map(dto -> dto.toEntity())       // 2. map()으로 dto가 하나 올 때마다 dto.toEntity() 수행해서 매핑
                .collect(Collectors.toList());    // 3. 매핑한 것들을 리스트로 묶음
        
        // 2. 엔티티 묶음을 DB에 저장
        articleList.stream().forEach(article -> articleRepository.save(article));
        
        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L) // 아이디가 -1인 데이터 찾기
                .orElseThrow(() -> new IllegalArgumentException("결제 실패")); // 찾는 데이터가 없으면 예외 발생
        
        // 4. 결과 값 반환하기
        return articleList;
    }
}
