package com.cyberpeace.cr.evaluation.dao.mode.topo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.evaluation.dao.mode.TopoElement;
import com.cyberpeace.integratation.model.mp.ImagesByIdVO;
import lombok.Data;

import java.util.List;

@Data
public class CpcloudElement {
    private String resourceId = "";
    private Integer role;
    private String imageType;
    private List<CpcloudElement> otherSimilarElements;
    private String imageUuid;
    private Boolean isBuildIn;
    private String image;
    private String platform;
    private Integer electricPortNumber;
    private String electricPortType = "E";
    private Integer opticalPortNumber = 0;
    private String opticalPortType = "sfp";
    private Boolean initSupport;
    private JSONArray firewallRules;
    private JSONArray staticRoutes;
    private Integer category;
    private Integer category2;
    private Integer category3;
    private String cidr;
    private String gateway;
    private JSONArray dnsNameservers;
    private String vlanId;
    private Boolean dhcp=true;
    private JSONArray allocationPools;
    private int wanPortNumber;
    private String wanPortType;
    private int lanPortNumber;
    private String lanPortType = "E";
    private String quickCreateParams;
    private String ospfRouterId = "";
    private String ripLoopback = "";
    private String isoAttachFilename;

//    public void setSnmpConfig(String snmpConfig) {
//        this.snmpConfig = snmpConfig;
//    }
//
//    public void setDockerParams() {
//        JSONObject dockerParams = new JSONObject();
//        dockerParams.put("environment_str", imagesByIdVO.getExtImageInfo().getMetaData().getEnvironmentStr());
//        dockerParams.put("tty", imagesByIdVO.getExtImageInfo().getMetaData().getTty());
//        dockerParams.put("interactive", imagesByIdVO.getExtImageInfo().getMetaData().getInteractive());
//        this.dockerParams = dockerParams;
//    }



    private String isoTmpdirUuid;
    private JSONArray virtualUsbs;
    private JSONArray entityUsbs;
    private JSONArray mibData;
    private String snmpConfig="undefined";
    private JSONObject dockerParams;
    private ImagesByIdVO sourceImageInfo;
    private TopoElement initTopoElement;
}
