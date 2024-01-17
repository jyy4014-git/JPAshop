package com.boardproject.jpashop.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //다른곳에서 객체생성 방지
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //fk설정
    private Member member;

//    orderItem과 deliverysms Order만 참조하기 때문에 casecade한 것.
//    만약 다른데서도 참조하면 casecade 쓰면 안된다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //order가 저장될떄 같이 persist 한다
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태



    //양방향일떄 쓰는 연관관례 메서드


    public void setMember (Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem (OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery (Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //    주문생성 메서드
    public  static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직

    //    주문취소
    public void cancel(){
        if (delivery.getStatus()==DeliveryStatus.COMPLITE){
            throw new IllegalStateException("이미 배송완료은 상품 취소 불가합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

//  전체 주문 가격 조회 로직
    public int getTotalPrice(){
        int totalPrice =0;
        for (OrderItem orderItem:orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
