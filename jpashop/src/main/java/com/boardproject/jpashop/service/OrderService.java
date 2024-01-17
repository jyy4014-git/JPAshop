package com.boardproject.jpashop.service;

import com.boardproject.jpashop.domain.Delivery;
import com.boardproject.jpashop.domain.Member;
import com.boardproject.jpashop.domain.Order;
import com.boardproject.jpashop.domain.OrderItem;
import com.boardproject.jpashop.domain.item.Item;
import com.boardproject.jpashop.repository.ItemRepository;
import com.boardproject.jpashop.repository.MemberRepository;
import com.boardproject.jpashop.repository.OrderRepository;
import com.boardproject.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


//    주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

//        엔티티조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.fiondOne(itemId);

//        배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

//        주문상품 생성
        OrderItem orderItem = OrderItem.createOrderitem(item, item.getPrice(), count);

//        주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

//        주문 저장 - Order에서 cascade되있기 떄문에 하나만 해줘도 orderitem, delivery가 자동으로 저장된다.
        orderRepository.save(order);


        return order.getId();
    }
//    쥐소
    @Transactional
    public void cancelOrder(Long orderId){
//        주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
//        주문취소
        order.cancel();
    }

//    검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
