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
}
