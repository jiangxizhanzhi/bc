package com.cyberpeace.oj.practice.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryReq;
import com.cyberpeace.oj.practice.controller.req.PracticeDelReq;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.constants.PracticeType;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 暂时将理论基础、实践操作分类整合至同一资源接口
 */
@Api(tags = "练习分类")
@RestController
@RequestMapping("/oj/practice-category")
public class PracticeCategoryController {
    // TODO insert

    @Autowired
    PracticeCategoryService categoryService;

    @ApiOperation("获取练习方向")
    @GetMapping()
    public BaseRsp<PracticeCategory> get(@ApiParam(name = "资源ID", required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
        return new BaseRsp<>(categoryService.get(resourceId));
    }

    @ApiOperation("获取练习方向列表")
    @GetMapping("/list")
    public BaseRsp<ListRsp<PracticeCategory>> getList(PracticeListReq queryParams) {
        return new BaseRsp<>(categoryService.search(queryParams));
    }

    @ApiOperation("新增练习方向")
    @PostMapping()
    public BaseRsp<PracticeCategory> create(@RequestBody PracticeCategoryReq requestData) {
        PracticeCategory object = new PracticeCategory();
        BeanUtils.copyProperties(requestData, object, "resourceId");
        return new BaseRsp<>(categoryService.create(object));
    }

    @ApiOperation("部分更新练习方向")
    @PatchMapping()
    public BaseRsp<PracticeCategory> partialUpdate(@RequestBody @Validated PracticeCategoryReq requestData) {
        PracticeCategory object = categoryService.get(requestData.getResourceId());
        String tableName = object.getPracticeType() == PracticeType.THEORY.ordinal() ? "practice_theory_category" : "practice_exercise_category";
        Integer isSuccess = categoryService.partialUpdate(tableName, object.getResourceId(), requestData.getName());
        if (isSuccess == 1) {
            BeanUtils.copyProperties(requestData, object, "resourceId");
        }
        return new BaseRsp<>(object);
    }

    @ApiOperation("批量删除练习方向")
    @DeleteMapping()
    public BaseRsp<Integer> batchDelete(@RequestBody @Validated PracticeDelReq requestData) {
        return new BaseRsp<>(categoryService.batchDelete(requestData.getResourceIds()));
    }
}
