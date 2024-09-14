package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

/**
 * 课堂中用户最后的场景
 *
 * @author SN
 * @date 2024/09/14
 */
@Data
@TableName("oj_classroom_scene_record")
public class ClassroomSceneRecord {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String classroomId;

	private String userId;

	private String groupId;

	private String sceneIds;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	public void addSceneId(String sceneId) {
		List<String> sceneIds;
		if (StringUtils.isBlank(this.sceneIds)) {
			sceneIds = Lists.newArrayList();
		} else {
			sceneIds = JsonUtil.toJsonObjectArray(this.sceneIds, String.class);
		}
		sceneIds.add(sceneId);
		this.sceneIds = JsonUtil.arrayToString(sceneIds);
	}

	public List<String> getSceneIdList() {
		return JsonUtil.toJsonObjectArray(this.sceneIds, String.class);
	}

}
