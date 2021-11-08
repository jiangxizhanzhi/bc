package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("oj_comment_like")
public class CommentLike extends BaseTable {

    private String userId;

    private String commentId;

}
