package com.db.bms.service;

import com.db.bms.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(User user,Integer flag);

    void saveUser(String token, User user);

    User getUser(String token);

    void removeUser(String token);

    Integer register(String username, String password, Integer identify, String question, String answer);

    void setPassword(Integer id, String password);

    Integer getCount(Integer identify);

    List<User> queryUsers();

    int getSearchCount(Map<String, Object> searchParam);

    List<User> searchUsersByPage(Map<String, Object> params);

    Integer addUser(User user);

    User getUserByUserName(String username);

    Integer deleteUser(User user);

    Integer deleteUsers(List<User> users);

    Integer updateUser(User user);

    void deleteUserByUserName(String username);

    List<User> selectUserByIdentify(int identify);

    String passwordRetrieveByQuestion(String username, String question, String password);
}
