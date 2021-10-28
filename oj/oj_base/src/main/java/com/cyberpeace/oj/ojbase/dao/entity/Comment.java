package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("oj_comment")
public class Comment extends BaseTable {

	private String resourceId;

	private String replyId;

	private String businessId;

	private String themeName;

	private String userId;

	private String content;

	private Integer businessType;

	private String rootId;

}
