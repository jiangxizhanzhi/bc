package com.cyberpeace.scene.sceneimport.parse.result.image;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author maojun
 * @Date 2021/11/2
 */
@Setter
@Getter
public class ImageInfoParseResult {
    private String 描述;
    private String 系统类型;
    private String 系统版本;
    private String 镜像分类;
    private String 系统语言;
    private String 系统架构;
    private String 实例大小;
    private String 镜像格式;
    private String 虚拟机文件;
    private String LOGO;
    private String 发布时间;
    private String 镜像来源;
    private String 系统服务;
    private String 安装软件;
    private String 授权类型;
    private String 备注;
    private String NAT到外网;
    private String 自定义命令;
    private String 角色;
    private String 虚拟类型;
    private List<AccessModeParseResult> 接入方式;
    private List<AccessModeParseResult> 管理接入;

    public Boolean getNat() {
        return Boolean.valueOf(this.NAT到外网);
    }

    public List<AccessModeParseResult> getAccessModes() {
        return this.接入方式;
    }

    public List<AccessModeParseResult> getManageAccessModes() {
        return this.管理接入;
    }

    public List<String> getInitCommands() {
        return Arrays.asList(this.自定义命令);
    }

    public Integer getVirtualType() {
        if (this.虚拟机文件.endsWith(".tar")) {
            return VirtualType.CONTAINER;
        } else {
            return VirtualType.VIRTUAL_MACHINE;
        }
    }
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
    public Integer getRole() {
        String role = this.角色;
        if (StringUtils.isEmpty(role)) {
            return null;
        }
        return ROLE_MAP.get(role);
    }

    public String getImageType() {
        String imageType = this.虚拟类型;
        if ("虚拟机".equals(imageType)) {
            return "vm";
        } else if ("容器".equals(imageType)) {
            return "container";
        } else {
            return null;
        }
    }

    private static Map<String, Integer> ROLE_MAP = new HashMap<>();

    static {
        ROLE_MAP.put("网关节点-路由器", Role.VIRTUAL_LAYER_3_SIMPLE_ROUTER);
        ROLE_MAP.put("网关节点-交换机", Role.VIRTUAL_LAYER_3_SIMPLE_SWITCH);
        ROLE_MAP.put("网关节点-防火墙", Role.VIRTUAL_LAYER_3_SIMPLE_FIREWALL);
        ROLE_MAP.put("终端节点", Role.VIRTUAL_TERMINAL);
        ROLE_MAP.put("外部网络", Role.VIRTUAL_EXTERNAL_NETWORK);
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
    
}
