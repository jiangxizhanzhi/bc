package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("video_progress")
public class VideoProgress extends BaseTable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 视频路径
	 */
	private String path;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 最后观看的检查点的时间
	 */
	private Integer time;

	/**
	 * 视频观看进度
	 */
	private Integer progress;

}
