package com.cyberpeace.oj.person.service.impl;

import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cyberpeace.auth.service.UserDataClearanceService;
import com.cyberpeace.oj.person.dao.mapper.UserDataClearanceMapper;
import com.cyberpeace.scene.service.SceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDataClearanceServiceImpl implements UserDataClearanceService {

	@Autowired
	private SceneService sceneService;

	@Autowired
	@Qualifier("asyncTaskExecutor")
	private Executor executor;

	@Autowired
	private UserDataClearanceMapper userDataClearanceMapper;

	@Override
	public void clearUserData(String userId) {
		executor.execute(() -> {
			// clear record
			clearOjRecord(userId);
			// clear scene
			clearScene(userId);
		});
	}

	private void clearOjRecord(String userId) {
		userDataClearanceMapper.clearCourseStudyHis(userId);
		userDataClearanceMapper.clearLessonRecord(userId);
		userDataClearanceMapper.clearOjNote(userId);
		userDataClearanceMapper.clearPracticeCount(userId);
		userDataClearanceMapper.clearPracticeMistake(userId);
		userDataClearanceMapper.clearPracticeSolved(userId);
		userDataClearanceMapper.clearResourceCollection(userId);
	}

	private void clearScene(String userId) {
		List<String> sceneIdList = sceneService.getUndeletedSceneIdByUserId(userId);
		for (String sceneId : sceneIdList) {
			try {
				sceneService.destroyScene(sceneId);
			} catch (Exception e) {
				log.error("failed to delete scene: [{}]", sceneId, e);
			}
		}
	}

}
