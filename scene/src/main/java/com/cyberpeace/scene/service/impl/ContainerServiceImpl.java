package com.cyberpeace.scene.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.service.ContainerService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.ContainerItem;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.res.ConsoleUrlRes;
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
     * 容器url
     */
    private String CONTAINER_URL;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init(){
        String cpHost = ConfigUtil.getContextProperty("cp.host");
        String cpContainer = ConfigUtil.getContextProperty("cp.containers");
        CONTAINER_URL = cpHost + cpContainer;
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
        CreateContainersRes res = HttpUtil.post(CONTAINER_URL, req, CreateContainersRes.class);
        log.info("create container end res:{}", req);
        return res;
    }

    @Override
    public void delete(String containerId) {
        HttpUtil.rawDelete(CONTAINER_URL + "/" + containerId, String.class);
    }

    @Override
    public ConsoleUrlRes console(String containerId) {
        if (Objects.nonNull(containerId)){
            return HttpUtil.rawGet(CONTAINER_URL + "/console" + "/" + containerId, ConsoleUrlRes.class);
        }
        return null;
    }

    @Override
    public ContainerItem getInstance(String containerId) {
        if (Objects.nonNull(containerId)){
            return HttpUtil.rawGet(CONTAINER_URL + "/" + containerId, ContainerItem.class);
        }
        return null;
    }

    @Override
    public String getStatus(String containerId) {
        ContainerItem instance = getInstance(containerId);
        if (!Objects.isNull(instance)){
            return instance.getStatus();
        }
        return null;
    }
}
