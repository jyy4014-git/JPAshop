package com.boardproject.jpashop.service;
import com.boardproject.jpashop.domain.Member;
import com.boardproject.jpashop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // pk값이 같으면 같은 영속성으로 관리한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("jeong");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }


    @Test
    public void 중복회원예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("jeong");

        Member member2 = new Member();
        member2.setName("jeong");

        //when 
        memberService.join(member1);
//        memberService.join(member2);


        //then 같은이름 member2 저장때 예외터지게 한다
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));

    }

}