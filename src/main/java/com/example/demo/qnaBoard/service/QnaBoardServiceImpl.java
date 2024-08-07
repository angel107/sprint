package com.example.demo.qnaBoard.service;

import com.example.demo.diaryBoard.dto.DiaryDTO;
import com.example.demo.diaryBoard.entity.Diary;
import com.example.demo.qnaBoard.dto.QnaDTO;
import com.example.demo.qnaBoard.entity.Qna;
import com.example.demo.qnaBoard.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QnaBoardServiceImpl implements QnaBoardService{

    @Autowired
    QnaRepository repository;

    @Override
    public int register(QnaDTO dto) {
        Qna entity = dtoToEntity(dto);
        repository.save(entity);
        int newNo = entity.getNo();
        return newNo;
    }

    @Override
    public Page<QnaDTO> getList(int pageNumber) {
        int pageNum = (pageNumber == 0) ? 0 : pageNumber - 1;

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("no").descending());

        Page<Qna> entityPage = repository.findAll(pageable);

        Page<QnaDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));

        return dtoPage;
    }
    @Override
    public QnaDTO read(int no) {
        Optional<Qna> result = repository.findById(no);

        if (result.isPresent()) {
            Qna qna = result.get();
            QnaDTO dto = entityToDto(qna);

            return dto;
        }
        return null;
    }

    public void modify(QnaDTO dto) {
        Optional<Qna> result = repository.findById(dto.getNo());

        if (result.isPresent()) {
            Qna entity = result.get();

            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public int remove(int no) {

        Optional<Qna> result = repository.findById(no);
        if (result.isPresent()) {
            repository.deleteById(no);

            return 1; // 삭제 성공
        } else {
            return 0; // 삭제 실패
        }

    }

    @Override
    public Page<QnaDTO> getSearchList(int pageNumber, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("no").descending());

        Page<Qna> entityPage = repository.findByTitleContainingIgnoreCase(keyword, pageable);

        Page<QnaDTO> dtoPage = entityPage.map(this::entityToDto);
        return dtoPage;
    }

    @Override
    public void addCountView(int no) {
        Optional<Qna> result = repository.findById(no);
        if(result.isPresent()){
            Qna qna = result.get();
            qna.setCountView(qna.getCountView()+1);
            repository.save(qna);
        }
    }

        @Override
        public Qna toggleLike(int no, String userId) {
            Optional<Qna> result = repository.findById(no);

            if (result.isPresent()) {
                Qna qna = result.get();
                Set<String> likedUsers = qna.getLikedUsers();

                if (likedUsers.contains(userId)) {
                    likedUsers.remove(userId);
                    qna.setCountLike(qna.getCountLike() - 1);
                } else {
                    likedUsers.add(userId);
                    qna.setCountLike(qna.getCountLike() + 1);
                }

                return repository.save(qna);
            }

            return null;

    }


}
