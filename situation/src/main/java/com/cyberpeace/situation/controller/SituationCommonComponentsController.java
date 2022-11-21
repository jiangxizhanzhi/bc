package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.service.SituationCommonComponentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:08
 */
@Api(tags = "控件组件资源")
@RestController
@RequestMapping("/situation/components")
public class SituationCommonComponentsController {

    @Autowired
    private SituationCommonComponentsService situationCommonComponentsService;

    @ApiOperation("获取控件组件资源列表")
    @GetMapping
    public BaseRsp<ListRsp<SituationCommonComponentsPageRes>> getGisPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
        return new BaseRsp<>(situationCommonComponentsService.getGisPage(situationCommonComponentsPageReq));
    }
}
