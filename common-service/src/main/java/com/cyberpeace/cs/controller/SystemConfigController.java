package com.cyberpeace.cs.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.dao.entity.DictInfo;
import com.cyberpeace.cs.service.DictInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :   SN
 * @date :   2024/1/4 17:51
 * @desc :
 **/
@Api(value = "系统配置", tags = "系统配置")
@RestController
@RequestMapping("/system_config")
public class SystemConfigController {

    @Autowired
    private DictInfoService dictInfoService;

    @RequestMapping("/query")
    public BaseRsp<List<DictInfo>> getConfig(){
        List<DictInfo> systemConfig = dictInfoService.getDictInfo("system_config");
        return new BaseRsp<>(systemConfig);
    }


}
