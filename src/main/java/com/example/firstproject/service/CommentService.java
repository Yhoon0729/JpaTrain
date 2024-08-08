package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment1;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // 댓글 조회
    public List<CommentDto> comments(Long articleId) {
        /*
        // 1-1. 댓글 조회
        List<Comment1> comments = commentRepository.findByArticleId(articleId);

        // 2-1. 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) { // 2-1. 조회한 댓글 엔티티 수만큼 반복하기
            Comment1 c = comments.get(i); // 2-2. 조회한 댓글 엔티티 하나씩 가져오기
            CommentDto dto = CommentDto.createCommentDto(c); // 2-3. 엔티티를 DTO로 변환
            dtos.add(dto); // 2-4. 변환한 DTO를 dtos 리스트에 삽입
        }

        // 3-1. 결과 반환
        return dtos;
    }
        */

        // 3-2. 결과 반환
        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록 조회
                .stream() // 스트림으로 하겠다, 댓글 엔티티 목록을 스트림으로 변환
                .map(comment1 -> CommentDto.createCommentDto(comment1)) // 스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑, 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 스트림 데이터를 리스트 자료형으로 변환;
    }

    // 댓글 작성
    @Transactional // create() 메서드는 DB의 내용을 바꾸기 때문에 롤백하기 위함
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId) // 부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 작성 실패. 대상 게시글이 없습니다."));
        // 2. 댓글 엔티티 생성
        Comment1 comment1 = Comment1.createComment(dto, article);

        // 3. 댓글 엔티티를 DB에 저장
        Comment1 created = commentRepository.save(comment1);

        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    // 댓글 수정
    @Transactional // 위와 같은 이유
    public CommentDto update(Long id, CommentDto dto) {
        // 1. 수정할 댓글 엔티티 불러오기
        Comment1 target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("대상 댓글이 없습니다."));

        // 2. 댓글 엔티티 수정
        target.patch(dto);

        // 3. 댓글 엔티티를 DB에 저장
        Comment1 updated = commentRepository.save(target);
        
        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 1. 삭제할 댓글 불러오기
        Comment1 target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("대상 댓글이 없습니다"));

        // 2. 댓글 엔티티 삭제
        commentRepository.delete(target);
        
        // 3. 삭제한 댓글 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
