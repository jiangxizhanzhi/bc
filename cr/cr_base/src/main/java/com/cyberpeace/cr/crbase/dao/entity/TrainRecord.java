package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 训练记录，记录训练房间的创建和销毁
 */
@Data
@TableName("cr_train_record")
public class TrainRecord implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer businessType;

	private String businessTemplateId;

	private String userId;

	private Date startTime;

	private Date endTime;

	private String roomId;

	private String roomName;

	private String extra;

	private String trainee;

}
