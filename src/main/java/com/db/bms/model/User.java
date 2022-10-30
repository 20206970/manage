package com.db.bms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer userId;

    private String username;

    private String userPassword;

    private Byte isadmin;

    private Integer identify;

    private String question;

    private String answer;

    private String phoneNumber;

    public User(String username, String userPassword, String question, String answer, String phoneNumber) {
        this.username = username;
        this.userPassword = userPassword;
        this.question = question;
        this.answer = answer;
        this.phoneNumber = phoneNumber;
    }
}