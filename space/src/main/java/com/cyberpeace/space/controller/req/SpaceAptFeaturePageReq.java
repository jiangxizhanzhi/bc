package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;

/**
 * 空间容易特征分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 * @author zhuyuzhi
 * @date 2021/9/28
 * comemnt
 */
@ApiModel
public class SpaceAptFeaturePageReq extends SpacePageReqBase {
    /**
     * 将请求转换为针对SpaceAptFeature实体的查询包装器
     *
     * 此方法用于构建一个LambdaQueryWrapper对象，该对象根据当前请求的条件来查询SpaceAptFeature表
     * 它首先按照更新时间降序排序查询结果，然后根据请求中的模式字符串对名称进行模糊查询
     * 如果模式字符串为空，则只进行排序操作
     *
     * @return LambdaQueryWrapper<SpaceAptFeature> 返回一个配置好的查询包装器对象
     */
    @Override
    public LambdaQueryWrapper<SpaceAptFeature> req2QueryWrapper() {
        // 创建LambdaQueryWrapper对象，用于后续的查询条件设置
        LambdaQueryWrapper<SpaceAptFeature> wrapper = new LambdaQueryWrapper<>();
        // 设置查询结果按照更新时间降序排序
        wrapper.orderByDesc(SpaceAptFeature::getUpdateTime);
        // 如果模式字符串不为空，则添加对名称的模糊查询条件
        if (StringUtils.isNotBlank(this.getPattern())) {
            wrapper.like(SpaceAptFeature::getName, this.getPattern());
        }
        // 设置查询的实体类类型
        wrapper.setEntityClass(SpaceAptFeature.class);
        // 返回配置好的查询包装器对象
        return wrapper;
    }
}
