package com.cyberpeace.cs.controller.res;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 快照路径res
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@ApiModel
public class SnapshotPathRes implements Serializable {

	@ApiModelProperty("快照数量")
	private Integer count;

	@ApiModelProperty("快照路径 树形结构")
	private JSONObject path;

}
