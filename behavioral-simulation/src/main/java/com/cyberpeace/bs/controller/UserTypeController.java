package com.cyberpeace.bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.user.UserTypeAddBo;
import com.cyberpeace.bs.service.bo.user.UserTypeBo;
import com.cyberpeace.bs.service.bo.user.UserTypeModifyBo;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户类型增删改查相关接口
 *
 * @author SN
 * @date 2023/05/06
 */
@Api(tags = "用户行为模拟-用户类型管理")
@RestController
@RequestMapping("/bs/user_type")
public class UserTypeController {

    @Autowired
    UserTypeService userTypeService;

    /**
     * 用户类型分页
     *
     * @param pageReq 分页请求体
     * @return {@code BaseRsp<ListRsp<UserTypeBo>>}
     */
    @ApiOperation("分页列表")
    @GetMapping("/page")
    public BaseRsp<ListRsp<UserTypeBo>> pageUserType(PageReqBase pageReq) {
        Page page = pageReq.toPageParam();
        ListRsp<UserTypeBo> ret = userTypeService.pageTypes(page, pageReq.getPattern());
        return new BaseRsp<>(ret);
    }

    /**
     * 创建用户类型
     *
     * @param addBo 新增请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("新增")
    @PostMapping("/")
    public BaseRsp create(@RequestBody @Validated UserTypeAddBo addBo) {
        userTypeService.create(addBo);
        return new BaseRsp();
    }

    /**
     * 更新用户类型
     *
     * @param modifyBo 修改请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("更新")
    @PutMapping("/")
    public BaseRsp update(@RequestBody @Validated UserTypeModifyBo modifyBo) {
        userTypeService.update(modifyBo);
        return new BaseRsp();
    }

    /**
     * 删除用户类型
     *
     * @param deleteReq 删除请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("删除")
    @DeleteMapping("/")
    public BaseRsp delete(@RequestBody @Validated BatchDeleteReq deleteReq) {
        userTypeService.batchDelete(deleteReq.getIds());
        return new BaseRsp();
    }
}
