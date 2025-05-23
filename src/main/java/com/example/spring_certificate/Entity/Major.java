package com.example.spring_certificate.Entity;

import com.example.spring_certificate.Dto.CertificateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Major {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private List<Certificate> certificates;
}
