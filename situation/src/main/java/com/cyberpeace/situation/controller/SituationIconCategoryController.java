package com.cyberpeace.situation.controller;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationIconCategoryReq;
import com.cyberpeace.situation.controller.req.SituationIconDelReq;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import com.cyberpeace.situation.service.SituationIconCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:47
 */
@Api(tags = "图标类型")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/icon/category")
public class SituationIconCategoryController {

    @Autowired
    private SituationIconCategoryService situationIconCategoryService;

    @ApiOperation("获取图标类型")
    @GetMapping("")
    public BaseRsp<List<SituationIconCategory>> getSituationIconCategoryList(@RequestParam(value = "name", required = false) @ApiParam(name = "name", value = "名称")
                                                                                     String name){
        return new BaseRsp<>(situationIconCategoryService.getSituationCategoryList(name));
    }
    @ApiOperation("修改图标类型")
    @PatchMapping("")
    public BaseRsp<String> updateituationIconCategory(@RequestBody @Validated(Update.class) SituationIconCategoryReq situationIconCategoryReq) {
        return new BaseRsp<>(situationIconCategoryService.updateSituationIconCategory(situationIconCategoryReq));
    }
    @ApiOperation("批量删除改图标类型")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteituationIconCategory(@RequestBody @Validated SituationIconDelReq SituationIconDelReq) {
        return new BaseRsp<>(situationIconCategoryService.deleteSituationIconCategory(SituationIconDelReq.getIdList()));
    }

    @ApiOperation("新增图标类型")
    @PostMapping()
    public BaseRsp<String> createituationIconCategory(@RequestBody @Validated(Create.class) SituationIconCategoryReq situationIconCategoryReq) {
        return new BaseRsp<>(situationIconCategoryService.createSituationIcon(situationIconCategoryReq));
    }

    @ApiOperation("获取图标类型详情")
    @GetMapping("/info")
    public BaseRsp<SituationIconCategory> getSituationIconCategory(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "api资源id")
                                                                           String resourceId){
        return new BaseRsp<>(situationIconCategoryService.getSituationIconCategory(resourceId));
    }
}
