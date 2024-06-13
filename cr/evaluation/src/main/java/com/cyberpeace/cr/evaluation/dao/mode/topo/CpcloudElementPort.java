package com.cyberpeace.cr.evaluation.dao.mode.topo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.mp.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CpcloudElementPort {
    private ImagesByIdVO imagesByIdVO;
    private JSONObject accessMode;
    private JSONArray firewallRules;
    public CpcloudElementPort(ImagesByIdVO imagesByIdVO){
        this.imagesByIdVO = imagesByIdVO;
    }

    public void setAccessMode() {
        ExtGateway extGateway = imagesByIdVO.getExtGateway();
        ExtVm extVm = imagesByIdVO.getExtVm();
        ExtDocker extDocker = imagesByIdVO.getExtDocker();
        JSONObject jsonObject = new JSONObject();
        if(null != extGateway){
            jsonObject.put("access_mode", extGateway);
        } else if(null != extVm.getAccessProtocols()){
            jsonObject.put("access_mode", extDocker.getAccessProtocols());
        } else if(null != extDocker.getAccessProtocols()){
            jsonObject.put("access_mode", extDocker.getAccessProtocols());
        }
        this.accessMode = jsonObject;
    }

    public void setConnectMode() {
        this.connectMode = (imagesByIdVO.getRole() == 1 || imagesByIdVO.getRole()==2) && !"simple".equals(imagesByIdVO.getImageType()) ?"access":null;
    }

    private String connectMode;
    private String logicNetworkId="";
    private String resourceI="";

}
