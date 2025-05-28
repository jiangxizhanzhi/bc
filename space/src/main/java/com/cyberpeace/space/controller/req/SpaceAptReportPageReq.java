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
