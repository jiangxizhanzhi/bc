package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminTheoryListReq extends PageReqBase {

//	@ApiModelProperty(name = "practice_set_id", value = "习题集")
//	private String practiceSetId;

	@ApiModelProperty(name = "category_id", value = "方向")
	private String categoryId;

	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	@ApiModelProperty(name = "theory_type", value = "题目类型:0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
	private Integer theoryType;

//	public void setPractice_set_id(String practiceSetId) {
//		this.practiceSetId = practiceSetId;
//	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setTheory_type(Integer theoryType){
		this.theoryType = theoryType;
	}

}
