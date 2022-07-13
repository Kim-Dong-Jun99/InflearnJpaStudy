package jpabook.jpashop;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id")) // 다대다 관계는 중간 테이블이 필요하다,
    private List<Item> items = new ArrayList<>();
    /*
    다대다 관계이다, 아이템과,
    실전에서 왜 쓰지 말라고 하냐, 이 그림 밖에 안되서? 실무에서 거의 안씀, 중간에 넣어줄 값이 있기에,
    그러면 애초에 오더랑 아이템처럼 일대다, 다대일로 분리해서 하나??

     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    /*
    셀프로 양방향 연관관계를 걸은 것이다,
    음 부모는 한명이니까 다대일, 자식은 많이 가질 수 있으니까, 일대다로하고
    주인을 부모로 둔듯하다,
     */

    // 연관관계 편의 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
