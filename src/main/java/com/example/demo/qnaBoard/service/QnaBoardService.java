package com.example.demo.qnaBoard.service;

import com.example.demo.diaryBoard.dto.DiaryDTO;
import com.example.demo.member.entity.Member;
import com.example.demo.qnaBoard.dto.QnaDTO;
import com.example.demo.qnaBoard.entity.Qna;
import org.springframework.data.domain.Page;

public interface QnaBoardService {
    int register(QnaDTO dto);

    Page<QnaDTO> getList(int pageNumber);

    QnaDTO read(int no);

    void modify(QnaDTO dto);

    int remove(int no);

    void addCountView(int no);

    Qna toggleLike(int no, String userId);

    Page<QnaDTO> getSearchList(int pageNumber, String keyword);

    default Qna dtoToEntity(QnaDTO dto) {
        Member member = Member.builder().id(dto.getWriter()).build();

        Qna entity = Qna.builder()
                .no(dto.getNo())
                .writer(member.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .countView(dto.getCountView())
                .build();

        return entity;

    }

    default QnaDTO entityToDto(Qna entity) {

        QnaDTO dto = QnaDTO.builder()
                .no(entity.getNo())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .content(entity.getContent())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .countView(entity.getCountView()).build();

        return dto;
    }
}
