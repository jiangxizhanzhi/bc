package com.cyberpeace.situation.controller;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIcon;
import com.cyberpeace.situation.service.SituationIconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午10:34
 */
@Api(tags = "图标管理")
@RestController
@RequestMapping("/situation/icon")
public class SituationIconController {

    @Autowired
    private SituationIconService situationIconService;

    @ApiOperation("新增图标")
    @PostMapping()
    public BaseRsp<String> createSituationIcon(@RequestBody @Validated(Create.class) SituationIconReq situationIconReq) {
        return new BaseRsp<>(situationIconService.createSituationIcon(situationIconReq));
    }

    @ApiOperation("修改图标")
    @PatchMapping("")
    public BaseRsp<String> updateSituationIcon(@RequestBody @Validated(Update.class) SituationIconReq SituationIconReq) {
        return new BaseRsp<>(situationIconService.updateSituationIcon(SituationIconReq));
    }

    @ApiOperation("获取改图标详情")
    @GetMapping("")
    public BaseRsp<SituationIconRes> getSituationIconInfo(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "api资源id")
                                                                  String resourceId) {
        return new BaseRsp<>(situationIconService.getSituationIconInfo(resourceId));
    }

    @ApiOperation("批量删除改图标")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteSituationIcon(@RequestBody @Validated SituationIconDelReq SituationIconDelReq) {
        return new BaseRsp<>(situationIconService.deleteSituationIcon(SituationIconDelReq.getIdList()));
    }

    @ApiOperation("图标分页查询")
    @GetMapping("list")
    public BaseRsp<ListRsp<SituationIconRes>> getSituationIconPage(SituationIconPageReq situationIconPageReq) {
        return new BaseRsp<>(situationIconService.getSituationIconPage(situationIconPageReq));
    }
}
