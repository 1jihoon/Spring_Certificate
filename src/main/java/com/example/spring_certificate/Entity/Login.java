package com.example.spring_certificate.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Login {
    @Id
    @Column(unique = true)
    private String id;

    private String password;

    private String name;

    @Column(unique=true)
    private String email;
    //패스워드와 이메일은 중복되면 안 되므로 unique 제약 조건을 넣는다.

    private String gender;

    //SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
    //WHERE TABLE_NAME = 'LOGIN';
    //로그인 테이블의 각 키의 모습을 볼 수 있게 하는 것으로 기본키, 외래키등을 볼 수 있다

    //SELECT k.COLUMN_NAME, t.CONSTRAINT_TYPE
    //FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE k
    //JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS t
    //  ON k.CONSTRAINT_NAME = t.CONSTRAINT_NAME
    //WHERE k.TABLE_NAME = 'LOGIN';
    //변수들이 어떤 키와 연결되는지를 볼 수 있다
}
