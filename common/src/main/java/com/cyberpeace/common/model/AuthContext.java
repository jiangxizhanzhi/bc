package com.cyberpeace.common.model;

import lombok.Data;

@Data
public class AuthContext {

    private String organization;

    private String likeOrganization;

    private String userId;

    private String likeUser;

    private Boolean isWeb;

}
