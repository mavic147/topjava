package ru.javawebinar.topjava.web.user;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.service.inmemory.UserServiceForInMemoryTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;

import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * If you want to run this test, remove comment lines from @Service
 *  annotation in UserServiceForInMemoryTest
 * */
public class InMemoryAdminRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static InMemoryUserRepository repository;
    private static UserServiceForInMemoryTest service;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext( "spring/spring-app-test.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        repository = appCtx.getBean(InMemoryUserRepository.class);
        service = appCtx.getBean(UserServiceForInMemoryTest.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Before
    public void setUp() {
        // re-initialize
        repository.init();
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        Assert.assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
}