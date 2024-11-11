package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.Note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 备注列表 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class NoteListBo extends Note {

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;

}
