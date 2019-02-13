package com.whitepages.dataservices.ee.load;

import com.whitepages.dataservices.ee.model.Address;

public interface HasAddress {

    static String DEFAULT_COUNTY = "USA";

    String getAddr1();
    String getAddr2();
    String getCity();
    String getState();
    String getZip();

    default Address getAsAddressInstance() {
        // TODO : inject utility to get the address's canonical form.
        return Address.builder()
                    .street(getAddr1())
                    .street2(getAddr2())
                    .city(getCity())
                    .state(getState())
                    .postalCode(getZip())
                    .country(DEFAULT_COUNTY)
                    .build();
    }


}
