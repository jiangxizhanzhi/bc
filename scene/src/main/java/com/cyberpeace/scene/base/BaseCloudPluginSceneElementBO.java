package com.cyberpeace.scene.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public  class BaseCloudPluginSceneElementBO {

    @JsonIgnore
    private BaseSceneElementBO srcElement;
    @ApiModelProperty(value = "是否外部引用资源")
    private Boolean external;
    @ApiModelProperty(value = "云平台虚拟化类型")
    private Integer virtualType;
    @ApiModelProperty(value = "是否透明模式")
    private Boolean transparent;
    @ApiModelProperty(value = "是否云平台內置靶標")
    private Boolean isBuildIn;
    @ApiModelProperty(value = "网络创建模式,0或空表示默认OpenStack网络,1表示macvlan网络")
    private Integer netMode;

    @ApiModelProperty(value = "云平台虚拟化资源id")
    private String resourceId;
    @ApiModelProperty(value = "云平台三层防火墙资源id")
    private String firewallResourceId;

    // 虚拟交换机/路由/防火墙属性
    @ApiModelProperty(value = "电口数量")
    private Integer electricPortNumber;
    @ApiModelProperty(value = "电口类型")
    private String electricPortType;
    @ApiModelProperty(value = "光口数量")
    private Integer opticalPortNumber;
    @ApiModelProperty(value = "光口类型")
    private String opticalPortType;
    @ApiModelProperty(value = "wan口数量")
    private Integer wanPortNumber;
    @ApiModelProperty(value = "wan口类型")
    private String wanPortType;
    @ApiModelProperty(value = "lan口数量")
    private Integer lanPortNumber;
    @ApiModelProperty(value = "lan口类型")
    private String lanPortType;

    // 虚拟交换机属性
    @ApiModelProperty(value = "虚拟交换机cidr地址段")
    private String cidr;
    @ApiModelProperty(value = "虚拟交换机网关")
    private String gateway;
    @ApiModelProperty(value = "关闭虚拟交换机网关的分配")
    private Boolean gatewayOff;
    @ApiModelProperty(value = "虚拟交换机dns")
    private List<String> dnsNameservers;
    @ApiModelProperty(value = "虚拟交换机是否开启dhcp")
    private Boolean dhcp;
    @ApiModelProperty(value = "虚拟交换机地址池")
    private List<AllocationPool> allocationPools;
    @ApiModelProperty(value = "虚拟交换机静态路由")
    private List<HostRoute> hostRoutes;
    @ApiModelProperty(value = "虚拟交换机vlan模式")
    private Boolean vlan;
    @ApiModelProperty(value = "虚拟交换机vlanId")

    private Integer vlanId;

    // 虚拟路由属性
    @ApiModelProperty(value = "虚拟路由静态路由")
  
    private List<StaticRoute> staticRoutes;

    // ospf网络属性
    @ApiModelProperty(value = "ospf网络下的routerID")
    private String ospfRouterId;

    @ApiModelProperty(value = "rip网络下的lookback")
    private String ripLoopback;

    // 虚拟防火墙属性
    @ApiModelProperty(value = "虚拟防火墙规则")
  
    private List<FirewallRule> firewallRules;

    // 虚拟机属性
    @ApiModelProperty(value = "挂载iso文件名")
    private String isoAttachFilename;
    @ApiModelProperty(value = "挂载iso临时文件地址")
    private String isoTmpdirUuid;

    // 虚拟机/容器属性
    @ApiModelProperty(value = "虚拟机/容器镜像名称")
    private String image;
    @ApiModelProperty(value = "虚拟机/容器镜像id")
    private String imageUuid;
    @ApiModelProperty(value = "终端初始包含工具")
  
    private List<InitialTool> initialTools;
    @ApiModelProperty(value = "仿真usb列表")
    private List<VirtualUsb> virtualUsbs;
    @ApiModelProperty(value = "实体usb列表")
    private List<EntityUsb> entityUsbs;
    @ApiModelProperty(value = "docker参数")
    private DockerParams dockerParams;

    // gns3 属性
    @ApiModelProperty(value = "gns3 platform")
    private String platform;

    @ApiModelProperty(value = "snmp协议配置")
    private SnmpConfig snmpConfig;

    @ApiModelProperty(value = "mib文件数据")
    private List<MibData> mibData;

    // 靶标属性
    @ApiModelProperty(value = "靶标角色")
    private Integer role;
    @ApiModelProperty(value = "镜像类型")
    private String imageType;
    @ApiModelProperty(value = "靶标分类")
    private Integer category;
    @ApiModelProperty(value = "靶标二级分类")
    private Integer category2;
    @ApiModelProperty(value = "靶标三级分类")
    private Integer category3;
    @ApiModelProperty(value = "是否支持初始化")
    private Boolean initSupport;
    @ApiModelProperty(value = "是否支持快速创建")
    private Boolean quickCreateSupport;
    @ApiModelProperty(value = "快速创建参数")
  
    private List<QuickCreateParam> quickCreateParams;

    @ApiModelProperty(value = "是否开启辅助网络", hidden = true)
    @JsonIgnore
    @Deprecated
    private Boolean auxiliaryNetworkOn;
    @ApiModelProperty(value = "辅助网络id", hidden = true)
    @JsonIgnore
    @Deprecated
    private String auxiliaryNetworkId;
    @ApiModelProperty(value = "辅助网络cidr", hidden = true)
    @JsonIgnore
    @Deprecated
    private String auxiliaryNetworkCidr;
    @ApiModelProperty(value = "辅助网络网关", hidden = true)
    @JsonIgnore
    @Deprecated
    private String auxiliaryNetworkGateway;
    @ApiModelProperty(value = "辅助网络dns", hidden = true)
    @JsonIgnore
    @Deprecated
    private List<String> auxiliaryNetworkDnsNameservers;
    @ApiModelProperty(value = "辅助网络dhcp", hidden = true)
    @JsonIgnore
    @Deprecated
    private Boolean auxiliaryNetworkDhcp;
    @ApiModelProperty(value = "辅助网络地址池", hidden = true)
    @JsonIgnore
  
    @Deprecated
    private List<AllocationPool> auxiliaryNetworkAllocationPools;
    @ApiModelProperty(value = "辅助网络静态路由", hidden = true)
    @JsonIgnore
  
    @Deprecated
    private List<HostRoute> auxiliaryNetworkHostRoutes;
    @ApiModelProperty(value = "辅助网络vlan模式", hidden = true)
    @JsonIgnore
    @Deprecated
    private Boolean auxiliaryNetworkVlan;
    @ApiModelProperty(value = "辅助网络vlanId", hidden = true)
    @JsonIgnore

    @Deprecated
    private Integer auxiliaryNetworkVlanId;
    @ApiModelProperty(value = "是否开启辅助网络辅助路由", hidden = true)
    @JsonIgnore
    private Boolean auxiliaryNetworkRouterOn;
    @ApiModelProperty(value = "辅助网络辅助路由id", hidden = true)
    @JsonIgnore
    private String auxiliaryNetworkRouterResourceId;
    @ApiModelProperty(value = "辅助网络辅助路由ip", hidden = true)
    @JsonIgnore
    private String auxiliaryNetworkRouterIp;
    @ApiModelProperty(value = "关闭辅助路由")
    private Boolean auxiliaryRouterOff;

    @ApiModelProperty(value = "是否开启管理网卡", hidden = true)
    @JsonIgnore
    private Boolean enableMgmt;
    @ApiModelProperty(value = "管理网卡端口资源id", hidden = true)
    @JsonIgnore
    private String mgmtPortResourceId;
    @ApiModelProperty(value = "管理网卡ip", hidden = true)
    @JsonIgnore
    private String mgmtIp;
    @ApiModelProperty(value = "管理网卡mac地址", hidden = true)
    @JsonIgnore
    private String mgmtMacAddr;

    @ApiModelProperty(value = "宿主机名称")
    private String host;

}
