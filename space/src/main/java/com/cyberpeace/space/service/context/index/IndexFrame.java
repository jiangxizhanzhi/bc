package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IndexFrame {

	@ApiModelProperty(value = "计数")
	private IndexFrameCount frameCount;

	@ApiModelProperty(value = "趋势图")
	private List<Object> trending;
}
