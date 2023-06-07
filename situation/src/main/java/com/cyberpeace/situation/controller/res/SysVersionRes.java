package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 可视化系统版本信息返回
 *
 * @author chenhy
 * @date 2023/05/29
 */
@Data
public class SysVersionRes implements Serializable {

	@ApiModelProperty("系统版本号")
	private String version;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("最近一次提交")
	private String latestCommit;

	@ApiModelProperty("是否有数据库变更")
	private Boolean dbChange;

	@ApiModelProperty("更新日志")
	private String log;
}
