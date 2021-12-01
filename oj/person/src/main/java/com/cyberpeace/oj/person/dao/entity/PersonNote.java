package com.cyberpeace.oj.person.dao.entity;

import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonNote extends NoteAddReq {

    @ApiModelProperty("评论资源编号")
    private String resourceId;

    @ApiModelProperty("用户名称")
    private String userName;

    private String userId;

}
