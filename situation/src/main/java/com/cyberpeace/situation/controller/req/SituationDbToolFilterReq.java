package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库工具查询请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationDbToolFilterReq extends PageReqBase implements Serializable {

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	/**
	 * 工具类型
	 */
	@ApiModelProperty(value = "工具类型")
	private String category;

}
