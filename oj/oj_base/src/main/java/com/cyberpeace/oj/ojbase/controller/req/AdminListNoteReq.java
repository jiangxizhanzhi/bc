package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class AdminListNoteReq extends PageReqBase {

    @ApiModelProperty("姓名查询条件")
    private String userName;

    @ApiModelProperty("笔记类型 笔记=1 实验报告=2")
    @NotNull
    private Integer noteType;

    @ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习")
    private Integer businessType;

    @ApiModelProperty("资源编号 课时")
    private String businessId;

    public void setUser_name(String userName) {
        this.userName = userName;
    }

    public void setNote_type(Integer noteType) {
        this.noteType = noteType;
    }

    public void setBusiness_type(Integer businessType) {
        this.businessType = businessType;
    }

    public void setBusiness_id(String businessId) {
        this.businessId = businessId;
    }
}
