package com.whitepages.dataservices.ee.load;

import com.whitepages.dataservices.ee.IntegrationTestApp;
import com.whitepages.dataservices.ee.dao.PersonDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@SpringBootTest(classes = IntegrationTestApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional  // mimic JPA open-in-view
@Slf4j
public class LoaderTest {


    @Autowired
    private PersonDao personDao;

    @Rollback   // H2's Roll-back support seems to be flaky
    @Test
    public void run() {
        // Now test that ratings were computed and persisted correctly.
        Pageable pageRequest = PageRequest.of(0, 30);
    }
}