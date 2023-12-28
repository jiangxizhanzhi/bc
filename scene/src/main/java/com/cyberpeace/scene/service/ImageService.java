package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.ImageInfo;

/**
 * @author :   SN
 * @date :   2023/12/27 15:39
 * @desc :
 **/
public interface ImageService {

    /**
     * 根据名称获取镜像信息
     * @param name  镜像名称
     * @return  镜像信息
     */
    ImageInfo getImageInfo(String name);
}
