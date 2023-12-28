package com.cyberpeace.scene.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.scene.service.ContainerService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.ImageInfo;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author :   SN
 * @date :   2023/12/21 19:55
 * @desc :
 **/
@Slf4j
@Service
public class ContainerServiceImpl implements ContainerService {

    /**
     * 云平台地址
     */
    private String CP_IMAGE;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init(){
        CP_IMAGE = ConfigUtil.getContextProperty("cp.host") + ConfigUtil.getContextProperty("cp.image");
    }

    @Override
    public void create(CreateContainerReq req) {
        if (Objects.isNull(req)){
            throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
        }
        //todo 待与云品台统一返回结果
//        ImageInfo imageInfo = imageService.getImageInfo(req.getName());
//        if (Objects.isNull(imageInfo)){
//            throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
//        }
//        req.setImage(imageInfo.getUuid());
        HttpUtil.post(CP_IMAGE, req, null);
    }
}
