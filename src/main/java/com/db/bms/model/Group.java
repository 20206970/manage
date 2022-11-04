package com.db.bms.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private Integer groupId;
    private Integer userId;
    private String groupName;
    private List<User> userList;
}
