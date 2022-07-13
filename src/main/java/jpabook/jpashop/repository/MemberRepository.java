package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링빈으로 스프링이 등록,
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // JPA 엔티티 매니저를 주입해주는 코드, 부트를 쓰면 이걸 안 써도된다, 스프링 데이터 jpa가 지원해줘서,
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 저장하는 메소드, 트랜잭션이 커밋될때 디비에 저장된다,
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 찾는 메서드
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        // 엔티티 멤버를 조회하라는 코드, sql이랑 비슷한데, From의 대상이 엔티티,
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        // 특정 회원을 이름으로 찾는 코성
    }
}
