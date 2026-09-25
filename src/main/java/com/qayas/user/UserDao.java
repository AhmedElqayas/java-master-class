package com.qayas.user;

import java.util.UUID;

public interface UserDao {
    User[] getAllUsers();
    User getUserById(UUID userId);
}
