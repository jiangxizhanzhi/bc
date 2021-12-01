package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PracticeLibInfoBo implements Serializable {



	@ApiModelProperty("习题信息")
	private ListRsp<LibPracticeListBo> practiceList;

}
