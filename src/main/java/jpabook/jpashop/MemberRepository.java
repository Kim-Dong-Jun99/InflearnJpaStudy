package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    // 스프링부트가 엔티티 메니저를 주입해준다,

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }
    // 왜 아이디만 반환하지?
    // 커멘드랑 쿼리를 분리, 저장을 하고 나면 리턴 값을 잘 안만든다,

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
