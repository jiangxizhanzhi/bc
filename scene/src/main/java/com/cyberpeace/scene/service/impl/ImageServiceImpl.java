package com.cyberpeace.scene.service.impl;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.scene.service.CpEnvService;
import com.cyberpeace.scene.service.ImageService;
import com.cyberpeace.scene.service.bo.res.ImageRes;
import com.cyberpeace.scene.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author :   SN
 * @date :   2023/12/27 15:39
 * @desc :
 **/
@Service
public class ImageServiceImpl implements ImageService {

    /**
     * 云平台地址
     */
    private String CP_HOST;

    /**
     * 云平台镜像
     */
    private String CP_IMAGE;

    @Autowired
    private CpEnvService cpEnvService;

    @PostConstruct
    public void init(){
        CP_HOST = cpEnvService.getCpHost();
        CP_IMAGE = ConfigUtil.getContextProperty("cp.image");
    }

    @Override
    public ImageRes getImageInfo(String name) {
        String url = CP_HOST + CP_IMAGE;
        if (StringUtils.isNotBlank(name)){
            url = url + "?name=" + name;
        }
        return HttpUtil.rawGet(url, ImageRes.class);
    }
}
