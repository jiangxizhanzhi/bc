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
public class SituationDecorationPageRes extends PageReqBase implements Serializable {
    @ApiModelProperty("资源id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("装饰类名称")
    private String name;

    @ApiModelProperty("装饰类类型")
    private String type;

    @ApiModelProperty("装饰类url")
    private String decorationUrl;

    @ApiModelProperty("创建人")
    private String createUserId;

    private String createUserName;

    private Date createTime;

    private Date updateTime;
}
