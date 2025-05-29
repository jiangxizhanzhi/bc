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
/**
 * 这段代码定义了一个用于分页查询空间威胁检测规则的请求类 `SpaceAptThreatDetectRulePageReq`。以下是代码的主要功能和结构分析： 
 * 1. **类的功能**： 
 * - 这是一个基于分页的请求类，继承自 `SpacePageReqBase`。 
 * - 提供了三个主要字段：`apt_code`（APT代号）、`tool`（工具名称）和 `pattern`（模式名称），用于过滤查询条件。 
 * 2. **字段说明**： 
 * - `apt_code` 和 `tool` 是通过 `@ApiModelProperty` 注解描述的请求参数，分别表示 APT 代号和工具名称。 
 * - `pattern` 是从父类继承的字段，用于模糊匹配规则名称。 
 * 3. **核心方法 `req2QueryWrapper`**： 
 * - 该方法将请求参数转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 对象。 
 * - 默认按 `update_time` 字段降序排序。 
 * - 根据非空字段动态添加模糊查询条件（`like`）： 
 * - 如果 `pattern` 不为空，则匹配规则名称。 
 * - 如果 `apt_code` 不为空，则匹配 APT 代号。 
 * - 如果 `tool` 不为空，则匹配工具名称。 
 * - 最后设置实体类类型并返回配置好的 `LambdaQueryWrapper`。 
 * 4. **设计特点**： 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 自动生成字段的 getter 和 setter 方法。 
 * - 借助 Swagger 注解 (`@ApiModel`, `@ApiModelProperty`) 描述 API 模型，便于生成文档。 
 * - 利用 MyBatis-Plus 提供的 `LambdaQueryWrapper` 实现动态 SQL 构建，提升代码可读性和灵活性。 
 * 总结：这是一个典型的分页查询请求类，通过动态条件过滤实现灵活的数据查询，同时结合 MyBatis-Plus 提供的强大功能简化了数据库操作逻辑。 
**/
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

