package com.boardproject.jpashop.controller;

import com.boardproject.jpashop.domain.Member;
import com.boardproject.jpashop.domain.Order;
import com.boardproject.jpashop.domain.item.Item;
import com.boardproject.jpashop.exception.NeedMoreException;
import com.boardproject.jpashop.repository.OrderSearch;
import com.boardproject.jpashop.service.ItemService;
import com.boardproject.jpashop.service.MemberService;
import com.boardproject.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItem();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";

    }


    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count,
                        Model model) {
        try {
            orderService.order(memberId, itemId, count);
            return "redirect:/";
        } catch (NeedMoreException e){
            model.addAttribute("countError", "재고가 부족합니다");
            return "order/orderForm"; // 주문 폼 페이지로 리턴
        }
    }




    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}
