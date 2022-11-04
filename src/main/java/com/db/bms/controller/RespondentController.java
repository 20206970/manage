package com.db.bms.controller;

import com.db.bms.model.User;
import com.db.bms.service.UserService;
import com.db.bms.utils.MyResult;
import com.db.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/respondent")
@RestController
public class RespondentController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add")
    public Map<String,Object> addRespondent(@RequestBody User user) {
        User userobj = userService.getUserByUserName(user.getUserName());
        if(userobj == null) {
            //数据库中不存在这个user，可以创建
            userService.addUser(user);
            return MyResult.getResultMap(200,"创建成功");
        } else {
            return MyResult.getResultMap(420,"用户名重复");
        }
    }

    @RequestMapping(value = "/updateRespondent")
    public Map<String,Object> updateRespondent(@RequestBody User user) {
        User userobj = userService.getUserByUserName(user.getUserName());
        if(userobj == null) {
            userService.updateUser(user);
            return MyResult.getResultMap(200,"修改成功");
        } else {
            return MyResult.getResultMap(420,"新答者名重复");
        }
    }

    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteRespondent(@RequestBody User user) {
        userService.deleteUserByUserName(user.getUserName());
        return MyResult.getResultMap(200,"删除成功");
    }

    @RequestMapping(value = "/queryRespondent")
    public Map<String,Object> queryRespondent() {
        List<User> list = userService.selectUserByIdentify(3);
        return MyResult.getResultMap(200,"查找成功",list);
    }

    @RequestMapping(value = "selectRespondentByUserName")
    public Map<String,Object> selectRespondentByName(@RequestBody User user) {
        User userobj = userService.getUserByUserName(user.getUserName());
        List<User> list = new ArrayList<>();
        list.add(userobj);
        if(userobj == null)
            return MyResult.getResultMap(420,"没有找到该用户",userService.selectUserByIdentify(user.getIdentify()));
        else
            return MyResult.getResultMap(200,"找到该用户",list);

    }
    @RequestMapping(value = "/getCount")
    public Integer getCount(@RequestBody Map<String,Object> map){
        Integer identity = Integer.parseInt(map.get("identify").toString());
        return userService.getCount(identity);
    }

    @RequestMapping(value = "/queryRespondentsByPage")
    public Map<String, Object> queryUsersByPage(@RequestBody HashMap<String, Object> params){
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
}
