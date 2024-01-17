package com.boardproject.jpashop.service;

import com.boardproject.jpashop.domain.Member;
import com.boardproject.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true) // JPA가 조회할때 최적화한다, 읽기엔 이거 넣는게 좋다
@RequiredArgsConstructor //final 가진 애들의 생성자를 자동 생성해준다
public class MemberService {


    private final MemberRepository memberRepository; //컴파일 시점에 값세팅 확인할수 있어 final 설정


    //회원 가입
    @Transactional //읽기가 아니라서 readonly 무시하기 위해 썼음
    public Long join(Member member) {
        validaateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validaateDuplicateMember(Member member) {
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
             throw new IllegalStateException("이미 존재하는 회원입니다");

        }

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return  memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
