package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 이름 명시
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 임을 명시
    @JoinColumn(name = "member_id") // 매핑을 뭐로 할거냐를 명, fk를 명시한 것이다
    // 이제 양방향 연관관계라 연관관계 주인을 정해줘야함
    private Member member;
    /*
    멤버는 오더를 리스트로 가지고 잇고, 오더도 멤버를 가지고 있음 양방향 참조가 일어남
    문제는 디비에 외래키는 멤버_아이디 밖에 없다, 관계를 바꾸고 싶으면 외래키 값을 변경해야함
    멤버에도 주문이 있고, 오더에도 멤버가 있어서 jpa가 혼란이 옴, 무슨 값이 바꼇을때 외래키를 바꿔야되지?

    외래키 값을 업데이트하는 것은 둘 중에 하나만 하기로 약속, 객체는 변경이 두군데여도 테이블은 외래키 하나만 변경하면 된다
    둘중에 하나를 주인이라는 개념으로 잡은 것이고 그게 주인이다,

    연관관계 주인은 외래키가 가까운 게 주인이다, 그래서 오더에 member_id가 주인이다,
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    /*
    오더 아이템스를 저장하고 컬렉션에 넣은 다음에 오더를 넣는다?
    오더 아이템스를 넣고, 오더를 넣어줘야하는데, cascade를 넣으면
    persist(order)만 하면된다
    cascade는 presist를 전파,
    모든 엔티티는 저장하고 싶으면 각자 Persist 해야되는데, cascade가 그걸 대신해주는 것 같다

     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING) //enum이 숫자로 들어간다, 문제가 다른 상태가 들어가면 망한다, 그래서 ORDINAL 쓰면안된다
    private OrderStatus status; // 주문 상태 Order, Cancel

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrder().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    /*
    나중에 수정사항이 생겨도 여기서 고치면 되서 수정하기 편하다
     */

    // 비즈니스 로직
    // 주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능하다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 재고 수량 원상 복귀
        }
    }

    // 조회로직
    // 전체 주문 가격 조회
    public int getTotalPrice() {
        // 수량이 하나가 아닐 수도 있기에,
        return orderItems.stream().mapToInt(orderItem -> orderItem.getTotalPrice()).sum();
    }
}
