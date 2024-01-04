package com.cyberpeace.scene.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.service.HostService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.ConsoleUrlRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import com.cyberpeace.scene.service.bo.res.ImageRes;
import com.cyberpeace.scene.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

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
    private NodeMapper nodeMapper;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init(){
        CP_HOST = ConfigUtil.getContextProperty("cp.host");
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
        String uuid = imageInfo.getItems().get(0).getUuid();
        req.setImage(uuid);
        req.setName(req.getName() + "_" + IDGeneratorUtil.getID());
        CreateInstanceRes res = HttpUtil.post(CP_HOST + CP_INSTANCES, req, CreateInstanceRes.class);
        log.info("end create instance end res : {}", res);
        return res;
    }

    @Override
    public void delete(String instanceId) {
        HttpUtil.rawDelete(CP_HOST + CP_INSTANCES + "/" + instanceId, String.class);
    }

    @Override
    public ConsoleUrlRes console(String instanceId) {
        if (Objects.nonNull(instanceId)){
            return HttpUtil.rawGet(CP_HOST + CP_INSTANCES + "/console" + "/" + instanceId, ConsoleUrlRes.class);
        }
        return null;
    }

    @Override
    public String status(String instanceId) {
        return null;
    }
}
