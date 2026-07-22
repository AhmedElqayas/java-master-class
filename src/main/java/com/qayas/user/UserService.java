package com.qayas.user;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] viewAllUsers() {
        return userDao.findAll();
    }
}
