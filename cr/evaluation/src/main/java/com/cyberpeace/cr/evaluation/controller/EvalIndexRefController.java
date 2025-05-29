package com.cyberpeace.cr.evaluation.controller;

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
 * @date 2024/11/07
 */
/**
 * 这段代码定义了一个名为 `EvalIndexRefController` 的 Spring Boot 控制器类，用于处理与评估索引引用相关的 HTTP 请求。以下是代码的简要分析： 
 * ### 包和导入 
 * - 该类位于 `com.cyberpeace.cr.evaluation.controller` 包中。 
 * - 导入了多个工具类和框架（如 Swagger、Apache Shiro、Spring 框架）以及自定义的实体类和服务。 
 * ### 类注解 
 * - `@RestController`：声明这是一个 RESTful 控制器。 
 * - `@Slf4j`：使用 Lombok 自动生成日志记录器。 
 * - `@RequestMapping("/cr/eval/index/ref")`：设置基础 URL 路径为 `/cr/eval/index/ref`。 
 * - `@RequiresAuthentication` 和 `@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)`：确保请求需要认证和特定权限。 
 * ### 成员变量 
 * - `EvalIndexRefMapper` 和 `EvalVariableRefMapper`：用于数据库操作的 MyBatis 映射器。 
 * - `EvalIndexRefService`：用于处理业务逻辑的服务类。 
 * ### 方法 
 * 1. **`update` 方法** 
 * - **HTTP 方法**：`PATCH` 
 * - **功能**：根据请求数据更新评估关联算法的评分方式。 
 * - **参数**：`EvalIndexRefReq` 类型的请求体。 
 * - **返回**：`BaseRsp<String>`，表示操作结果。 
 * - **处理逻辑**： 
 * - 根据请求中的 `level` 和 `type` 更新不同类型的实体（`EvalIndexRef` 或 `EvalVariableRef`）。 
 * - 处理不同类型的操作并调用相应的数据库更新方法。 
 * - 抛出 `BusinessException` 异常处理无效的 `level` 或 `type`。 
 * 2. **`get` 方法** 
 * - **HTTP 方法**：`GET` 
 * - **功能**：根据资源 ID 获取评估关联算法及其评分方式。 
 * - **参数**：`resource_id`，资源 ID。 
 * - **返回**：`BaseRsp<EvalIndexRef>`，包含查询结果或错误信息。 
 * - **处理逻辑**： 
 * - 从数据库中查找对应的 `EvalIndexRef` 实体。 
 * - 如果找不到资源，则记录错误日志并抛出 `BusinessException`。 
 * ### 总结 
 * 这个控制器主要用于处理与评估索引引用相关的 CRUD 操作，并且确保这些操作需要认证和特定权限。它通过调用服务层和映射器来完成具体的业务逻辑和数据访问。 
**/
@Api(tags = "靶场测评-评估关联算法")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/index/ref")
@Slf4j
public class EvalIndexRefController {

    /**
     * eval 索引 ref 映射器
     */
    @Autowired
    private EvalIndexRefMapper evalIndexRefMapper;

    /**
     * eval 变量 ref 映射器
     */
    @Autowired
    private EvalVariableRefMapper evalVariableRefMapper;

    /**
     * 评估指数裁判Service
     */
    @Autowired
    private EvalIndexRefService evalIndexRefService;

    /**
     * 更新
     *
     * @param requestData 请求数据
     * @return {@code BaseRsp<String> }
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

