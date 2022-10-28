package com.db.bms.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class BookInfo {
    private Integer bookid;

    private String bookname;

    private String bookauthor;

    private BigDecimal bookprice;

    private Integer booktypeid;

    private String booktypename;

    private String bookdesc;

    private Byte isborrowed;


}
