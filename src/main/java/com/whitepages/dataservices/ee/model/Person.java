package com.whitepages.dataservices.ee.model;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a Person in this model.
 * <p>
 *     The notion of identity has been abstracted away from the specific identifiers, like email and phone number.
 *     This allows for changes in those identifiers without change of identity.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
public class Person extends EntityBase {

    private static final long serialVersionUID = 7296545321407917841L;

    /**
     * The user's identities / indentifiers.
     * These are included in the ToString and the JSON serialization.
     */
    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_identifier"
                , joinColumns = {@JoinColumn(name = "profile_id")}

                // Do we support the same identifier associated with multiple Profiles?
                //   , uniqueConstraints = {@UniqueConstraint(columnNames = {"id_type", "id_value"})}
    )
    private Set<PersonIdentifier> identifiers = new HashSet<>();

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_address"
                , joinColumns = {@JoinColumn(name = "profile_id")}
    )
    private Set<Address> addresses;

    public Person(final Long id) {
        super(id);
    }

    /**
     * Default constructor, subclasses should invoke this method.
     *
     * @param id the instances unique identifier, or null for new instances.
     * @param name the person's name (required)
     * @param email the person's email address (optional)
     * @param phoneNumber the person's phoneNumber (optional)
     */
    protected Person(final Long id, @Nonnull final String name, final String email, final String phoneNumber) {
        super(id);

        addIdentifier(new PersonIdentifier(PersonIdentifier.IdType.NAME, name));

        if (email != null) {
            addIdentifier(new PersonIdentifier(PersonIdentifier.IdType.EMAIL, email));
        }
        if (phoneNumber != null) {
            addIdentifier(new PersonIdentifier(PersonIdentifier.IdType.PHONE, phoneNumber));
        }
    }

    public boolean addIdentifier(final PersonIdentifier pid) {
        if (identifiers == null) {
            identifiers = new HashSet<>();
        }
        return identifiers.add(pid);
    }

    /**
     * Is this {@link PersonIdentifier} associated with this {@code Person}?
     * @param pid the {@code PersonIdentifier} in question
     * @return {@code true} if the {@code PersonIdentifier} is associated with this {@code Person} instance and
     * {@code false} otherwise.
     */
    public boolean hasIdentifier(final PersonIdentifier pid) {
        return (identifiers != null) && identifiers.contains(pid);
    }

    public boolean addAddress(final Address addr) {
        if (addresses == null) {
            addresses = new HashSet<>();
        }
        return addresses.add(addr);
    }

    public boolean hasAddress(final Address addr) {
        return (addresses != null) && addresses.contains(addr);
    }



}
