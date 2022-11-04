package com.db.bms.service.impl;

import com.db.bms.mapper.GroupMapper;
import com.db.bms.model.Group;
import com.db.bms.model.User;
import com.db.bms.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public int addGroup(Group group) {
        return groupMapper.insertGroup(group);
    }

    @Override
    public int deleteGroup(Group group) {
        return groupMapper.deleteGroupByPrimary(group.getGroupId());
    }

    @Override
    public int updateGroup(Group group) {
        return groupMapper.updateGroupByPrimaryKey(group);
    }

    @Override
    public Group selectGroupByGroupName(String groupName) {
        return groupMapper.selectGroupByGroupName(groupName);
    }

    @Override
    public List<Group> getGroupListByUser(User user) {
        return groupMapper.selectGroupByUserId(user.getUserId());
    }
    //
//    @Override
//    public int selectCount() {
//        return 0;
//    }
//
    @Override
    public int selectCount() {
        return  groupMapper.selectCount();
    }

    @Override
    public int getSearchCount(Map<String, Object> params) {
        return groupMapper.selectCountBySearch(params);
    }

    @Override
    public List<Group> searchGroupsByPage(Map<String, Object> params) {
        return groupMapper.selectBySearch(params);
    }

    @Override
    public Group getGroup(String token) {
        // 根据token得到user
        return (Group) redisTemplate.opsForValue().get(token);
    }
}
