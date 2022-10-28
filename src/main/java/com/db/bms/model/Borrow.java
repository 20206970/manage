package com.db.bms.model;

import lombok.Data;

import java.util.Date;
@Data
public class Borrow {
    private Integer borrowid;

    private Integer userid;

    private String username;

    private Integer bookid;

    private String bookname;

    private Date borrowtime;

    private String borrowtimestr;

    private Date returntime;

    private String returntimestr;


}
