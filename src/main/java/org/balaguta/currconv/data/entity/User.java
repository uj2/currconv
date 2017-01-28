package org.balaguta.currconv.data.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();
}
