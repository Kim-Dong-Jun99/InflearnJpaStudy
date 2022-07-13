package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAllCriteria(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o join o.member m where o.status =:status and m.name like :name ", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000)
//                .getResultList();
        // 페이징도 할 수 있다, 근데 정적 쿼리다 이건, 동적 쿼리는 어떻게 쓸까
        // JPQL로 하는 것은 보기보다 엄청 힘들다, 버그도 찾기 힘들다,
        // 근데 이것도 실무에서는 안쓴다, 실무에서는 Querydsl을 쓴다,
//    }


}
