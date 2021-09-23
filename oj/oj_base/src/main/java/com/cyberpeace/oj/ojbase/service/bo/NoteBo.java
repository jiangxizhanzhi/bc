package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class NoteBo implements Serializable {

    private String resourceId;

    private Integer auth;

    private String authOrganization;

    private Integer noteType;

    private Integer resourceType;

    private String content;

    private String user;

    private String resource;

    private Date createTime;

    private Date updateTime;

}
