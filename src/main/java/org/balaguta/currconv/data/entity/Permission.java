package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "PERMISSION")
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 1;

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    private String name;
}
