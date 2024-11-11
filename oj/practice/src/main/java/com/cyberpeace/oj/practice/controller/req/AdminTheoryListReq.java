package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理理论列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class AdminTheoryListReq extends PageReqBase {

	/**
	 * 练习设置id
	 */
	@ApiModelProperty(name = "practice_set_id", value = "习题集")
	private String practiceSetId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(name = "category_id", value = "方向")
	private String categoryId;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 理论类型
	 */
	@ApiModelProperty(name = "theory_type", value = "题目类型:0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
	private Integer theoryType;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setTheory_type(Integer theoryType){
		this.theoryType = theoryType;
	}

}
