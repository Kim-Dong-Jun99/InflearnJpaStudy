package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    /*
    onetoone에서는 근심과 걱정이 있다, jpa에서 일대일은 외래키를 어디다 넣어도된다
    어디에 두냐에 따라 장단점이 있다, 접근을 많이 하는 곳에 주로 둔다,
    주문을 보면 배송을 본다고 가정, 그럼 오더에 외래키를 둔다
    그럼 연관관계 주인은 오더이다,
     */

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //enum이 숫자로 들어간다, 문제가 다른 상태가 들어가면 망한다, 그래서 ORDINAL 쓰면안된다
    private DeliveryStatus status; // 주문 상태, Ready, Comp
}
