package com.teamedv.javaclasses.todolist.repository.impl;

import com.teamedv.javaclasses.todolist.entity.User;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository extends InMemoryRepository<User, UserId> {

    private static UserRepository instance;

    private final AtomicLong idCounter = new AtomicLong(0L);

    private UserRepository(){}

    public Optional<User> getUserByUsername(String userName) {
        for (User user : entries.values()) {
            if (user.getUsername().equals(userName)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    protected UserId nextId() {
        return new UserId(idCounter.getAndAdd(1L));
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            return instance = new UserRepository();
        }
        return instance;
    }
}
