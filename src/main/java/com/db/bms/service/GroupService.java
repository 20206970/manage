package com.db.bms.service;

import com.db.bms.model.Group;
import com.db.bms.model.User;

import java.util.List;
import java.util.Map;

public interface GroupService {

    public int addGroup(Group group);

    public int deleteGroup(Group group);

    public int updateGroup(Group group);

    public Group selectGroupByGroupName(String groupName);

    public List<Group> getGroupListByUser(User user);

    public int selectCount();

    int getSearchCount(Map<String, Object> params);

    List<Group> searchGroupsByPage(Map<String, Object> params);
    public Group getGroup(String token);
}
