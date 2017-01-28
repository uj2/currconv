package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "PERMISSION")
@Data
public class Permission {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    private String name;
}
