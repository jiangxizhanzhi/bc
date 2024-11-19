package com.cyberpeace.job;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.cs.service.impl.SceneStatusCompute;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 同步中台场景工作
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class SyncMpSceneJob {

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 当地场景Mapper
	 *
	 * @see LocalSceneMapper
	 */
	@Autowired
	private LocalSceneMapper localSceneMapper;

	/**
	 * OJ当地场景Service
	 *
	 * @see OjLocalSceneService
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * OJ当地场景Mapper
	 *
	 * @see OjLocalSceneMapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 课堂场景Service
	 *
	 * @see ClassroomSceneService
	 */
	@Autowired
	private ClassroomSceneService classroomSceneService;

	/**
	 * 课堂组信息Mapper
	 *
	 * @see ClassroomGroupInfoMapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * 同步中台场景关闭
	 *
	 * @throws
	 */
	@Scheduled(initialDelay = 1000, fixedRate = 300000)
	public void syncMpSceneClose() {
		log.info("syncMpSceneClose start");
		// 只有课程、练习、课程表等类型场景存在自动关闭场景,需要清理数据
		List<Integer> businessCodes = Lists.newArrayList();
		businessCodes.add(BusinessCode.LESSON.getCode());
		businessCodes.add(BusinessCode.PRACTICE.getCode());
		businessCodes.add(BusinessCode.CLASSROOM.getCode());
		businessCodes.add(BusinessCode.LESSON_PRACTICE.getCode());

		List<LocalScene> localScenes = localSceneMapper.selectToDelete(businessCodes);
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
	 * 同步中台场景关闭
	 *
	 * @param localScene 当地场景
	 * @throws
	 * @see LocalScene
	 */
	private void syncMpSceneClose(LocalScene localScene) {
		if (StringUtils.isBlank(localScene.getMpSceneId())) {
			log.info("mp_scene_id is null, continue, local_scene id is [{}]", localScene.getResourceId());
			return;
		}

		try {
			JSONObject scene = remoteSceneService.getById(localScene.getMpSceneId(),
					localScene.getUserId());

			SceneStatusEnum statusEnum = SceneStatusCompute.compute(scene);
			if (Boolean.FALSE.equals(statusEnum.isClose())) {
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

		if (Boolean.TRUE.equals(isOj(localScene.getBusinessCode()))) {
			ojLocalSceneService.deleteLocalScene(localScene.getBusinessInstanceId());

		} else if (Boolean.TRUE.equals(isClassroom(localScene.getBusinessCode()))) {
			deleteClassScene(localScene);
		}else {
			String enumDesc = EnumUtils.getEnumDescByCode(localScene.getBusinessCode(), BusinessCode.class);
			log.warn("exec sync mp scene close failed! local_scene_id=[{}] business_code=[{}]:[{}], not support",
					localScene.getMpSceneId(), localScene.getBusinessCode(), enumDesc);
		}
	}

	/**
	 * 是否OJ
	 *
	 * @param businessCode 业务代号
	 * @return {@code Boolean }
	 * @throws
	 * @see int
	 * @see Boolean
	 */
	private Boolean isOj(int businessCode) {
		return businessCode == BusinessCode.LESSON.getCode().intValue()
				|| businessCode == BusinessCode.PRACTICE.getCode().intValue()
				|| businessCode == BusinessCode.LESSON_PRACTICE.getCode().intValue();
	}

	/**
	 * 是否课堂
	 *
	 * @param businessCode 业务代号
	 * @return {@code Boolean }
	 * @throws
	 * @see int
	 * @see Boolean
	 */
	private Boolean isClassroom(int businessCode) {
		return businessCode == BusinessCode.CLASSROOM.getCode().intValue();
	}

	/**
	 * 删除类场景
	 *
	 * @param localScene 当地场景
	 * @throws
	 * @see LocalScene
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
