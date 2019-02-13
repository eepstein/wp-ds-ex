package com.whitepages.dataservices.ee.load;

import com.whitepages.dataservices.ee.model.PersonIdentifier;

public interface HasName {

    String getName();

    default PersonIdentifier getNameIdentifier() {
        return new PersonIdentifier(PersonIdentifier.IdType.NAME, getName());
    }
}
