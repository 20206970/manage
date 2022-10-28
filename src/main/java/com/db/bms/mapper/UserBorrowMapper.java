package com.db.bms.mapper;

import com.db.bms.model.UserBorrow;

import java.util.List;
import java.util.Map;

public interface UserBorrowMapper {
    int selectCountBySearch(Map<String, Object> searchParam);
    List<UserBorrow> selectBySearch(Map<String, Object> searchParam);

    UserBorrow selectUserB0rrow(Integer userid);

    void insert(UserBorrow userBorrow);

    void drop(Integer userid);
}
