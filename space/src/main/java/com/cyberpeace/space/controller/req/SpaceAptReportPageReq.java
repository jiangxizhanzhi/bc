package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间容易报告分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 * @description: APT报告分页请求参数
 * @author: zhangyw
 * @date: 2022/9/23
 */
/**
 * 这段代码定义了一个用于分页查询空间APT（高级持续性威胁）报告的请求类 `SpaceAptReportPageReq`。以下是对代码的简要分析： 
 * ### 主要功能 
 * - **继承与扩展**：该类继承自 `SpacePageReqBase`，并添加了额外的查询字段如 `apt_org`（APT组织）、`report_type`（报告类型）。 
 * - **注解说明**：使用了 `@ApiModel` 和 `@ApiModelProperty` 注解，便于文档生成工具（如 Swagger）生成接口文档。 
 * - **核心方法**：重写了 `req2QueryWrapper()` 方法，将请求参数转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 对象，用于构造数据库查询条件。 
 * ### 关键点解析 
 * 1. **查询条件构建** 
 * - 根据 `apt_org` 和 `report_type` 字段动态添加查询条件。 
 * - 使用 `StringUtils.isNotBlank()` 判断字符串是否非空，避免无效查询。 
 * - 时间字段按降序排序，确保最新数据优先返回。 
 * 2. **封装查询逻辑** 
 * - `req2QueryWrapper()` 方法集中管理查询条件的生成逻辑，便于复用和维护。 
 * - 支持灵活组合多种查询条件（如模糊匹配或精确匹配），提升查询灵活性。 
 * 3. **代码结构** 
 * - 类的设计遵循单一职责原则，专注于分页查询请求的定义和条件构建。 
 * - 属性使用 `@Getter` 和 `@Setter` 注解，简化了属性访问和修改。 
 * ### 总结 
 * 此代码是一个典型的分页查询请求类，通过封装查询条件生成逻辑，提高了代码的可读性和可维护性。同时，结合 MyBatis-Plus 提供的强大查询能力，实现了高效的数据查询操作。 
**/
@ApiModel
public class SpaceAptReportPageReq extends SpacePageReqBase {

	@Getter
	@Setter
	@ApiModelProperty(value = "APT组织")
	private String apt_org;
	@Getter
	@Setter
	@ApiModelProperty(value = "报告类型")
	private Integer report_type;
	/**
	 * 将请求转换为针对SpaceAptReport的查询包装器
	 *
	 * 此方法根据不同的查询条件生成一个LambdaQueryWrapper对象，用于后续的数据库查询操作
	 * 它首先按照更新时间降序排序查询结果，然后根据名称、机构和报告类型等条件进行筛选
	 * 这样做是为了提高查询的效率和准确性，确保用户能够快速获取到所需的信息
	 *
	 * @return LambdaQueryWrapper<SpaceAptReport> 返回一个配置好的查询包装器对象，用于执行数据库查询
	 */
	@Override
	public LambdaQueryWrapper<SpaceAptReport> req2QueryWrapper() {
	    // 创建一个LambdaQueryWrapper对象，用于构建查询条件
	    LambdaQueryWrapper<SpaceAptReport> wrapper = new LambdaQueryWrapper<>();
	    // 按照更新时间降序排序查询结果，确保最新的记录优先展示
	    wrapper.orderByDesc(SpaceAptReport::getUpdateTime);

	    // 如果模式不为空，根据名称进行模糊查询
	    if (StringUtils.isNotBlank(this.getPattern())) {
	        wrapper.like(SpaceAptReport::getName, this.getPattern());
	    }

	    // 如果机构不为空，根据机构进行模糊查询
	    if (StringUtils.isNotBlank(this.apt_org)) {
	        wrapper.like(SpaceAptReport::getAptOrg, this.apt_org);
	    }

	    // 如果报告类型不为空，根据报告类型进行精确查询
	    if (this.report_type != null) {
	        wrapper.eq(SpaceAptReport::getType, this.getReport_type());
	    }

	    // 设置实体类，确保查询操作与SpaceAptReport表相关联
	    wrapper.setEntityClass(SpaceAptReport.class);

	    // 返回配置好的查询包装器对象
	    return wrapper;
	}
}

