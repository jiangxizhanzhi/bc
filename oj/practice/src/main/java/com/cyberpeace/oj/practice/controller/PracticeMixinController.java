package com.cyberpeace.oj.practice.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.practice.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.practice.controller.req.ResourcePublicReq;
import com.cyberpeace.oj.practice.controller.req.ResourceShareReq;
import com.cyberpeace.oj.practice.dao.mapper.PracticeMixinMapper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供练习公共功能接口
 *
 * @param <Entity> 实体类
 * @param <Mapper> mybatis-plus Mapper
 */
@RestController
public abstract class PracticeMixinController<Entity, Mapper extends BaseMapper<Entity>> {

    @Autowired
    private PracticeMixinMapper practiceMixinMapper;

    public abstract Mapper getMapper();

    // 子类显式设置`Entity`class  TODO 替换其它获取方式
    public abstract Class<Entity> getEntityCls();

    /**
     * 获取实体类关联资源数据表名称,
     * 无`com.baomidou.mybatisplus.annotation.TableName`注解情况返回转换后实体类名称 example: TableName -> table_name.
     *
     * @return table name string
     */
    private String getTableName() {
        TableName annotation = getEntityCls().getAnnotation(TableName.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            Pattern regexRule = Pattern.compile("[A-Z][a-z]+");
            Matcher m = regexRule.matcher(getEntityCls().getSimpleName());

            List<String> listByString = new ArrayList<>();
            while (m.find()) {
                listByString.add(m.group().toLowerCase());
            }

            return StringUtils.join(listByString, "_");
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

    @GetMapping("/auth")
    @ApiOperation("获取授权访问信息")
    public BaseRsp<Object> getAuth(@RequestParam @NotBlank String resourceId) {
        isAllowApi("auth");
        // TODO 待调用MP
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
    public BaseRsp<Object> getShare(@RequestParam @NotBlank String resourceId) {
        isAllowApi("share");
        // TODO 待调用MP
        return new BaseRsp<>();
    }
}
