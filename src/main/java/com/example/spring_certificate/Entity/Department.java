package com.example.spring_certificate.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Major> majors;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Certificate> certificates;
}
