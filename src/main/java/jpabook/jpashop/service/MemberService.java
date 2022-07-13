package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)// 트랜잭션 안에서 실행된다는 어노테이션, readOnly = true 면 조회하는 곳에서 성능을 최적화한다,
@RequiredArgsConstructor // 파이널 붙은 변수들로만 생성자 생성
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원 가입
    @Transactional // 쓰기할때는 readOnly = true면 안된다
    public Long join(Member member) {
        // 중복회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // 디비에 넣지 않아도 아이디 값을 구할 수 있다,
    }

    private void validateDuplicateMember(Member member) {
        // 중복 회원이면 예외 터짐
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // 멀티 스레드 환경에서는 이름이 중복으로 등록될 수도 있어서, 디비에서 이름을 유니크하게 설정하도록 설정하자

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
