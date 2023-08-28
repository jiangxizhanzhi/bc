package com.cyberpeace.cr.evaluation.dao.entity.rb;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cr_eval_rb_room")
public class CrEvalRbRoom extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private String templateName;

	private Integer trainingStatus;

	private String name;

	private String createUserId;

	private Integer subType;

	private String description;

	private String preSkill;

	private String trainSkill;

	private Integer difficulty;

	private Date startTime;

	private Date endTime;

	private String localSceneId;

	private String copyedTopoId;

	private Boolean enableRecording;

	private Boolean supportPartialStart;

	private String logo;


}
