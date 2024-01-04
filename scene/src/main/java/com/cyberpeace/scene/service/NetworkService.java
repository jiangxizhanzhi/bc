package com.cyberpeace.scene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.service.bo.NetworkInstance;

/**
 * @author :   SN
 * @date :   2023/12/25 16:56
 * @desc :
 **/
public interface NetworkService extends IService<NetworkEntity> {

    /**
     * 创建网络
     * @param name 网络名称
     * @param cidr 网络地址
     * @param gateway 网关地址
     * @return
     */
    NetworkEntity create(String name, String cidr, String gateway, String sceneId, String pid);

    /**
     * 根据网络id删除网络
     * @param networkId 网络id
     */
    void delete(String networkId);

    /**
     * 获取网络实例
     * @param networkId 网络id
     * @return  网络实例
     */
    NetworkInstance getInstance(String networkId);

    /**
     * 获取网络进度
     * @param sceneId   场景id
     * @return  进度
     */
    Integer getStage(String sceneId);
}
