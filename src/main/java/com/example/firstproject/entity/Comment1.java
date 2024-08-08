package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Comment1 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentseq_gen")
    @SequenceGenerator(name = "commentseq_gen", sequenceName = "commentseq", allocationSize = 1)
    private Long id;

    @ManyToOne // Comment 엔티티와 Article 엔티티를 다대일 관계로설정
    @JoinColumn(name = "article_id") // 외래키 생성, Article 엔티티의 pk와 매핑
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment1 createComment(CommentDto dto, Article article) {
        // 예외 발생
        if(dto.getId() != null) {
            throw new IllegalArgumentException("댓글의 id가 없어야 합니다");
        }
        if(dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("게시글의 id가 잘못되었습니다.");
        }
        // 엔티티 생성 및 반환
        return new Comment1(
                dto.getId(),        // 댓글 아이디
                article,            // 부모 게시글
                dto.getNickname(),  // 댓글 닉네임
                dto.getBody()       // 댓글 내용
        );
    }


    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId()) {
            throw new IllegalArgumentException("잘못된 id가 입력되었습니다.");
        }

        // 객체 수정
        if(dto.getNickname() != null) { // 닉네임을 수정한다면
            this.nickname = dto.getNickname();
        }

        if(dto.getBody() != null) { // 댓글 내용을 수정한다면
            this.body = dto.getBody();
        }
    }
}
