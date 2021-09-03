package com.cyberpeace.oj.practice.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.practice.controller.req.ResourcePublicReq;
import com.cyberpeace.oj.practice.controller.req.ResourceShareReq;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 提供练习公共功能接口
 *
 * @param <Entity> 实体类
 * @param <Mapper> mybatis-plus Mapper
 */
@RestController
public abstract class PracticeMixinController<Entity, Mapper extends BaseMapper<Entity>> {

    public abstract Mapper getMapper();

    // 子类显式设置`Entity`class  TODO 替换其它获取方式
    public abstract Class<Entity> getEntityCls();

    /**
     * 构建实体类时, 摒弃`conditions`外所有属性
     *
     * @param conditions 属性设置
     * @return Entity
     */
    public Entity newInstance(String... conditions) throws ReflectiveOperationException {
        Entity object = getEntityCls().newInstance();
        Map<String, String> objectAttr = Maps.newHashMap();

        for (String item : conditions) {
            if (item.indexOf("=") == 1) {
                String[] condition = item.split("=");
                objectAttr.put(condition[0], condition[1]);
            }
        }

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // TODO 布尔类型无法设置null, 临时跳过
            if (field.getType() != boolean.class) {
                field.set(object, objectAttr.getOrDefault(field.getName(), null));
            }
        }

        return object;
    }

    public Entity newInstance() throws ReflectiveOperationException {
        return newInstance("");
    }

    @PatchMapping("/batch-public")
    @ApiOperation("资源公开/隐藏")
    public boolean batchPublic(@RequestBody ResourcePublicReq requestData) throws ReflectiveOperationException {
        UpdateWrapper<Entity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("resource_id", requestData.getResourceIds());
        int matchCount = getMapper().update(newInstance(String.format("public=%s", requestData.isPublic())), updateWrapper);

        return matchCount >= 1;
    }

    @PatchMapping("/batch-auth")
    @ApiOperation("资源授权访问")
    public boolean batchAuth(@RequestBody ResourceAuthReq requestData) {
        return true;
    }

    @PatchMapping("/batch-share")
    @ApiOperation("资源授权操作")
    public boolean batchShare(@RequestBody ResourceShareReq requestData) {
        return true;
    }
}
