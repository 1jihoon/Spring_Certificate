package com.example.spring_certificate.Entity.CertificateEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CertificateImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String altText;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;
}
