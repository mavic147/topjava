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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    public static Map<Integer, User> userRepo = new ConcurrentHashMap<>();
    private final int id = SecurityUtil.authUserId();
    private final AtomicInteger counter = new AtomicInteger(0);

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
        return userRepo.get(id) != null && userRepo.remove(id) != null;
    }

    @Override
    public User save(User user) {
        //creates new user
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userRepo.put(id, user);
            return user;
        }
        //updates existing user
        return user.getId() != null ? userRepo.computeIfPresent(user.getId(), (id, oldUser) -> user) : null;
    }

    @Override
    public User get(int id) {
        return userRepo.values().stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepo.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());
//        return userRepo.values().stream().sorted()
//                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
//        return email != null ? userRepo.entrySet().stream().filter(user -> user.getValue().getEmail().equals(email))
//                .findFirst().get().getValue() : null ;
        return userRepo.values().stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
