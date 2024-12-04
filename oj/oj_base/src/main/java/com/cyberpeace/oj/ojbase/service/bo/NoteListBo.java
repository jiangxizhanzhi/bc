package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.Note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 备注列表 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Note
 * @since 1.0.0
 */
@Data
public class NoteListBo extends Note {

    /**
     * 用户名称
     *
     * @see String
     */
    @ApiModelProperty("用户名称")
    private String userName;

}
