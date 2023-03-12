package com.cyberpeace.integratation.model.situation.layer;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class SectionEventContext implements Serializable {

	@ApiModelProperty("事件段类型")
	private String type;

	@ApiModelProperty("事件段描述")
	private String desc;

	@ApiModelProperty("事件段时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

	@ApiModelProperty("源元素ID")
	private String sourceId;

	@ApiModelProperty("目标元素ID")
	private String targetId;
}
