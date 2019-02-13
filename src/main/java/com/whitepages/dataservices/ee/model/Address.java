package com.whitepages.dataservices.ee.model;


import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Embeddable
public class Address implements Serializable {
    private static final long serialVersionUID = 5284969669603275986L;

    private String street;
    private String street2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    /*
    These should be populated by a service.
     */
    private Double latitude;
    private Double longitude;

}
