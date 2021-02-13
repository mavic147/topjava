package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    public static Map<Integer, User> userRepo = new ConcurrentHashMap<>();
    private final int id = SecurityUtil.authUserId();

    {
        this.save(new User(id, "Peter", "peter@gmail.com", "peter123", Role.USER));
        this.save(new User(id, "Anna", "anna@gmail.com", "anna123", Role.USER));
        this.save(new User(id, "Nicholas", "nic@gmail.com", "nic123", Role.USER));
        this.save(new User(id, "Clementine", "clem@gmail.com", "clem123", Role.USER));
        this.save(new User(id, "Sean", "sean@gmail.com", "sean123", Role.USER));
        this.save(new User(id, "Samantha", "samantha@gmail.com", "sam123", Role.USER));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepo.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        userRepo.put(id, user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepo.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userRepo.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());
//        return userRepo.values().stream().sorted()
//                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return email != null ? userRepo.entrySet().stream().filter(user -> user.getValue().getEmail().equals(email))
                .findFirst().get().getValue() : null ;
    }
}
