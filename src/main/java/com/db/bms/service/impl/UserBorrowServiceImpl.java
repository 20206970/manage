package com.db.bms.service.impl;

import com.db.bms.mapper.UserBorrowMapper;
import com.db.bms.model.UserBorrow;
import com.db.bms.service.UserBorrowService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class UserBorrowServiceImpl implements UserBorrowService {
    @Resource
    private UserBorrowMapper userBorrowMapper;
    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        return userBorrowMapper.selectCountBySearch(params);
    }

    @Override
    public List<UserBorrow> searchBookInfosByPage(Map<String, Object> params) {
        return userBorrowMapper.selectBySearch(params);
    }

    @Override
    public UserBorrow selectUserB0rrow(Integer userid) {
        return userBorrowMapper.selectUserB0rrow(userid);
    }

    @Override
    public void insert(UserBorrow userBorrow) {
        userBorrowMapper.insert(userBorrow);
    }

    @Override
    public void drop(Integer userid) {
        userBorrowMapper.drop(userid);
    }
}
