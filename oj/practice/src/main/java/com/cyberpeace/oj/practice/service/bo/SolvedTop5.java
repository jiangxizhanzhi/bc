package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 解决 Top5
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SolvedTop5 {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
    private String userName;

	/**
	 * 是否解出
	 */
	@ApiModelProperty("是否解出")
    private boolean solved;

	/**
	 * 答题时间
	 */
	@ApiModelProperty("答题时间")
    private Date updateTime;
}
