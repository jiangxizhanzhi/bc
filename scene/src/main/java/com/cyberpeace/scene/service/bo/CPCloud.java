package com.cyberpeace.scene.service.bo;

import com.cyberpeace.common.util.ConfigUtil;
import lombok.Data;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/25 17:10
 * @desc :
 **/
@Data
public class CPCloud implements Serializable {

    /**
     * 云平台地址
     */
    String CP_HOST;

    /**
     * 网络接口
     */
    String CP_NETWORK;

    /**
     * 容器接口
     */
    String CP_CONTAINERS;

    /**
     * 默认网段
     */
    String DEFAULT_CIDR;

    /**
     * 默认网关
     */
    String DEFAULT_GATEWAY;

    @PostConstruct
    public void init(){
        CP_HOST = ConfigUtil.getContextProperty("cp.host");
        CP_NETWORK = ConfigUtil.getContextProperty("cp.network");
        CP_CONTAINERS = ConfigUtil.getContextProperty("cp.containers");
        DEFAULT_CIDR = ConfigUtil.getContextProperty("network.cidr");
        DEFAULT_GATEWAY = ConfigUtil.getContextProperty("network.gateway");
    }

    public String network(){
        return CP_HOST + CP_NETWORK;
    }

    public String containers(){
        return CP_HOST + CP_CONTAINERS;
    }

    public static CPCloud getInstance(){
        return new CPCloud();
    }
}
