package com.cyberpeace.integratation.model.situation.layer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LayerEventContext implements Serializable {

	@ApiModelProperty("事件段类型")
	private String eventId;

	@ApiModelProperty("事件时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date eventTime;

	@ApiModelProperty("用户元素名称")
	private String userName;

	@ApiModelProperty("应用元素名称")
	private String appName;

	@ApiModelProperty("逻辑元素名称")
	private String logicName;

	@ApiModelProperty("物理元素名称")
	private String physicalName;

	@ApiModelProperty("事件描述（格式化模板）")
	private String eventDesc;

	@ApiModelProperty("事件段列表")
	private List<SectionEventContext> sections;
}
