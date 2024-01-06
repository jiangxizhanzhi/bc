package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.constant.enums.StatusEnum;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.bo.NetworkInstance;
import com.cyberpeace.scene.service.bo.req.CreateNetworkReq;
import com.cyberpeace.scene.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
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

    @Autowired
    private NetworkMapper networkMapper;

    @PostConstruct
    public void init(){
        CP_HOST = ConfigUtil.getContextProperty("cp.host");
        CP_NETWORK = ConfigUtil.getContextProperty("cp.network");
        DEFAULT_NAME =ConfigUtil.getContextProperty("network.default.name");
        DEFAULT_CIDR = ConfigUtil.getContextProperty("network.cidr");
        DEFAULT_GATEWAY = ConfigUtil.getContextProperty("network.gateway");
    }

    @Override
    public NetworkEntity create(String name, String cidr, String gateway, String sceneId, String pid) {
        log.info("create network start, name:{}, cidr：{}, gateway:{}", name, cidr, gateway);
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
                log.error("create network failed, name:{}, cidr：{}, gateway:{}", name, cidr, gateway);
                throw new BusinessException(ErrorCode.CREATE_NETWORK_ERROR);
            }
            networkEntity.setUser(pid);
            networkEntity.setSid(sceneId);
            networkEntity.setNetworkStatus(SceneEnum.CREATE.getCode());
            save(networkEntity);
            log.info("create network success, name:{}, cidr：{}, gateway:{}", name, cidr, gateway);
        }
        return networkEntity;
    }

    @Override
    public void delete(String networkId) {
        HttpUtil.rawDelete(CP_HOST + CP_NETWORK + "/" + networkId, String.class);
    }

    @Override
    public NetworkInstance getInstance(String networkId) {
        return HttpUtil.rawGet(CP_HOST + CP_NETWORK + "/" + networkId, NetworkInstance.class);
    }

    @Override
    public Integer getStage(String sceneId) {
        if (StringUtils.isNotBlank(sceneId)){
            List<NetworkEntity> networkEntities = networkMapper.selectList(new LambdaQueryWrapper<NetworkEntity>()
                   .eq(NetworkEntity::getSid, sceneId));
            int count = 0;
            if (!CollectionUtils.isEmpty(networkEntities)){
                for (NetworkEntity network : networkEntities){
                    NetworkInstance instance = getInstance(network.getUuid());
                    if (!Objects.isNull(instance)){
                        if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getStatus())){
                            count++;
                        }
                    }
                }
                Double divide = MathUtils.divide((double) networkEntities.size(), (double) count, 2);
                return divide.intValue() * 100;
            }
        }
        return 0;
    }
}
