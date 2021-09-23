package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class NoteReq implements Serializable {

    @ApiModelProperty("笔记编号 没有则新增 有则修改")
    private String resourceId;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记类型 1=笔记 2=实验报告")
    @NotNull
    private Integer noteType;

    @ApiModelProperty("资源编号 课程 课时 习题")
    @NotBlank
    private String resource;
}
