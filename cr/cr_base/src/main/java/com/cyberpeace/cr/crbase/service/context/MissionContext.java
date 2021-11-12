package com.cyberpeace.cr.crbase.service.context;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

@Data
@TableName("cr_mission")
public class MissionContext extends Mission {

	private Integer state;

	private TrainingRecord trainingRecord;

}
