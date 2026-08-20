package com.qayas.user;

import java.util.UUID;

public class UserDao {
    private static final User[] users;

    static {
        users = new User[] {
                new User(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Ahmed"),
                new User(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Kareem")
        };
    }

    public User findById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user;
        }
        return null;
    }
    public User[] findAll() {
        return users;
    }
}
