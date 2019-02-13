package com.whitepages.dataservices.ee.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
//@AllArgsConstructor

@Embeddable
public class PersonIdentifier implements Serializable {
  private static final long serialVersionUID = -1556705316084015671L;

  public enum IdType {
    @JsonProperty("Email")
    EMAIL,
    @JsonProperty("Phone")
    PHONE,
    @JsonProperty("Name")
    NAME,
  }

  @EqualsAndHashCode.Include
  @Column(name = "id_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private IdType idType;

  /*
   TODO : persist the original, raw value ?  Among other things this can facilitate future processing if the
    chosen canonical form is subsequently deemed insufficient.
    */

  /**
   * An identifier in canonical form.
   * <p>If the raw form is different, then it is the consumer's responsibility to pre-process the raw
   * form and produce a canonical representation.</p>
   */
  @EqualsAndHashCode.Include
  @Column(name = "id_value", nullable = false)
  private String idValue;

  // TODO : support the notion of a proximate identifier where we're not 100% certain it belongs to a person.
//  private double weight = 1.0;

  public PersonIdentifier(final IdType type, final String value) {
    this.idType = type;
    this.idValue = value;
  }

}
