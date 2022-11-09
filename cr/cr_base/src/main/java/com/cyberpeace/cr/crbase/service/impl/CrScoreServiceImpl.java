package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrUserReport;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrScoreMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrUserReportMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.UserReport;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class CrScoreServiceImpl extends ServiceImpl<CrScoreMapper, CrScore> implements CrScoreService {

	@Autowired
	CrScoreMapper crScoreMapper;

	@Autowired
	MissionService missionService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	CrTaskService crTaskService;

	@Autowired
	CrUserReportMapper crUserReportMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public void addImportUser(List<UserReport> userReportList, String roomId) {
		List<CrUserReport> userList = crUserReportMapper.selectUserReportByRoomId(roomId);
		List<UserReport> users = userList.stream().map(crUserReport -> {
			UserReport userReport = BeanUtils.copy(crUserReport, UserReport.class);

			List<Integer> list = Lists.newArrayList();
			list.add(crUserReport.getCompletedCount());
			list.add(crUserReport.getMissionCount());
			userReport.setMissionCompleted(list);

			return userReport;
		}).collect(Collectors.toList());

		userReportList.addAll(users);
	}

	@Override
	public CrScore getByRoomIdAndUser(String roomId, String userId) {
		return crScoreMapper.getByRoomIdAndUser(roomId, userId);
	}

	@Override
	public MissionsStatistics getMissionsStatistics(String roomId, List<CrRole> crRoleList) {
		MissionsStatistics missionsStatistics = new MissionsStatistics();

		Integer missionCount = 0;
		for (CrRole crRole : crRoleList) {
			Integer roleMissionCount = crRoleService.getMissionCount(crRole);
			List<String> userList = crRole.getUserIdList();
			missionCount += roleMissionCount * userList.size();
		}
		List<String> crRoleIds = StreamApiUtils.map(crRoleList, CrRole::getResourceId);

		missionsStatistics.setMissionCount(missionCount);
		missionsStatistics.setUnpublishedProportion(
				missionCount - crTaskService.countByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setNotSubmitProportion(
				crTaskService.countUnsubmittedByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setNotFinishProportion(
				crTaskService.countUnfinishedByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setFinishProportion(
				crTaskService.countFinishedByRoomIdAndRoleIds(roomId, crRoleIds));
		return missionsStatistics;
	}

	@Override
	public List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public Map<String, Integer> getMissionsCategory(String roomId, List<CrRole> crRoleList) {
		Map<String, Integer> missionsCategory = Maps.newHashMap();

		List<Mission> missionList = missionService.batchGetByRoomAndCrRoles(roomId, crRoleList);

		List<MissionCategory> missionCategories = missionService.listCategories();

		Map<String, String> missionCategoryMap = missionCategories.stream()
				.collect(Collectors.toMap(MissionCategory::getResourceId, bo -> bo.getName()));

		for (Mission mission : missionList) {
			Integer count = missionsCategory
					.getOrDefault(missionCategoryMap.get(mission.getCategoryId()), 0) + 1;
			missionsCategory.put(missionCategoryMap.get(mission.getCategoryId()), count);
		}

		return missionsCategory;
	}

	@Override
	public void importTrainReport(String realPath, UserInfo userContext, String roomId) {

		// 获取excel数据
		ExcelDataHandler dataHandler = new ExcelDataHandler(realPath, CommonConstant.ZERO);

		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);

		dataHandler.closeIO();
		// 数 据类型转换
		List<CrUserReport> crUserReportList = StreamApiUtils.map(sheetData,
				rowData -> excelDataToCrUserReport(rowData,roomId));

		transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				// saveOrUpdate(practiceSet);
				return crUserReportMapper.batchInsert(crUserReportList);
			}
		});

	}

	private CrUserReport excelDataToCrUserReport(List<String> excelRowData, String roomId) {
		// 数据长度为零 返回空过滤掉
		if (StringUtils.isBlank(String.join("", excelRowData))) {
			return null;
		}


		CrUserReport crUserReport = new CrUserReport();
		crUserReport.setUserId("-");
		crUserReport.setRoomId(roomId);
		crUserReport.setUserName(excelRowData.get(0));

		if (StringUtils.isBlank(excelRowData.get(1))) {
			crUserReport.setRoleName("");
		}else{
			crUserReport.setRoleName(excelRowData.get(1));
		}

		try {
			crUserReport.setScore(new Double(excelRowData.get(2)).intValue());

		} catch (Exception e) {

			throw new BusinessException(CrBusinessErrorCode.SCORE_NOT_NUMBER);
		}

		try {

			crUserReport.setCompletedCount(new Double(excelRowData.get(3)).intValue());

		} catch (Exception e) {

			throw new BusinessException(CrBusinessErrorCode.COMPLETED_NOT_NUMBER);
		}

		try {

			crUserReport.setMissionCount(new Double(excelRowData.get(4)).intValue());
		} catch (Exception e) {

			throw new BusinessException(CrBusinessErrorCode.MISSION_COUNT_NOT_NUMBER);
		}
		if (excelRowData.size()==5||StringUtils.isBlank(excelRowData.get(5))) {
			crUserReport.setComment("");
		}else{
			crUserReport.setComment(excelRowData.get(5));
		}




		return crUserReport;
	}
}
