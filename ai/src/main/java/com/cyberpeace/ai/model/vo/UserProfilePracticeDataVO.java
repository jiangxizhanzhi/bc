package com.cyberpeace.ai.model.vo;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.ai.model.vo.practice.PracticeDataVO;
import com.cyberpeace.ai.model.vo.practice.PracticeTheoryDataVO;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像-题目练习数据")
public class UserProfilePracticeDataVO {

	@ApiModelProperty("理论题总体练习情况")
	private ProgressStatisticsRes theoryOverallStats;

	@ApiModelProperty("理论题各方向题目练习情况")
	private List<PracticeTheoryDataVO> theoryDirectionStats = Lists.newArrayList();

	@ApiModelProperty("实操题总体练习情况")
	private List<CategoryProgressRes> practicalOverallStats = Lists.newArrayList();

	@ApiModelProperty("实操题各方向题目练习情况")
	private List<PracticeDataVO> practicalDirectionStats = Lists.newArrayList();
}