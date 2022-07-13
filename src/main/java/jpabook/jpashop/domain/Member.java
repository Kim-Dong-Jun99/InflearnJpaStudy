package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id") // 칼럼 이름 지정
    private Long id;


    private String name;

    @Embedded // 임베디드 타입임을 명시
    private Address address;

    @OneToMany(mappedBy = "member") // 하나의 회원이 여러개의 주문을 하니 일대다
    // 오더 테이블에 멤버로 매핑되었다는 의미, 읽기 전용이 되었다,
    private List<Order> order = new ArrayList<>();
}
