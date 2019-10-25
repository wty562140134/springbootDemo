package com.wu.springboot.demo.entity;

import lombok.*;

@Data
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private String email;
    private String password;

}
