package com.cyberpeace.scene.service.impl;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.scene.service.ContainerService;
import com.cyberpeace.scene.service.CpEnvService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.ContainerItem;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import com.cyberpeace.scene.service.bo.res.ImageRes;
import com.cyberpeace.scene.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private CpEnvService cpEnvService;

	@PostConstruct
	public void init() {
		String cpHost = cpEnvService.getCpHost();
		String cpContainer = ConfigUtil.getContextProperty("cp.containers");
		CONTAINER_URL = cpHost + cpContainer;
	}

    @Override
    public CreateContainersRes create(CreateContainerReq req) {
		log.info("create container start req:{}", req);
		if (Objects.isNull(req)) {
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
		}
		ImageRes imageInfo = imageService.getImageInfo(req.getName());
		if (Objects.isNull(imageInfo)) {
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
		}
		if (imageInfo.getTotal() == 0 || CollectionUtils.isEmpty(imageInfo.getItems())) {
			throw new BusinessException(ErrorCode.IMAGE_NOT_FOUND, req.getName());
		}
        String uuid = imageInfo.getItems().get(0).getUuid();
        req.setImage(uuid);
        req.setName(IDGeneratorUtil.getID());
        CreateContainersRes res = HttpUtil.post(CONTAINER_URL, req, CreateContainersRes.class);
        log.info("create container end res:{}", res);
        return res;
    }

	@Override
	public void delete(String containerId) {
		HttpUtil.delete(CONTAINER_URL + "/" + containerId, new JSONObject(), String.class);
	}

    @Override
    public String console(String containerId) {
        if (Objects.nonNull(containerId)){
            return HttpUtil.rawGet(CONTAINER_URL + "/console" + "/" + containerId, null);
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
