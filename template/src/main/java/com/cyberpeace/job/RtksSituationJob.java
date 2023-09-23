package com.cyberpeace.job;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.dao.mapper.UserRoleMapper;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.bo.UserRoleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RtksSituationJob {

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RtksSituationService rtksSituationService;

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
				rtksSituationService.recordSituation(userId, userInfo.getName(),
						roleContext.getRoleId(), roleContext.getRoleName());
			} catch (Exception e) {
				log.error(String.format("rtks situation job failed with userId:[%s], roleId: [%s]",
						userId, roleContext.getRoleId()), e);
			}
		}
		UserInfoContext.removeUserContext();
		log.info("rtks situation job ends");
	}

}
