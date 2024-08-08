package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 1. Get 이용한 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comment(@PathVariable Long articleId) {
        List<CommentDto> dtos = commentService.comments(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. POST 이용한 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments") // 1. 댓글 생성 요청 접수
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        // 서비스에 위임
        CommentDto createdDto = commentService.create(articleId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 3. PATCH 이용한 댓글 수정
    @PatchMapping("/api/comments/{id}") // 1. 댓글 수정 요청 접수
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        // 서비스에 위임
        CommentDto updatedDto = commentService.update(id,dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // 4. DELETE 이용한 댓글 삭제
    @DeleteMapping("/api/comments/{id}") // 1. 댓글 삭제 요청 접수
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        // 서비스에 위임
        CommentDto deletedDto = commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
