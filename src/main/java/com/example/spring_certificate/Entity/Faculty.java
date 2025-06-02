package com.example.spring_certificate.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Faculty {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Department> departments;
    //mappedBy = "faculty"는 department와의 1:N 연관관계에서 주인이라는 뜻이고
    //OneToMany는 하나의 학부가 여러 개의 학과와 연결돼있다는 뜻이다.
    //직접 연관관계를 조절하진 않고 직접 외래키를 변경하거나 하진 않는다.
}
