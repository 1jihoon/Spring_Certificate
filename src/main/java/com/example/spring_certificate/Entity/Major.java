package com.example.spring_certificate.Entity;

import com.example.spring_certificate.Entity.CertificateEntity.Certificate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "major_certificate",
            joinColumns = @JoinColumn(name = "major_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    @OrderBy("name ASC")
    private Set<Certificate> certificates = new HashSet<>();
}
