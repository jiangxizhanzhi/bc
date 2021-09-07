package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthContext implements Serializable {

    private String organization;

    private String userId;

    private String likeUser;

    private Boolean isWeb;

}
