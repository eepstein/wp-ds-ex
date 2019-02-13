package com.whitepages.dataservices.ee;

import com.whitepages.dataservices.ee.load.Loader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile({"!test"})   // Don't run during tests.
@SpringBootApplication
@Slf4j
public class DsApp /* implements ApplicationListener<ApplicationStartedEvent> */ {

    private final Loader loader;

    @Autowired
    public DsApp(Loader loader) {
        this.loader = loader;
    }

    /**
     * Runs in regular mode: an embedded web server will start, so this application will stay running until killed.
     *
     * @param args the arguments passed in via the command line
     */
    public static void main(final String[] args) {
        SpringApplication.run(DsApp.class, args);
    }

    /**
     * Calls the {@link #loader}'s {@link Loader#doRun(String...)} method, passing in {@code null} so the default,
     * embedded data file is used.  This is intended to make bootstrapping very easy.
     * <p>
     *     The {@link Loader#doRun(String...)} only loads data if the database is empty, hence only has an effect the
     *     first time the application is started.
     * </p>
     *
     */
//    @Transactional
//    @Override
//    public void onApplicationEvent(@Nonnull final ApplicationStartedEvent ignored) {
//        try {
//            loader.doRun();
//        } catch (Exception e) {
//            log.error("Exception loading initial data on app startup.", e);
//            System.exit(-2);
//        }
//        log.info("Ready for requests.");
//    }


}
