package com.whitepages.dataservices.ee.load;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.whitepages.dataservices.ee.model.PersonIdentifier;
import lombok.Data;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class NameTelephoneEmail implements HasName {

    private String name;

    private String phone;

    private String email;

    public PersonIdentifier getPhoneIdentifier() {
        return new PersonIdentifier(PersonIdentifier.IdType.PHONE, getPhone());
    }

    public PersonIdentifier getEmailIdentifier() {
        return new PersonIdentifier(PersonIdentifier.IdType.EMAIL, getEmail());
    }

}
