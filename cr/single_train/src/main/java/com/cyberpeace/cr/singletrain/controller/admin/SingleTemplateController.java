package com.cyberpeace.cr.singletrain.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReqRel;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "单兵训练模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/admin/single_train/template")
public class SingleTemplateController {
    @Autowired
    private SingleTemplateService singleTemplateService;

    @Autowired
    private BusinessTemplateService businessTemplateService;

    @GetMapping
    @ApiOperation("单兵训练模板获取")
    public BaseRsp<SingleTemplateBo> get(@RequestParam("resource_id") String businessTemplateId) {
        return new BaseRsp<>(singleTemplateService.get(businessTemplateId));
    }

    @PostMapping
    @ApiOperation("单兵训练模板创建")
    public BaseRsp<SingleTemplateBo> create(@RequestBody SingleTrainTemplateReqRel requestDataRel) {
        // 入参`event_data` -> `base_info`
        SingleTrainTemplateReq requestData = new SingleTrainTemplateReq();

        BaseInfo baseInfo = new BaseInfo();
        BeanUtils.copyProperties(requestDataRel.getEventData(), baseInfo);
        requestData.setBaseInfo(baseInfo);
        requestData.setMissionsScheme(requestDataRel.getMissionsScheme());

        requestData.getBaseInfo().setType(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
        // TODO 待addTemplate返回值 -> singleTemplateService.get(trainBusinessId)
        String trainBusinessId = businessTemplateService.addTemplate(requestData);
        return new BaseRsp<>();
    }

    @PatchMapping
    @ApiOperation("单兵训练模板更新")
    public BaseRsp<SingleTemplateBo> update(@RequestParam("resource_id") String resourceId, @RequestBody SingleTrainTemplateReqRel requestDataRel) {
        SingleTrainTemplateReq requestData = new SingleTrainTemplateReq();

        BaseInfo baseInfo = new BaseInfo();
        BeanUtils.copyProperties(requestDataRel.getEventData(), baseInfo);
        requestData.setBaseInfo(baseInfo);
        requestData.setMissionsScheme(requestDataRel.getMissionsScheme());

        return new BaseRsp<>(singleTemplateService.update(resourceId, requestData.getBaseInfo(), requestData.getMissionsScheme()));
    }
}
