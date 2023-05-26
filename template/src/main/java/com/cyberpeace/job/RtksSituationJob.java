package com.cyberpeace.job;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.dao.mapper.RtksRoleMatchRecordMapper;
import com.cyberpeace.rtks.dao.mapper.UserRoleMapper;
import com.cyberpeace.rtks.service.RtksDailyMatchLogService;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.bo.UserRoleContext;
import com.cyberpeace.rtks.service.bo.situation.RoleMatchRecordContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RtksSituationJob {

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RtksSituationService rtksSituationService;

	@Autowired
	private RtksRoleMatchRecordMapper rtksRoleMatchRecordMapper;

	@Autowired
	private RtksStatisticService rtksStatisticService;

	@Autowired
	private RtksDailyMatchLogService rtksDailyMatchLogService;

	@Scheduled(fixedRate = 1000 * 300)
	public void calculate() {
		log.info("start rtks situation job");
		List<UserRoleContext> userRoleList = userRoleMapper.selectAll();
		if (userRoleList.isEmpty()) {
			return;
		}
		List<String> userIds = userRoleList.stream().map(UserRoleContext::getUserId)
				.collect(Collectors.toList());
		Map<String, UserRoleContext> userRoleMap = StreamApiUtils.toMap(userRoleList,
				UserRoleContext::getUserId, context -> context);
		List<UserInfo> userInfoList = remoteUserService.getByIdList(userIds);
		// get existing user
		String userId;
		UserRoleContext roleContext;
		Integer match;
		for (UserInfo userInfo : userInfoList) {
			userId = userInfo.getUserId();
			roleContext = userRoleMap.get(userId);
			UserInfoContext.setUserContext(userInfo);
			try {
				// cal match & record
				match = rtksSituationService.recordSituation(userId, userInfo.getName(),
						roleContext.getRoleId(), roleContext.getRoleName());
			} catch (Exception e) {
				log.error(String.format("rtks situation job failed with userId:[%s], roleId: [%s]",
						userId, roleContext.getRoleId()), e);
			}
		}
		UserInfoContext.removeUserContext();
		log.info("rtks situation job ends");
	}

	/**
	 * 每天23时记录一次匹配度
	 * 所有人参与过的所有岗位
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void logDaily() {
		log.info("start rtks match daily log job");
		// 获取所有人曾经选择过的岗位
		List<RoleMatchRecordContext> recordList = rtksRoleMatchRecordMapper
				.selectAllByExistingRoles();
		List<String> userIds = recordList.stream().map(RoleMatchRecordContext::getUserId)
				.collect(Collectors.toList());
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
		for (RoleMatchRecordContext context : recordList) {
			try {
				String userId = context.getUserId();
				UserInfo userInfo = userInfoMap.get(userId);
				if (null == userInfo) {
					continue;
				}
				// 计算知识点的匹配度，需要设置UserContext
				UserInfoContext.setUserContext(userInfo);
				Integer match = rtksStatisticService.roleMatchPercent(context.getRoleId(),
						userId);
				rtksDailyMatchLogService.log(userId, context.getRoleId(), match);
			} catch (Exception e) {
				log.error(String.format(
						"rtks situation daily log job failed with userId:[%s], roleId: [%s]",
						context.getUserId(), context.getRoleId()), e);
			}
		}
		UserInfoContext.removeUserContext();
		log.info("rtks situation daily log job ends");
	}

}
