package com.example.spring_certificate.Entity;

import com.example.spring_certificate.Entity.CertificateEntity.Certificate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Department {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    //N : 1 즉 다대일 관계의 주인이고 여러 개의 학과에서 하나의 학부를 뜻하고, 여기서부턴 department 테이블에서
    //faculty 테이블의 Id를 외래키로 받아야 되기 때문에 이렇게 쓴다.

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Major> majors = new ArrayList<>();
    //department 테이블은 1 : N 연관관게에서 주인이라는 뜻이고, major 테이블에서 그 연관관계를 나타낸다.

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Certificate> certificates;
    //마찬가지로 certificate 테이블에 한해서 1 : N 연관관계의 주인이라는 것을 나타낸다.
}
