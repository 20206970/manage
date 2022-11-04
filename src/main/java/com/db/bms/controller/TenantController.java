package com.db.bms.controller;

import com.db.bms.model.User;
import com.db.bms.service.TenantInfoService;
import com.db.bms.service.UserService;
import com.db.bms.utils.MyResult;
import com.db.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tenant")
public class TenantController {
    @Autowired
    private TenantInfoService tenantInfoService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/selectAll")
    public Map<String,Object> getAllTenants(){
        return MyResult.getResultMap(200,"查询成功",tenantInfoService.getAllTenants());
    }

    @RequestMapping(value = "/getCount")
    public Integer getCount(@RequestBody Map<String,Object> map){
        Integer identity = Integer.parseInt(map.get("identify").toString());
        return userService.getCount(identity);
    }

    @RequestMapping(value = "/selectTenantByUserName")
    public Map<String,Object> getTenantByUserName(@RequestBody User user){
        System.out.println(user);
        if(user.getUserName() == null){
            return MyResult.getResultMap(200, "查找失败",tenantInfoService.getAllTenants());
        }
        User u = userService.getUserByUserName(user.getUserName());
        if(u == null){
            return MyResult.getResultMap(420, "查找失败",tenantInfoService.getAllTenants());
        }
        List<User> list = new ArrayList<>();
        list.add(u);
        return MyResult.getResultMap(200, "查找成功",list);
    }

    @RequestMapping(value = "/addTenant")
    public Map<String,Object> addTenant(@RequestBody User user){
        int res = userService.addUser(user);
        if(res == 1){
            return MyResult.getResultMap(200, "添加成功",getAllTenants());
        }
        else{
            return MyResult.getResultMap(420, "添加失败,该用户名已存在",getAllTenants());
        }
    }

    @RequestMapping(value = "/deleteTenantByUserName")
    public Map<String,Object> deleteTenantByUserName(@RequestBody User user){
        userService.deleteUserByUserName(user.getUserName());
        return MyResult.getResultMap(200, "删除成功",getAllTenants());
    }

    @RequestMapping(value = "/modifyTenantById")
    public Map<String,Object> modifyTenant(@RequestBody User user){
        int res = userService.updateUser(user);
        if(res == 1){
            return MyResult.getResultMap(200, "修改成功",getAllTenants());
        }
        return MyResult.getResultMap(200, "修改失败,该用户名已存在",getAllTenants());
    }

    @RequestMapping(value = "/queryTenantsByPage")
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
