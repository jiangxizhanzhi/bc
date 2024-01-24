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
import com.cyberpeace.scene.service.CpEnvService;
import com.cyberpeace.scene.service.HostService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.ConsoleUrlRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import com.cyberpeace.scene.service.bo.res.ImageRes;
import com.cyberpeace.scene.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/27 10:44
 * @desc :
 **/
@Slf4j
@Service
public class HostServiceImpl implements HostService {

    /**
     * 云平台地址
     */
    private String CP_HOST;

    /**
     * 云平台实例地址
     */
    private String CP_INSTANCES;

    @Autowired
    private ImageService imageService;

	@Autowired
	private CpEnvService cpEnvService;

	@PostConstruct
	public void init() {
		CP_HOST = cpEnvService.getCpHost();
		CP_INSTANCES = ConfigUtil.getContextProperty("cp.instances");
	}

    @Override
    public CreateInstanceRes create(CreateInstanceReq req) {
        log.info("start create instance req : {}", req);
        if (Objects.isNull(req)){
            throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
        }
        ImageRes imageInfo = imageService.getImageInfo(req.getName());
        if (Objects.isNull(imageInfo)){
            throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
        }
        if (imageInfo.getTotal() == 0 || CollectionUtils.isEmpty(imageInfo.getItems())) {
            throw new BusinessException(ErrorCode.IMAGE_NOT_FOUND, req.getName());
        }
        String uuid = imageInfo.getItems().get(0).getUuid();
        req.setImage(uuid);
        req.setName(req.getName() + "_" + IDGeneratorUtil.getID());
        log.info("creating vm instance, max wait time 10min, req: {}", req);
        CreateInstanceRes res = HttpUtil.post(CP_HOST + CP_INSTANCES, req, CreateInstanceRes.class);
        log.info("end create instance end res : {}", res);
        return res;
    }

	@Override
	public void delete(String instanceId) {
		HttpUtil.delete(CP_HOST + CP_INSTANCES + "/" + instanceId, new JSONObject(), String.class);
	}

    @Override
    public ConsoleUrlRes console(String instanceId) {
        if (Objects.nonNull(instanceId)){
            return HttpUtil.rawGet(CP_HOST + CP_INSTANCES + "/console" + "/" + instanceId, ConsoleUrlRes.class);
        }
        return null;
    }

    @Override
    public CreateInstanceRes getInstance(String instanceId) {
        if (Objects.nonNull(instanceId)){
            return HttpUtil.rawGet(CP_HOST + CP_INSTANCES + "/" + instanceId, CreateInstanceRes.class);
        }
        return null;
    }

    @Override
    public String getStatus(String instanceId) {
        CreateInstanceRes instance = getInstance(instanceId);
        if (Objects.nonNull(instance)){
            return instance.getVmState();
        }
        return null;
    }
}
