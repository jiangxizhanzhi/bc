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
 * @date 2024/11/07
 */
@Data
@TableName("cr_train_record")
public class TrainRecord implements Serializable {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 房间名称
	 */
	private String roomName;

	/**
	 * 额外
	 */
	private String extra;

}
