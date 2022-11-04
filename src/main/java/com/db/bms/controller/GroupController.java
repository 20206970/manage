package com.db.bms.controller;

import com.db.bms.model.Group;
import com.db.bms.model.User;
import com.db.bms.service.GroupService;
import com.db.bms.service.UserService;
import com.db.bms.utils.MyResult;
import com.db.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add")
    public Map<String, Object> addGroup(@RequestBody Map<String,Object> map) {
        System.out.println(map.get("token").toString());
        Group group = new Group();
        User user = userService.getUser(map.get("token").toString());
        group.setUserId(user.getUserId());
        group.setGroupName(map.get("groupName").toString());
        System.out.println(group);
        Group groupobj = groupService.selectGroupByGroupName(group.getGroupName());

        //查看新建的group
        if(groupobj == null) {
            //没用group,可以创建
            groupService.addGroup(group);
            System.out.println("yes");
            return MyResult.getResultMap(200,"创建成功",groupService.getGroupListByUser(user));
        } else {
            //group重复，不可以创建
            System.out.println("no");
            return MyResult.getResultMap(420,"创建重复",groupService.getGroupListByUser(user));
        }
    }

    @RequestMapping(value = "/updateGroup")
    public Map<String,Object> updateGroup(@RequestBody Map<String,Object> params) {
        User user = userService.getUser(params.get("token").toString());
        System.out.println(params.get("token").toString());
        Group group = new Group();
        group.setUserId(user.getUserId());
        group.setGroupId(Integer.parseInt(params.get("groupId").toString()));
        group.setGroupName(params.get("groupName").toString());
        groupService.updateGroup(group);
        return MyResult.getResultMap(200,"更新成功",groupService.getGroupListByUser(user));
    }

    @RequestMapping(value = "/queryGroupsByPage")
    public Map<String,Object> queryGroup(@RequestBody Map<String,Object> params) {
        User user = userService.getUser(params.get("token").toString());
        List<Group> list  = groupService.getGroupListByUser(user);
        return MyResult.getResultMap(200,"获取成功",list);
    }

    @RequestMapping(value ="/deleteGroups")
    public Map<String,Object> deleteGroups(@RequestBody List<Map<String,Object>> items) {
        User user = new User();
        user.setUserId(Integer.parseInt(items.get(0).get("userId").toString()));
        for(Map<String,Object> l1 : items) {
            Group group = new Group();
            group.setGroupName(l1.get("groupName").toString());
            group.setUserId(Integer.parseInt(l1.get("userId").toString()));
            System.out.println(group.getUserId());
            group.setGroupId(Integer.parseInt(l1.get("groupId").toString()));
            groupService.deleteGroup(group);
        }

        return MyResult.getResultMap(200,"删除成功",groupService.getGroupListByUser(user));
    }

    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteGroup(@RequestBody Map<String,Object> params) {
        Group group = new Group();
        group.setGroupId(Integer.parseInt(params.get("groupId").toString()));
        group.setGroupName(params.get("groupName").toString());
        User user = userService.getUser(params.get("token").toString());
        groupService.deleteGroup(group);
        return MyResult.getResultMap(200,"删除成功",groupService.getGroupListByUser(user));
    }

    @RequestMapping(value = "/selectGroupByGroupName")
    public Map<String,Object> selectGroupByGroupName(@RequestBody Map<String,Object> params) {
        System.out.println(params);
        Group groupobj = groupService.selectGroupByGroupName(params.get("groupName").toString());
        List<Group> list = new ArrayList<>();
        list.add(groupobj);
        if(groupobj == null){
            return MyResult.getResultMap(420,"查询失败",groupService.getGroupListByUser(userService.getUser(params.get("token").toString())));
        }
        return MyResult.getResultMap(200,"查询成功",list);
    }

//    @PostMapping(value = "/queryGroupsByPage")
//    public Map<String, Object> queryGroupsByPage(String page,String limit,String token){
////        System.out.println(params);
////        MyUtils.parsePageParams(params);
////        int count = groupService.getSearchCount(params);
////        List<Group> users = groupService.searchGroupsByPage(params);
////        return MyResult.getListResultMap(200, "success", count, users);
//
//        User user = userService.getUser(token);
//        System.out.println(token);
//        return MyResult.getResultMap(200,"成功",user);
//    }
//
//    @RequestMapping(value = "/getcount")
//    public Map<String,Object> getCount() {
//
//    }

}
