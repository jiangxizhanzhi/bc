package com.cyberpeace.oj.course.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.bo.ScreenRecordRsp;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSceneRecord;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSceneRecordMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneRecordService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassroomSceneRecordServiceImpl extends ServiceImpl<ClassroomSceneRecordMapper, ClassroomSceneRecord> implements ClassroomSceneRecordService {

	@Autowired
	private ClassroomSceneRecordMapper mapper;

	@Autowired
	private RemoteSceneService sceneService;

	@Override
	public void record(ClassroomGroupInfo groupInfo, String sceneId) {
		List<String> userIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
				String.class);
		for (String userId : userIds) {
			ClassroomSceneRecord exist = mapper.selectByRoomAndUser(groupInfo.getClassroomId(),
					userId);
			if (null != exist) {
				exist.addSceneId(sceneId);
				this.updateById(exist);
			} else {
				ClassroomSceneRecord scene = new ClassroomSceneRecord();
				scene.setClassroomId(groupInfo.getClassroomId());
				scene.setUserId(userId);
				scene.setGroupId(groupInfo.getResourceId());
				scene.addSceneId(sceneId);
				this.save(scene);
			}
		}
	}

	@Override
	public Map<String, ScreenRecordRsp> getLastRecordMap(String classroomId, List<String> userIds) {
		Map<String, ScreenRecordRsp> res = Maps.newHashMap();
		if (userIds.isEmpty()) {
			return res;
		}
		List<ClassroomSceneRecord> recordList = mapper.selectByRoomAndUsers(classroomId, userIds);
		for (ClassroomSceneRecord record : recordList) {
			List<String> sceneIds = record.getSceneIdList();
			Collections.reverse(sceneIds);
			for (String sceneId : sceneIds) {
				List<ScreenRecordRsp> rspList = sceneService.pageSceneRecord(sceneId,
						record.getUserId());
				if (!rspList.isEmpty()) {
					res.put(record.getUserId(), rspList.get(0));
					break;
				}
			}
		}
		return res;
	}
}
