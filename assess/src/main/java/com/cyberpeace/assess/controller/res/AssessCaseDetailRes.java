package com.cyberpeace.assess.controller.res;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.assess.dao.entity.AssessMaterial;
import org.apache.commons.collections.CollectionUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程目录配置信息")
public class AssessCaseDetailRes implements Serializable {


	@ApiModelProperty("类型")
	private Integer type;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty("描述")
	private String overview;

	@ApiModelProperty("封面")
	private String cover;

	@ApiModelProperty("标签")
	private List<String> tag;

	@ApiModelProperty("标签名")
	private String tagName;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "材料数量")
	private Integer materialCount;

	@ApiModelProperty("材料附件")
	private List<AssessMaterial> attachments;

}
