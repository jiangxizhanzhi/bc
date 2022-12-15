package com.cyberpeace.situation.controller.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午10:47
 */
@Data
public class SituationIconRes extends BaseTable implements Serializable {

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

    private String createUserName;

    @ApiModelProperty("")
    private String typeName;
}
