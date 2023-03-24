package com.cyberpeace.situation.controller;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.req.SituationPlotPixelPageReq;
import com.cyberpeace.situation.controller.req.SituationPlotPixelReq;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;
import com.cyberpeace.situation.service.SituationPlotPixelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author xuxz
 * @Date 17/12/2022 下午5:01
 */

@Api(tags = "绘标图元管理")
@RestController
@CrossOrigin("*")
@RequiresAuthentication
@RequestMapping("/situation/plot/pixel")
public class SituationPlotPixelController {

    @Autowired
    private SituationPlotPixelService situationPlotPixelService;

    @ApiOperation("新增绘标图元")
    @PostMapping()
    public BaseRsp<String> createColorTheme(@RequestBody @Validated(Create.class) SituationPlotPixelReq SituationPlotPixelReq) {
        return new BaseRsp<>(situationPlotPixelService.createSituationPlotPixel(SituationPlotPixelReq));
    }

    @ApiOperation("修改绘标图元")
    @PatchMapping("")
    public BaseRsp<String> updateSituationPlotPixel(@RequestBody @Validated(Update.class) SituationPlotPixelReq SituationPlotPixelReq) {
        return new BaseRsp<>(situationPlotPixelService.updateSituationPlotPixel(SituationPlotPixelReq));
    }

    @ApiOperation("获取改绘标图元详情")
    @GetMapping("")
    public BaseRsp<SituationPlotPixel> getSituationPlotPixelInfo(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "api资源id")
                                                                         String resourceId) {
        return new BaseRsp<>(situationPlotPixelService.getSituationPlotPixelInfo(resourceId));
    }

    @ApiOperation("批量删除改绘标图元")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteSituationPlotPixel(@RequestBody @Validated SituationIconDelReq situationIconDelReq) {
        return new BaseRsp<>(situationPlotPixelService.deleteSituationPlotPixel(situationIconDelReq.getIdList()));
    }

    @ApiOperation("绘标图元分页查询")
    @GetMapping("list")
    public BaseRsp<ListRsp<SituationPlotPixel>> getSituationPlotPixelPage(SituationPlotPixelPageReq SituationPlotPixelPageReq) {
        return new BaseRsp<>(situationPlotPixelService.getSituationPlotPixelPage(SituationPlotPixelPageReq));
    }
}
