package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午10:36
 */

@Data
@TableName("situation_icon")
public class SituationIcon extends BaseTable implements Serializable {

    @ApiModelProperty("资源id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("图标名称")
    private String name;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("图标url")
    private String iconUrl;

    @ApiModelProperty("创建人")
    private String createUserId;
}
