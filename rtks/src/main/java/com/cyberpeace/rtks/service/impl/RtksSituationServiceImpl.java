package com.cyberpeace.rtks.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.rtks.controller.res.TalentPersonReleaseRes;
import com.cyberpeace.rtks.controller.res.TalentReleaseRes;
import com.cyberpeace.rtks.controller.res.TalentUserReleaseRes;
import com.cyberpeace.rtks.controller.res.TalentUserRoleInfoRes;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.common.model.cache.RtksMatch;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.constant.RtksCommonConstants;
import com.cyberpeace.rtks.dao.entity.SituationLog;
import com.cyberpeace.rtks.dao.mapper.RtksRoleMatchRecordMapper;
import com.cyberpeace.rtks.dao.mapper.RtksSituationMapper;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.bo.situation.*;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RtksSituationServiceImpl implements RtksSituationService {

	@Autowired
	private RtksSituationMapper rtksSituationMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private RtksStatisticService rtksStatisticService;

	@Autowired
	private RtksRoleMatchRecordMapper rtksRoleMatchRecordMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public RtksSituationRankings getRankings() {
		RtksSituationRankings result = new RtksSituationRankings();
		List<UserRankingBo> userRankingBoList = Lists.newArrayList();
		List<OrgRankingBo> orgRankingBoList = Lists.newArrayList();
		List<MultiRoleRankingBo> multiRoleRankingBoList;
		// get all records
		List<RoleMatchRecordContext> recordContextList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();
		if (recordContextList.isEmpty()) {
			return result;
		}
		// get users
		List<String> userIds = recordContextList.stream().map(RoleMatchRecordContext::getUserId)
				.distinct().collect(Collectors.toList());
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
		// user ranking
		String userId;
		for (RoleMatchRecordContext recordContext : recordContextList) {
			userId = recordContext.getUserId();
			if (userInfoMap.containsKey(userId)) {
				UserRankingBo userRankingBo = new UserRankingBo(recordContext);
				userRankingBo.setName(userInfoMap.get(userId).getName());
				userRankingBoList.add(userRankingBo);
			}
		}
		userRankingBoList.sort(Comparator.comparing(UserRankingBo::getMatch).reversed());
		// star of role
		int temp = 0;
		int sMatch;
		for (int i = 0; i < userRankingBoList.size(); i++) {
			sMatch = userRankingBoList.get(i).getMatch();
			if (i < 3) {
				if (sMatch < RtksCommonConstants.OUTSTANDING_MATCH) {
					break;
				} else {
					temp = sMatch;
					userRankingBoList.get(i).setStar(true);
				}
			} else {
				// 同分处理
				if (temp == sMatch) {
					userRankingBoList.get(i).setStar(true);
				} else {
					break;
				}
			}
		}
		result.setUserRanking(userRankingBoList);

		// org ranking
		List<RtksMatch> matches = Lists.newArrayList();
		for (RoleMatchRecordContext recordContext : recordContextList) {
			userId = recordContext.getUserId();
			if (userInfoMap.containsKey(userId)) {
				// get orgName
				RtksMatch rtksMatch = new RtksMatch(userInfoMap.get(userId));
				rtksMatch.setMatch(recordContext.getMatch());
				matches.add(rtksMatch);
			}
		}
		// group by org
		Map<String, List<RtksMatch>> mapByOrg = StreamApiUtils.groupBy(matches,
				RtksMatch::getOrgId);
		// make result
		for (Map.Entry<String, List<RtksMatch>> entry : mapByOrg.entrySet()) {
			OrgRankingBo orgRankingBo = new OrgRankingBo(entry.getValue().get(0));
			// count
			for (RtksMatch match : entry.getValue()) {
				if (match.getMatch() >= RtksCommonConstants.EXCELLENT_MATCH) {
					orgRankingBo.addExcellent();
					orgRankingBo.addMatchSum(match.getMatch());
					continue;
				}
				if (match.getMatch() >= RtksCommonConstants.QUALIFIED_MATCH) {
					orgRankingBo.addQualified();
				}
			}
			// set sum
			orgRankingBo.setSum(orgRankingBo.getExcellent() + orgRankingBo.getQualified());
			orgRankingBoList.add(orgRankingBo);
		}
		// sort org ranking
		orgRankingBoList.sort(new OrgComparator());
		result.setOrgRanking(orgRankingBoList);

		// multi-role ranking
		Map<String, MultiRoleRankingBo> userMultiMap = StreamApiUtils.toMap(userInfoMap.entrySet(),
				Map.Entry::getKey,
				entry -> new MultiRoleRankingBo(entry.getKey(), entry.getValue().getName()));
		MultiRoleRankingBo multiRoleRankingBo;
		for (RoleMatchRecordContext recordContext : recordContextList) {
			userId = recordContext.getUserId();
			if (userInfoMap.containsKey(userId)) {
				// get multi role ranking bo
				multiRoleRankingBo = userMultiMap.get(userId);
				if (recordContext.getMatch() >= RtksCommonConstants.EXCELLENT_MATCH) {
					multiRoleRankingBo.addExcellent();
					multiRoleRankingBo.addMatchSum(recordContext.getMatch());
					continue;
				}
				if (recordContext.getMatch() >= RtksCommonConstants.QUALIFIED_MATCH) {
					multiRoleRankingBo.addQualified();
				}
			}
		}
		// remove students with no qualified role
		List<String> mUserIds = new ArrayList<>(userMultiMap.keySet());
		mUserIds.forEach(uId -> {
			if (userMultiMap.get(uId).getExcellent() == 0
					&& userMultiMap.get(uId).getQualified() == 0) {
				userMultiMap.remove(uId);
			}
		});
		// sort
		multiRoleRankingBoList = new ArrayList<>(userMultiMap.values());
		multiRoleRankingBoList.sort(new MultiRoleComparator());
		result.setMultiRoleRanking(multiRoleRankingBoList);

		return result;
	}

	@Override
	public List<LevelStatisticBo> getSituation() {
		// get levels
		List<LevelRoleContext> levelList = rtksSituationMapper
				.getLevels(RtksCommonConstants.FRAME_MASTER_LEVEL_ID);
		if (levelList.isEmpty()) {
			return Lists.newArrayList();
		}
		// keep order
		LinkedHashMap<String, List<LevelRoleContext>> levelMap = Maps.newLinkedHashMap();
		for (LevelRoleContext levelRoleContext : levelList) {
			String levelId = levelRoleContext.getLevelId();
			if (!levelMap.containsKey(levelId)) {
				levelMap.put(levelId, Lists.newArrayList());
			}
			levelMap.get(levelId).add(levelRoleContext);
		}
		// get match records
		List<RoleMatchRecordContext> recordContextList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();

		// get existing users
		List<String> userIds = recordContextList.stream().map(RoleMatchRecordContext::getUserId)
				.distinct().collect(Collectors.toList());
		Map<String, String> idNameMap = userIds.isEmpty() ? Maps.newHashMap()
				: remoteUserService.getIdNameMap(userIds);
		// count
		Map<String, RoleContext> roleCountMap = Maps.newHashMap();
		String userId;
		String roleId;
		for (RoleMatchRecordContext recordContext : recordContextList) {
			userId = recordContext.getUserId();
			if (!idNameMap.containsKey(userId)) {
				continue;
			}
			roleId = recordContext.getRoleId();
			if (!roleCountMap.containsKey(roleId)) {
				roleCountMap.put(roleId, new RoleContext(roleId, recordContext.getRoleName()));
			}
			if (recordContext.getMatch() >= RtksCommonConstants.QUALIFIED_MATCH) {
				roleCountMap.get(roleId).addQualified();
			}
		}
		// make result
		List<LevelRoleContext> roleContexts;
		List<LevelStatisticBo> result = Lists.newArrayList();
		for (Map.Entry<String, List<LevelRoleContext>> entry : levelMap.entrySet()) {
			roleContexts = entry.getValue();
			LevelStatisticBo levelBo = new LevelStatisticBo(entry.getKey(),
					roleContexts.get(0).getLevelName());
			for (LevelRoleContext roleContext : roleContexts) {
				roleId = roleContext.getRoleId();
				if (roleId == null) {
					continue;
				}
				// 岗位不一定有record
				if (roleCountMap.containsKey(roleId)) {
					levelBo.getRoles().add(roleCountMap.get(roleId));
				} else {
					levelBo.getRoles().add(new RoleContext(roleId, roleContext.getRoleName()));
				}
			}
			result.add(levelBo);
		}
		return result;
	}

	@Override
	public Integer recordSituation(String userId, String userName, String roleId, String roleName) {
		// 计算匹配度
		Integer match = rtksStatisticService.roleMatchPercent(roleId, userId);
		try {
			// record match
			recordMatch(userId, roleId, match);
			// 记录动态
			logMoment(userId, userName, roleId, roleName, match);
		} catch (DuplicateKeyException e) {
			log.error("duplicationKeyException when inserting rtks situation data into db");
			e.printStackTrace();
		}
		return match;
	}

	@Override
	public SituationTrendBo getTrends() {
		SituationTrendBo result = new SituationTrendBo();
		// 确定曲线开始和结束时间
		Date startTime = rtksRoleMatchRecordMapper.getFirstRecordTime();
		if (startTime == null) {
			return new SituationTrendBo(2);
		}
		Date endTime = new Date();
		// get all qualification log
		List<SituationLog> logs = rtksSituationMapper
				.getAllLogByType(SituationLog.MatchType.QUALIFIED.getCode());
		Map<String, Integer> dateNumMap = Maps.newHashMap();
		String date6;
		// count by date
		for (SituationLog log : logs) {
			date6 = TimeUtil.format6(log.getCreateTime());
			if (!dateNumMap.containsKey(date6)) {
				dateNumMap.put(date6, 1);
			} else {
				dateNumMap.put(date6, dateNumMap.get(date6) + 1);
			}
		}
		// group by date
		int daysBetween = TimeUtil.getDaysBetween(TimeUtil.getStartOfDay(startTime),
				TimeUtil.getStartOfDay(endTime));
		Date date = startTime;
		Integer sum = 0;
		for (int i = 0; i < daysBetween; i++) {
			date6 = TimeUtil.format6(date);
			result.getXDates().add(date6);
			if (dateNumMap.containsKey(date6)) {
				sum += dateNumMap.get(date6);
			}
			result.getYNums().add(sum);
			date = TimeUtil.addDates(date, 1);
		}
		// 填充不足
		result.fill(2, startTime);
		return result;
	}

    @Override
    public List<TalentReleaseRes> getTalentReleaseRanking() {
		List<Role> roles = roleMapper.selectList(null);
		List<TalentReleaseRes> res = new ArrayList<>();
		// TODO 用户删除处理逻辑
		List<RoleMatchRecordContext> recordContextList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();
		Map<String, List<RoleMatchRecordContext>> matchGroup = StreamApiUtils.groupBy(recordContextList, RoleMatchRecordContext::getRoleId);

		roles.forEach(role -> {
			TalentReleaseRes talentReleaseRes = new TalentReleaseRes();
			talentReleaseRes.setAppName(role.getName());
			String roleId = role.getResourceId();
			if(matchGroup.containsKey(roleId)){
				// 设置匹配度等级初始值
				int excellent = 0;
				int qualified = 0;
				int other = 0;
				List<RoleMatchRecordContext> roleMatchRecordContexts = matchGroup.get(roleId);
				for(RoleMatchRecordContext tmp:roleMatchRecordContexts){
					Integer match = tmp.getMatch();
					if ( match >= RtksCommonConstants.EXCELLENT_MATCH) {
						excellent += 1;

					} else if (match>= RtksCommonConstants.QUALIFIED_MATCH) {
						qualified += 1;
					} else {
						other += 1;
					}
				}
				// 更新当前岗位得 各等级匹配度数量
				talentReleaseRes.setCount(excellent);
				talentReleaseRes.setCount1(qualified);
				talentReleaseRes.setCount2(other);
			}
			res.add(talentReleaseRes);
		});

		return res;
    }

	@Override
	public List<TalentPersonReleaseRes> getTalentPersonRanking() {
		List<TalentPersonReleaseRes> res = new ArrayList<>();
		List<RoleMatchRecordContext> recordContextList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();
		Map<String, List<RoleMatchRecordContext>> userGroup = StreamApiUtils.groupBy(recordContextList, RoleMatchRecordContext::getUserId);
		// get existing users
		List<String> userIds = recordContextList.stream().map(RoleMatchRecordContext::getUserId)
				.distinct().collect(Collectors.toList());
		// TODO 用户删除处理逻辑
		List<UserInfo> users = remoteUserService.getByIdList(userIds);
		Map<String, String> userMap = StreamApiUtils.toMap(users, UserInfo::getUserId, UserInfo::getName);
		for(Map.Entry<String, List<RoleMatchRecordContext>> entry : userGroup.entrySet()){
			TalentPersonReleaseRes personReleaseRes = new TalentPersonReleaseRes();
			String key = entry.getKey();
			String username = userMap.get(key);
			List<RoleMatchRecordContext> value = entry.getValue();
			int excellent = 0;
			int qualified = 0;
			int other = 0;
			for(RoleMatchRecordContext val:value){
				Integer match = val.getMatch();
				if ( match >= RtksCommonConstants.EXCELLENT_MATCH) {
					excellent += 1;

				} else if (match>= RtksCommonConstants.QUALIFIED_MATCH) {
					qualified += 1;
				} else {
					other += 1;
				}
			}
			if(null == username){
				continue;
			}
			personReleaseRes.setUserName(username);
			personReleaseRes.setExcellent(excellent);
			personReleaseRes.setQualified(qualified);
			personReleaseRes.setOther(other);
			res.add(personReleaseRes);
		}
		// 多个字段排序
		res.sort(Comparator.comparing(TalentPersonReleaseRes::getExcellent).reversed().thenComparing(TalentPersonReleaseRes::getQualified).reversed().thenComparing(TalentPersonReleaseRes::getOther).reversed());
		res.subList(0, 5);
		return res;
	}

	@Override
	public List<TalentPersonReleaseRes> getTalentOrgRanking() {
		List<TalentPersonReleaseRes> res = new ArrayList<>();
		List<RoleMatchRecordContext> recordContextList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();

		Map<String, UserInfo> allUsers = remoteUserService.allUsers();
		List<RtksMatch> matches = Lists.newArrayList();

		for (RoleMatchRecordContext recordContext : recordContextList) {
			String userId = recordContext.getUserId();
			if (allUsers.containsKey(userId)) {
				// get orgName
				RtksMatch rtksMatch = new RtksMatch(allUsers.get(userId));
				rtksMatch.setMatch(recordContext.getMatch());
				matches.add(rtksMatch);
			}
		}

		Map<String, List<RtksMatch>> mapByOrg = StreamApiUtils.groupBy(matches,
				RtksMatch::getOrgId);
		for (Map.Entry<String, List<RtksMatch>> entry : mapByOrg.entrySet()) {
			TalentPersonReleaseRes orgRankingBo = new TalentPersonReleaseRes();
			// count
			for (RtksMatch match : entry.getValue()) {
				if (match.getMatch() >= RtksCommonConstants.EXCELLENT_MATCH) {
					orgRankingBo.addExcellent();
				} else if (match.getMatch() >= RtksCommonConstants.QUALIFIED_MATCH) {
					orgRankingBo.addQualified();
				} else {
					orgRankingBo.addOther();
				}
			}
			res.add(orgRankingBo);
		}


		return res;
	}

	@Override
	public List<TalentUserReleaseRes> getTalentUserRanking() {
		List<TalentUserReleaseRes> res = new ArrayList<>();
		List<RoleMatchRecordContext> recordContextList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();
		Map<String, List<RoleMatchRecordContext>> userGroup = StreamApiUtils.groupBy(recordContextList, RoleMatchRecordContext::getUserId);
		// get existing users
		List<String> userIds = recordContextList.stream().map(RoleMatchRecordContext::getUserId)
				.distinct().collect(Collectors.toList());
		List<UserInfo> users = remoteUserService.getByIdList(userIds);
		Map<String, UserInfo> userMap = StreamApiUtils.toMap(users, UserInfo::getUserId, s->s);
		// TODO 排名添加
		for(Map.Entry<String, List<RoleMatchRecordContext>> entry : userGroup.entrySet()){
			TalentUserReleaseRes tmp = new TalentUserReleaseRes();
			String userId = entry.getKey();
			List<RoleMatchRecordContext> roles = entry.getValue();
			List<String> roleNames = StreamApiUtils.getColumn(roles, RoleMatchRecordContext::getRoleName);
			String roleNameStr = String.join(",", roleNames);
			UserInfo userInfo = userMap.get(userId);
			BeanUtils.copyProperties(userInfo, tmp);
			tmp.setRoleName(roleNameStr);
			res.add(tmp);
		}
//		res.sort(Comparator.comparing(TalentPersonReleaseRes::getExcellent).reversed().thenComparing(TalentPersonReleaseRes::getQualified).reversed().thenComparing(TalentPersonReleaseRes::getOther).reversed());

		return res;
	}

	@Override
	public List<TalentUserRoleInfoRes> getTalentUserRoleInfos() {


		return null;
	}

	@Override
	public List<NewsBo> getNews() {
		List<NewsBo> result = Lists.newArrayList();
		// get recent 100 logs
		List<SituationLog> logs = rtksSituationMapper.getMomentsDesc();
		if (logs.isEmpty()) {
			return result;
		}
		// get existing users
		List<String> userIds = logs.stream().map(SituationLog::getUserId).distinct()
				.collect(Collectors.toList());
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);
		String userId;
		for (SituationLog log : logs) {
			userId = log.getUserId();
			if (idNameMap.containsKey(userId)) {
				// use logged roleName
				NewsBo newsBo = new NewsBo(log);
				newsBo.setUserName(idNameMap.get(userId));
				result.add(newsBo);
			}
		}
		return result;
	}

	@Override
	public void recordMatch(String userId, String roleId, Integer match) {
		Boolean exist = rtksRoleMatchRecordMapper.existBy(userId, roleId);
		if (exist) {
			rtksRoleMatchRecordMapper.updateRecord(userId, roleId, match);
		} else {
			rtksRoleMatchRecordMapper.insertRecord(userId, roleId, match);
		}
	}

	private void logMoment(String userId, String userName, String roleId, String roleName,
			Integer match) {

		if (match < RtksCommonConstants.QUALIFIED_MATCH) {
			return;
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 避免重复记录
				Boolean qualificationLogged = rtksSituationMapper.countByUserIdAndRoleIdAndType(
						userId, roleId, SituationLog.MatchType.QUALIFIED.getCode());
				if (!qualificationLogged) {
					rtksSituationMapper.createLog(userId, userName, roleId, roleName,
							SituationLog.MatchType.QUALIFIED.getCode());
				}
				// if excellent
				if (match >= RtksCommonConstants.EXCELLENT_MATCH) {
					Boolean excellenceLogged = rtksSituationMapper.countByUserIdAndRoleIdAndType(
							userId, roleId, SituationLog.MatchType.EXCELLENT.getCode());
					if (!excellenceLogged) {
						rtksSituationMapper.createLog(userId, userName, roleId, roleName,
								SituationLog.MatchType.EXCELLENT.getCode());
					}
				}
			}
		});
	}
}

class OrgComparator implements Comparator<OrgRankingBo> {

	/**
	 * 先比较优秀岗位，再比较合格岗位，再比较优秀岗位的匹配度之和
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(OrgRankingBo o1, OrgRankingBo o2) {
		if (o1.getExcellent().intValue() != o2.getExcellent().intValue()) {
			return o2.getExcellent() - o1.getExcellent();
		}
		if (o1.getQualified().intValue() != o2.getQualified().intValue()) {
			return o2.getQualified() - o1.getQualified();
		}
		return o2.getExcellentMatchSum() - o1.getExcellentMatchSum();
	}
}

class MultiRoleComparator implements Comparator<MultiRoleRankingBo> {

	@Override
	public int compare(MultiRoleRankingBo o1, MultiRoleRankingBo o2) {
		if (o1.getExcellent().intValue() != o2.getExcellent().intValue()) {
			return o2.getExcellent() - o1.getExcellent();
		}
		if (o1.getQualified().intValue() != o2.getQualified().intValue()) {
			return o2.getQualified() - o1.getQualified();
		}
		return o2.getExcellentMatchSum() - o1.getExcellentMatchSum();
	}
}
