package com.cyberpeace.cs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.annoations.AdminRequired;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.controller.req.DictInfoReq;
import com.cyberpeace.cs.dao.entity.DictInfo;
import com.cyberpeace.cs.service.DictInfoService;

import io.swagger.annotations.Api;

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

    @GetMapping("/query")
    public BaseRsp<List<DictInfo>> getConfig(){
        List<DictInfo> systemConfig = dictInfoService.getDictInfo(DictInfo.KeyModel.SYSTEM_CONFIG.getKey());
        return new BaseRsp<>(systemConfig);
    }

    @PostMapping("/set")
	@AdminRequired
    public BaseRsp<Void> setSystemConfig(@RequestBody List<DictInfoReq> dictInfoReqList) {
        // 系统内置名称、logo、版权信息

        List<DictInfo> dictInfos = StreamApiUtils.map(dictInfoReqList,
                dictInfoReq -> {
                    DictInfo dictInfo = new DictInfo();
                    dictInfo.setId(dictInfoReq.getId());
                    dictInfo.setItemValue(dictInfoReq.getItemValue());
                    dictInfo.setParentKey(dictInfoReq.getParentKey());
                    dictInfo.setKey(dictInfoReq.getKey());
                    return dictInfo;
                }
        );

        dictInfoService.updateBatchById(dictInfos);

        return new BaseRsp<>();
    }


}
