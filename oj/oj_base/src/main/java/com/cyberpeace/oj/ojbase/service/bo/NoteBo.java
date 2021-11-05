package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class NoteBo implements Serializable {

    private String resourceId;

    private Integer readAuthType;

    private String readAuthIds;

    private Integer operationAuthType;

    private String operationAuthIds;

    private Integer noteType;

    private Integer businessType;

    private String content;

    private String userId;

    private String businessId;

    private Date createTime;

    private Date updateTime;

}
