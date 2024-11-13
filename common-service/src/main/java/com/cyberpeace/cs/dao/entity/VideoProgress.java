package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 视频进度
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("video_progress")
public class VideoProgress extends BaseTable {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 视频路径
	 */
	private String path;

	/**
	 * 业务id
	 */
	private String businessId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 检查点观看情况
	 */
	private String watchedPoint;

	/**
	 * 最后观看的检查点的时间
	 */
	private Integer time;

}
