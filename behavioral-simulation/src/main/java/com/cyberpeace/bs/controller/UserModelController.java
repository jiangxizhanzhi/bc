package com.cyberpeace.bs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.controller.req.PageReqWithType;
import com.cyberpeace.bs.service.UserModelService;
import com.cyberpeace.bs.service.bo.behavior.BehaviorWithVariable;
import com.cyberpeace.bs.service.bo.model.*;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户模型相关接口
 * 用于用户模型管理页面
 *
 * @author SN
 * @date 2023/05/06
 */
@Api(tags = "行为模拟-网络用户行为模型")
@RestController
@RequestMapping("/bs/user_model")
// @RequiresAuthentication
public class UserModelController {

    @Autowired
    private UserModelService userModelService;

    /**
     * 获取用户模型分页列表
     *
     * @param pageReq 分页请求体
     * @return {@code BaseRsp<ListRsp<UserModelBo>>}
     */
    @ApiOperation("获取用户行为模型分页")
    @GetMapping("/list")
    public BaseRsp<ListRsp<UserModelBo>> getUserModelPage(PageReqWithType pageReq) {
        Page page = pageReq.toPageParam();
        ListRsp<UserModelBo> res = userModelService.listUserModel(page, pageReq.getType(),
                pageReq.getPattern(), pageReq.getAccess_os());
        return new BaseRsp<>(res);
    }

    /**
     * 获取单个用户模型详情
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<UserModelDetail>}
     */
    @ApiOperation("查询详情")
    @GetMapping("/")
    public BaseRsp<UserModelDetail> get(
            @ApiParam(name = "resource_id", value = "查询编号", required = true) String resource_id) {
        UserModelDetail detail = userModelService.getDetail(resource_id);
        return new BaseRsp<>(detail);
    }


    /**
     * 创建用户模型接口
     *
     * @param addBo 新增请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("新增")
    @PostMapping("/")
    public BaseRsp create(@RequestBody @Validated UserModelAddBo addBo) {
        addBo.validate();
        userModelService.create(addBo);
        return new BaseRsp();
    }

    /**
     * 更新用户模型接口
     *
     * @param modifyBo 修改请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("修改")
    @PutMapping("/")
    public BaseRsp update(@RequestBody @Validated UserModelModifyBo modifyBo) {
        modifyBo.validate();
        userModelService.update(modifyBo);
        return new BaseRsp();
    }

    /**
     * 批量删除用户模型
     *
     * @param deleteReq 删除请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("删除")
    @DeleteMapping("/")
    public BaseRsp batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
        userModelService.delete(deleteReq.getIds());
        return new BaseRsp();
    }

    /**
     * 获取图表数据接口
     * 包括活跃度曲线图和访问习惯饼图
     * 根据用户前端输入实时输出
     *
     * @param chartInput 图输入
     * @return {@code BaseRsp<ChartResult>}
     */
    @ApiOperation("获取活跃度曲线图和访问习惯饼图数据")
    @PostMapping("/chart")
    public BaseRsp<ChartResult> getChartData(@Validated @RequestBody ChartInput chartInput) {
        chartInput.validate();
        ChartResult res = userModelService.createChart(chartInput);
        return new BaseRsp<>(res);
    }

    /**
     * 获取行为与变量
     * 获取单个用户模型包含的行为及行为变量
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<List<BehaviorWithVariable>>}
     */
    @ApiOperation("获取单个用户模型包含的行为及行为变量")
    @GetMapping("/behavior")
    public BaseRsp<List<BehaviorWithVariable>> getBehaviorsWithVariables(
            @ApiParam(value = "用户模型id", name = "resource_id") String resource_id) {
        List<BehaviorWithVariable> res = userModelService.getBehaviorsWithVariables(resource_id);
        return new BaseRsp(res);
    }

    /**
     * 批量获取多个用户模型包含的行为及行为变量
     *
     * @param req 请求体
     * @return {@code BaseRsp<List<UserModelWithVariable>>}
     */
    @ApiOperation("批量获取多个用户模型包含的行为及行为变量")
    @PostMapping("/behavior")
    public BaseRsp<List<UserModelWithVariable>>
    getBehaviorsBatchWithVariable(@Validated @RequestBody IdListReq req) {
        List<UserModelWithVariable> res = userModelService
                .getBehaviorsBatchWithVariables(req.getIds());
        return new BaseRsp<>(res);
    }
}
