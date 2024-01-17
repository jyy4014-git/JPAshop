package com.boardproject.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //각 타입은 변경 불가능하게 설계, protected로 두는게 안전하다
//JPA 구현 라이브러리가 객체 생성할떄 리플렉션 같은 기술 사용할수 있또록 지원해야하기 때문
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;


    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
