package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间加载文件威胁统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceLoadedFileThreatRulePageReq extends SpaceToolPageReq {

	@Getter
	@Setter
	private String type;

	/**
	 * 将请求转换为查询包装器
	 *
	 * 此方法根据请求中的条件构建一个LambdaQueryWrapper对象，用于查询空间加载文件威胁规则
	 * 它会检查请求中的模式、工具、类型和阶段字段，并根据这些条件生成相应的查询条件
	 * 最后，它还会设置查询结果的排序方式和实体类类型
	 *
	 * @return LambdaQueryWrapper<SpaceLoadedFileThreatRule> 返回构建好的查询包装器对象
	 */
	@Override
	public LambdaQueryWrapper<SpaceLoadedFileThreatRule> req2QueryWrapper() {
	    // 创建一个新的LambdaQueryWrapper对象
	    LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();

	    // 如果模式字段不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getPattern())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getName, this.getPattern());
	    }

	    // 如果工具字段不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getTool, this.getTool());
	    }

	    // 如果类型字段不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getType())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getType, this.getType());
	    }

	    // 如果阶段字段不为空，则添加精确查询条件
	    if (null != this.getStage()) {
	        wrapper.eq(SpaceLoadedFileThreatRule::getStage, this.getStage());
	    }

	    // 设置查询结果按更新时间降序排序
	    wrapper.orderByDesc(SpaceLoadedFileThreatRule::getUpdateTime);

	    // 设置查询器的实体类类型
	    wrapper.setEntityClass(SpaceLoadedFileThreatRule.class);

	    // 返回构建好的查询包装器对象
	    return wrapper;
	}
}
