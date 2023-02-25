package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import com.cyberpeace.situation.service.SituationCommonComponentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.cyberpeace.situation.dao.entity.SituationCommonComponents.getCommonComponentsType;

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
    public BaseRsp<ListRsp<SituationCommonComponentsPageRes>> getComponentsPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
        return new BaseRsp<>(situationCommonComponentsService.getComponentsPage(situationCommonComponentsPageReq));
    }

    @ApiOperation("获取组件详情")
    @GetMapping("/info")
    public BaseRsp<SituationCommonComponents> getComponentsInfo(@RequestParam("resource_id")
                                                                @ApiParam(required = true, name = "resource_id", value = "api资源id") String resourceId) {
        return new BaseRsp<>(situationCommonComponentsService.getComponentsInfo(resourceId));
    }

    @ApiOperation("新增组件信息")
    @PostMapping("")
    public BaseRsp<String> createComponents(@RequestBody @Validated(Create.class) CommonComponentsReq commonComponentsReq) {
        return new BaseRsp<>(situationCommonComponentsService.createComponents(commonComponentsReq));
    }

    @ApiOperation("修改组件信息")
    @PatchMapping("")
    public BaseRsp<String> updateComponents(@RequestBody @Validated(Update.class) CommonComponentsReq commonComponentsReq) {
        return new BaseRsp<>(situationCommonComponentsService.updateComponents(commonComponentsReq));
    }

    @ApiOperation("批量删除组件")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteComponents(@RequestBody @Validated ColorThemeDelReq idList) {
        return new BaseRsp<>(situationCommonComponentsService.deleteComponents(idList.getIdList()));
    }

    @ApiOperation("获取组件类型列表")
    @GetMapping("/type/list")
    public BaseRsp<Map<Integer, String>> getComponentsType(){
        return new BaseRsp<>(getCommonComponentsType());
    }

    @ApiOperation("获取海康rtsp直播流")
    @GetMapping("/hik/rtsp/channels")
    public BaseRsp<JSONObject> getHikVisionRtsp(HikVisionReq hikVisionReq){
        return new BaseRsp<>(situationCommonComponentsService.getHikVisionRtsp(hikVisionReq));
    }

    @ApiOperation("获取海康flv直播流")
    @GetMapping("/hik/flv/channels")
    public BaseRsp<JSONObject> getHikVisionFlv(@RequestParam("rtspUrl")String rtspUrl){
        return new BaseRsp<>(situationCommonComponentsService.getHikVisionFLv(rtspUrl));
    }
}
