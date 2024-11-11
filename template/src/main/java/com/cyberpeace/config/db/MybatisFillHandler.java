package com.cyberpeace.config.db;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * MybatisFillHandler 类实现了 MetaObjectHandler 接口，用于在 MyBatis-Plus 框架中自动填充创建时间和更新时间字段
 */
public class MybatisFillHandler implements MetaObjectHandler {

    /**
     * 在插入操作时填充 MetaObject 中的字段
     * 此方法自动为 "createTime" 和 "updateTime" 字段设置当前日期和时间，避免了手动设置的繁琐
     *
     * @param metaObject MyBatis-Plus 框架提供的元对象，用于操作实体类的属性
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 在更新操作时填充 MetaObject 中的字段
     * 仅更新 "updateTime" 字段，以记录实体的最后更新时间
     *
     * @param metaObject MyBatis-Plus 框架提供的元对象，用于操作实体类的属性
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
