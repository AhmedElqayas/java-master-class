package com.qayas.user;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User[] getAllUsers() {
        return userDao.findAll();
    }
}
