package com.whitepages.dataservices.ee.load;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.whitepages.dataservices.ee.model.PersonIdentifier;
import lombok.Data;

/**
 * Utility class that maps verbatim from the fields in the {@code Name-Email-Address.csv} file(s).
 * <p>
 *     The fields are as declared in the associated {@code .csv} file.
 * </p>
 * <pre>
 * averageRating,sitter_image,end_date,text,owner_image,dogs,sitter,userAccount,start_date,sitter_phone_number,sitter_email,owner_phone_number,owner_email
 * </pre>
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class NameEmailAddress implements HasAddress, HasName {

    private String name;

    private String email;

    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;

    public PersonIdentifier getEmailIdentifier() {
        return new PersonIdentifier(PersonIdentifier.IdType.EMAIL, getEmail());
    }

}
