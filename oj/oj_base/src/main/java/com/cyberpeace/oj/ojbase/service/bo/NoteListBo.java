package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.Note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteListBo extends Note {

    @ApiModelProperty("用户名称")
    private String userName;

}
