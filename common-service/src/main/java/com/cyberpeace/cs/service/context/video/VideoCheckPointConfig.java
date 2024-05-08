package com.cyberpeace.cs.service.context.video;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoCheckPointConfig {

	@ApiModelProperty(value = "检查点列表")
	private List<VideoCheckPointItem> points = Lists.newArrayList();

	@ApiModelProperty(value = "自定义检查点列表")
	private List<VideoCheckPointItem> customPoints = Lists.newArrayList();
}
