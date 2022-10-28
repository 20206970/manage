package com.db.bms.service;

import com.db.bms.model.UserBorrow;

import java.util.List;
import java.util.Map;

public interface UserBorrowService  {
    Integer getSearchCount(Map<String, Object> params);
    List<UserBorrow> searchBookInfosByPage(Map<String, Object> params);
    UserBorrow selectUserB0rrow(Integer userid);

    void insert(UserBorrow userBorrow);

    void drop(Integer userid);
}
