package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class Address {
    private String line1;
    private String line2;
    private String city;
    private String zip;
    @ManyToOne
    private Country country;
}
