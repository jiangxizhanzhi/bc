package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.evaluate.service.bo.TestItemBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评测结果
 */
@Data
public class TestResultContext implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String desc;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("结束时间")
	private Date endTime;

	@ApiModelProperty("实例id")
	private String instanceId;

	@ApiModelProperty("3=放弃保存;2=保存")
	private Integer status;

	@ApiModelProperty("测试项")
	private List<TestItemBO> items;
}
