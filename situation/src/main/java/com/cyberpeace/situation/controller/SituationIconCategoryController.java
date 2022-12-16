package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import com.cyberpeace.situation.service.SituationIconCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
