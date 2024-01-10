package com.cyberpeace.scene.base;


import lombok.Data;

@Data
public class BaseCloudPluginSceneElementModel extends BasePluginSceneElementModel {
    /**
     * 外部资源
     */
    
    private Boolean external;
    /**
     * 云平台资源类型
     */
    private Integer virtualType;
    
    private Boolean transparent;
    
    private Boolean isBuildIn;
    
    private Integer netMode;

    /**
     * 云平台资源id
     */
    
    private String resourceId;
    /**
     * 三层防火墙对应防火墙资源id
     */
    
    private String firewallResourceId;

    // 虚拟交换机/路由/防火墙属性
    
    private Integer electricPortNumber;

    private String electricPortType;
    
    private Integer opticalPortNumber;

    private String opticalPortType;
    
    private Integer wanPortNumber;

    private String wanPortType;
    
    private Integer lanPortNumber;

    private String lanPortType;

    // 虚拟交换机属性
    
    private String cidr;
    
    private String gateway;
    
    private Boolean gatewayOff;
    
    private String dnsNameservers;

    private Boolean dhcp;
    
    private String allocationPools;
    
    private String hostRoutes;
    
    private Boolean vlan;
    
    private Integer vlanId;
    
    private Boolean auxiliaryRouterOff;


    // 虚拟路由属性
    
    private String staticRoutes;

    // ospf网络属性
    
    private String ospfRouterId;

    // rip网络属性
    
    private String ripLoopback;

    // 虚拟防火墙属性
    
    private String firewallRules;

    // 虚拟机属性
    
    private String isoAttachFilename;
    
    private String isoTmpdirUuid;
    
    private String virtualUsbs;
    
    private String entityUsbs;
    
    private String dockerParams;
    
    private String platform;

    
    private String snmpConfig;
    
    private String mibData;

    // 虚拟机/容器属性
    
    private String image;
    
    private String imageUuid;
    
    private String initialTools;

    // 靶标属性
    
    private Integer role;
    
    private String imageType;
    
    private Integer category;
    
    private Integer category2;
    
    private Integer category3;
    
    private Boolean initSupport;
    
    private Boolean quickCreateSupport;
    
    private String quickCreateParams;

    // 宿主机信息
    
    private String host;


    public static class VirtualType {
        public static final int VIRTUAL_SWITCH = 1;
        public static final int VIRTUAL_ROUTER = 2;
        public static final int VIRTUAL_FIREWALL = 3;
        public static final int VIRTUAL_MACHINE = 4;
        public static final int CONTAINER = 5;
        public static final int ENTITY_NODE = 6;
        public static final int NPC = 7;
        public static final int DYNAMIPS = 8;

        private VirtualType() {
        }
    }

    public static class ElectricPortType {
        public static final String E = "E";
        public static final String FE = "FE";
        public static final String GE = "GE";

        private ElectricPortType() {}
    }

    public static class OpticalPortType {
        public static final String SFP = "sfp";
        public static final String SFP_PLUS = "sfp+";

        private OpticalPortType() {}
    }

    public static class WanPortType {
        public static final String E = "E";

        private WanPortType() {
        }
    }

    public static class LanPortType {
        public static final String E = "E";

        private LanPortType() {
        }
    }

    public static class Role {
        public static final int VIRTUAL_LAYER_2_GATEWAY = 1;
        public static final int VIRTUAL_LAYER_3_GATEWAY = 2;
        public static final int VIRTUAL_EXTERNAL_NETWORK = 3;
        public static final int VIRTUAL_TERMINAL = 4;
        public static final int VIRTUAL_TRANSPARENT_GATEWAY = 5;
        public static final int VIRTUAL_GATEWAY = 6;
        public static final int NPC = 7;
        public static final int VIRTUAL_LAYER_3_IMAGE_GATEWAY = 20;
        public static final int VIRTUAL_LAYER_3_SIMPLE_ROUTER = 21;
        public static final int VIRTUAL_LAYER_3_SIMPLE_SWITCH = 22;
        public static final int VIRTUAL_LAYER_3_SIMPLE_FIREWALL = 23;
        public static final int ENTITY_GATEWAY = 50;
        public static final int ENTITY_TERMINAL = 51;

        private Role() {}
    }

    public static class ImageType {
        public static final String SIMPLE = "simple";
        public static final String VM = "vm";
        public static final String CONTAINER = "container";
        public static final String ENTITY = "entity";
        public static final String DYNAMIPS = "dynamips";

        private ImageType() {}
    }

    public static class NetMode {
        public static final Integer NORMAL = 0;
        public static final Integer MACVLAN = 1;

        private NetMode() {
        }
    }

    // 用于设备连接的辅助网络
    
    private Boolean auxiliaryNetworkOn;
    
    private String auxiliaryNetworkId;
    
    private String auxiliaryNetworkCidr;
    
    private String auxiliaryNetworkGateway;
    
    private String auxiliaryNetworkDnsNameservers;

    private Boolean auxiliaryNetworkDhcp;
    
    private String auxiliaryNetworkAllocationPools;
    
    private String auxiliaryNetworkHostRoutes;
    
    private Boolean auxiliaryNetworkVlan;
    
    private Integer auxiliaryNetworkVlanId;
    /**
     * 辅助网络辅助路由，ovs流表需要网络必须有默认路由
     */
    
    private Boolean auxiliaryNetworkRouterOn;
    
    private String auxiliaryNetworkRouterResourceId;
    
    private String auxiliaryNetworkRouterIp;

    // 用于管理网卡
    
    private Boolean enableMgmt;
    
    private String mgmtPortResourceId;
    
    private String mgmtIp;
    
    private String mgmtMacAddr;

}
