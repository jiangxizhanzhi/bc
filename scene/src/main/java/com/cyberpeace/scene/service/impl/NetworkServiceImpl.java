package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.bo.CPCloud;
import com.cyberpeace.scene.service.bo.req.CreateNetworkReq;
import com.cyberpeace.scene.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author :   SN
 * @date :   2023/12/25 16:57
 * @desc :
 **/
@Slf4j
@Service
public class NetworkServiceImpl extends ServiceImpl<NetworkMapper, NetworkEntity>
        implements NetworkService {

    /**
     * 云平台地址
     */
    private String CP_HOST;

    /**
     * 网络接口
     */
    private String CP_NETWORK;

    /**
     * 默认网络名称
     */
    private String DEFAULT_NAME;

    /**
     * 默认网段
     */
    private String DEFAULT_CIDR;

    /**
     * 默认网关
     */
    private String DEFAULT_GATEWAY;

    @PostConstruct
    public void init(){
        CP_HOST = ConfigUtil.getContextProperty("cp.host");
        CP_NETWORK = ConfigUtil.getContextProperty("cp.network");
        DEFAULT_NAME =ConfigUtil.getContextProperty("network.default.name");
        DEFAULT_CIDR = ConfigUtil.getContextProperty("network.cidr");
        DEFAULT_GATEWAY = ConfigUtil.getContextProperty("network.gateway");
    }

    @Override
    public NetworkEntity createNetwork(String name, String cidr, String gateway) {
        CreateNetworkReq req;
        if (StringUtils.isBlank(cidr) || StringUtils.isBlank(gateway)){
            String uuid = IDGeneratorUtil.getID();
            req = new CreateNetworkReq(DEFAULT_NAME + "_" + uuid, DEFAULT_CIDR, DEFAULT_GATEWAY);
        }else {
            req = new CreateNetworkReq(name, cidr, gateway);
        }
        LambdaQueryWrapper<NetworkEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NetworkEntity::getName, name);
        NetworkEntity networkEntity = getOne(queryWrapper);
        //根据网段和网关判断网络是否已创建，如果已创建，则直接返回网络信息，如果未创建，则创建网络并返回网络信息
        if (Objects.isNull(networkEntity)){
            networkEntity = HttpUtil.post(CP_HOST + CP_NETWORK, req, NetworkEntity.class);
            if (Objects.isNull(networkEntity)){
                log.error("创建网络失败");
                throw new BusinessException(ErrorCode.CREATE_NETWORK_ERROR);
            }
            save(networkEntity);
        }
        return networkEntity;
    }

    @Override
    public void deleteNetwork(String networkId) {
        HttpUtil.rawDelete(CP_HOST + CP_NETWORK + "/" + networkId, null);
    }
}
