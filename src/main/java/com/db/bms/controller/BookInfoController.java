package com.db.bms.controller;

import com.db.bms.model.BookInfo;
import com.db.bms.service.BookInfoService;
import com.db.bms.utils.MyResult;
import com.db.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bookInfo")
public class BookInfoController {

    @Autowired
    BookInfoService bookInfoService;


    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return bookInfoService.getCount();
    }


    @GetMapping(value = "/queryBookInfos")
    public List<BookInfo> queryBookInfos(){
        return bookInfoService.queryBookInfos();
    }


    @GetMapping(value = "/queryBookInfosByPage")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = bookInfoService.getSearchCount(params);  // 获得总数
        List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage(params);  // 分页查询
        return MyResult.getListResultMap(0, "success", count, bookInfos);
    }


    @PostMapping(value = "/addBookInfo")
    public Integer addBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.addBookInfo(bookInfo);
    }


    @DeleteMapping(value = "/deleteBookInfo")
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.deleteBookInfo(bookInfo);
    }


    @DeleteMapping(value = "/deleteBookInfos")
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos){
        return bookInfoService.deleteBookInfos(bookInfos);
    }


    @PutMapping(value = "/updateBookInfo")
    public Integer updateBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.updateBookInfo(bookInfo);
    }
}
