package com.db.bms.controller;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.db.bms.model.User;
import com.db.bms.service.UserService;
import com.db.bms.utils.MyResult;
import com.db.bms.utils.MyUtils;
import com.db.bms.utils.SendSms;
import com.db.bms.utils.TokenProcessor;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.apache.ibatis.mapping.ResultMap;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestBody Map<String,Object> requestParams) {
        // 登录
        String account = requestParams.get("username").toString();
        String regex = "0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(account);
        User userObj = null;
        if(matcher.matches()){

            userObj = userService.login(new User(requestParams.get("username").toString(),requestParams.get("userpassword").toString()),1);
        }
        else{
            userObj = userService.login(new User(requestParams.get("username").toString(),requestParams.get("userpassword").toString()),0);
        }
        if(userObj == null) {   // 账号或密码错误
            // 返回结果对象
            return MyResult.getResultMap(420, "账号或密码错误");
        } else {    // 账号密码正确
            // 创建token
            String token = TokenProcessor.getInstance().makeToken();
            // 保存到Redis
            userService.saveUser(token, userObj);
            // 返回结果对象
            return MyResult.getResultMap(200, "登录成功",
                    new HashMap<String, String>(){{ put("token", token); }});
        }
    }

    // 查看用户信息
    @RequestMapping(value = "/info")
    public Map<String, Object> info(@RequestBody Map<String,Object> token) {
        // 从redis中取用户
        User user = userService.getUser(token.get("token").toString());
        if(user == null) {  // 获取失败
            return MyResult.getResultMap(420, "获取用户信息失败");
        } else {    // 获取成功
            return MyResult.getResultMap(200, "获取用户信息成功", user);
        }
    }

    // 退出登录
    @RequestMapping(value = "/logout")
    public Map<String, Object> logout(@RequestBody String token) {
        // 从redis中移除用户
        userService.removeUser(token);
        return MyResult.getResultMap(200, "退出登录成功" );
    }

    // 注册
    @RequestMapping(value = "/register")
    public Map<String,Object> register(@RequestBody Map<String,Object> userObj){
        if(Integer.parseInt(userObj.get("code").toString()) != SendSms.code){
            return MyResult.getResultMap(410,"验证码错误");
        }
        User user = new User();
        user.setUserName(userObj.get("username").toString());
        user.setIdentify(Integer.parseInt(userObj.get("identify").toString()));
        user.setUserPassword(userObj.get("password").toString());
        user.setQuestion(userObj.get("question").toString());
        user.setAnswer(userObj.get("answer").toString());
        user.setPhoneNumber(userObj.get("phone").toString());
        User userobj = userService.getUserByUserName(user.getUserName());
        if(userobj == null) {
            //没有重复用户，可以创建
            userService.addUser(user);
            return MyResult.getResultMap(200,"创建成功");
        } else {
            return MyResult.getResultMap(420,"用户重复");
        }
    }

    // 修改密码
    @RequestMapping(value = {"/alterPassword", "reader/alterPassword"})
    public Integer alterPassword( @RequestBody HashMap<String,Object> map){
        //检查旧密码是否正确
        User user = userService.getUser(map.get("token").toString());
        if(!user.getUserPassword().equals(map.get("oldPassword").toString())) {  //旧密码不正确
            return 0;
        }
        else {    //旧密码正确，设置新密码
            user.setUserPassword(map.get("newPassword").toString());
            updateUser(user);
            return 1;
        }
    }

    // 获得数量
    @RequestMapping(value = "/getCount")
    public Integer getCount(@RequestBody Map<String,Object> map){
        Integer identity = Integer.parseInt(map.get("identify").toString());
        return userService.getCount(identity);
    }

    // 查询所有用户
    @GetMapping(value = "/queryUsers")
    public Map<String, Object> queryUsers(@RequestParam Integer identify){
        return MyResult.getResultMap(200,"success",userService.selectUserByIdentify(identify));
    }

    @RequestMapping(value = "/retrieve")
    public Map<String,Object> Retrieve(@RequestBody HashMap<String,Object> map) {
        String username = map.get("username").toString();
        String question = map.get("question").toString();
        String answer = map.get("answer").toString();
        String s = userService.passwordRetrieveByQuestion(username,question,answer);
        if(s==null)
            return MyResult.getResultMap(420,"用户不存在");
        if(s.equals("false"))
            return MyResult.getResultMap(420,"密保问题回答错误");
        return MyResult.getResultMap(200,"验证成功");
    }
    // 分页查询用户 params: {page, limit, identify}
    @PostMapping(value = "/queryUsersByPage")
    public Map<String, Object> queryUsersByPage(@RequestBody Map<String, Object> params){
        HashMap<String,Object> tmp = new HashMap<>();
        for(String key : params.keySet()){
            if(params.get(key) == null){
                continue;
            }
            else{
                tmp.put(key,params.get(key).toString());
            }
        }
        params = tmp;
        MyUtils.parsePageParams(params);
        int count = userService.getSearchCount(params);
        List<User> users = userService.searchUsersByPage(params);
        return MyResult.getListResultMap(200, "success", count, users);
    }

    // 添加用户
    @PostMapping(value = "/addUser")
    public Map<String,Object> addUser(@RequestBody User user){
        int res = userService.addUser(user);
        if(res == 1){
            return MyResult.getResultMap(200, "添加成功",userService.selectUserByIdentify(user.getIdentify()));
        }
        else{
            return MyResult.getResultMap(420, "添加失败,该用户名已存在",userService.selectUserByIdentify(user.getIdentify()));
        }
    }

    // 删除用户
    @DeleteMapping(value = "/deleteUser")
    public Map<String,Object> deleteUser(@RequestBody User user){
        userService.deleteUserByUserName(user.getUserName());
        return MyResult.getResultMap(200,"删除成功");
    }



    // 更新用户
    @RequestMapping(value = "/updateUser")
    public Map<String,Object> updateUser(@RequestBody User user){
        if(userService.updateUser(user) == 1){
            return MyResult.getResultMap(200,"修改成功");
        }
        return MyResult.getResultMap(420,"修改失败,该用户名已存在");
    }


    @RequestMapping(value = "/deleteUsers")
    public Integer deleteUsers(@RequestBody List<User> userList){
        userService.deleteUsers(userList);
        return userList.size();
    }

    @RequestMapping(value = "/selectUserByUserName")
    public Map<String,Object> getTenantByUserName(@RequestBody User user){
        if(user.getUserName() == null){
            return MyResult.getResultMap(200, "查找失败",userService.selectUserByIdentify(user.getIdentify()));
        }
        User u = userService.getUserByUserName(user.getUserName());
        if(u == null){
            return MyResult.getResultMap(420, "查找失败",userService.selectUserByIdentify(user.getIdentify()));
        }
        List<User> list = new ArrayList<>();
        list.add(u);
        return MyResult.getResultMap(200, "查找成功",list);
    }

    @RequestMapping(value = "/getCode")
    public Integer getCode(@RequestBody Map<String, Object> phone) throws Exception {
        return new SendSms().send(phone.get("phone").toString());
    }
    @RequestMapping(value = "/bulkImport")
    public Map<String,Object> bulkImport(@RequestBody String fileName){
        String[] data = fileName.split("\n");
        List<User> users = new ArrayList<>();
        try {
            for(int i = 4; i < data.length - 1; i++){
                String[] columns = data[i].split(",");
                User user = new User(columns[0],columns[1],Integer.parseInt(columns[2]),columns[3],columns[4],columns[5].substring(0,columns[5].length()-1));
                if(userService.getUserByUserName(user.getUserName()) == null){
                    userService.addUser(user);
                }
                users.add(userService.getUserByUserName(user.getUserName()));
            }
        }catch (Exception e){
            return MyResult.getResultMap(410,"文件格式错误");
        }
        return MyResult.getResultMap(200,"文件读取成功",users);
    }
}
