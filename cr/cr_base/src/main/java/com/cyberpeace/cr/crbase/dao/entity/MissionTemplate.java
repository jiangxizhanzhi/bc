package com.cyberpeace.cr.crbase.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

@Data
@TableName("cr_mission_template")
public class MissionTemplate extends MissionBase {

	private String templateId;

	private String attachment;

	public void setAttachment(List<String> images) {
		if (images == null || images.size() == 0) {
			this.attachment = CommonConstant.EMPTY_LIST_JSON;
		} else {
			this.attachment = JsonUtil.toJsonString(images);
		}
	}
}
