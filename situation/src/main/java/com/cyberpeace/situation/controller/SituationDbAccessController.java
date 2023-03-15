package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.controller.req.SituationDbAccessAddReq;
import com.cyberpeace.situation.controller.req.SituationDbAccessReq;
import com.cyberpeace.situation.dao.entity.SituationDbAccess;
import com.cyberpeace.situation.dao.mapper.SituationDbAccessMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@Api(tags = "数据库访问")
@RestController
@RequiresAuthentication
@RequestMapping("/db/access")
public class SituationDbAccessController {

    @Autowired
    private SituationDbAccessMapper situationDbAccessAuthorityMapper;


    @ApiOperation("获取数据库访问列表")
    @GetMapping("/list")
    public BaseRsp<List<SituationDbAccess>> getDbAccess(@ApiParam @Validated SituationDbAccessReq req) {
        LambdaQueryWrapper<SituationDbAccess> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(req.getTableName())) {
            queryWrapper.like(SituationDbAccess::getTableName, req.getTableName());
        }
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        Date startTimeDate;
        Date endTimeDate;
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            endTimeDate = TimeUtil.toDate2(endTime);
            startTimeDate = TimeUtil.toDate2(startTime);
            if (endTimeDate.before(startTimeDate)) {
                throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
            }
            queryWrapper.ge(SituationDbAccess::getCreateTime, startTimeDate);
            queryWrapper.le(SituationDbAccess::getCreateTime, endTimeDate);
        }
        if (StringUtils.isNotEmpty(req.getOperationType())) {
            queryWrapper.eq(SituationDbAccess::getOperationType, req.getOperationType());
        }
        List<SituationDbAccess> situationDbAccesses = situationDbAccessAuthorityMapper.selectList(queryWrapper);
        return new BaseRsp<>(situationDbAccesses);
    }

    @ApiOperation("新增数据库访问")
    @GetMapping("/")
    public BaseRsp<SituationDbAccess> addDbAccess(@ApiParam @Validated SituationDbAccessAddReq req) {
        SituationDbAccess situationDbAccess = new SituationDbAccess();
        BeanUtils.copyProperties(req, situationDbAccess);
        situationDbAccess.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        int insert = situationDbAccessAuthorityMapper.insert(situationDbAccess);
        return new BaseRsp<>(situationDbAccess);
    }
}
