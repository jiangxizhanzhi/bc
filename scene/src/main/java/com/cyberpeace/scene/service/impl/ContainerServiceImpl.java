package com.cyberpeace.scene.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.service.ContainerService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import com.cyberpeace.scene.service.bo.res.ImageRes;
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
    private String CP_HOST;

    /**
     *
     */
    private String CP_CONTAINER;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init(){
        CP_HOST = ConfigUtil.getContextProperty("cp.host");
        CP_CONTAINER = ConfigUtil.getContextProperty("cp.containers");
    }

    @Override
    public CreateContainersRes create(CreateContainerReq req) {
        log.info("create container start req:{}", req);
        if (Objects.isNull(req)){
            throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
        }
        ImageRes imageInfo = imageService.getImageInfo(req.getName());
        if (Objects.isNull(imageInfo)){
            throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
        }
        String uuid = imageInfo.getItems().get(0).getUuid();
        req.setImage(uuid);
        req.setName(IDGeneratorUtil.getID());
        CreateContainersRes res = HttpUtil.post(CP_HOST + CP_CONTAINER, req, CreateContainersRes.class);
        log.info("create container end res:{}", req);
        return res;
    }

    @Override
    public void delete(String containerId) {
        HttpUtil.rawDelete(CP_HOST + CP_CONTAINER + "/" + containerId, String.class);
    }
}
