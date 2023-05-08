package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.dao.entity.DashboardComponents;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实例数据查询返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class InstanceDataQueryRes implements Serializable {

	/**
	 * 组件配置信息
	 */
	@ApiModelProperty("组件配置信息")
	private List<DashboardComponents> componentsSetting;

}
