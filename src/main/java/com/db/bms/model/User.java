package com.db.bms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class User implements Serializable {
    private Integer userId;

    private String userName;

    public User(Integer userId, String userName, String userPassword, Integer identify, String question, String answer, String phoneNumber) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.identify = identify;
        this.question = question;
        this.answer = answer;
        this.phoneNumber = phoneNumber;
    }

    private String userPassword;

    private Integer identify;

    private String question;

    private String answer;

    private String phoneNumber;

    public User(String username, String userPassword, String question, String answer, String phoneNumber) {
        this.userName = username;
        this.userPassword = userPassword;
        this.question = question;
        this.answer = answer;
        this.phoneNumber = phoneNumber;
    }

    public User(String username, String userpassword) {
        this.userName = username;
        this.userPassword = userpassword;
    }



    public User(String userName, String userPassword, Integer identify, String question, String answer, String phoneNumber) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.identify = identify;
        this.question = question;
        this.answer = answer;
        this.phoneNumber = phoneNumber;
    }


    public User(Integer userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}