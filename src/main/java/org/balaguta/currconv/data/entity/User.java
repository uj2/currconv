package org.balaguta.currconv.data.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    @Embedded
    private Address address;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();
}
