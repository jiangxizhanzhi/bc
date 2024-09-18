package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.integratation.service.bo.ScreenRecordByNode;
import com.cyberpeace.integratation.service.bo.ScreenRecordRsp;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSceneRecord;

import java.util.List;
import java.util.Map;

public interface ClassroomSceneRecordService extends IService<ClassroomSceneRecord> {

	void record(ClassroomGroupInfo groupInfo, String sceneId);

	Map<String, ScreenRecordRsp> getLastRecordMap(String classroomId, List<String> userIds);

	List<ScreenRecordByNode> listScreenRecord(String classroomId, String userId);
}
