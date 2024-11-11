package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 评论赞
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("oj_comment_like")
public class CommentLike extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评论id
     */
    private String commentId;

}
