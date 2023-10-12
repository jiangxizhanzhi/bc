package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
public class SituationGuideListRsp implements Serializable {

    @ApiModelProperty("resourceId")
    private String resourceId;

    @ApiModelProperty("房间id")
    private String roomId;

    private Integer businessType;

    @ApiModelProperty("动效方案名称")
    private String eventName;

    @ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
    private Integer eventType;

    @ApiModelProperty("攻防类型")
    private Integer category;

    @ApiModelProperty("攻防类型名称")
    private String categoryName;

    @ApiModelProperty("靶标源id")
    private String source;

    @ApiModelProperty("靶标目标id")
    private String target;

    @ApiModelProperty("靶标源名称")
    private String sourceName;

    @ApiModelProperty("靶标目标名称")
    private String targetName;

	@ApiModelProperty("是否可执行")
	private Boolean isExecuted = true;

    @ApiModelProperty("归属角色")
    private Integer role;

    @ApiModelProperty("归属角色名称")
    private String roleName;

}
