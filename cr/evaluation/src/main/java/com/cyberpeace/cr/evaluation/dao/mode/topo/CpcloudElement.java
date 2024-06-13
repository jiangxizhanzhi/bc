package com.cyberpeace.cr.evaluation.dao.mode.topo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.mp.ImagesByIdVO;
import com.cyberpeace.integratation.model.mp.PortConfig;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CpcloudElement {
    private ImagesByIdVO imagesByIdVO;
    private String resourceId = "";
    private Integer role;
    private String imageType;
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
    public CpcloudElement(ImagesByIdVO imagesByIdVO){
        this.imagesByIdVO = imagesByIdVO;
    }


    public void setRole() {
        this.role = imagesByIdVO.getRole();
    }

    public void setImageType() {
        this.imageType = imagesByIdVO.getImageType();
    }

    public void setImageUuid() {
        this.imageUuid = imagesByIdVO.getUuid();
    }

    public void setBuildIn() {
        isBuildIn = imagesByIdVO.getIsInternal();
    }

    public void setImage() {
        // TODO 字段待填充
        this.image = imagesByIdVO.getImageType();
    }

    public void setPlatform() throws NoSuchFieldException {
        Class<?> object = imagesByIdVO.getExtVm().getExtInfo().getClass();
        try {
            this.platform = object.getDeclaredField("platform").get(object).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setElectricPortNumber() {
        this.electricPortNumber = "simple".equals(imagesByIdVO.getImageType()) ? 4:0;
    }

    public void setInitSupport() {
        this.initSupport = null != imagesByIdVO.getExtVm().getInitSupport() ?
                imagesByIdVO.getExtVm().getInitSupport(): imagesByIdVO.getExtDocker().getInitSupport();
    }

    public void setCategory() {
        this.category = imagesByIdVO.getCategory();
    }

    public void setCategory2() {
        this.category2 = imagesByIdVO.getSecondCategory();
    }

    public void setCategory3() {
        this.category3 = imagesByIdVO.getThirdCategory();
    }

    public void setCidr() {
        this.cidr = imagesByIdVO.getRole() == 1 ? "192.168.1.10/24":"";
    }

    public void setGateway() {
        this.gateway = imagesByIdVO.getRole() == 1 ? "192.168.1.254":"";
    }

    public void setDnsNameservers(String dnsNameservers) {
        JSONArray objects = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("dns", "8.8.8.8");
        JSONObject obj2 = new JSONObject();
        obj.put("dns", "114.114.114.114");
        objects.add(obj);
        objects.add(obj2);
        this.dnsNameservers = imagesByIdVO.getRole() == 1 ? objects:new JSONArray();
    }

    public void setVlanId(String vlanId) {
        // TODO 值类型待设置
        this.vlanId = vlanId;
    }

    public void setWanPortNumber() {
        List<PortConfig> portConfigs = imagesByIdVO.getExtGateway().getPortConfigs();
        this.wanPortNumber = portConfigs.stream().filter(s-> s.getPortType() == 1).collect(Collectors.toList()).size();
    }


    public void setWanPortType() {
        // 待确认判断逻辑 wan_port_type: "是核心路由器" || TargetData.image_type === 'dynamips' ? 'sfp' : 'E',
        this.wanPortType = "dynamips".equals(imagesByIdVO.getImageType()) ? "sfp" :"E";
    }

    public void setLanPortNumber() {
        List<PortConfig> portConfigs = imagesByIdVO.getExtGateway().getPortConfigs();
        this.lanPortNumber = portConfigs.stream().filter(s-> s.getPortType() == 2).collect(Collectors.toList()).size();
    }

    public void setQuickCreateParams(String quickCreateParams) {

//        List<QuickCreateParam> quickCreateParams1 = imagesByIdVO.getExtVm().getQuickCreateParams();
//        List<QuickCreateParam> quickCreateParams1 = imagesByIdVO.getExtDocker().getQuickCreateParams();
        this.quickCreateParams = "";
    }

    public void setIsoAttachFilename() {
        this.isoAttachFilename = "vm".equals(imagesByIdVO.getImageType()) && imagesByIdVO.getExtVm().getIsoAttach()? imagesByIdVO.getExtVm().getIsoAttachFilename():"undefined";
    }

    public void setIsoTmpdirUuid() {
        this.isoTmpdirUuid = "vm".equals(imagesByIdVO.getImageType()) && imagesByIdVO.getExtVm().getIsoAttach() ? imagesByIdVO.getExtVm().getIsoTmpdirUuid():"undefined";
    }

    public void setSnmpConfig(String snmpConfig) {
        this.snmpConfig = snmpConfig;
    }

    public void setDockerParams() {
        JSONObject dockerParams = new JSONObject();
        dockerParams.put("environment_str", imagesByIdVO.getExtImageInfo().getMetaData().getEnvironmentStr());
        dockerParams.put("tty", imagesByIdVO.getExtImageInfo().getMetaData().getTty());
        dockerParams.put("interactive", imagesByIdVO.getExtImageInfo().getMetaData().getInteractive());
        this.dockerParams = dockerParams;
    }



    private String isoTmpdirUuid;
    private JSONArray virtualUsbs;
    private JSONArray entityUsbs;
    private JSONArray mibData;
    private String snmpConfig="undefined";
    private JSONObject dockerParams;
}
