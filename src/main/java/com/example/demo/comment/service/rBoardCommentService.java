package com.example.demo.comment.service;

// 기본 Java 및 Spring 프레임워크 패키지와, 애플리케이션 내의 DTO, entity 클래스를 임포트한다.
import java.util.List;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.rBoardComment;
import com.example.demo.member.entity.Member;
import com.example.demo.runningBoard.entity.Running;

// 댓글 기능을 위한 서비스 인터페이스 정의.
public interface rBoardCommentService {

    // 댓글을 등록하는 메서드로 CommentDTO를 매개변수로 받아 처리 결과로 int 값을 리턴한다.
    int register(CommentDTO dto);

    // 특정 게시글(보드 넘버)에 해당하는 모든 댓글을 조회하여 CommentDTO 리스트로 반환하는 메서드.
    List<CommentDTO> getListByBoardNo(int boardNo);

    // 댓글 번호(no)를 매개변수로 받아 해당 댓글을 삭제하는 메서드.
    void remove(int no);

    // CommentDTO를 rBoardComment 엔티티로 변환하기 위한 기본 메서드. 확장 기능을 위해 default로 정의된다.
    default rBoardComment dtoToEntity(CommentDTO dto) {
        // 댓글 작성자를 식별하기 위한 Member 엔티티 생성.
        Member member = Member.builder().id(dto.getWriter()).build(); 
        
        // 댓글이 속한 게시글(보드) 정보를 식별하기 위한 Running 엔티티 생성.
        Running board = Running.builder().no(dto.getBoardNo()).build();  

        // DTO의 정보를 바탕으로 rBoardComment 엔티티 객체를 생성한다.
        rBoardComment entity = rBoardComment.builder()
                .commentNo(dto.getCommentNo())
                .board(board)
                .content(dto.getContent())
                .writer(member)
                .build();
        
        return entity; // 생성된 rBoardComment 엔티티를 반환한다.
    }

    // rBoardComment 엔티티를 CommentDTO로 변환하기 위한 기본 메서드.
    default CommentDTO entityToDto(rBoardComment entity) {
        // 엔티티의 정보를 활용하여 CommentDTO 객체를 생성한다.
        CommentDTO dto = CommentDTO.builder()
                .commentNo(entity.getCommentNo())
                .boardNo(entity.getBoard().getNo())
                .content(entity.getContent())
                .writer(entity.getWriter().getId())
                .regDate(entity.getRegDate()) // 등록 날짜
                .modDate(entity.getModDate()) // 수정 날짜
                .build();

        return dto; // 생성된 CommentDTO를 반환한다.
    }

}

