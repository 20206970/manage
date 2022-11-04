package com.db.bms.service.impl;

import com.db.bms.mapper.UserMapper;
import com.db.bms.model.User;
import com.db.bms.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public User login(User user,Integer flag) {
        if(flag == 0){
            return userMapper.selectByUsernameAndPasswordAndIdentify(user.getUserName(), user.getUserPassword(), user.getIdentify());
        }
        else{
            return userMapper.selectByPhoneNumberAndPasswordAndIdentify(user.getUserName(), user.getUserPassword(), user.getIdentify());
        }
    }

    @Override
    public void saveUser(String token, User user) {
        // 设置redisTemplate对象key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // key是token，value是用户保存到redis中，超时时间1小时
        redisTemplate.opsForValue().set(token, user, 1, TimeUnit.HOURS);
    }

    @Override
    public User getUser(String token) {
        // 根据token得到user
        return (User) redisTemplate.opsForValue().get(token);
    }

    @Override
    public void removeUser(String token) {
        // 移除token
        redisTemplate.delete(token);
    }

    @Override
    public Integer register(String username, String password, Integer identify, String question, String answer) {
        User tmp = userMapper.selectByUsername(username);
        if(tmp != null) return 0;  //账号重复

        User user = new User();
        user.setUserName(username);
        user.setUserPassword(password);
        user.setIdentify(identify);
        user.setQuestion(question);
        user.setAnswer(answer);
        return userMapper.insertSelective(user);
    }

    @Override
    public void setPassword(Integer id, String password) {
        User user = new User();
        user.setUserId(id);
        user.setUserPassword(password);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer getCount(Integer identify) {
        return userMapper.selectCount(identify);
    }

    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    @Override
    public int getSearchCount(Map<String, Object> params) {
        return userMapper.selectCountBySearch(params);
    }

    @Override
    public List<User> searchUsersByPage(Map<String, Object> params) {
        return userMapper.selectBySearch(params);
    }

    @Override
    public Integer addUser(User user) {
        if(getUserByUserName(user.getUserName()) == null){
            userMapper.insert(user);
            return 1;
        }
        return 0;
    }

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public Integer deleteUser(User user) {
        return userMapper.deleteByPrimaryKey(user.getUserId());
    }

    @Override
    public Integer deleteUsers(List<User> users) {
        int count = 0;
        for(User user : users) {
            count += deleteUser(user);
        }
        return count;
    }

    @Override
    public Integer updateUser(User user) {
        if(getUserByUserName(user.getUserName()) == null || Objects.equals(getUserByUserName(user.getUserName()).getUserId(), user.getUserId())){
            userMapper.modifyUser(user);
            return 1;
        }
        return 0;
    }

    @Override
    public void deleteUserByUserName(String username) {
        userMapper.deleteUserByUserName(username);
    }

    @Override
    public List<User> selectUserByIdentify(int identify) {
        return userMapper.selectUserByIdentify(identify);
    }

    @Override
    public String passwordRetrieveByQuestion(String username,String question,String answer) {
        User userobj = userMapper.selectByUsername(username);
        if(userobj==null)
            return null;
        if(userobj.getAnswer().equals(answer))
            return "true";
        else
            return "false";
    }




}
