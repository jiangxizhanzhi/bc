package com.cyberpeace.oj.ojbase.controller.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WebNoteRes implements Serializable {

    private String resourceId;

    private Integer readAuthType;

    private String readAuthIds;

    private String content;

    private Date updateTime;

}
