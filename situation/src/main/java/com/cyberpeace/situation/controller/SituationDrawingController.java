package com.cyberpeace.situation.controller;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.SituationDrawing;
import com.cyberpeace.situation.dao.entity.SituationDrawingCategory;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;
import com.cyberpeace.situation.service.SituationDrawingService;
import com.cyberpeace.situation.service.SituationPlotPixelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xuxz
 * @Date 4/1/2023 上午11:11
 */
@Api(tags = "图形管理-图形资源管理")
@RestController
@Slf4j
@RequestMapping("/situation/drawing")
public class SituationDrawingController {

    @Autowired
    private SituationDrawingService situationDrawingService;

    @ApiOperation("新增图形资源")
    @PostMapping()
    public BaseRsp<String> createSituationDrawing(@RequestBody @Validated(Create.class) SituationDrawingReq situationDrawingReq) {
        return new BaseRsp<>(situationDrawingService.createSituationDrawing(situationDrawingReq));
    }

    @ApiOperation("修改图形资源")
    @PatchMapping("")
    public BaseRsp<String> updateSituationDrawing(@RequestBody @Validated(Update.class) SituationDrawingReq situationDrawingReq) {
        return new BaseRsp<>(situationDrawingService.updateSituationDrawing(situationDrawingReq));
    }

    @ApiOperation("获取改图形资源详情")
    @GetMapping("")
    public BaseRsp<SituationDrawing> getSituationDrawingInfo(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "资源id")
                                                                         String resourceId) {
        return new BaseRsp<>(situationDrawingService.getSituationDrawingInfo(resourceId));
    }

    @ApiOperation("批量删除改图形资源")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteSituationDrawing(@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
        return new BaseRsp<>(situationDrawingService.deleteSituationDrawing(situationIconDelReq.getIdList()));
    }

    @ApiOperation("图形资源分页查询")
    @GetMapping("/list")
    public BaseRsp<ListRsp<SituationDrawing>> getSituationDrawingPage(SituationDrawingPageReq situationDrawingPageReq) {
        return new BaseRsp<>(situationDrawingService.getSituationDrawingPage(situationDrawingPageReq));
    }

    @ApiOperation("图形类型查询")
    @GetMapping("/category")
    public BaseRsp<List<SituationDrawingCategory>> getSituationDrawingCategory(){
        return new BaseRsp<>(situationDrawingService.getSituationCategory());
    }
}
