package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndexRefReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
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

/**
 * EVAL 索引引用控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "靶场测评-评估关联算法")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/index/ref")
@Slf4j
public class EvalIndexRefController {

    /**
     * 评估指数裁判Mapper
     *
     * @see EvalIndexRefMapper
     */
    @Autowired
    private EvalIndexRefMapper evalIndexRefMapper;

    /**
     * 评估变量裁判Mapper
     *
     * @see EvalVariableRefMapper
     */
    @Autowired
    private EvalVariableRefMapper evalVariableRefMapper;

    /**
     * 评估指数裁判Service
     *
     * @see EvalIndexRefService
     */
    @Autowired
    private EvalIndexRefService evalIndexRefService;

    /**
     * 更新
     *
     * @param requestData 请求数据
     * @return {@code BaseRsp<String> }
     * @throws
     * @see EvalIndexRefReq
     * @see BaseRsp<String>
     */
    @ApiOperation("更新评估关联算法的评分方式")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("")
    public BaseRsp<String> update(@RequestBody EvalIndexRefReq requestData) {
        Integer type = requestData.getType();
        if(requestData.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())){
            EvalIndexRef evalIndexRef = evalIndexRefMapper.selectById(requestData.getResourceId());
            EvalRecordSpecialistContext specialist = requestData.getSpecialist();
            evalIndexRef.setType(type);
            if(type.equals(EvalIndexRef.Type.SPECIALIST.getCode())){
                evalIndexRef.setSpecialistValue(specialist.getValue());
                evalIndexRef.setScore(specialist.getValue());
            }
            evalIndexRefMapper.updateById(evalIndexRef);

        } else if(requestData.getLevel().equals(EvalIndexRef.Level.VARIABLE.getCode())){
            EvalVariableRef evalVariableRef = evalVariableRefMapper.selectById(requestData.getResourceId());
            EvalRecordSpecialistContext specialist = requestData.getSpecialist();
            if(type.equals(EvalIndexRef.Type.SPECIALIST.getCode())){
                evalVariableRef.setScore(specialist.getValue());
                evalVariableRef.setProgress(EvalIndexRef.Progress.END.getCode());
                evalVariableRef.setType(EvalIndexRef.Type.SPECIALIST.getCode());
            } else if(type.equals(EvalIndexRef.Type.SYSTEM.getCode())){
                evalVariableRef.setDeviceId(requestData.getDeviceId());
                evalVariableRef.setProbeId(requestData.getProbeId());
                evalVariableRef.setType(EvalIndexRef.Type.SYSTEM.getCode());
            } else {
                throw new BusinessException(CrBusinessErrorCode.INVALID_TYPE);
            }
            evalVariableRefMapper.updateById(evalVariableRef);
        }
        else if( requestData.getLevel().equals(EvalIndex.Level.THIRD.getCode())){
            EvalIndexRef evalIndexRef = evalIndexRefMapper.selectById(requestData.getResourceId());
            EvalRecordSpecialistContext specialist = requestData.getSpecialist();
            evalIndexRef.setType(type);
            if(type.equals(EvalIndexRef.Type.SPECIALIST.getCode())){
                // TODO 设置回显 speciallist的其他值
                evalIndexRef.setSpecialistValue(specialist.getValue());
                evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(evalIndexRef.getSpecialistType(), evalIndexRef.getSpecialistAttr(), evalIndexRef.getSpecialistAttrValue(), evalIndexRef.getSpecialistValue()));
                evalIndexRef.setProgress(EvalIndexRef.Progress.END.getCode());
            }
            evalIndexRefMapper.updateById(evalIndexRef);
        } else{
            throw new BusinessException(CrBusinessErrorCode.INVALID_LEVEL, requestData.getLevel());
        }
        return new BaseRsp<>("success");
    }

    /**
     * 获取
     *
     * @param resourceId 资源id
     * @return {@code BaseRsp<EvalIndexRef> }
     * @throws
     * @see String
     * @see BaseRsp<EvalIndexRef>
     */
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
