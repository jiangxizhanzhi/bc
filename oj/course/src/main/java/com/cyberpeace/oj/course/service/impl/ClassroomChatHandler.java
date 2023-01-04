package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.model.ws.ChatContent;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ClassroomChatHandler extends ChatWebSocketHandler {

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private ClassroomMapper classroomMapper;

	@Override
	public Integer declareMessageType() {
		return CHAT_TYPE.CLASSROOM.getCode();
	}

	@Override
	public List<String> getGroup(String businessId, ChatContent chatContent) {
		String classroomId = chatContent.getTo().getId();
		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.error("Classroom information not found in group chat! classroom_id=[{}]", classroomId);
			return Lists.newArrayList();
		}
		
		List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
		if (CollectionUtils.isEmpty(groupInfos)) {
			return Lists.newArrayList();
		}

		List<String> userIds = StreamApiUtils.flatMap(groupInfos,
				groupInfo -> JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(), String.class));
		if (!userIds.contains(ResourceImportConstant.SUPER_ADMIN_HASH)) {
			userIds.add(ResourceImportConstant.SUPER_ADMIN_HASH);
		}
		if (!userIds.contains(classroom.getCreateUserId())) {
			userIds.add(classroom.getCreateUserId());
		}

		return userIds;
	}
}
