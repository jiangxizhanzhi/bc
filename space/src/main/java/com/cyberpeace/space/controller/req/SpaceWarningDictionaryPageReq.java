package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

/**
 * 空间警告字典分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceWarningDictionaryPageReq extends SpaceToolPageReq {

	/**
	 * 将请求转换为SpaceWarningDictionary表的查询包装器
	 *
	 * 此方法用于根据当前请求参数构建一个LambdaQueryWrapper对象，以便进行SpaceWarningDictionary表的查询
	 * 它会根据请求中的参数，添加相应的查询条件和排序规则
	 *
	 * @return LambdaQueryWrapper<SpaceWarningDictionary>: 返回一个LambdaQueryWrapper对象，包含查询条件和排序规则
	 */
	@Override
	public LambdaQueryWrapper<SpaceWarningDictionary> req2QueryWrapper() {
	    // 创建一个LambdaQueryWrapper对象，用于构建查询条件
	    LambdaQueryWrapper<SpaceWarningDictionary> wrapper = new LambdaQueryWrapper<>();
	    // 添加排序规则，根据更新时间降序排序
	    wrapper.orderByDesc(SpaceWarningDictionary::getUpdateTime);

	    // 如果模式（名称）参数不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getPattern())) {
	        wrapper.like(SpaceWarningDictionary::getName, this.getPattern());
	    }

	    // 如果工具类型参数不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceWarningDictionary::getToolType, this.getTool());
	    }

	    // 设置实体类，指定查询的表
	    wrapper.setEntityClass(SpaceWarningDictionary.class);

	    // 返回构建好的查询包装器对象
	    return wrapper;
	}

}
