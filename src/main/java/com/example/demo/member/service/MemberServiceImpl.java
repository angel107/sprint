package com.example.demo.member.service;

import java.util.Optional;

import com.example.demo.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.web.multipart.MultipartFile;

//MemberServiceImpl 클래스: 회원 관련 서비스를 제공하는 클래스입니다.
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

  // MemberRepository 인터페이스를 자동으로 주입받습니다 (의존성 주입).
  @Autowired
  MemberRepository repository;

  @Autowired
  private FileUtil fileUtil;

  // PasswordEncoder 인터페이스를 자동으로 주입받습니다 (비밀번호 암호화에 사용).
  @Autowired
  PasswordEncoder passwordEncoder;

  // 회원 목록을 페이징 처리해서 반환하는 메소드입니다.
  @Override
  public Page<MemberDTO> getList(int pageNumber) {
    // 페이지 번호가 0이면 첫 페이지로 설정하고, 아니면 입력된 페이지에서 -1을 합니다 (페이지 인덱스는 0부터 시작하기 때문).
    int pageIndex = (pageNumber == 0) ? 0 : pageNumber - 1;
    // 요청한 페이지 정보와 정렬 방식(Sort)을 Pageable 객체에 담습니다.
    Pageable pageable = PageRequest.of(pageIndex, 10, Sort.by("regDate").descending());
    // repository에서 모든 회원 정보를 조회합니다. 결과는 Page 객체로 받습니다.
    Page<Member> entityPage = repository.findAll(pageable);
    // Page 객체의 map 메소드를 사용해 Member 엔티티를 MemberDTO로 변환합니다.
    Page<MemberDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));

    return dtoPage;
  }

  // 회원을 등록하는 메소드입니다.
  @Override
  public boolean register(MemberDTO dto) {
    String id = dto.getId();
    log.info("Register Id = {}", id);
    // ID로 회원 정보를 조회합니다.
    MemberDTO getDto = read(id);
    // 같은 ID의 회원 정보가 이미 있으면 false를 반환합니다.
    if (getDto != null) {
      System.out.println("사용중인 아이디입니다.");
      return false;
    }
    // DTO를 엔티티로 변환합니다.
    Member entity = dtoToEntity(dto);
    // 새 회원의 권한을 "ROLE_USER"로 설정합니다.
    entity.setRole("ROLE_USER");
    // 비밀번호를 암호화하여 설정합니다.
    String enPw = passwordEncoder.encode(entity.getPassword());
    entity.setPassword(enPw);

    // 변경된 엔티티를 저장합니다.
    repository.save(entity);
    return true;
  }

  // ID로 회원을 찾아서 반환하는 메소드입니다.
  @Override
  public MemberDTO findMemberById(String id) {
    // repository에서 ID로 회원 정보를 조회합니다.
    Optional<Member> optionalMember = repository.findById(id);
    if (optionalMember.isPresent()) {
      // 존재하면 Member 객체를 가져와서 DTO로 변환합니다.
      Member member = optionalMember.get();
      return entityToDto(member);
    } else {
      // 존재하지 않으면 null을 반환합니다.
      return null;
    }
  }

  // ID로 회원을 읽어서 반환하는 메소드입니다.
  @Override
  public MemberDTO read(String id) {
    Optional<Member> result = repository.findById(id);
    if (result.isPresent()) {
      // 존재하면 Member 객체를 가져와서 DTO로 변환합니다.
      Member member = result.get();
      return entityToDto(member);
    } else {
      // 존재하지 않으면 null을 반환합니다.
      return null;
    }
  }

  // 소셜 로그인을 사용해 회원가입하는 메소드입니다.
  @Override
  public MemberDTO saveSocialMember(String email) {
    // repository에서 이메일로 회원 정보를 조회합니다.
    Optional<Member> result = repository.findById(email);

    if (result.isPresent()) {
      // 이미 존재하는 회원이면, 그 정보를 DTO로 변환하여 반환합니다.
      return entityToDto(result.get());
    }

    // 존재하지 않는다면 새로운 회원 정보를 생성합니다.
    // 이메일을 ID와 이름으로 사용하고, 기본 비밀번호와 권한을 설정합니다.
    Member member = Member.builder().id(email)
      .name(email)
      .password(passwordEncoder.encode("1111")) // 임시 비밀번호
      .fromSocial(true).role("ROLE_USER")
      .build();

    // 새 회원 정보를 저장합니다.
    repository.save(member);
    result = repository.findById(email);

    // 생성된 새 회원 정보를 DTO로 변환하여 반환합니다.
    return entityToDto(result.get());
  }

  // 회원 정보를 수정하는 메소드입니다.
  @Override
  public void modify(MemberDTO dto) {
    Optional<Member> result = repository.findById(dto.getId());

    if (result.isPresent()) {
      Member entity = result.get();
      // 이름과 권한을 변경합니다.
      entity.setName(dto.getName());
//      entity.setRole(dto.getRole());
      if (!dto.getProfileImage().isEmpty()) {
        String path = fileUtil.profileFileUpload(dto.getProfileImage(), dto.getId());
        entity.setProfileImagePath(path);
      }

      // 기존 비밀번호와 다르면 새로운 비밀번호로 변경합니다.
      boolean matchResult1 = entity.getPassword().equals(dto.getPassword());
      if (!matchResult1) {
        System.out.println("패스워드가 변경되었습니다");
        String hashpassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(hashpassword);
      }
      // 변경된 회원 정보를 저장합니다.
      repository.save(entity);
    }
  }

  // ID로 회원의 이름을 찾아 반환하는 메소드입니다.
  @Override
  public String findNameById(String id) {
    Optional<Member> optionalMember = repository.findById(id);
    if (optionalMember.isPresent()) {
      // 존재하면 Member 객체에서 이름을 가져와 반환합니다.
      Member member = optionalMember.get();
      return member.getName();
    } else {
      // 존재하지 않으면 null을 반환합니다.
      return null;
    }
  }
}
