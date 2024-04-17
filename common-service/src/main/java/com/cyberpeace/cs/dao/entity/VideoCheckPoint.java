package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

@Data
@TableName("video_check_point")
public class VideoCheckPoint {

	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 视频文件路径
	 */
	private String path;

	/**
	 * 视频时长
	 */
	private Integer duration;

	/**
	 * 检查点分布
	 * json
	 */
	private String checkPoint;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
}
