package ru.javawebinar.topjava.service.inmemory;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * This service layer was created for inMemory test purposes. The bean of this class is used only in
 * InMemoryAdminRestControllerSpringTest and InMemoryAdminRestControllerTest classes and added in spring-app-test.xml.
 * The bean conflicts with UserService as it has @Service annotation and Spring sees it and tries to create it.
 * But this bean is not included into spring-app.xml and that's why MealServiceTest and UserServiceTest fail with NoSuchBeanException.
 * To run InMemoryAdminRestControllerSpringTest and InMemoryAdminRestControllerTest remove comment lines from @Service
 * annotation here.
 */
//@Service
public class UserServiceForInMemoryTest {

    private final InMemoryUserRepository userRepository;

    public UserServiceForInMemoryTest(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.get(id), id);
    }

    public User getByEmail(String email) {
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void update(User user) {
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }
}
