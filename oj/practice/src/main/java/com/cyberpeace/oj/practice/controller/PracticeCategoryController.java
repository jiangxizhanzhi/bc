package com.cyberpeace.oj.practice.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryReq;
import com.cyberpeace.oj.practice.controller.req.PracticeDelReq;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "练习分类")
@RestController
@RequestMapping("/oj/practice-category")
public class PracticeCategoryController {

    @Autowired
    PracticeCategoryService categoryService;

    @ApiOperation("获取练习方向详情")
    @GetMapping()
    public BaseRsp<PracticeCategory> get(@ApiParam(name = "资源ID", required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
        return new BaseRsp<>(categoryService.getById(resourceId));
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
        BeanUtils.copyProperties(requestData, object);
        categoryService.save(object);
        return new BaseRsp<>(object);
    }

    @ApiOperation("更新练习方向")
    @PatchMapping()
    public BaseRsp<PracticeCategory> partialUpdate(@RequestBody @Validated PracticeCategoryReq requestData) {
        PracticeCategory object = new PracticeCategory();
        BeanUtils.copyProperties(requestData, object);
        categoryService.updateById(object);

        object = categoryService.getById(object);
        return new BaseRsp<>(object);
    }

    @ApiOperation("批量删除练习方向")
    @DeleteMapping()
    public BaseRsp<Boolean> batchDelete(@RequestBody @Validated PracticeDelReq requestData) {
        return new BaseRsp<>(categoryService.batchDelete(requestData.getResourceIds()) >= 1);
    }
}
