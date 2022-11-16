package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.GisPageReq;
import com.cyberpeace.situation.controller.res.GisPageRes;
import com.cyberpeace.situation.service.SituationGisService;
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
@Api(tags = "GIS资源")
@RestController
@RequestMapping("/situation/gis")
public class SituationGisController {

    @Autowired
    private SituationGisService situationGisService;

    @ApiOperation("获取GIS资源")
    @GetMapping
    public BaseRsp<ListRsp<GisPageRes>> getGisPage(GisPageReq gisPageReq) {
        return new BaseRsp<>(situationGisService.getGisPage(gisPageReq));
    }
}
