package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("资源评论信息")
public class CommentReq implements Serializable {

    @ApiModelProperty(value = "评论内容")
    @NotBlank
    private String content;

    @ApiModelProperty("主题信息 课时笔记=课程名称/课时名称 课程笔记=课程名称 实操习题笔记=实操题名称")
    @NotBlank
    private String themeName;

    @ApiModelProperty("资源编号 课程 课时 练习")
    @NotBlank
    private String businessId;

    @ApiModelProperty("资源评论编号")
    private String rootId;

    @ApiModelProperty("被回复评论编号")
    private String replyId;

    @ApiModelProperty("评论资源类型 1=课程 2=课时 3=实操")
    @NotNull
    private Integer businessType;

}
