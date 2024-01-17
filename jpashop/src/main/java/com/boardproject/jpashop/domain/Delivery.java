package com.boardproject.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private  Address address;

    @Enumerated(EnumType.STRING) // 중간에 들어가도 장애 안일으킨다. Ordinal은 장애 일으킴
    private DeliveryStatus status; //Ready, Comp
}
