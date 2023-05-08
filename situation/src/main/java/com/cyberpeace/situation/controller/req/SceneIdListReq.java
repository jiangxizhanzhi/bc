package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景id列表请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SceneIdListReq implements Serializable {

	/**
	 * 场景id列表
	 */
	@ApiModelProperty("场景id列表")
	private List<String> sceneIds = Lists.newArrayList();

}
