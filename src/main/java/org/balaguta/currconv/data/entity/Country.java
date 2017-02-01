package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Country implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
