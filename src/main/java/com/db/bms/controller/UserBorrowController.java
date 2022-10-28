package com.db.bms.controller;

import com.db.bms.model.UserBorrow;
import com.db.bms.service.UserBorrowService;
import com.db.bms.utils.MyResult;
import com.db.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/userBorrow")
public class UserBorrowController {
    @Autowired
    private UserBorrowService userBorrowService;

    @GetMapping(value = "/queryBookInfosByPage")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = userBorrowService.getSearchCount(params);  // 获得总数
        List<UserBorrow> bookInfos = userBorrowService.searchBookInfosByPage(params);  // 分页查询
        return MyResult.getListResultMap(200, "success", count, bookInfos);
    }

}
