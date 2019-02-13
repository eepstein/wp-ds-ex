package com.whitepages.dataservices.ee.dao;

import com.whitepages.dataservices.ee.model.Person;
import com.whitepages.dataservices.ee.model.PersonIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDao extends JpaRepository<Person, Long> {

    List<Person> findByIdentifiersContains(PersonIdentifier pid);

    default List<Person> findByName(String name) {
        return findByIdentifiersContains(new PersonIdentifier(PersonIdentifier.IdType.NAME, name));
    }

    default List<Person> findByEmail(String email) {
        return findByIdentifiersContains(new PersonIdentifier(PersonIdentifier.IdType.EMAIL, email));
    }

    default List<Person> findByPhone(String phone) {
        return findByIdentifiersContains(new PersonIdentifier(PersonIdentifier.IdType.PHONE, phone));
    }


}
