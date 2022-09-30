package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("oj_comment_like")
public class CommentLike extends BaseTable {
    
	@TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String userId;

    private String commentId;

}
