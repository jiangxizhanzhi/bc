package com.cyberpeace.scene.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author :   SN
 * @date :   2023/12/20 16:51
 * @desc :
 **/
@Data
@ApiModel
@TableName("cr_scene")
public class SceneEntity extends BaseTable {

    @ApiModelProperty("场景id")
    private String resourceId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景启动时间")
    private Date startTime;

    @ApiModelProperty("场景结束时间")
    private Date endTime;

    @ApiModelProperty("场景自动销毁时间")
    private Date destroyTime;

    @ApiModelProperty("场景所属实例id")
    private String instanceId;

    @ApiModelProperty("场景状态")
    private Integer sceneStatus;

    @ApiModelProperty("场景拓扑")
    private String topologyId;

    @ApiModelProperty("场景创建者")
    private String userId;

    @ApiModelProperty("场景存活时间，单位：小时")
    private Integer sceneTtl;

    @ApiModelProperty("场景元数据")
    private String meta;
}
