package com.cyberpeace.oj.course.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时知识检查点用户提交答案
 * @author SN
 * @date 2023/09/08
 */
@Data
@TableName("oj_check_point_answer")
public class LessonCheckPointAnswer implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String lessonId;

	private String userId;

	private String answer;

	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
