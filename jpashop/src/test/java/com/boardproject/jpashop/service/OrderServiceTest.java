package com.boardproject.jpashop.service;

import com.boardproject.jpashop.domain.Address;
import com.boardproject.jpashop.domain.Member;
import com.boardproject.jpashop.domain.Order;
import com.boardproject.jpashop.domain.OrderStatus;
import com.boardproject.jpashop.domain.item.Book;
import com.boardproject.jpashop.exception.NeedMoreException;
import com.boardproject.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();

        Book book = createBook();

        //when

        int orderCount=2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        System.out.println("getOrder.getStatus ========>> " + getOrder.getStatus());
        
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());  // 주문 상태는 ORDER 이어야 한다
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 1이어야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격 총합은 가격 * 주문 수량");
        assertEquals(8, book.getStockQuantity(), "상품의 재고 수량은 주문한 수량만큼 감소 해야 한다.");

    }


    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);


        int orderCount = 11;
        // when
        // then
        assertThrows(NeedMoreException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("JPA", 100000, 10);

        int orderCount =2;
        Long orderID = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderID);
        //then
        Order getOrder = orderRepository.findOne(orderID);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, item.getStockQuantity(), "주문이 취소되면 재고가 증가해야한다");

    }

    private Book createBook() {
        return createBook("시골 JPA", 10000, 10);
    }

    private Book createBook(String name, int price, int count) {
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(count);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member=new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

}