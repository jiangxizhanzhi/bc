package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MpLogFile implements Serializable {

    private String elementId;

    private String agentName;

    private String osType;

    private String path;

    private String url;

    private String name;

    private String size;

    private Date createTime;

    private Date modifyTime;

    private Date accessTime;


}
