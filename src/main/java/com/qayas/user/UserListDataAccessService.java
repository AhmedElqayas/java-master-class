package com.qayas.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserListDataAccessService implements UserDao {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Ahmed"));
        users.add(new User(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Qayas"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user;
        }
        return null;
    }
}
