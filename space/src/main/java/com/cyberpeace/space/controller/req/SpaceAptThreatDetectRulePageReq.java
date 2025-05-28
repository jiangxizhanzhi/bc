package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间容易威胁检测统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 */
@ApiModel
public class SpaceAptThreatDetectRulePageReq extends SpacePageReqBase {

	@Getter
	@Setter
	@ApiModelProperty(value = "APT代号")
	private String apt_code;

	@Getter
	@Setter
	@ApiModelProperty(value = "工具名称")
	private String tool;

	/**
	 * 将请求转换为查询包装器
	 *
	 * 此方法用于将用户请求参数转换为MyBatis-Plus的LambdaQueryWrapper对象，
	 * 以便于后续的数据库查询操作它根据用户输入的条件（如模式、APT代码、工具等）
	 * 来构建查询条件，并默认按照更新时间降序排序
	 *
	 * @return LambdaQueryWrapper<SpaceAptThreatDetectRule> 返回一个配置好查询条件的LambdaQueryWrapper对象
	 */
	@Override
	public LambdaQueryWrapper<SpaceAptThreatDetectRule> req2QueryWrapper() {
	    // 创建一个LambdaQueryWrapper对象
	    LambdaQueryWrapper<SpaceAptThreatDetectRule> wrapper = new LambdaQueryWrapper<>();
	    // 默认按照更新时间降序排序
	    wrapper.orderByDesc(SpaceAptThreatDetectRule::getUpdateTime);

	    // 如果模式（名称）条件不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getPattern())) {
	        wrapper.like(SpaceAptThreatDetectRule::getName, this.getPattern());
	    }

	    // 如果APT代码条件不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getApt_code())) {
	        wrapper.like(SpaceAptThreatDetectRule::getAptCode, this.getApt_code());
	    }

	    // 如果工具条件不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceAptThreatDetectRule::getTool, this.getTool());
	    }

	    // 设置实体类类型
	    wrapper.setEntityClass(SpaceAptThreatDetectRule.class);

	    // 返回配置好的查询包装器
	    return wrapper;
	}
}
