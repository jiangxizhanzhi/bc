package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionCompletion;
import com.google.common.collect.Maps;

/**
 * @author yinqingrong
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private MissionTemplateMapper missionTemplateMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private MissionCategoryMapper missionCategoryMapper;

	private Map<String, MissionCategory> categoryCache;

	@PostConstruct
	public void init() {
		List<MissionCategory> categories = missionCategoryMapper.selectAll();

		categoryCache = Maps.newHashMap();

		for (MissionCategory item : categories) {
			categoryCache.put(item.getResourceId(), item);
		}
	}

	@Override
	public void addMission(Mission mission) {
		missionMapper.insert(mission);
	}

	@Override
	public void addMissionTemplate(MissionTemplate missionTemplate) {
		missionTemplateMapper.insert(missionTemplate);
	}

	@Override
	public void updateMissionTemplate(MissionTemplate missionTemplate) {
		missionTemplateMapper.updateById(missionTemplate);
	}

	@Override
	public Mission getById(String missionId) {
		return missionMapper.selectById(missionId);
	}

	@Override
	public List<Mission> batchGet(List<String> resourceIds) {
		return missionMapper.batchSelect(resourceIds);
	}

	@Override
	public List<Mission> batchGetByRoom(String roomId) {
		return missionMapper.batchSelectByRoom(roomId);
	}

	@Override
	public void updateShowWriteup(String missionId, Boolean showWriteup) {
		Mission mission = missionMapper.selectById(missionId);
		mission.setShowWriteup(showWriteup);
		missionMapper.updateById(mission);
	}

	@Override
	public void release(String missionId, String roleId, String currentUserId) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			throw new RuntimeException();
		}

		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			throw new RuntimeException();
		}

		Date releaseTime = new Date();

		List<CrTask> tasks = new ArrayList<>();
		for (String userId : JsonUtil.toJsonObjectArray(role.getUserIds(), String.class)) {
			CrTask task = new CrTask();
			task.setScore(mission.getScore());
			task.setMissionId(mission.getResourceId());
			task.setUserId(userId);
			task.setStatus(CrTask.TaskStatus.RELEASED.getCode());
			task.setReleaseTime(releaseTime);
			task.setRoomId(mission.getRoomId());
			task.setRoleId(role.getResourceId());
			task.setBusinessType(role.getBusinessType());

			tasks.add(task);
		}

		CrTaskLog taskLog = new CrTaskLog();
		taskLog.setRoomId(mission.getRoomId());
		taskLog.setMissionId(mission.getResourceId());
		taskLog.setActionCode(CrTaskLog.ActionCode.RELEASE.getCode());
		taskLog.setUserId(currentUserId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.batchInsert(tasks);
				crTaskLogMapper.insert(taskLog);
			}
		});
	}

	@Override
	public List<MissionCategory> listCategories() {
		return new ArrayList<>(categoryCache.values());
	}

	@Override
	public Map<String, MissionCompletion> getMissionStatusData(String roomId) {

		List<CrTask> tasks = crTaskMapper.selectByRoomId(roomId);
		Map<String, MissionCompletion> missionMap = Maps.newHashMap();
		for (CrTask task : tasks) {
			if (!missionMap.containsKey(task.getMissionId())) {
				missionMap.put(task.getMissionId(), new MissionCompletion(1, 0));
			}
		}

		for (CrTask task : tasks) {
			if (task.isFinished()) {
				missionMap.get(task.getResourceId()).addCompletionNum();
			}
		}
		return missionMap;
	}

	@Override
	public MissionCategory getCategoryById(String categoryId) {
		return categoryCache.get(categoryId);
	}

	@Override
	public Map<String, String> copyScheme(List<String> missionTemplateIds, String userId,
			String roomId) {
		List<MissionTemplate> missionTemplates = missionTemplateMapper
				.selectBatchIds(missionTemplateIds);

		List<Mission> missions = new ArrayList<>();
		Map<String, String> missionCopyMap = Maps.newHashMap();

		for (MissionTemplate missionTemplate : missionTemplates) {
			Mission mission = BeanUtils.copy(missionTemplate, Mission.class);
			mission.setShowWriteup(false);
			mission.setCreateUserId(userId);
			mission.setRoomId(roomId);
			mission.setResourceId(IDGeneratorUtil.getID());

			missions.add(mission);
			missionCopyMap.put(missionTemplate.getResourceId(), mission.getResourceId());
		}

		missionMapper.batchInsert(missions);
		return missionCopyMap;
	}

	@Override
	public void openOrCloseWriteup(String missionId, Boolean showWriteup) {
		Mission mission = missionMapper.selectById(missionId);
		mission.setShowWriteup(showWriteup);
		missionMapper.updateById(mission);
	}
}
