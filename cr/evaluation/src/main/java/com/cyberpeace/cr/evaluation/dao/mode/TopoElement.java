package com.cyberpeace.cr.evaluation.dao.mode;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

@Data
public class TopoElement {
    private String displayContext = "";
    private String eid = "";
    private String id = "";
    private String area = "";
    private String name;
    private Integer type;
    private String icon2;
    private String icon3 = "";
    private String systemLanguage;
    private String systemArchitecture;
    private Integer systemType;
    private Integer maxPortNumber = 0;
    private Integer cpu = 0;
    private Integer disk = 0;
    private Integer ram = 0;
    private String nvram = "";
    private String initCommands = "";
    private String file = "";
    private String nodeCommand = "";
    private String position2 = "";
    private List<Router> routers;
    private JSONArray ports;
}
