package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

/**
 * 空间流量检测统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceTrafficDetectRulePageReq extends SpaceToolPageReq {

	/**
	 * 将请求转换为查询包装器
	 *
	 * 此方法根据请求中的条件生成一个LambdaQueryWrapper对象，用于查询空间流量检测规则
	 * 它首先创建一个LambdaQueryWrapper对象，并根据请求中的条件进行排序、模糊匹配和精确匹配操作
	 * 最后设置实体类并返回查询包装器对象
	 *
	 * @return LambdaQueryWrapper<SpaceTrafficDetectRule> 查询包装器对象，用于执行数据库查询
	 */
	@Override
	public LambdaQueryWrapper<SpaceTrafficDetectRule> req2QueryWrapper() {
	    // 创建LambdaQueryWrapper对象，用于构建查询条件
	    LambdaQueryWrapper<SpaceTrafficDetectRule> wrapper = new LambdaQueryWrapper<>();
	    // 按更新时间降序排序
	    wrapper.orderByDesc(SpaceTrafficDetectRule::getUpdateTime);

	    // 如果工具字段不为空，则添加模糊匹配条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceTrafficDetectRule::getTool, this.getTool());
	    }
	    // 如果阶段字段不为空，则添加精确匹配条件
	    if (null != this.getStage()) {
	        wrapper.eq(SpaceTrafficDetectRule::getStage, this.getStage());
	    }
	    // 设置实体类，指定查询的实体类型
	    wrapper.setEntityClass(SpaceTrafficDetectRule.class);
	    // 返回构建好的查询包装器对象
	    return wrapper;
	}
}
