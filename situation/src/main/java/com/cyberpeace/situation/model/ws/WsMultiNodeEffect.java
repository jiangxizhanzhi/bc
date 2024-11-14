package com.cyberpeace.situation.model.ws;

import com.cyberpeace.situation.model.LineStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsMultiNodeEffect implements Serializable {

	/**
	 * 场景id
	 */
    @ApiModelProperty("场景id")
    private String sceneId;

	/**
	 * 持续时间（秒）
	 */
    @ApiModelProperty("持续时间（秒）")
    private Integer duration;

	/**
	 * 事件名称
	 */
    @ApiModelProperty("事件名称")
    private String name;

	/**
	 * 攻防类别
	 */
    @ApiModelProperty("攻防类别")
    private Integer category;

	/**
	 * 事件类型 1:攻击 2:防御 3:阻断 4:归属
	 */
    @ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
    private Integer eventType;

	/**
	 * 源节点id
	 */
    @ApiModelProperty("源节点id")
    private String sourceId;

	/**
	 * 目的节点id
	 */
    @ApiModelProperty("目的节点id")
    private String targetId;

	/**
	 * 攻击结果 1:成功 2失败
	 */
    @ApiModelProperty("攻击结果 1:成功 2失败")
    private Integer result;

	/**
	 * 归属角色 4:红队 5蓝队
	 */
    @ApiModelProperty("归属角色 4:红队 5蓝队")
    private Integer role;

	/**
	 * 线型
	 */
    @ApiModelProperty("线型")
    private LineStyle lineStyle;

}
