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
@RequestMapping("/situation/icon/category")
public class SituationIconCategoryController {

    @Autowired
    private SituationIconCategoryService situationIconCategoryService;

    @ApiOperation("获取图标类型")
    @GetMapping("")
    public BaseRsp<List<SituationIconCategory>> getSituationCategoryList(){
        return new BaseRsp<>(situationIconCategoryService.getSituationCategoryList());
    }
    @ApiOperation("修改图标类型")
    @PatchMapping("")
    public BaseRsp<String> updateSituationIcon(@RequestBody @Validated(Update.class) SituationIconCategoryReq situationIconCategoryReq) {
        return new BaseRsp<>(situationIconCategoryService.updateSituationIconCategory(situationIconCategoryReq));
    }
    @ApiOperation("批量删除改图标类型")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteSituationIcon(@RequestBody @Validated SituationIconDelReq SituationIconDelReq) {
        return new BaseRsp<>(situationIconCategoryService.deleteSituationIconCategory(SituationIconDelReq.getIdList()));
    }

    @ApiOperation("新增图标类型")
    @PostMapping()
    public BaseRsp<String> createColorTheme(@RequestBody @Validated(Create.class) SituationIconCategoryReq situationIconCategoryReq) {
        return new BaseRsp<>(situationIconCategoryService.createSituationIcon(situationIconCategoryReq));
    }
}
