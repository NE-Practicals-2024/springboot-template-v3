package com.mugishap.templates.springboot.v1.models;


import com.mugishap.templates.springboot.v1.enums.ERole;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private ERole name;

    @Column(name="description")
    private String description;

    public Role(ERole name, String description) {
        this.name = name;
        this.description = description;
    }
}
