package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 态势导调列表响应
 *
 * @author myy@date 2024/11/12
 */
@Data
public class SituationGuideListRsp implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("resourceId")
    private String resourceId;

    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    private String roomId;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 事件名称
     */
    @ApiModelProperty("动效方案名称")
    private String eventName;

    /**
     * 事件类型
     */
    @ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
    private Integer eventType;

    /**
     * 类别
     */
    @ApiModelProperty("攻防类型")
    private Integer category;

    /**
     * 类别名称
     */
    @ApiModelProperty("攻防类型名称")
    private String categoryName;

    /**
     * 源
     */
    @ApiModelProperty("靶标源id")
    private String source;

    /**
     * 目标
     */
    @ApiModelProperty("靶标目标id")
    private String target;

    /**
     * 源名称
     */
    @ApiModelProperty("靶标源名称")
    private String sourceName;

    /**
     * 目标名称
     */
    @ApiModelProperty("靶标目标名称")
    private String targetName;

    /**
     * 被执行
     */
    @ApiModelProperty("是否可执行")
	private Boolean isExecuted = true;

    /**
     * 岗位
     */
    @ApiModelProperty("归属角色")
    private Integer role;

    /**
     * 岗位名称
     */
    @ApiModelProperty("归属角色名称")
    private String roleName;

}
