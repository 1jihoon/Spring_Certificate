package com.example.spring_certificate.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    private String name;

    @Column(length = 1000)
    private String detail;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

   /* @ManyToMany(mappedBy = "certificates")
    private Set<Major> majors = new HashSet<>();*/


    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateLink> links = new ArrayList<>();

    @OneToMany(mappedBy = "certificate" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "certificate" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateCharacter> characters = new ArrayList<>();
}
