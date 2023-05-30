package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndexRefReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "靶场测评-评估关联算法")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/index/ref")
@Slf4j
public class EvalIndexRefController {

    @Autowired
    private EvalIndexRefMapper evalIndexRefMapper;

    @Autowired
    private EvalIndexRefService evalIndexRefService;

    @ApiOperation("更新评估关联算法的评分方式")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("")
    public BaseRsp<EvalIndexRef> update(@RequestBody EvalIndexRefReq requestData) {
        EvalIndexRef evalIndexRef = evalIndexRefMapper.selectById(requestData.getResourceId());
        if(null == evalIndexRef){
            log.error("Resource not found! resourceId = [{}]", requestData.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        EvalRecordSpecialistContext specialist = requestData.getSpecialist();
        Integer type = specialist.getType();
        evalIndexRef.setType(type);
        if(type.equals(EvalIndexRef.Type.SPECIALIST.getCode())){
            evalIndexRef.setSpecialistValue(specialist.getValue());
            evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(evalIndexRef.getSpecialistType(), evalIndexRef.getSpecialistAttr(), evalIndexRef.getSpecialistAttrValue(), evalIndexRef.getSpecialistValue()));
            evalIndexRef.setProgress(EvalIndexRef.Progress.END.getCode());
        }
        evalIndexRefMapper.updateById(evalIndexRef);
        return new BaseRsp<>(evalIndexRef);
    }

    @ApiOperation("获取单个评估关联算法及评分方式")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("")
    public BaseRsp<EvalIndexRef> get(@ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String resourceId) {
        EvalIndexRef evalIndexRef = evalIndexRefMapper.selectById(resourceId);
        if(null == evalIndexRef){
            log.error("Resource not found! resourceId = [{}]", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return new BaseRsp<>(evalIndexRef);
    }


}
