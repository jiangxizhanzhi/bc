package com.cyberpeace.scene.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
import com.cyberpeace.scene.service.CpEnvService;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.bo.NetworkInstance;
import com.cyberpeace.scene.service.bo.req.CreateNetworkReq;
import com.cyberpeace.scene.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

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

    @Autowired
    private CpEnvService cpEnvService;

    @PostConstruct
    public void init(){
        CP_HOST = cpEnvService.getCpHost();
        CP_NETWORK = ConfigUtil.getContextProperty("cp.network");
        DEFAULT_NAME =ConfigUtil.getContextProperty("network.default.name");
        DEFAULT_CIDR = ConfigUtil.getContextProperty("network.default.cidr");
        DEFAULT_GATEWAY = ConfigUtil.getContextProperty("network.default.gateway");
    }

	@Override
	public NetworkEntity create(String name, String cidr, String gateway, String sceneId,
			String pid) {
		// 1.先保存entity数据库
		NetworkEntity entity = new NetworkEntity();
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setSid(sceneId);
		entity.setNetworkStatus(SceneEnum.CREATE.getCode());

		this.save(entity);
		// 2.再远程创建network
		log.info("create network start, name:{}, cidr：{}, gateway:{}", name, cidr, gateway);
		CreateNetworkReq req;
		if (StringUtils.isBlank(cidr) || StringUtils.isBlank(gateway)) {
			req = new CreateNetworkReq(DEFAULT_NAME + "_" + name, DEFAULT_CIDR, DEFAULT_GATEWAY);
		} else {
			req = new CreateNetworkReq(name, cidr, gateway);
		}
		try {
			// 根据网段和网关判断网络是否已创建，如果已创建，则直接返回网络信息，如果未创建，则创建网络并返回网络信息
			NetworkEntity rsp = HttpUtil.post(CP_HOST + CP_NETWORK, req, NetworkEntity.class);
			if (Objects.isNull(rsp)) {
				log.error("create network failed, name:{}, cidr：{}, gateway:{}", name, cidr,
						gateway);
				throw new BusinessException(ErrorCode.CREATE_NETWORK_ERROR);
			}
			// 3.更新entity
			entity.setNetworkStatus(SceneEnum.ACTIVE.getCode());
			entity.setUuid(rsp.getUuid());
			entity.setType(rsp.getType());
			entity.setCidr(rsp.getCidr());
			entity.setGateway(rsp.getGateway());
			entity.setMtu(rsp.getMtu());
			entity.setVlan(rsp.getVlan());
			log.info("create network success, name:{}, cidr：{}, gateway:{}", name, cidr, gateway);
		} catch (Exception e) {
			entity.setNetworkStatus(SceneEnum.ERROR.getCode());
			throw e;
		} finally {
			this.updateById(entity);
		}
		return entity;
	}

    @Override
    public void delete(String networkId) {
        HttpUtil.rawDelete(CP_HOST + CP_NETWORK + "/" + networkId, String.class);
    }

    @Override
    public NetworkInstance getInstance(String networkId) {
        if (StringUtils.isBlank(networkId)){
            return null;
        }
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

	@Override
	public NetworkEntity create(String sceneId) {
		// 1.先保存entity数据库
		NetworkEntity entity = new NetworkEntity();
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setSid(sceneId);
		entity.setNetworkStatus(SceneEnum.CREATE.getCode());

		this.save(entity);
		// 2.再远程创建network
		log.info("create network start, scene_id:{}", sceneId);
		CreateNetworkReq req = new CreateNetworkReq(DEFAULT_NAME + "_" + entity.getResourceId(),
				DEFAULT_CIDR, DEFAULT_GATEWAY);
		try {
			// 根据网段和网关判断网络是否已创建，如果已创建，则直接返回网络信息，如果未创建，则创建网络并返回网络信息
			NetworkEntity rsp = HttpUtil.post(CP_HOST + CP_NETWORK, req, NetworkEntity.class);
			if (Objects.isNull(rsp)) {
				log.error("create network failed, name:{}, cidr：{}, gateway:{}", req.getName(),
						req.getCidr(), req.getGateway());
				throw new BusinessException(ErrorCode.CREATE_NETWORK_ERROR);
			}
			// 3.更新entity
			entity.setNetworkStatus(SceneEnum.ACTIVE.getCode());
			entity.setUuid(rsp.getUuid());
			entity.setType(rsp.getType());
			entity.setCidr(rsp.getCidr());
			entity.setGateway(rsp.getGateway());
			entity.setMtu(rsp.getMtu());
			entity.setVlan(rsp.getVlan());
			log.info("create network success, scene_id:{}", sceneId);
		} catch (Exception e) {
			entity.setNetworkStatus(SceneEnum.ERROR.getCode());
			throw e;
		} finally {
			this.updateById(entity);
		}
		return entity;
	}
}
