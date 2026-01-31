package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 团队房间列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@ApiModel
@Data
public class TeamRoomListReq extends PageReqBase {

	/**
	 * 类别ID
	 */
	@ApiModelProperty(value = "类别ID")
	private String category;


    @ApiModelProperty(value = "xd type id", required = true)
    private Integer xdType;

	/**
	 * 检索参数
	 */
	@ApiModelProperty(value = "检索参数")
	private String search;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * id列表
	 */
	@ApiModelProperty(value = "id列表")
	private List<String> ids;


	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始日期")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束日期")
	private String endTime;


    public void setXd_type(Integer xdType) {
        this.xdType = xdType;
    }

}
