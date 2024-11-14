package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 列出时间表
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ListSchedule implements Serializable {

	/**
	 * 上课时间
	 */
	@ApiModelProperty("上课时间")
  private String date;

	/**
	 * 待上课程
	 */
	@ApiModelProperty("待上课程")
  private List<String> classroomInfo;

}
