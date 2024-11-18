package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 训练记录，记录训练房间的创建和销毁
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("cr_train_record")
public class TrainRecord implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 房间名称
	 *
	 * @see String
	 */
	private String roomName;

	/**
	 * 额外
	 *
	 * @see String
	 */
	private String extra;

}
