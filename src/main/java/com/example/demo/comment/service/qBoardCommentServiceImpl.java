package com.example.demo.comment.service;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.qBoardComment;
import com.example.demo.comment.repository.qBoardCommentRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.qnaBoard.entity.Qna;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class qBoardCommentServiceImpl implements qBoardCommentService{

    @Autowired
    private qBoardCommentRepository repository;

    @Autowired
    private AuthenticationFacade authenticationFacade;


    @Override
    public int register(CommentDTO dto) {
        qBoardComment entity = dtoToEntity(dto);
        System.out.println(entity);
        repository.save(entity);
        return entity.getCommentNo();
    }

    @Override
    public List<CommentDTO> getListByBoardNo(int boardNo) {
        Qna board = Qna.builder().no(boardNo).build(); // 게시물 조회

        List<qBoardComment> entityList = repository.findByBoard(board);

        List<CommentDTO> dtoList = new ArrayList<>();

        for (qBoardComment entity : entityList) {
            CommentDTO dto = entityToDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public void remove(int no) {
        qBoardComment comment = repository.findById(no)
                .orElseThrow(()-> new EntityNotFoundException("해당 댓글을 찾지 못했습니다. "));

        Member member = comment.getWriter();

        Authentication authentication = authenticationFacade.getAuthentication();   // 현재 로그인한 사용자 확인
        String writer = member.getId();

        if(!writer.equals(authentication.getName())){
            System.out.println("해당 댓글은 삭제 불가능합니다. (접근 불가능)");
        }else{
            repository.deleteById(no);
        }
    }

}
