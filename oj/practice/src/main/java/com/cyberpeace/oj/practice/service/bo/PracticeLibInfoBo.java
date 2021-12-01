package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PracticeLibInfoBo implements Serializable {

	@ApiModelProperty("习题集数据")
	private List<PracticeSetRes> practiceSet;

	@ApiModelProperty("分类数据")
	private List<CategoryListBo> category;

	@ApiModelProperty("题目类型 0=单选 1=多选....")
	private List<Integer> questionType;

	@ApiModelProperty("习题信息")
	private ListRsp<LibPracticeListBo> practiceList;

}
