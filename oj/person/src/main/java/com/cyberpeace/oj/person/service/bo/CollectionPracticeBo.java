package com.cyberpeace.oj.person.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CollectionPracticeBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("习题类型")
	private Integer practiceType;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("分类名称")
	private String categoryName;

}
