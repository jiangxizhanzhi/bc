package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.res.RelatedCourse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 知识新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("")
public class KnowledgeAddReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("知识点id")
	private String resourceId;

	/**
	 * 内容
	 */
	@NotBlank
	@ApiModelProperty("知识点名称")
	private String content;

	/**
	 * 类别id
	 */
	@NotBlank
	@ApiModelProperty("知识点类别id")
	private String categoryId;

	/**
	 * 重量
	 */
	@ApiModelProperty("知识点课程权重")
	private Integer weight = 1;

	/**
	 * 课程列表
	 */
	@ApiModelProperty("知识点关联课程")
	private List<RelatedCourse> courseList;

	public void validate() {
		if (CollectionUtils.isNotEmpty(courseList)) {
			for (RelatedCourse relatedCourse : courseList) {
				if (null == relatedCourse.getWeight()
						|| relatedCourse.getWeight() < CommonConstant.ZERO
						|| relatedCourse.getWeight() > CommonConstant.NINE) {
					throw new BusinessException(RtksBusinessErrorCode.INVALID_WEIGHT,
							CommonConstant.ZERO, CommonConstant.NINE);
				}
			}
		}
	}
}
