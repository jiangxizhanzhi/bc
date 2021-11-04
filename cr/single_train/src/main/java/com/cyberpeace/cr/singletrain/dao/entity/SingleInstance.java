package com.cyberpeace.cr.singletrain.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_single_instance")
public class SingleInstance extends BaseTable {

	private String singleTemplateId;

	private Date startTime;

	private Date endTime;

	private String trainingRecord;

	private String localSceneId;

	private String userId;

	private String copyedTopoId;
}
