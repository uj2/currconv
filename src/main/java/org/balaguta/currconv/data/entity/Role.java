package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ROLE")
@Data
public class Role {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Permission> permissions = new HashSet<>();

}
