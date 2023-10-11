package com.cyberpeace.cr.redvsblue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.redvsblue.controller.req.RbTimeReq;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeMapper;
import com.cyberpeace.cr.redvsblue.entity.RbTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "红蓝对抗实例授时")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/time")
public class RbTimeController {


    @Autowired
    private RbTimeMapper rbTimeMapper;

    @ApiOperation("新增/修改")
    @PostMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbTime> create(@RequestBody @Validated RbTimeReq rbTimeReq) {

        LambdaQueryWrapper<RbTime> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RbTime::getRoomId, rbTimeReq.getRoomId());
        RbTime rbTime = rbTimeMapper.selectOne(queryWrapper);

        if(null != rbTime){
            rbTime.setAstronomicalTime(rbTimeReq.getAstronomicalTime());
            rbTime.setRate(rbTimeReq.getRate());
            rbTime.setCombatTime(rbTimeReq.getCombatTime());
            rbTimeMapper.updateById(rbTime);
            return new BaseRsp<>(rbTime);
        }
        rbTime = new RbTime();
        rbTime.setAstronomicalTime(rbTimeReq.getAstronomicalTime());
        rbTime.setRate(rbTimeReq.getRate());
        rbTime.setCombatTime(rbTimeReq.getCombatTime());
        rbTimeMapper.insert(rbTime);
        return new BaseRsp<>(rbTime);
    }

    @ApiOperation("查询")
    @GetMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbTime> get(@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
            value = "resource_id", required = true) String resourceId) {

        LambdaQueryWrapper<RbTime> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RbTime::getRoomId, resourceId);
        RbTime rbTime = rbTimeMapper.selectOne(queryWrapper);
        return new BaseRsp<>(rbTime);
    }

}
