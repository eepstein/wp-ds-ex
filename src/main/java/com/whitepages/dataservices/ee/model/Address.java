package com.whitepages.dataservices.ee.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
public class Address extends EntityBase implements Serializable {
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

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private Set<Person> people;

    public boolean addPerson(final Person p) {
        if (people == null) {
            people = new HashSet<>();
        }
        return people.add(p);
    }

    public boolean hasPerson(final Person p) {
        return (people != null) && people.contains(p);
    }

}
