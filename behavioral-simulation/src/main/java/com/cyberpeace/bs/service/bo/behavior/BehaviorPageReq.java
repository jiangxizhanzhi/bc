package com.cyberpeace.bs.service.bo.behavior;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 行为分页请求体
 *
 * @author SN
 * @date 2023/05/06
 */
public class BehaviorPageReq extends PageReqBase {

    /**
     * 设备操作系统类型
     */
    @ApiModelProperty(value = "设备类型")
    @Getter
    @Setter
    private Integer accessOs;

    /**
     * 网络类型
     */
    @ApiModelProperty(value = "网络类型")
    @Getter
    @Setter
    private Integer networkType;

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "客户端类型，type=45")
    @Getter
    @Setter
    private Integer appType;

	@ApiModelProperty(value = "行为tag")
	@Getter
	@Setter
	private String tag;

    /**
     * 是否实体设备
     */
    @ApiModelProperty(value = "是否实体设备")
    @Getter
    @Setter
    private Integer isActual;

}
