package com.cyberpeace.oj.practice.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeEvaluateReq;
import com.cyberpeace.oj.practice.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.practice.controller.req.ResourcePublicReq;
import com.cyberpeace.oj.practice.controller.req.ResourceShareReq;
import com.cyberpeace.oj.practice.dao.mapper.PracticeMixinMapper;
import com.cyberpeace.oj.practice.service.PracticeService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

/**
 * 提供练习公共功能接口
 *
 * @param <Entity> 实体类
 */
@RestController
@RequiresAuthentication
public abstract class PracticeMixinController<Entity> {

    @Autowired
    protected PracticeMixinMapper practiceMixinMapper;

    @Autowired
    protected PracticeService practiceService;

    protected Class<Entity> entityClass = this.currentModelCls();

    @SuppressWarnings("unchecked")
    protected Class<Entity> currentModelCls() {
        return (Class<Entity>) ReflectionKit.getSuperClassGenericType(this.getClass(), 0);
    }

    public Class<Entity> getEntityCls() {
        return this.entityClass;
    }

    /**
     * 获取实体类关联资源数据表名称, 必须拥有`com.baomidou.mybatisplus.annotation.TableName`注解
     *
     * @return table name string
     */
    private String getTableName() {
        TableName annotation = getEntityCls().getAnnotation(TableName.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            throw new NullPointerException("Get DB TABLE NAME is failed, entity class need to use Annotation `TableName`");
        }
    }

    /**
     * 接口是否允许被调用
     *
     * @param field 属性字符串
     */
    private void isAllowApi(String field) {
        // TODO 临时认证方式
        try {
            Field f = getEntityCls().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            try {
                Field f = getEntityCls().getSuperclass().getDeclaredField(field);
            } catch (NoSuchFieldException _e) {
                throw new NullPointerException("api not allow.");
            }
        }
    }

    @PatchMapping("/batch-public")
    @ApiOperation("资源公开/隐藏")
    public BaseRsp<Boolean> batchPublic(@RequestBody ResourcePublicReq requestData) {
        isAllowApi("isPublic");
        if (requestData.getResourceIds() == null) {
            throw new IllegalArgumentException("无效参数");
        }
        String tableName = getTableName();
        Integer matchCount = practiceMixinMapper.batchPublic(tableName, requestData.getResourceIds(), requestData.isPublic());

        return new BaseRsp<>(matchCount >= 1);
    }

    @PatchMapping("/batch-auth")
    @ApiOperation("资源授权访问")
    public BaseRsp<Boolean> batchAuth(@RequestBody ResourceAuthReq requestData) {
        isAllowApi("auth");

        Integer matchCount = practiceMixinMapper.batchAuth(
                getTableName(),
                requestData.getResourceIds(),
                JsonUtil.toJsonString(requestData.getAuthOrganizations()),
                requestData.getAuthMode()
        );

        return new BaseRsp<>(matchCount >= 1);
    }

    // TODO 定义`auth`、`share`接口无意义, 浙能版本中已由前端实现
    @GetMapping("/auth")
    @ApiOperation("获取授权访问信息")
    @Deprecated
    public BaseRsp<Entity> getAuth(@RequestParam("resource_id") @NotBlank String resourceId) {
        isAllowApi("auth");
        return new BaseRsp<>();
    }

    @PatchMapping("/batch-share")
    @ApiOperation("资源授权操作")
    public BaseRsp<Boolean> batchShare(@RequestBody ResourceShareReq requestData) {
        isAllowApi("share");
        Integer matchCount = practiceMixinMapper.batchShare(
                getTableName(),
                requestData.getResourceIds(),
                JsonUtil.toJsonString(requestData.getShareTeachers()),
                requestData.getShareMode()
        );
        return new BaseRsp<>(matchCount >= 1);
    }

    @GetMapping("/share")
    @ApiOperation("获取授权操作信息")
    @Deprecated
    public BaseRsp<Entity> getShare(@RequestParam("resource_id") @NotBlank String resourceId) {
        isAllowApi("share");
        return new BaseRsp<>();
    }

    @PostMapping("/evaluate")
    @ApiOperation("练习评估")
    public BaseRsp<EvaluateResult> evaluate(@RequestBody PracticeEvaluateReq requestData) {
        isAllowApi("answer");
        String userId = UserInfoContext.getUserContext().getUserId();
        return new BaseRsp<>(practiceService.evaluator(requestData.getResourceId(), requestData.getPracticeType(), requestData.getAnswer(), userId));
    }
}
