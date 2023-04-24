package com.cyberpeace.situation.controller.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @Author xuxz
 * @Date 4/1/2023 下午2:32
 */
@Data
public class SituationDrawingPageRes extends PageReqBase implements Serializable {
    @ApiModelProperty("资源id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("图形名称")
    private String name;

    @ApiModelProperty("图形类型")
    private String type;

    @ApiModelProperty("图形url")
    private String drawingUrl;

    @ApiModelProperty("创建人")
    private String createUserId;

    private String createUserName;

    private Date createTime;

    private Date updateTime;
}
