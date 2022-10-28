package com.db.bms.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBorrow implements Serializable {
    private int ubid;
    private int userid;
    private int hasborrow;
    private int maxborrow;
    private String userName;

}
