package com.db.bms.mapper;

import com.db.bms.model.Group;
import com.db.bms.model.User;

import java.util.List;
import java.util.Map;

public interface GroupMapper{
    List<Group> selectGroupByUserId(Integer userId);

    int insertGroup(Group group);

    int deleteGroupByPrimary(Integer groupId);

    Group selectGroupByGroupName(String groupName);

    int updateGroupByPrimaryKey(Group group);

    int selectCount();


    int selectCountBySearch(Map<String, Object> params);

    List<Group> selectBySearch(Map<String, Object> params);

}