package com.example.spring_certificate.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CertificateLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String url;
    private String description;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;
}
