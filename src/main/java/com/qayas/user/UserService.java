package com.qayas.user;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] getAllUsers() {
        return userDao.getAllUsers();
    }
}
