package com.whitepages.dataservices.ee.load;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.whitepages.dataservices.ee.model.PersonIdentifier;
import lombok.Data;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class NameTelephoneAddress implements HasAddress, HasName {

    private String name;

    private String phone;

    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;

    public PersonIdentifier getPhoneIdentifier() {
        return new PersonIdentifier(PersonIdentifier.IdType.PHONE, getPhone());
    }

}
