package com.boardproject.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름은 빈칸 불가합니다")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
