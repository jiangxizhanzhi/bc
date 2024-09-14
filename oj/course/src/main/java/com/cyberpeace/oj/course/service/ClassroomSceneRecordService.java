package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSceneRecord;

public interface ClassroomSceneRecordService extends IService<ClassroomSceneRecord> {

	void record(ClassroomGroupInfo groupInfo, String sceneId);
}
