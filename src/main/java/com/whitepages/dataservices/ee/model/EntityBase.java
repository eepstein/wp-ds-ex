package com.whitepages.dataservices.ee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ComparisonChain;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Abstract base class of the entities in this model.
 * <p>
 *     Instances of this base class receive a database-assigned primary key identifier.
 *     The {@link Object#equals(Object o)} and {@link Object# hashCode()} methods are overridden so that
 *     Java instance identity aligns with database identity.  Non-persisted instances fall back to
 *     {@link System#identityHashCode(Object)} to avoid collisions in collections.
 * </p>
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

@MappedSuperclass
public abstract class EntityBase implements Comparable<EntityBase>, Serializable {

    private static final long serialVersionUID = -1959431565670775469L;

    /**
     * Default constructor.  Subclasses should call this method.
     * @param id the primary key identifier, or {@code null} for a new instance.
     */
    @SuppressWarnings("WeakerAccess")
    protected EntityBase(final Long id) {
        super();
        this.id = id;
    }

    /**
     * The primary key in the database and, when not null, the instance's identity in-memory as well.
     * <p>
     *     A {@code null} value indicates an instance that has no persisted state.
     * </p>
     * @see Id
     */
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PROTECTED)
    @Id
    @GenericGenerator(name = "pk_id_seq",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = {
                            @Parameter(name = "sequence_name", value = "pk_id_seq"),
                            @Parameter(name = "initial_value", value = "1100"),
                            @Parameter(name = "increment_size", value = "1")
                }
    )
    @GeneratedValue(strategy = SEQUENCE, generator = "pk_id_seq")
    protected Long id;

    /**
     * Used to support "optimistic locking". Can also improve performance in web/REST scenarios via
     * use of headers that check the version as part of cache and update management.
     *
     * @see Version
     */
    @Version
    protected int version;

    /**
     * The fallback identity for non-persisted instances.
     *
     * @return if (@link #id} is {@code null} this method just returns 0, otherwise this method will return
     * the result of invoking {@link System#identityHashCode(Object)} on {@code this} instance.
     *
     * @see Object#hashCode()
     * @see System#identityHashCode(Object)
     */
    @JsonIgnore
    @Transient // Don't save to the db
    @EqualsAndHashCode.Include
    protected int getSystemIdentity() {
        return (id == null) ? System.identityHashCode(this) : 0;
    }

    /**
     * Must be {@link Transient} in order to ensure that no JPA provider complains because of a missing setter.
     *
     * @see org.springframework.data.domain.Persistable#isNew()
     */
    @SuppressWarnings("unused")
    @JsonIgnore
    @Transient // DATAJPA-622
    public boolean isNew() {
        return null == getId();
    }

    /**
     * Compares by {@link #id} and then by {@link #getSystemIdentity()}.
     * <p>
     *     Since {@link #getSystemIdentity()} returns {@code 0} for persisted entities, this works well for those.
     * </p>
     * <p>
     *     WARNING, however: for entities that become persistent, the result of invoking this method will likely change.
     *     Thus the same two entities compared before being persisted may yield a different result from this method
     *     after they have been persisted.
     * </p>
     *
     * @param o the other instance to compare to.
     * @return 0, negative integer, positive integer, depending on whether {@code this} compares to {@code o}
     * as equals, less than, or greater than, respectively.
     * @see Comparable#compareTo(Object)
     */
    @SuppressWarnings({"ConstantConditions"})
    @Override
    public int compareTo(@Nonnull final EntityBase o) {
        if (o == null) {
            return 1;   // Nulls come first
        } else if (this == o) {
            return 0;
        } else if (!this.getClass().equals(o.getClass())) {
            // compare instances of distinct  sub-classes lexicographically by class name
            return this.getClass().getCanonicalName().compareTo(o.getClass().getCanonicalName());
        } else {
            return ComparisonChain.start()
                        .compare(this.getId(), o.getId())
                        .compare(this.getSystemIdentity(), o.getSystemIdentity())
                        .result();
        }
    }

}
