package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PracticeCategoryParent implements Serializable {
    private String resourceId;

    private String code;

    private String name;

    private Date createTime;

    private Date updateTime;

    private PracticeCategoryParent parent;
}
