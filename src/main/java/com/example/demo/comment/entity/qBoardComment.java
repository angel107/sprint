package com.example.demo.comment.entity;

import com.example.demo.member.entity.Member;
import com.example.demo.qnaBoard.entity.Qna;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class qBoardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int commentNo;

    @ManyToOne
    Qna board;

    @Column(length = 1500)
    String content;

    @ManyToOne
    Member writer;
}
