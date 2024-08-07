// com.example.demo.joinMember.repository 패키지를 선언합니다.
package com.example.demo.joinMember.repository;

// 필요한 클래스들을 임포트합니다.
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.joinMember.entity.JoinMember;

// JoinMemberRepository 인터페이스를 선언하고, JpaRepository를 상속받습니다. JpaRepository의 제네릭 타입으로 JoinMember 엔티티와, 그 기본키의 타입인 Integer를 사용합니다.
public interface JoinMemberRepository extends JpaRepository<JoinMember, Integer> {

    // runningNo가 일치하는 모든 JoinMember 엔티티를 찾아 리스트로 반환하는 메소드를 선언합니다.
    List<JoinMember> findByRunningNo_No(int runningNo);

    // runningNo와 joinNo가 모두 일치하는 JoinMember 엔티티를 찾아 Optional로 감싸서 반환하는 메소드를 선언합니다.
    Optional<JoinMember> findByRunningNo_noAndJoinNo(int runningNo, int joinNo);
    
    // runningNo와 runnerId가 일치하는 JoinMember 엔티티를 찾아 Optional로 감싸서 반환하는 메소드를 선언합니다. 메소드 네이밍에서 대소문자 구별에 주의하세요.
     Optional<JoinMember> findByRunningNo_NoAndRunnerId_Id(int runningNo, String runnerId);
}
