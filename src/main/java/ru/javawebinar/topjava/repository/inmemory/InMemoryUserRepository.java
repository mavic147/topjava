package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    public static Map<Integer, User> userRepo = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        userRepo.put(counter.incrementAndGet(), new User(counter.get(), "Peter", "peter@gmail.com", "peter123", Role.USER));
        userRepo.put(counter.incrementAndGet(), new User(counter.get(), "Anna", "anna@gmail.com", "anna123", Role.USER));
        userRepo.put(counter.incrementAndGet(), new User(counter.get(), "Nicholas", "nic@gmail.com", "nic123", Role.USER));
        userRepo.put(counter.incrementAndGet(), new User(counter.get(), "Clementine", "clem@gmail.com", "clem123", Role.USER));
        userRepo.put(counter.incrementAndGet(), new User(counter.get(), "Sean", "sean@gmail.com", "sean123", Role.USER));
        userRepo.put(counter.incrementAndGet(), new User(counter.get(), "Samantha", "samantha@gmail.com", "sam123", Role.USER));
    }

    @Override
    public boolean delete(int id) {
        return userRepo.get(id) != null && userRepo.remove(id) != null;
    }

    @Override
    public User save(User user) {
        //creates new user
        if (user.isNew()) {
            userRepo.put(counter.incrementAndGet(), user);
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
        List<User> users = new CopyOnWriteArrayList<>(userRepo.values());
        Comparator<User> comparator = Comparator.comparing(AbstractNamedEntity::getName);
        comparator = comparator.thenComparing(User::getEmail);
        users.sort(comparator);
        return users;
    }

    @Override
    public User getByEmail(String email) {
        return userRepo.values().stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
