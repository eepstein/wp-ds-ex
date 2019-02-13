package com.whitepages.dataservices.ee.load;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.whitepages.dataservices.ee.dao.PersonDao;
import com.whitepages.dataservices.ee.model.Person;
import com.whitepages.dataservices.ee.model.PersonIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

// PENDING : impl if not using Spark

// PENDING : consider using a general-purpose, configurable object mapper for conversion if we end up with
// other files/formats.

/**
 * Utility class for loading the data from the {@code .csv} files and ingesting into the backing store.
 */

@Component
@Slf4j
public class Loader {

    private final ResourceLoader resourceLoader;

    private final PersonDao personDao;

    // Local caches to support making queries against db happen in batches.
    private final MultiValueMap<String, Person> personByName;

    /**
     * Constructor.
     *
     * @param resourceLoader Autowired by Spring.
     */
    public Loader(ResourceLoader resourceLoader, PersonDao personDao) {
        this.resourceLoader = resourceLoader;
        this.personDao = personDao;

        this.personByName = new LinkedMultiValueMap<>();
    }

    /**
     * Utility method to create and configure the Jackson mapper that will help parse the csv records.
     * <p>
     *     Could instead do this in a {@code static} block, but prefer to have as much user-driven
     *     invocation be attempted before we do so.
     * </p>
     * @return the {@link CsvMapper} configured to parse {@link java.time.LocalDate} value from Strings in the
     * format seen in the scraped data.
     *
     */
    private static CsvMapper createAndConfigureMapper() {
        final CsvMapper mapper = new CsvMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
        return mapper;
    }

    /**
     * Uses the {@link CsvMapper} from {@link #createAndConfigureMapper()} to create an {@link ObjectReader}
     * that can read records into instances of our custom classes that load data from .csv files with headers.
     * @return the configured {@link ObjectReader}
     */
    private static ObjectReader createObjectReaderForData(Class clazz) {
        final CsvSchema schemaFromHeader = CsvSchema.emptySchema().withHeader();
        return createAndConfigureMapper()
                    .readerFor(clazz)
                    .with(schemaFromHeader);
    }

    private Resource getReviewsAsResource(final String path) {
        log.debug("Using {} as path to resource file containing data.", path);
        final Resource resource = resourceLoader.getResource(path);
        if (!resource.exists()) {
            final String msg = "No resource file found at " + path + ". Exiting...";
            log.error(msg);
            System.err.println(msg);
            System.exit(-1);
        }
        return resource;
    }

    private Person getOrCreatePerson(final NameEmailAddress record) {
        final String name = record.getName();

        final PersonIdentifier emailId = record.getEmailIdentifier();

        Person person = null;
        List<Person> people = personByName.get(name);
        for (Person p : people) {
            if (p.hasIdentifier(emailId)) {
                person = p;
                break;
            }
        }

        if (person == null) {
            // New person ?
            List<Person> matchesEmail = personDao.findByIdentifiersContains(emailId);

            final PersonIdentifier nameId = record.getNameIdentifier();
            for (Person p : matchesEmail) {
                if (p.hasIdentifier(nameId)) {
                    person = p;
                }
            }
        }

        if (person == null) {
            // found new person
            // add to batch persist
            // do a batch INSERT on some configurable interval

        } else {
            log.debug("Possible duplicate, found existing Person record for name {} and email {}: {}",
                        name, record.getEmail(), person);
        }


        return person;
    }


    public boolean dbIsEmpty() {
        return personDao.count() == 0;
    }


}
