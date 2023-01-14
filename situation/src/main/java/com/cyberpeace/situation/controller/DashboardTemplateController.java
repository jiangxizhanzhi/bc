package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.ComponentsDataReq;
import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.req.DashboardTemplateDelReq;
import com.cyberpeace.situation.controller.req.DashboardTemplatePageReq;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.service.DashboardTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author xuxz
 * @Date 10/11/2022 上午11:58
 */

@Api(tags = "态势模板")
@RestController
@RequestMapping("dashboard/template/")
public class DashboardTemplateController {

    @Autowired
    private DashboardTemplateService dashboardTemplateService;

    @ApiOperation("新增态势模板")
    @PostMapping()
    public BaseRsp<String> createDashboardTemplate(@RequestBody @Validated(Create.class) DashBoardTemplateReq dashBoardTemplateReq) {
        return new BaseRsp<>(dashboardTemplateService.createDashboardTemplate(dashBoardTemplateReq));
    }

    @ApiOperation("修改态势模板")
    @PatchMapping("")
    public BaseRsp<String> updateDashboardTemplate(@RequestBody @Validated(Update.class) DashBoardTemplateReq dashBoardTemplateReq) {
        return new BaseRsp<>(dashboardTemplateService.updateDashboardTemplate(dashBoardTemplateReq));
    }

    @ApiOperation("获取改态势模板详情")
    @GetMapping("")
    public BaseRsp<DashboardTemplateRes> getDashboardTemplateInfo(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "资源id")
                                                                          String resourceId,
                                                                  @RequestParam(value = "type", required = false) @ApiParam(name = "type", value = "视角类型")
                                                                          String type) {
        return new BaseRsp<>(dashboardTemplateService.getDashboardTemplateInfo(resourceId, type, Boolean.FALSE));
    }

    @ApiOperation("批量删除改态势模板")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteDashboardTemplate(@RequestBody @Validated DashboardTemplateDelReq dashboardTemplateDelReq) {
        return new BaseRsp<>(dashboardTemplateService.deleteDashboardTemplate(dashboardTemplateDelReq.getDashboardTemplateIdList()));
    }

    @ApiOperation("获取改态势模板列表")
    @GetMapping("list")
    public BaseRsp<ListRsp<DashboardTemplatePageRes>> getDashboardTemplatePage(DashboardTemplatePageReq dashboardTemplatePageReq) {
        return new BaseRsp<>(dashboardTemplateService.getDashboardTemplatePage(dashboardTemplatePageReq));
    }

    @ApiOperation("获取组件类型")
    @GetMapping("type/list")
    public BaseRsp<Map<Integer, String>> getComponentType() {
        return new BaseRsp<>(DashboardComponents.getComponentType());
    }

    @ApiOperation("获取模板等级列表")
    @GetMapping("level/list")
    public BaseRsp<Map<Integer, String>> getDashboardTemplateLevel() {
        return new BaseRsp<>(DashboardTemplate.getDashboardTemplateLevel());
    }

    @ApiOperation("获取echarts数据")
    @GetMapping("option/data")
    public BaseRsp<JSONArray> getEchartsOption(ComponentsDataReq componentsDataReq) {
        return new BaseRsp<>(dashboardTemplateService.getComponentsEchartsData(componentsDataReq));
    }
}
