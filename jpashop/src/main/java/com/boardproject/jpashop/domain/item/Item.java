package com.boardproject.jpashop.domain.item;

import com.boardproject.jpashop.domain.Category;
import com.boardproject.jpashop.exception.NeedMoreException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //비즈니스 로직

    public void addStock(int quantity){
//       재고증가
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity -quantity;
        if (restStock <0) {
            throw new NeedMoreException("need more stock");
        }
        this.stockQuantity = restStock;

    }
}
