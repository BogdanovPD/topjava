package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        Set roleSet = new HashSet<Role>();
        roleSet.add(Role.ROLE_USER);
        this.save(new User(null, "Peter B", "aqwe@jsjsjs.com", "1234", 1000, true, roleSet));
        this.save(new User(null, "Albert H", "aqwe22@jsjsjs.com", "1234", 1000, true, roleSet));
        this.save(new User(null, "Sidor D", "aqwe11@jsjsjs.com", "1234", 1000, true, roleSet));
    }


    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> users = repository.values().stream().collect(Collectors.toList());
        users.sort((u1, u2)->u1.getName().compareTo(u2.getName()));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        final User[] user = {null};
        repository.forEach((key, userInner) -> {
            if (userInner.getEmail().equals(email)){
            user[0] = userInner;}
        });
        return user[0];
    }
}
