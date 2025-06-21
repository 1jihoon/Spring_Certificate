package com.example.spring_certificate.Entity.CertificateEntity;


import com.example.spring_certificate.Entity.Department;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateLink> links = new ArrayList<>();

    @OneToMany(mappedBy = "certificate" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "certificate" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificateCharacter> characters = new ArrayList<>();
}
