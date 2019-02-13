package com.whitepages.dataservices.ee;

import com.whitepages.dataservices.ee.load.Loader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile({"test"})
@SpringBootApplication
@Slf4j
public class IntegrationTestApp /* implements ApplicationListener<ApplicationReadyEvent> */ {

    @Autowired
    private Loader loader;

    public static void main(String[] args) {
        SpringApplication.run(IntegrationTestApp.class, args);
    }

//    @Override
//    public void onApplicationEvent(@Nonnull ApplicationReadyEvent event) {
//        if (loader.dbIsEmpty()) {
//            // The default behavior a) create the in-memory db, and b) load the data from the .csv file on the classpath
//            // Despite its simplicity, this is a robust integration test that covers most of the model code and all
//            // of the loader code.
//            try {
//                assertTrue(loader.run(null));  // otherwise a (recoverable) error occurred; other errors lead to exceptions.
//            } catch (Exception e) {
//                throw new RuntimeException("Unable to load data for tests.", e);
//            }
//        }
//    }

}
