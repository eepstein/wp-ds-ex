package com.whitepages.dataservices.ee.load;

import com.whitepages.dataservices.ee.model.Address;
import com.whitepages.dataservices.ee.model.PersonIdentifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NameEmailAddressTest {

    private String name = "John Dokes";
    private String email = "jdokes@yahoo.com";
//    private String phone = "15065551212";

    private NameEmailAddress namo;

    private Address addr;

    @Before
    public void setUp() throws Exception {
        addr = Address.builder()
                    .street("1234 Main St.")
                    .city("Tacoma")
                    .state("WA")
                    .country(HasAddress.DEFAULT_COUNTY)
                    .build();

        namo = new NameEmailAddress();
        namo.setAddr1("1234 Main St.");
        namo.setCity("Tacoma");
        namo.setState("WA");

        namo.setName(name);
        namo.setEmail(email);
    }

    @Test
    public void getEmailIdentifier() {
        PersonIdentifier emId = new PersonIdentifier(PersonIdentifier.IdType.EMAIL, email);
        assertEquals(emId, namo.getEmailIdentifier());
    }

    @Test
    public void checkNameIdentifier() {
        PersonIdentifier nameId = new PersonIdentifier(PersonIdentifier.IdType.NAME, name);
        assertEquals(nameId, namo.getNameIdentifier());
    }

    @Test
    public void checkAddressMatch() {
        assertEquals(addr, namo.getAsAddressInstance());
    }
}