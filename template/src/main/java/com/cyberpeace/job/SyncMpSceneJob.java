package com.cyberpeace.job;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.SceneStatusCompute;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 同步mp场景作业
 *
 * @author admin
 * @date 2024/11/12
 */
@Slf4j
@Component
public class SyncMpSceneJob {

	/**
	 * 远程场景服务
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 局部场景映射器
	 */
	@Autowired
	private LocalSceneMapper localSceneMapper;

	/**
	 * oj本地场景服务
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * oj本地场景文件夹
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 课堂现场服务
	 */
	@Autowired
	private ClassroomSceneService classroomSceneService;

	/**
	 * 教室组信息映射器
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * 同步mp场景关闭
	 */
	@Scheduled(initialDelay = 1000, fixedRate = 300000)
	public void syncMpSceneClose() {
		log.info("syncMpSceneClose start");

		List<LocalScene> localScenes = localSceneMapper.selectAll();
		for (LocalScene localScene : localScenes) {
			log.info("exec {} local_scene sync mp scene close status, local_scene_id=[{}]",
					localScene.getSceneName(), localScene.getResourceId());

			try {
				syncMpSceneClose(localScene);
			} catch (Exception e) {
				log.error("exec sync mp scene close failed! local_scene_id=[{}], catch error:",
						localScene.getResourceId(), e);
			}
		}

		log.info("syncMpSceneClose end");
	}

	/**
	 * 同步mp场景关闭
	 *
	 * @param localScene 本地场景
	 */
	private void syncMpSceneClose(LocalScene localScene) {
		if (StringUtils.isBlank(localScene.getMpSceneId())) {
			log.info("mp_scene_id is null, continue....");
			return;
		}

		try {
			JSONObject scene = remoteSceneService.getById(localScene.getMpSceneId(),
					localScene.getUserId());

			LocalScene.SceneStatusEnum statusEnum = SceneStatusCompute.compute(scene);
			if (!statusEnum.isClose()) {
				return;
			}
		} catch (RemoteServiceException e) {
			log.error("exec sync mp scene close failed! scene_id=[{}] catch error:",
					localScene.getMpSceneId(), e);

			// 如果非场景不存在问题 则抛出异常
			if (!(e.getMessage().equals(ErrorCode.SCENE_NOT_EXIST_ZH)
					|| e.getMessage().equals(ErrorCode.SCENE_NOT_EXIST_EN))) {
				throw e;
			}
		}

		if (isOj(localScene.getBusinessCode())) {
			ojLocalSceneService.deleteLocalScene(localScene.getBusinessInstanceId());

		} else if (isClassroom(localScene.getBusinessCode())) {
			deleteClassScene(localScene);
		}
	}

	/**
	 * 是oj
	 *
	 * @param businessCode 商业代码
	 * @return {@link Boolean }
	 */
	private Boolean isOj(int businessCode) {
		return businessCode == LocalScene.BusinessCode.LESSON.getCode().intValue()
				|| businessCode == LocalScene.BusinessCode.PRACTICE.getCode().intValue()
				|| businessCode == LocalScene.BusinessCode.LESSON_PRACTICE.getCode().intValue();
	}

	/**
	 * 是课堂
	 *
	 * @param businessCode 业务code码
	 * @return {@link Boolean }
	 */
	private Boolean isClassroom(int businessCode) {
		return businessCode == LocalScene.BusinessCode.CLASSROOM.getCode().intValue();
	}

	/**
	 * 删除课堂场景
	 *
	 * @param localScene 本地场景
	 */
	private void deleteClassScene(LocalScene localScene) {
		OjLocalScene ojLocalScene = ojLocalSceneMapper
				.selectById(localScene.getBusinessInstanceId());
		if (null == ojLocalScene) {
			return;
		}

		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());
		if (null == groupInfo) {
			return;
		}

		classroomSceneService.deleteLocalScene(localScene, groupInfo);
	}
}
