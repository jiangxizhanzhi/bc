package com.cyberpeace.scene.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/20 16:51
 * @desc :
 **/
@Data
@ApiModel
@TableName("cr_scene")
public class SceneEntity extends BaseTable implements Serializable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("场景名称")
    private String sceneName;

	@ApiModelProperty("场景自动销毁时间")
	private Date destroyTime;

	private Date startTime;

    @ApiModelProperty("场景状态")
    private Integer sceneStatus;

	@ApiModelProperty("场景存活时间，单位：小时")
	private Integer sceneTtl;

	private Integer scenario;

}
