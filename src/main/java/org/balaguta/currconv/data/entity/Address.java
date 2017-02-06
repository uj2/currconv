package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class Address implements Serializable {
    private static final long serialVersionUID = 1;

    private String line1;
    private String line2;
    private String city;
    private String zip;
    @ManyToOne
    private Country country;
}
